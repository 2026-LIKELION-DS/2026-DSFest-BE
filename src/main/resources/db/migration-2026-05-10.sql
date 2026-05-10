-- =================================================================
-- 운영 DB 델타 적용 (2026-05-10)
-- 1) 부스 1(09~22), 2(11~19), 14(수/목 15~22) 운영 시간 행 신설
--    + booths.operating_days_text 비우기 → 일반 시간 기반 처리로 통일
-- 2) 부스 21 description / 이미지 URL 갱신
-- 재실행 가능하도록 사전 DELETE 후 INSERT
-- =================================================================
USE dsfest;

START TRANSACTION;

-- 1-a) 비정규 운영 시간(부스 1/2/14) — 기존 행이 있으면 정리 후 재삽입
DELETE FROM booth_operating_days WHERE booth_id IN (1, 2, 14);

INSERT INTO booth_operating_days (booth_id, festival_day, start_time, end_time) VALUES
  (1, 1, '09:00:00', '16:00:00'),
  (1, 1, '16:00:00', '22:00:00'),
  (1, 2, '09:00:00', '16:00:00'),
  (1, 2, '16:00:00', '22:00:00'),
  (1, 3, '09:00:00', '16:00:00'),
  (1, 3, '16:00:00', '22:00:00'),
  (2, 1, '11:00:00', '16:00:00'),
  (2, 1, '16:00:00', '19:00:00'),
  (2, 2, '11:00:00', '16:00:00'),
  (2, 2, '16:00:00', '19:00:00'),
  (2, 3, '11:00:00', '16:00:00'),
  (2, 3, '16:00:00', '19:00:00'),
  (14, 1, '15:00:00', '16:00:00'),
  (14, 1, '16:00:00', '22:00:00'),
  (14, 2, '15:00:00', '16:00:00'),
  (14, 2, '16:00:00', '22:00:00');

-- 1-b) operating_days_text 비우기 → 일반 부스와 동일하게 시간 기반 처리
UPDATE booths
SET operating_days_text = NULL,
    updated_at = NOW()
WHERE id IN (1, 2, 14);

-- 2) 부스 21 description 갱신
UPDATE booths
SET description = '다가올 유행을 가현융이 먼저 선보입니다. ''네임택 키링'' 판매와 럭키드로우 이벤트를 함께 즐길 수 있는 일일 팝업 소품샵입니다.',
    updated_at = NOW()
WHERE id = 21;

-- 3) 부스 21 이미지 URL 교체
UPDATE booth_images
SET image_url = 'https://lh3.googleusercontent.com/d/14sBkBkFxwE33KxkEdkbr3Mb0USQCTaGn=w1200'
WHERE booth_id = 21 AND image_order = 1;

COMMIT;

-- 검증용 쿼리 (필요 시 수동 실행)
-- SELECT * FROM booth_operating_days WHERE booth_id IN (1, 2, 14) ORDER BY booth_id, festival_day, start_time;
-- SELECT id, name, operating_days_text FROM booths WHERE id IN (1, 2, 14);
-- SELECT id, name, description FROM booths WHERE id = 21;
-- SELECT * FROM booth_images WHERE booth_id = 21;
