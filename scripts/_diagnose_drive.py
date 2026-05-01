import re
from pathlib import Path

html = Path(r"C:\temp\drive_full.html").read_text(encoding="utf-8", errors="replace")

# Find all filename-shaped tokens with extension
pat = re.compile(r"(\d{1,3})(?:-\d+)?\.\s*[^\"\\<>]{1,80}?\.(?:jpg|jpeg|png|gif|webp|heic|pdf)", re.IGNORECASE)
found = set()
samples = {}
for m in pat.finditer(html):
    num = int(m.group(1))
    found.add(num)
    samples.setdefault(num, m.group(0))

print("Booth numbers detected:")
print(sorted(found))
print(f"Total: {len(found)}\n")

# Check specific high numbers
for n in [44, 45, 46, 47, 48, 50, 55, 60, 65, 70, 75, 76]:
    sample = samples.get(n, "(none)")
    print(f"  #{n:>2}: {sample!r}")

# Total file count hint - look for "files" or count distinct mime appearances
mime_count = len(re.findall(r'"image/(?:jpeg|png|gif|webp)"', html))
pdf_count = len(re.findall(r'"application/pdf"', html))
print(f"\nMime appearances: image={mime_count}, pdf={pdf_count}")
