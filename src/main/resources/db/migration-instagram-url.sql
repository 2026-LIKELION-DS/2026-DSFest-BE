-- =================================================================
-- booths.instagram_url 정규화: @아이디 형태 -> https://www.instagram.com/{id}/
-- 대상: instagram_url 컬럼이 @아이디로만 저장된 행
-- id=65(초음): 멀티라인으로 @id + 풀 URL이 같이 저장된 케이스 → URL만 남김
-- 사전 조건: migration-v2.sql 적용 (instagram_url, collab_instagram_url 컬럼 존재)
-- =================================================================

SET NAMES utf8mb4;
USE dsfest;

UPDATE booths SET instagram_url = 'https://www.instagram.com/journey_mode_duksung/' WHERE id IN (1, 2, 3, 4);
UPDATE booths SET instagram_url = 'https://www.instagram.com/dswu_yellowblock/' WHERE id = 14;
UPDATE booths SET instagram_url = 'https://www.instagram.com/duksung_vr/' WHERE id = 21;
UPDATE booths SET instagram_url = 'https://www.instagram.com/floral_shoes/' WHERE id = 25;
UPDATE booths SET instagram_url = 'https://www.instagram.com/duknyangdang/' WHERE id = 26;
UPDATE booths SET instagram_url = 'https://www.instagram.com/duksung_kumdo/' WHERE id = 27;
UPDATE booths SET instagram_url = 'https://www.instagram.com/ds_dodam/' WHERE id = 28;
UPDATE booths SET instagram_url = 'https://www.instagram.com/swim_underduksea/' WHERE id = 29;
UPDATE booths SET instagram_url = 'https://www.instagram.com/duksung_club.union/' WHERE id = 30;
UPDATE booths SET instagram_url = 'https://www.instagram.com/mul_ggo/' WHERE id = 34;
UPDATE booths SET instagram_url = 'https://www.instagram.com/duksung_biotech/' WHERE id = 35;
UPDATE booths SET instagram_url = 'https://www.instagram.com/duksung_bitnaemi/' WHERE id = 40;
UPDATE booths SET instagram_url = 'https://www.instagram.com/dswu_sportsforall/' WHERE id = 41;
UPDATE booths SET instagram_url = 'https://www.instagram.com/beautifly_ds/' WHERE id = 44;
UPDATE booths SET instagram_url = 'https://www.instagram.com/duksung_f_n/' WHERE id = 45;
UPDATE booths SET instagram_url = 'https://www.instagram.com/duksung_psychology/' WHERE id = 46;
UPDATE booths SET instagram_url = 'https://www.instagram.com/dswu_pharm/' WHERE id = 48;
UPDATE booths SET instagram_url = 'https://www.instagram.com/duksung_english/' WHERE id = 50;
UPDATE booths SET instagram_url = 'https://www.instagram.com/dswu_yewoon/' WHERE id = 51;
UPDATE booths SET instagram_url = 'https://www.instagram.com/clounji_dswu/' WHERE id = 53;
UPDATE booths SET instagram_url = 'https://www.instagram.com/dswbs1967/' WHERE id = 55;
UPDATE booths SET instagram_url = 'https://www.instagram.com/duksung_ece/' WHERE id = 57;
UPDATE booths SET instagram_url = 'https://www.instagram.com/dsqueer25/' WHERE id = 60;
UPDATE booths SET instagram_url = 'https://www.instagram.com/dobongddobom/' WHERE id = 62;
UPDATE booths SET instagram_url = 'https://www.instagram.com/duksung_psis/' WHERE id = 63;
UPDATE booths SET instagram_url = 'https://www.instagram.com/choe.um?igsh=MWg3aTc0ZDE3bm4xaw==' WHERE id = 65;
UPDATE booths SET instagram_url = 'https://www.instagram.com/filmsophy20/' WHERE id = 69;
UPDATE booths SET instagram_url = 'https://www.instagram.com/_hanbit_exhibition_/' WHERE id = 70;
UPDATE booths SET instagram_url = 'https://www.instagram.com/duksung_chem/' WHERE id = 71;
UPDATE booths SET instagram_url = 'https://www.instagram.com/dswu_ai.drug/' WHERE id = 72;
UPDATE booths SET instagram_url = 'https://www.instagram.com/lpworkspace/' WHERE id = 74;
UPDATE booths SET instagram_url = 'https://www.instagram.com/happiz77/' WHERE id = 76;
