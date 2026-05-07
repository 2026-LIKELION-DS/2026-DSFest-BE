"""
구글 드라이브 폴더 (공개) HTML 페이지를 파싱해서 (file_id, filename) 목록 추출.

폴더: https://drive.google.com/drive/folders/<FOLDER_ID>
파일명 규칙: {boothNumber}-{order}. {anything}.{ext}
            예) "2-1. 청춘스토어.jpg" → boothNumber=2, order=1
"""

from __future__ import annotations

import html as htmllib
import re
import sys
import urllib.request
from pathlib import Path

FOLDER_ID = (
    "1TWzUDG_Z2PrP-l8pnWnxOTTLsp_THzagJR0RZ8lKVZsqpA6HebXQQOA57zHErU38f3bmMv4p"
)
# embeddedfolderview는 페이지네이션 없이 모든 파일을 한 번에 노출
FOLDER_URL = f"https://drive.google.com/embeddedfolderview?id={FOLDER_ID}#list"

OUT_PATH = Path(__file__).resolve().parent / "drive_files.tsv"


def fetch(url: str) -> str:
    req = urllib.request.Request(url, headers={"User-Agent": "Mozilla/5.0"})
    with urllib.request.urlopen(req, timeout=60) as resp:
        return resp.read().decode("utf-8", errors="replace")


def extract_pairs(text: str) -> list[tuple[str, str]]:
    """embeddedfolderview HTML에서 (file_id, filename) 추출.

    구조 예시:
      <div class="flip-entry" id="entry-{FILE_ID}" tabindex="0" role="link">
        ...
        <div class="flip-entry-title">{FILENAME}</div>
    """
    text = htmllib.unescape(text)
    # entry-<ID>로 시작하는 블록 안에서 flip-entry-title 의 텍스트를 캡처
    pattern = re.compile(
        r'id="entry-(1[A-Za-z0-9_-]{20,80})"[^>]*>.*?'
        r'<div class="flip-entry-title">([^<]+)</div>',
        re.DOTALL,
    )
    pairs: list[tuple[str, str]] = []
    for m in pattern.finditer(text):
        pairs.append((m.group(1), m.group(2).strip()))
    return pairs


def main() -> None:
    print(f"Fetching: {FOLDER_URL}")
    html = fetch(FOLDER_URL)
    print(f"HTML size: {len(html):,} chars")

    pairs = extract_pairs(html)
    print(f"Extracted {len(pairs)} (file_id, filename) pairs")

    # Save TSV
    with OUT_PATH.open("w", encoding="utf-8", newline="") as f:
        f.write("file_id\tfilename\n")
        for fid, fname in pairs:
            f.write(f"{fid}\t{fname}\n")

    print(f"Wrote {OUT_PATH}")

    # Show sample
    print("\nSample (first 20):")
    for fid, fname in pairs[:20]:
        print(f"  {fid}  {fname}")


if __name__ == "__main__":
    main()
