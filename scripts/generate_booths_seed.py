"""
멋사 전달(부스, 푸드트럭) - 부스 정보 취합(최종).csv → booths-seed-final.sql 변환기.

사전 적용 필요: src/main/resources/db/migration-v2.sql

매핑:
  CSV 부스 운영 주체 → booths.affiliation       (예: 창업동아리/소모임)
  CSV 부스 운영 단체 → booths.operating_subject (예: 물꼬)
  CSV 부스명         → booths.name              (예: 꼬물꼬물 반짝이는 모험)
  CSV 부스 카테고리  → booth_categories(booth_id, category_name)  -- 콤마 split
  CSV 부스 운영 일차 → 정규: booth_operating_days, 비정규: booths.operating_days_text
  CSV 6개 위치번호   → booth_map_positions
  나머지 URL/설명    → booths 컬럼 직접
"""

from __future__ import annotations

import re
from pathlib import Path

import pandas as pd

CSV_PATH = Path(
    r"C:\Users\YSB\OneDrive\Desktop\멋사 전달(부스, 푸드트럭) - 부스 정보 취합(최종).csv"
)
OUT_PATH = Path(__file__).resolve().parents[1] / "src/main/resources/db/booths-seed-final.sql"

DAY_TIME = ("11:00:00", "14:30:00")
NIGHT_TIME = ("16:00:00", "19:30:00")

POSITION_COLS = [
    (1, "DAY", 5),
    (1, "NIGHT", 6),
    (2, "DAY", 7),
    (2, "NIGHT", 8),
    (3, "DAY", 9),
    (3, "NIGHT", 10),
]

OPERATING_DAY_PATTERN = re.compile(r"(\d+)\s*일\s*(낮|밤)")


def sql_str(value):
    if value is None:
        return "NULL"
    s = str(value).strip()
    if s == "" or s.lower() == "nan":
        return "NULL"
    s = s.replace("\\", "\\\\").replace("'", "''")
    return f"'{s}'"


def parse_operating_days(text: str):
    """정규 표현은 booth_operating_days 행 리스트, 비정규는 override_text 반환."""
    if not text:
        return [], None
    text = text.strip()
    matches = OPERATING_DAY_PATTERN.findall(text)
    if matches:
        days = []
        for day_str, slot in matches:
            festival_day = int(day_str) - 12  # 13->1, 14->2, 15->3
            if festival_day < 1:
                continue
            if slot == "낮":
                days.append((festival_day, *DAY_TIME))
            else:
                days.append((festival_day, *NIGHT_TIME))
        return days, None
    return [], text


def parse_categories(text: str) -> list[str]:
    if not text:
        return []
    return [p.strip() for p in text.split(",") if p.strip()]


def parse_positions(value: str) -> list[int]:
    if not value:
        return []
    parts = [p.strip() for p in str(value).split(",")]
    out = []
    for p in parts:
        if p.isdigit():
            out.append(int(p))
    return out


def main() -> None:
    df = pd.read_csv(CSV_PATH, header=1, dtype=str, keep_default_na=False).fillna("")

    rows = []
    for _, row in df.iterrows():
        cells = [str(c).strip() for c in row.tolist()]
        if not cells or not cells[0].isdigit():
            continue
        booth_no = int(cells[0])

        affiliation = cells[1]
        operating_subject = cells[2]
        name = cells[3]
        operating_days_raw = cells[4]
        category_raw = cells[11] if len(cells) > 11 else ""
        description = cells[12] if len(cells) > 12 else ""
        everytime_url = cells[13] if len(cells) > 13 else ""
        instagram_url = cells[14] if len(cells) > 14 else ""
        collab_instagram_url = cells[15] if len(cells) > 15 else ""
        youtube_url = cells[16] if len(cells) > 16 else ""
        open_kakao_url = cells[17] if len(cells) > 17 else ""

        structured_days, override_text = parse_operating_days(operating_days_raw)
        categories = parse_categories(category_raw)

        positions = []
        booth_types = set()
        for festival_day, day_night, col_idx in POSITION_COLS:
            cell = cells[col_idx] if len(cells) > col_idx else ""
            for pos_num in parse_positions(cell):
                positions.append((festival_day, day_night, pos_num))
                booth_types.add(day_night)

        rows.append(
            {
                "id": booth_no,
                "name": name,
                "affiliation": affiliation,
                "operating_subject": operating_subject,
                "description": description,
                "everytime_url": everytime_url,
                "instagram_url": instagram_url,
                "collab_instagram_url": collab_instagram_url,
                "youtube_url": youtube_url,
                "open_kakao_url": open_kakao_url,
                "operating_days_text": override_text,
                "structured_days": structured_days,
                "categories": categories,
                "positions": positions,
                "booth_types": booth_types,
            }
        )

    rows.sort(key=lambda r: r["id"])

    out: list[str] = []
    out.append("-- =================================================================")
    out.append("-- 부스 데이터 시드 (최종)")
    out.append("-- 출처: 멋사 전달(부스, 푸드트럭) - 부스 정보 취합(최종).csv")
    out.append("-- 사전 조건: migration-v2.sql 적용")
    out.append("-- festivalDay: 13일=1, 14일=2, 15일=3")
    out.append("-- 낮 11:00~14:30 / 밤 16:00~19:30")
    out.append("-- =================================================================")
    out.append("")
    out.append("SET NAMES utf8mb4;")
    out.append("USE dsfest;")
    out.append("")
    out.append("-- 기존 데이터 초기화")
    out.append("DELETE FROM booth_categories;")
    out.append("DELETE FROM booth_operating_days;")
    out.append("DELETE FROM booth_map_positions;")
    out.append("DELETE FROM booth_tags;")
    out.append("DELETE FROM booth_images;")
    out.append("DELETE FROM booth_types;")
    out.append("DELETE FROM booths;")
    out.append("ALTER TABLE booths AUTO_INCREMENT = 1;")
    out.append("ALTER TABLE booth_operating_days AUTO_INCREMENT = 1;")
    out.append("ALTER TABLE booth_map_positions AUTO_INCREMENT = 1;")
    out.append("")

    # booths
    out.append("-- =================================================================")
    out.append("-- booths")
    out.append("-- =================================================================")
    out.append("INSERT INTO booths")
    out.append(
        "  (id, booth_number, name, operating_subject, affiliation, description,"
    )
    out.append(
        "   open_kakao_url, everytime_url, instagram_url, collab_instagram_url,"
    )
    out.append(
        "   youtube_url, operating_days_text, created_at, updated_at)"
    )
    out.append("VALUES")
    booth_lines = []
    for r in rows:
        booth_lines.append(
            f"  ({r['id']}, {r['id']}, {sql_str(r['name'])}, "
            f"{sql_str(r['operating_subject'])}, {sql_str(r['affiliation'])}, "
            f"{sql_str(r['description'])}, {sql_str(r['open_kakao_url'])}, "
            f"{sql_str(r['everytime_url'])}, {sql_str(r['instagram_url'])}, "
            f"{sql_str(r['collab_instagram_url'])}, {sql_str(r['youtube_url'])}, "
            f"{sql_str(r['operating_days_text'])}, NOW(), NOW())"
        )
    out.append(",\n".join(booth_lines) + ";")
    out.append("")

    # booth_types
    out.append("-- booth_types")
    type_lines = []
    for r in rows:
        for bt in sorted(r["booth_types"]):
            type_lines.append(f"  ({r['id']}, '{bt}')")
    if type_lines:
        out.append("INSERT INTO booth_types (booth_id, booth_type) VALUES")
        out.append(",\n".join(type_lines) + ";")
    out.append("")

    # booth_categories
    out.append("-- booth_categories")
    cat_lines = []
    for r in rows:
        for cat in r["categories"]:
            cat_lines.append(f"  ({r['id']}, {sql_str(cat)})")
    if cat_lines:
        out.append("INSERT INTO booth_categories (booth_id, category_name) VALUES")
        out.append(",\n".join(cat_lines) + ";")
    out.append("")

    # booth_operating_days (정규 케이스만)
    out.append("-- booth_operating_days (비정규 케이스는 booths.operating_days_text 사용)")
    od_lines = []
    for r in rows:
        for fd, st, et in r["structured_days"]:
            od_lines.append(f"  ({r['id']}, {fd}, '{st}', '{et}')")
    if od_lines:
        out.append(
            "INSERT INTO booth_operating_days (booth_id, festival_day, start_time, end_time) VALUES"
        )
        out.append(",\n".join(od_lines) + ";")
    out.append("")

    # booth_map_positions
    out.append("-- booth_map_positions")
    pos_lines = []
    for r in rows:
        for fd, dn, pos in r["positions"]:
            pos_lines.append(f"  ({r['id']}, {fd}, '{dn}', {pos}, NOW(), NOW())")
    if pos_lines:
        out.append(
            "INSERT INTO booth_map_positions (booth_id, festival_day, day_night_type, position_number, created_at, updated_at) VALUES"
        )
        out.append(",\n".join(pos_lines) + ";")
    out.append("")

    OUT_PATH.write_text("\n".join(out), encoding="utf-8")

    print(f"Generated: {OUT_PATH}")
    print(f"Booth count: {len(rows)}")
    nonstd = [r for r in rows if r["operating_days_text"]]
    print(f"Non-standard operating-days (operating_days_text): {len(nonstd)}")
    for r in nonstd:
        print(f"  #{r['id']:>3} {r['name']:<30} → {r['operating_days_text']!r}")


if __name__ == "__main__":
    main()
