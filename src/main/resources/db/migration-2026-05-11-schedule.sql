-- =================================================================
-- 축제 일정 변경 적용 (2026-05-11)
-- 변경 내역
--   DAY1: 총장님 인사 시간 / 재학생 및 동아리 공연 시작 시간 / 연예인→아티스트
--   DAY2: QUIZ! 덕쏭달쏭 / 덕우존 입장 시작 / 운현가요제 / 연예인→아티스트
--   DAY3: 재학생 및 동아리 공연 / 총학생회 콘텐츠→프로그램 / 연예인→아티스트 /
--         불꽃놀이 안내·마무리 멘트 통합 / 불꽃놀이 시간
-- =================================================================
USE dsfest;

START TRANSACTION;

-- =================== DAY 1 (2026-05-13 수) ===================
UPDATE festival_schedules
SET start_time = '2026-05-13 17:40:00',
    end_time   = '2026-05-13 17:50:00',
    updated_at = NOW()
WHERE festival_day = 1
  AND title = '총장님 인사'
  AND start_time = '2026-05-13 18:00:00';

UPDATE festival_schedules
SET start_time = '2026-05-13 17:55:00',
    end_time   = '2026-05-13 20:00:00',
    updated_at = NOW()
WHERE festival_day = 1
  AND title = '재학생 및 동아리 공연'
  AND start_time = '2026-05-13 18:00:00';

UPDATE festival_schedules
SET title = '아티스트 공연',
    updated_at = NOW()
WHERE festival_day = 1
  AND title = '연예인 공연'
  AND start_time = '2026-05-13 20:30:00';

-- =================== DAY 2 (2026-05-14 목) ===================
UPDATE festival_schedules
SET start_time = '2026-05-14 11:00:00',
    end_time   = '2026-05-14 13:00:00',
    updated_at = NOW()
WHERE festival_day = 2
  AND title = 'QUIZ! 덕쏭달쏭'
  AND start_time = '2026-05-14 11:00:00'
  AND end_time   = '2026-05-14 11:30:00';

UPDATE festival_schedules
SET start_time = '2026-05-14 17:30:00',
    end_time   = '2026-05-14 18:00:00',
    updated_at = NOW()
WHERE festival_day = 2
  AND title = '덕우존 입장 시작'
  AND start_time = '2026-05-14 17:00:00';

UPDATE festival_schedules
SET start_time = '2026-05-14 18:00:00',
    end_time   = '2026-05-14 20:10:00',
    updated_at = NOW()
WHERE festival_day = 2
  AND title = '운현가요제'
  AND end_time = '2026-05-14 20:30:00';

UPDATE festival_schedules
SET title = '아티스트 공연',
    start_time = '2026-05-14 20:10:00',
    end_time   = '2026-05-14 21:00:00',
    updated_at = NOW()
WHERE festival_day = 2
  AND title = '연예인 공연'
  AND start_time = '2026-05-14 20:30:00';

-- =================== DAY 3 (2026-05-15 금) ===================
UPDATE festival_schedules
SET end_time = '2026-05-15 18:20:00',
    updated_at = NOW()
WHERE festival_day = 3
  AND title = '재학생 및 동아리 공연'
  AND start_time = '2026-05-15 16:00:00'
  AND end_time   = '2026-05-15 18:30:00';

UPDATE festival_schedules
SET title = '총학생회 프로그램',
    start_time = '2026-05-15 18:20:00',
    end_time   = '2026-05-15 19:20:00',
    updated_at = NOW()
WHERE festival_day = 3
  AND title = '총학생회 콘텐츠'
  AND start_time = '2026-05-15 18:30:00';

UPDATE festival_schedules
SET title = '아티스트 공연',
    start_time = '2026-05-15 19:20:00',
    end_time   = '2026-05-15 21:10:00',
    updated_at = NOW()
WHERE festival_day = 3
  AND title = '연예인 공연'
  AND start_time = '2026-05-15 18:30:00';

-- DAY3 피날레: 불꽃놀이 안내 + 마무리 멘트 → "안내 및 마무리 멘트" 1행으로 통합
DELETE FROM festival_schedules
WHERE festival_day = 3
  AND title IN ('불꽃놀이 안내', '마무리 멘트')
  AND start_time = '2026-05-15 21:00:00'
  AND end_time   = '2026-05-15 21:30:00';

INSERT INTO festival_schedules
  (title, description, festival_day, start_time, end_time, schedule_type, created_at, updated_at)
VALUES
  ('안내 및 마무리 멘트', NULL, 3, '2026-05-15 21:10:00', '2026-05-15 21:20:00', 'EVENT', NOW(), NOW());

-- 불꽃놀이: 21:00~21:30 → 21:20~21:23
UPDATE festival_schedules
SET start_time = '2026-05-15 21:20:00',
    end_time   = '2026-05-15 21:23:00',
    updated_at = NOW()
WHERE festival_day = 3
  AND title = '불꽃놀이'
  AND start_time = '2026-05-15 21:00:00'
  AND end_time   = '2026-05-15 21:30:00';

COMMIT;
