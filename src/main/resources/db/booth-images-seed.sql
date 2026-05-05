-- =================================================================
-- 부스 이미지 시드 (구글 드라이브 링크)
-- 출처: drive_files.tsv (extract_drive_files.py로 생성)
-- URL 형식: https://lh3.googleusercontent.com/d/{FILE_ID}=w1200
-- =================================================================

SET NAMES utf8mb4;
USE dsfest;

DELETE FROM booth_images;
ALTER TABLE booth_images AUTO_INCREMENT = 1;

INSERT INTO booth_images (booth_id, image_order, image_url) VALUES
  (2, 1, 'https://lh3.googleusercontent.com/d/1MpS-ENygST58N6sxcVlECE7xWgmMZmVm=w1200'),  -- 2-1. 청춘스토어.jpg
  (2, 2, 'https://lh3.googleusercontent.com/d/1YRXfTrYeRK3OZ5qPIEwCV3PkxJNN9RkP=w1200'),  -- 2-2. 청춘 스토어.jpg
  (3, 1, 'https://lh3.googleusercontent.com/d/12egTkeaMmlQjCz9f4QpLAWeVNPcGQhE4=w1200'),  -- 3-1. 청춘 매치: 행운을 잡아라.png
  (3, 2, 'https://lh3.googleusercontent.com/d/1CPj6UYanmo8oE-M23I4kX3ZgkAStWwto=w1200'),  -- 3-2. 청춘매치: 행운을 잡아라.jpg
  (14, 1, 'https://lh3.googleusercontent.com/d/1BG6NYmBQP7AVh6ALAQqh-NphUXXZAw0y=w1200'),  -- 14. 옐로우 링크.jpeg
  (21, 1, 'https://lh3.googleusercontent.com/d/1AnYyWBQBQ-RawMmQ-ndwDeVFX9iFTQJP=w1200'),  -- 21. 빠순희 소품샵.png
  (22, 1, 'https://lh3.googleusercontent.com/d/1wCDhs3dUaBuT5qvD8ArVYAXly3GJ7fJu=w1200'),  -- 22-1. 과기대는 오락오락하지 않다.png
  (22, 2, 'https://lh3.googleusercontent.com/d/1Iliq1DCvsnZ6jdaKVHsxB3jPsoDnLn30=w1200'),  -- 22-2. 과기대는 오락오락하지 않다.jpg
  (22, 3, 'https://lh3.googleusercontent.com/d/1GA6T-jHHM4Zd-Wu75-cMAiDsDkO0iT-3=w1200'),  -- 22-3. 과기대는 오락오락하지 않다jpg
  (24, 1, 'https://lh3.googleusercontent.com/d/16GTXMLzKAJ63jAVEr52ZHl-LgUZWucJ0=w1200'),  -- 24-1. 🏴‍☠️ 글로벌융합대학 해적단 🏴‍☠️.jpg
  (24, 2, 'https://lh3.googleusercontent.com/d/1JuO0ui5uEqPoGVa6LXAEqc1638ZSNpkm=w1200'),  -- 24-2. 🏴‍☠️ 글로벌융합대학 해적단 🏴‍☠️.png
  (24, 3, 'https://lh3.googleusercontent.com/d/1W1ktlKmYES8_Faf7DGTZYG8EVdMEZ_Bd=w1200'),  -- 24-3. 🏴‍☠️ 글로벌융합대학 해적단 🏴‍☠️.jpg
  (24, 4, 'https://lh3.googleusercontent.com/d/1bCYV8kYRmCQ4aYVTK6hRW_XkRGWlbGAE=w1200'),  -- 24-4. 🏴‍☠️ 글로벌융합대학 해적단 🏴‍☠️.png
  (25, 1, 'https://lh3.googleusercontent.com/d/11vwd0ubF9bZZG6ITUw7n2bg-Lyi1Bdk7=w1200'),  -- 25. 꽃신을 신고.png
  (26, 1, 'https://lh3.googleusercontent.com/d/1Eay0zk0PhYxwx58sUfKCIx1TNt2lr5nK=w1200'),  -- 26-1. 덕냥당.png
  (26, 2, 'https://lh3.googleusercontent.com/d/1acIDpuT1NUSsoOXoHkWuRSjyBGJ5VriO=w1200'),  -- 26-2. 덕냥당.png
  (26, 3, 'https://lh3.googleusercontent.com/d/1PjEp2qeKGYtITk7q8B1Cjz86jSUV1nm1=w1200'),  -- 26-3. 덕냥당.png
  (26, 4, 'https://lh3.googleusercontent.com/d/191e4Y1i0WHtZRZ_3IuhVKAX7v1rH9xZc=w1200'),  -- 26-4. 덕냥당.png
  (26, 5, 'https://lh3.googleusercontent.com/d/1HbpGvu2eMZkhiSCCxneQticw6bssJUPj=w1200'),  -- 26-5. 덕냥당.png
  (27, 1, 'https://lh3.googleusercontent.com/d/1x4BoqfONxrmSnDWoVfhLdJU1CdTXAhMm=w1200'),  -- 27-1. 그런데 그때 갑자기 검도부가 나타났다 머리잇~!.jpg
  (27, 2, 'https://lh3.googleusercontent.com/d/1Ly3huIqGZDlnJF4FcmSJptNe9smVtRep=w1200'),  -- 27-2. 그런데 그때 갑자기 검도부가 나타났다 머리잇~!.jpg
  (27, 3, 'https://lh3.googleusercontent.com/d/1ibP6Iz-8ZErPfg_L__dqq6pNYs9ATjvI=w1200'),  -- 27-3. 그런데 그때 갑자기 검도부가 나타났다 머리잇~!.jpg
  (28, 1, 'https://lh3.googleusercontent.com/d/1R1c2Q8g7QJBD0UWbfzzKgI3QYgYJlq3D=w1200'),  -- 28. 도담도담.jpeg
  (29, 1, 'https://lh3.googleusercontent.com/d/1WJzjRySevA9H-XLAu8VG1kPM77HEoTOx=w1200'),  -- 29-1. 언더덕씨.jpg
  (29, 2, 'https://lh3.googleusercontent.com/d/1zI_oxdqMfqWHBdo1b8U4saKnxw2nj1EP=w1200'),  -- 29-2. 언더덕씨.jpg
  (29, 3, 'https://lh3.googleusercontent.com/d/1j1SLxHATETXYonNNcfRz6g9mYhhtLFdo=w1200'),  -- 29-3. 언더덕씨.jpg
  (29, 4, 'https://lh3.googleusercontent.com/d/1C9qzvboZfdKshE7F_IpEJWlfT5gxg-sK=w1200'),  -- 29-4. 언더덕씨.jpg
  (29, 5, 'https://lh3.googleusercontent.com/d/1oua4QWhE7TeSum2gXM5-VDoehNbxtfYW=w1200'),  -- 29-5. 언더덕씨.jpg
  (30, 1, 'https://drive.google.com/thumbnail?id=1rXs93AAzU1tB1QloKPhRWY1xdU-KMnni&sz=w1200'),  -- 30. 우리에서 만난 우리.pdf
  (32, 1, 'https://lh3.googleusercontent.com/d/1RRp90GJwZlRP7kxbU31-qK_rFtLpyhYo=w1200'),  -- 32-1. 덕컴타자연습.png
  (32, 2, 'https://lh3.googleusercontent.com/d/1eap9iNcwjhNiC668MkkbMmZCaYXJVh2q=w1200'),  -- 32-2. 덕컴타자연습.png
  (33, 1, 'https://lh3.googleusercontent.com/d/1KUuMuGs1YwPeHuza_cFuoEH_GQLUcx-m=w1200'),  -- 33. 혜연이가 뜨개 그만하래 같이 살 집 뜨고 있었는데.jpeg
  (34, 1, 'https://lh3.googleusercontent.com/d/1eTDN1PmBktlEjBB7UX6R-sMWNqLUtwNS=w1200'),  -- 34-1. 꼬물꼬물 반짝이는 모험.jpeg
  (34, 2, 'https://lh3.googleusercontent.com/d/1W3KgX_SIQjLxQ83I7pIGga2OX5cUjlUl=w1200'),  -- 34-2. 꼬물꼬물 반짝이는 모험.jpeg
  (34, 3, 'https://lh3.googleusercontent.com/d/1w4lF2U2f9mxEmNF-T2okabEZPhe5mH4w=w1200'),  -- 34-3. 꼬물꼬물 반짝이는 모험.jpeg
  (36, 1, 'https://lh3.googleusercontent.com/d/13KRB210fZUVquw0wOuoJb5xFGAIZYVKl=w1200'),  -- 36-1. 비 온 뒤 땅이 굳는다.jpg
  (36, 2, 'https://lh3.googleusercontent.com/d/1N2Dmm2_PNTGc04OmNs1lIfCLVIupiKOv=w1200'),  -- 36-2. 비 온 뒤 땅이 굳는다.jpg
  (36, 3, 'https://lh3.googleusercontent.com/d/1jwHRXVjIRUXLvyJsVfhXp8T6mJNjIWzu=w1200'),  -- 36-3. 비 온 뒤 땅이 굳는다.jpg
  (36, 4, 'https://lh3.googleusercontent.com/d/1UWdXXBJZGpSm0mWadLqEKGzSv3DPI5oW=w1200'),  -- 36-4. 비 온 뒤 땅이 굳는다.jpg
  (36, 5, 'https://lh3.googleusercontent.com/d/18a5ROpYYx9qdmkMjNW9Y-ZkO-rzac0q1=w1200'),  -- 36-5. 비 온 뒤 땅이 굳는다.jpg
  (37, 1, 'https://lh3.googleusercontent.com/d/1-f7B14cnXe6ahITmlKy78ygBegPYmVPM=w1200'),  -- 37-1. 청사과 낭만부.jpeg
  (37, 2, 'https://lh3.googleusercontent.com/d/1oYWzy-fZ1wZ6OybaNRL3_shuf2TSwSWj=w1200'),  -- 37-2. 청사과 낭만부.jpeg
  (37, 3, 'https://lh3.googleusercontent.com/d/12OOEEbuDFX9chKIgA6SbZgUgJaN_rZyU=w1200'),  -- 37-3.청사과 낭만부.jpeg
  (38, 1, 'https://lh3.googleusercontent.com/d/1RhGzPigc1nSDkyGpWdc2WqzxvFnHgRme=w1200'),  -- 38-1. 행운의 명태입양소.jpg
  (38, 2, 'https://lh3.googleusercontent.com/d/1YsQzYitJBLPMIQM0vKNBpNBtCdV8Ph_f=w1200'),  -- 38-2. 행운의 명태입양소.jpg
  (38, 3, 'https://lh3.googleusercontent.com/d/17wLuMzYeEOse2rbgJpm4n4p2T5vvIIFX=w1200'),  -- 38-3. 행운의 명태입양소.jpg
  (40, 1, 'https://lh3.googleusercontent.com/d/1QkMfo7enmAVvu5fhATrT7tXQNnJwhlO_=w1200'),  -- 40. 빛내미 공방.png
  (41, 1, 'https://lh3.googleusercontent.com/d/1gvOCaohcfErO0QDJObJVrsfe7dlH1O7H=w1200'),  -- 41. 생체 도전장.jpeg
  (42, 1, 'https://lh3.googleusercontent.com/d/1xhjLdta4JBWPgEVyCDHJWul0vF0qurkj=w1200'),  -- 42. 페이스페인팅, 참 쉽죠?.jpeg
  (43, 1, 'https://lh3.googleusercontent.com/d/1T8xfpQNMcQhDPsUnO-8yMupiO2NQqorW=w1200'),  -- 43. 수학전공.png
  (44, 1, 'https://lh3.googleusercontent.com/d/12eyf0xUhS986_mG1YUZCxRNmE7-A8BGf=w1200'),  -- 44-1. Beautifly 🦋.jpeg
  (44, 2, 'https://lh3.googleusercontent.com/d/1JRCG3TiGHY9GWMr1rU1CR7YFK6c01td-=w1200'),  -- 44-2. Beautifly 🦋jpeg
  (44, 3, 'https://lh3.googleusercontent.com/d/1_Kk6jBAFLQfKo42849JEU4G0mh6xa8_R=w1200'),  -- 44-3. Beautifly 🦋.jpeg
  (45, 1, 'https://lh3.googleusercontent.com/d/1avQruV6iJRZYMw4DFuEcP8vk9eA0zYYE=w1200'),  -- 45-1. 콩식이네 말랑공방.jpg
  (45, 2, 'https://lh3.googleusercontent.com/d/1fn_otB7AdXP-e-Suqq9YbbzmUIsAstkg=w1200'),  -- 45-2. 콩식이네 말랑공방.png
  (45, 3, 'https://lh3.googleusercontent.com/d/1vZT6WJOTac8DQHg8tKzmppevVTnK23a8=w1200'),  -- 45-3. 콩식이네 말랑공방.png
  (45, 4, 'https://lh3.googleusercontent.com/d/1UAXQahD6WekDVuL1OirFXFoSe3MzPXHz=w1200'),  -- 45-4. 콩식이네 말랑공방.png
  (46, 1, 'https://drive.google.com/thumbnail?id=140vT6LhUghCwZOlq5bmc5tCLG7nBclUR&sz=w1200'),  -- 46-1. 뇌랑해 ♥ : 뇌를 말랑하게.pdf
  (46, 2, 'https://drive.google.com/thumbnail?id=1oBDVM2Eq4Q4sFStfGFToVZgHuwJc8Kxb&sz=w1200'),  -- 46-2. 뇌랑해 ♥ : 뇌를 말랑하게.pdf
  (46, 3, 'https://drive.google.com/thumbnail?id=1qB0HhVKHS_HJWlj0oXsh0g7LELr5kmFI&sz=w1200'),  -- 46-3. 뇌랑해 ♥ : 뇌를 말랑하게.pdf
  (46, 4, 'https://drive.google.com/thumbnail?id=1IYSiO_JGiLX3Q83GOEuZEe1T7k0kPrYM&sz=w1200'),  -- 46-4. 뇌랑해 ♥ : 뇌를 말랑하게.pdf
  (47, 1, 'https://lh3.googleusercontent.com/d/1Xp9wC56COOwMK7VxahrE2WSx-EYMILTH=w1200'),  -- 47. 응답하라 응애!.png
  (48, 1, 'https://lh3.googleusercontent.com/d/1NYKL45eDukY918Vytg9LmI1RpcZBaxrA=w1200'),  -- 48. 덕성약국.jpeg
  (49, 1, 'https://lh3.googleusercontent.com/d/1utyU_YLfG2as7XLtKh4n8o-53IlAagNu=w1200'),  -- 49-1. 열음.jpeg
  (49, 2, 'https://lh3.googleusercontent.com/d/1iXLMizgbFMDD0zTC1HqaHLYTwGW3Gmds=w1200'),  -- 49-2. 열음.jpeg
  (50, 1, 'https://lh3.googleusercontent.com/d/1jZhOMPcDPZLDf_9D7bFOMNET0DMu5fel=w1200'),  -- 50. English Carnival.webp
  (51, 1, 'https://lh3.googleusercontent.com/d/1TwoLt-RMoW0kUQRcFCVrWf7m5eZtRzhH=w1200'),  -- 51. 여름을 그리다.jpg
  (52, 1, 'https://lh3.googleusercontent.com/d/1vJDSqcBxvXnbFXib4pcu8agaYxnGBhrB=w1200'),  -- 52-1. 덕우야❗️잡화점💫.jpeg
  (52, 2, 'https://lh3.googleusercontent.com/d/1o2UOQZWq6a8jbo7E-JB4zNEUyuAZT-ZZ=w1200'),  -- 52-2. 덕우야❗️잡화점💫.jpeg
  (52, 3, 'https://lh3.googleusercontent.com/d/1bEYtbJCkDp84wHKY2o18H9oe0wva_yq3=w1200'),  -- 52-3. 덕우야❗️잡화점💫.jpeg
  (52, 4, 'https://lh3.googleusercontent.com/d/1Sl2bHVdu1Ggrmzbq9T2ROKrPB5PaK2Z8=w1200'),  -- 52-4. 덕우야❗️잡화점💫.jpeg
  (53, 1, 'https://lh3.googleusercontent.com/d/1h9UdVae_56MNVQAbcNu3YRgDx9GtuCO4=w1200'),  -- 53. 운명의 서점.jpeg
  (54, 1, 'https://lh3.googleusercontent.com/d/1OMX6K1N8Rt0NPAUVaNm6MdjSl80fQALN=w1200'),  -- 54-1. 운현극락(雲峴極樂).jpeg
  (54, 2, 'https://lh3.googleusercontent.com/d/1up5cJMbUNFFYnG9zR3R1WZZ-4Lf_KWN3=w1200'),  -- 54-2. 운현극락(雲峴極樂).jpeg
  (55, 1, 'https://lh3.googleusercontent.com/d/18FgLs4w6kwaeqFiqsaezpd25VUDZgeog=w1200'),  -- 55-1. 운현방송국 Dream-Catcher🌙.png
  (55, 2, 'https://lh3.googleusercontent.com/d/1ESY9k7ExWPKquwfZ8E_OgkQYudq33pA4=w1200'),  -- 55-2. 운현방송국 Dream-Catcher🌙.jpeg
  (55, 3, 'https://lh3.googleusercontent.com/d/1iED3oprVIOSISUxsq_lfbtiwGDApBZIN=w1200'),  -- 55-3. 운현방송국 Dream-Catcher🌙.jpeg
  (55, 4, 'https://lh3.googleusercontent.com/d/1pHto2X0S3TZJ2VmxT7rmGYFpq3FCW56I=w1200'),  -- 55-4. 운현방송국 Dream-Catcher🌙.jpeg
  (56, 1, 'https://lh3.googleusercontent.com/d/1bBzwuo9Rhsbas9eE554pqcn_eMMY-r8C=w1200'),  -- 56-1. 자투리 과일가게.jpg
  (56, 2, 'https://lh3.googleusercontent.com/d/1PE1d3U3JAUStnFjT1SPKDwxsrLeZBqAN=w1200'),  -- 56-2. 자투리 과일가게.jpg
  (56, 3, 'https://lh3.googleusercontent.com/d/1u-LXaZFlE8r-K8ZErq87KG3IR3X8mDpp=w1200'),  -- 56-3. 자투리 과일가게.jpg
  (56, 4, 'https://lh3.googleusercontent.com/d/1_j8Lp8cBqyF_p2B23f3EvweYSmpsT7In=w1200'),  -- 56-4. 자투리 과일가게.jpg
  (56, 5, 'https://lh3.googleusercontent.com/d/1EKRZ9nGjdhgTocD9JbMmVV7EJnXTO4RC=w1200'),  -- 56-5. 자투리 과일가게.jpg
  (57, 1, 'https://lh3.googleusercontent.com/d/1AR7uEiJeYcRqK4Y-XgeZ5BlFX7CD1fCT=w1200'),  -- 57. 아름드리.png
  (58, 1, 'https://lh3.googleusercontent.com/d/1EL8J_5cRQSQ3TgRDm42YC5EjCIdlM6V0=w1200'),  -- 58. 덕새가 머무는 곳.png
  (59, 1, 'https://lh3.googleusercontent.com/d/1bcdwMxh9-sWXFlN_tW1hyQSd6E4RHTDq=w1200'),  -- 59-1. isy 이시.jpg
  (59, 2, 'https://lh3.googleusercontent.com/d/1N-h8MMSjXmsUyvAMn6wkP7BH8LxoWy-f=w1200'),  -- 59-2. isy 이시.jpg
  (60, 1, 'https://lh3.googleusercontent.com/d/1vkN3Xra-M0xNo3QZMVeT717dM0x-jzGd=w1200'),  -- 60. 이오.jpg
  (61, 1, 'https://lh3.googleusercontent.com/d/1V8plbP_cbehWVjman3CQaZM_-0-kA4Qh=w1200'),  -- 61. 근화마츠리.jpg
  (62, 1, 'https://lh3.googleusercontent.com/d/1GTqO61HGLlzOIEX9MFTbvGYEnsb9VPrs=w1200'),  -- 62-1. 도봉또봄.jpeg
  (62, 2, 'https://lh3.googleusercontent.com/d/1hMUw5WC2zyNcZEqGDzvvnUuoJbAxR1Y7=w1200'),  -- 62-2. 도봉또봄.jpeg
  (64, 1, 'https://lh3.googleusercontent.com/d/1yrQ3cPGauU49fe5ov-mWlSc4DnF13Gpz=w1200'),  -- 64. 룡하다! 공룡타로.png
  (66, 1, 'https://lh3.googleusercontent.com/d/1-p47-e5CzouHNy4lE8E5EAPyjId2gHJn=w1200'),  -- 66. 35mm Archive.JPG(수정)
  (67, 1, 'https://lh3.googleusercontent.com/d/1RfqP_1kpy9nnN1cXZdTPmB819BTS6fiE=w1200'),  -- 67-1. 한 코, 한 땀.jpeg
  (67, 2, 'https://lh3.googleusercontent.com/d/1YWRiwkgurs9WrHkT9VSqAhzuB7hZZAFy=w1200'),  -- 67-2. 한 코, 한 땀.jpeg
  (67, 3, 'https://lh3.googleusercontent.com/d/1Rp8OyZiulUoCiMcntCjxcsDere6SmYed=w1200'),  -- 67-3. 한 코, 한 땀.jpeg
  (68, 1, 'https://lh3.googleusercontent.com/d/1mCALyj6RyP4E6_HTvToxFJWPTja8NKXu=w1200'),  -- 68. 팀복학생.png
  (69, 1, 'https://lh3.googleusercontent.com/d/1FtiGdId_BJzbJvmgXHPCaDXrBAVua4TR=w1200'),  -- 69. 필소굿:필름소피굿즈팝니다..png
  (70, 1, 'https://lh3.googleusercontent.com/d/1A-2hKiyiCBr87ERRWcpW5o6hPJF41bDG=w1200'),  -- 70-1. 한빛 현상소.jpeg
  (70, 2, 'https://lh3.googleusercontent.com/d/1UvB5-W3D9_S3AIjmANzS5v4405Cl-XiW=w1200'),  -- 70-2. 한빛 현상소.jpeg
  (71, 1, 'https://lh3.googleusercontent.com/d/1Bu-YrHjkck-hxEtwmzaRehqG4m-sO2T4=w1200'),  -- 71. 케미케미체인지.jpeg
  (72, 1, 'https://lh3.googleusercontent.com/d/1LsxwPUem085YQcoTLXjndjzzTReomIws=w1200'),  -- 72. 내 손안의 미니 Lab.jpg
  (73, 1, 'https://lh3.googleusercontent.com/d/1XAxDyGFsMBw6xiNfJzC8kQ3vfIcoXVTw=w1200'),  -- 73-1. Fork&Roll.jpeg
  (73, 2, 'https://lh3.googleusercontent.com/d/1yUO1P9OLUWYYnxrgmJcMeuSbgFzDHIa4=w1200'),  -- 73-2. Fork&Roll.jpeg
  (73, 3, 'https://lh3.googleusercontent.com/d/1Q3TZ6RKs0d8kncBLX0HOG5xetIhn08bU=w1200'),  -- 73-3. Fork&Roll.jpeg
  (74, 1, 'https://lh3.googleusercontent.com/d/1XWON5T3BZJAMWcV4schRi_92gIx12-DS=w1200'),  -- 74-1. LIFE PROJECT.jpg
  (74, 2, 'https://lh3.googleusercontent.com/d/1a5WRHw6wv0-PD9rAh5qMRSwOq_le9d4o=w1200'),  -- 74-2. LIFE PROJECT.jpg
  (74, 3, 'https://lh3.googleusercontent.com/d/1k_ySmHZMLKAZhiqMHcR5yz5Si1ueFYaJ=w1200'),  -- 74-3. LIFE PROJECT.jpg
  (75, 1, 'https://lh3.googleusercontent.com/d/1cwUxEZnRhUWf13xA_aw4CJCBZ_Wegdzi=w1200'),  -- 75-1. 쿠사싹.png
  (75, 2, 'https://lh3.googleusercontent.com/d/1yK6EHM895-QOYIgbM_wqgF8KkpAQCwGB=w1200'),  -- 75-2. 쿠사싹.png
  (75, 3, 'https://lh3.googleusercontent.com/d/1740ii87Tp0QDYNPKTEdK46llK6iYYzv4=w1200'),  -- 75-3. 쿠사싹.png
  (75, 4, 'https://lh3.googleusercontent.com/d/1lwLM1KHLXE9mgYQoVuWdzndV-cVscz-q=w1200'),  -- 75-4. 쿠사싹.png
  (76, 1, 'https://lh3.googleusercontent.com/d/1yx1TI50KKyGlpl6pIlYAyNOl62Lw0p9f=w1200'),  -- 76-1. 해삐즈 HAPPiZ.jpg
  (76, 2, 'https://lh3.googleusercontent.com/d/1cyFTQYgwH3isG01XSuNX-grPpoSK9QFh=w1200'),  -- 76-2. 해삐즈 HAPPiZ.jpg
  (76, 3, 'https://lh3.googleusercontent.com/d/1wjakW3H4TTTKEisMq4zbwdgUm935I95n=w1200'),  -- 76-3. 해삐즈 HAPPiZ.jpg
  (76, 4, 'https://lh3.googleusercontent.com/d/1oZ8sQ2OmRJa37hmE03pAnS8PrL4kOCJZ=w1200');  -- 76-4. 해삐즈 HAPPiZ.jpg
