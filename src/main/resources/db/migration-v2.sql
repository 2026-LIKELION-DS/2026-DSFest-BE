-- ================================================================
-- Migration v2: 최종 CSV 반영을 위한 스키마 변경
-- - booths: affiliation, collab_instagram_url, youtube_url, operating_days_text 컬럼 추가
-- - booth_categories: 멀티값 카테고리 신규 테이블
-- - booth_map_positions: 합동 부스 (다른 부스가 같은 위치 공유) 허용을 위해 UNIQUE 제약 제거
-- ================================================================

SET NAMES utf8mb4;
USE dsfest;

ALTER TABLE booths
  ADD COLUMN affiliation VARCHAR(100) NULL AFTER operating_subject,
  ADD COLUMN collab_instagram_url VARCHAR(500) NULL AFTER instagram_url,
  ADD COLUMN youtube_url VARCHAR(500) NULL AFTER collab_instagram_url,
  ADD COLUMN operating_days_text VARCHAR(255) NULL AFTER youtube_url;

CREATE TABLE booth_categories (
  booth_id BIGINT NOT NULL,
  category_name VARCHAR(50) NOT NULL,
  KEY idx_booth_categories_booth (booth_id),
  CONSTRAINT FK_booth_categories_booth FOREIGN KEY (booth_id) REFERENCES booths(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

ALTER TABLE booth_map_positions
  DROP INDEX uk_booth_map_day_type_pos;