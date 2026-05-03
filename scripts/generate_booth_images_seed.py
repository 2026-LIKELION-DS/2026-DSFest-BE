"""
drive_files.tsv → booth-images-seed.sql 생성기.

파일명 규칙: "{boothNumber}-{order}. ...{ext}" 또는 "{boothNumber}. ...{ext}"
URL 형식: https://lh3.googleusercontent.com/d/{ID}=w1200  (CDN, 안정적)

사전: extract_drive_files.py 실행해서 drive_files.tsv 생성되어 있어야 함.
"""

from __future__ import annotations

import re
from pathlib import Path

ROOT = Path(__file__).resolve().parents[1]
TSV_PATH = ROOT / "scripts" / "drive_files.tsv"
OUT_PATH = ROOT / "src" / "main" / "resources" / "db" / "booth-images-seed.sql"

# 운영진이 사진을 수정/재업로드 중인 부스 — INSERT 라인을 주석으로 출력.
# 작업 끝나면 set에서 제거 후 재실행.
SKIP_BOOTH_IDS: set[int] = {66}

# "{boothNumber}-{order}. " 또는 "{boothNumber}. " (뒤 공백 없는 케이스도)
FILENAME_RE = re.compile(r"^(\d+)(?:-(\d+))?\.\s*")
ALLOWED_EXTS = {"jpg", "jpeg", "png", "gif", "webp", "heic", "pdf"}


def url_for(file_id: str, ext: str) -> str:
    # 이미지: lh3 CDN (안정/빠름)
    # PDF: drive.google.com/thumbnail (lh3는 일부 PDF에서 렌더 실패)
    if ext == "pdf":
        return f"https://drive.google.com/thumbnail?id={file_id}&sz=w1200"
    return f"https://lh3.googleusercontent.com/d/{file_id}=w1200"


def main() -> None:
    parsed: list[tuple[int, int, str, str]] = []  # booth_id, filename_order, file_id, fname
    skipped: list[str] = []

    with TSV_PATH.open(encoding="utf-8") as f:
        next(f)  # header
        for line in f:
            line = line.rstrip("\n")
            if not line:
                continue
            file_id, fname = line.split("\t", 1)

            # 확장자 매칭: 점이 누락된 케이스(예: "...다jpg") 까지 처리
            ext_match = re.search(
                r"\.?(jpg|jpeg|png|gif|webp|heic|pdf)$", fname, re.IGNORECASE
            )
            if not ext_match:
                skipped.append(f"UNSUPPORTED-EXT: {fname}")
                continue
            ext = ext_match.group(1).lower()

            m = FILENAME_RE.match(fname)
            if not m:
                skipped.append(f"PARSE-FAIL: {fname}")
                continue

            booth_id = int(m.group(1))
            fname_order = int(m.group(2)) if m.group(2) else 1
            parsed.append((booth_id, fname_order, file_id, fname, ext))

    # 정렬: booth, 파일명 내 순서, 파일ID (중복 파일명 안정 정렬)
    parsed.sort(key=lambda r: (r[0], r[1], r[2]))

    # 부스별 image_order 1..N 순차 부여 (중복 파일명 있어도 모두 다른 order로)
    rows: list[tuple[int, int, str, str, str]] = []
    counter: dict[int, int] = {}
    for booth_id, _, fid, fname, ext in parsed:
        counter[booth_id] = counter.get(booth_id, 0) + 1
        rows.append((booth_id, counter[booth_id], fid, fname, ext))

    out: list[str] = []
    out.append("-- =================================================================")
    out.append("-- 부스 이미지 시드 (구글 드라이브 링크)")
    out.append("-- 출처: drive_files.tsv (extract_drive_files.py로 생성)")
    out.append("-- URL 형식: https://lh3.googleusercontent.com/d/{FILE_ID}=w1200")
    out.append("-- =================================================================")
    out.append("")
    out.append("SET NAMES utf8mb4;")
    out.append("USE dsfest;")
    out.append("")
    out.append("DELETE FROM booth_images;")
    out.append("ALTER TABLE booth_images AUTO_INCREMENT = 1;")
    out.append("")
    out.append("INSERT INTO booth_images (booth_id, image_order, image_url) VALUES")

    # 마지막 활성 row 인덱스를 찾아서 거기에만 ; 붙임 (skip 처리된 마지막 row 회피)
    active_indices = [i for i, r in enumerate(rows) if r[0] not in SKIP_BOOTH_IDS]
    last_active = active_indices[-1] if active_indices else -1

    for i, (booth_id, order, fid, fname, ext) in enumerate(rows):
        url = url_for(fid, ext).replace("'", "''")
        comment = fname.replace("--", "- -")
        if booth_id in SKIP_BOOTH_IDS:
            out.append(
                f"  -- TODO: 운영진 작업 대기 (skip list) — 끝나면 SKIP_BOOTH_IDS에서 제거 후 재실행"
            )
            out.append(
                f"  -- ({booth_id}, {order}, '{url}'),  -- {comment}"
            )
            continue
        sep = "," if i < last_active else ";"
        out.append(f"  ({booth_id}, {order}, '{url}'){sep}  -- {comment}")
    out.append("")

    OUT_PATH.write_text("\n".join(out), encoding="utf-8")

    print(f"Generated: {OUT_PATH}")
    print(f"Inserted rows: {len(rows)}")
    booths_covered = sorted({r[0] for r in rows})
    print(f"Booths covered ({len(booths_covered)}): {booths_covered}")
    if skipped:
        print(f"\nSkipped ({len(skipped)}):")
        for s in skipped:
            print(f"  - {s}")


if __name__ == "__main__":
    main()
