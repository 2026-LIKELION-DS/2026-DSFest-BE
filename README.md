# 🦁 2026-DSFest-BE

## 🖥️ BE Developer
**덕성여자대학교 멋쟁이사자처럼 14기 백엔드 운영진**

<table>
  <tr>
    <td align="center"><a href="https://github.com/leewatertrue"><img src="https://avatars.githubusercontent.com/leewatertrue" width="120px;" alt=""/><br /><sub><b>이수진</b></sub></a><br /><sub>세팅 · ERD · 어드민 · 검색</sub></td>
    <td align="center"><a href="https://github.com/leeyumin626"><img src="https://avatars.githubusercontent.com/leeyumin626" width="120px;" alt=""/><br /><sub><b>이유민</b></sub></a><br /><sub>아티스트 · 라이브톡</sub></td>
    <td align="center"><a href="https://github.com/b1nnnnid"><img src="https://avatars.githubusercontent.com/b1nnnnid" width="120px;" alt=""/><br /><sub><b>유수빈</b></sub></a><br /><sub>부스 · 축제일정</sub></td>
    <td align="center"><a href="https://github.com/rhdbqls"><img src="https://avatars.githubusercontent.com/rhdbqls" width="120px;" alt=""/><br /><sub><b>고유빈</b></sub></a><br /><sub>사진 콘테스트 · 푸드트럭</sub></td>
    <td align="center"><a href="https://github.com/naeuun"><img src="https://avatars.githubusercontent.com/naeuun" width="120px;" alt=""/><br /><sub><b>김나은</b></sub></a><br /><sub>배포 · 홈 · 공지사항</sub></td>
  </tr>
</table>

---

## 🛠 기술 스택

| **역할** | **종류** | **선정 이유** |
| --- | --- | --- |
| Language | <img src="https://img.shields.io/badge/Java-007396?style=for-the-badge&logo=openjdk&logoColor=white"> | LTS 버전, Spring Boot 3.x 공식 지원 |
| Framework | <img src="https://img.shields.io/badge/Spring Boot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white"> | 국내외 백엔드 표준 스택 |
| Security | <img src="https://img.shields.io/badge/Spring Security-6DB33F?style=for-the-badge&logo=springsecurity&logoColor=white"> <img src="https://img.shields.io/badge/JWT-000000?style=for-the-badge&logo=jsonwebtokens&logoColor=white"> | 인증/인가 처리 |
| Build Tool | <img src="https://img.shields.io/badge/Gradle-02303A?style=for-the-badge&logo=gradle&logoColor=white"> | 간결한 문법, 빠른 빌드 속도 |
| ORM | <img src="https://img.shields.io/badge/Spring Data JPA-6DB33F?style=for-the-badge&logo=spring&logoColor=white"> <img src="https://img.shields.io/badge/QueryDSL-0769AD?style=for-the-badge&logoColor=white"> | 객체 중심 DB 접근, 복잡한 쿼리 처리 |
| Database | <img src="https://img.shields.io/badge/MySQL-4479A1?style=for-the-badge&logo=mysql&logoColor=white"> <img src="https://img.shields.io/badge/AWS RDS-527FFF?style=for-the-badge&logo=amazonrds&logoColor=white"> | 안정적인 관계형 DB, 클라우드 관리형 |
| Real-time | <img src="https://img.shields.io/badge/WebSocket-010101?style=for-the-badge&logoColor=white"> | 실시간 채팅 (LiveTalk) |
| Storage | <img src="https://img.shields.io/badge/AWS S3-569A31?style=for-the-badge&logo=amazons3&logoColor=white"> | 이미지 파일 저장 |
| Deployment | <img src="https://img.shields.io/badge/AWS EC2-FF9900?style=for-the-badge&logo=amazonec2&logoColor=white"> | 안정적인 클라우드 서버 |
| CI/CD | <img src="https://img.shields.io/badge/GitHub Actions-2088FF?style=for-the-badge&logo=githubactions&logoColor=white"> | 자동화된 빌드 및 배포 |
| Version Control | <img src="https://img.shields.io/badge/GitHub-181717?style=for-the-badge&logo=github&logoColor=white"> | 코드 버전 관리 및 협업 |
| Infra | <img src="https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=docker&logoColor=white"> <img src="https://img.shields.io/badge/Nginx-009639?style=for-the-badge&logo=nginx&logoColor=white"> | 컨테이너 기반 배포, 리버스 프록시 |
| Tools | <img src="https://img.shields.io/badge/Postman-FF6C37?style=for-the-badge&logo=postman&logoColor=white"> <img src="https://img.shields.io/badge/Swagger-85EA2D?style=for-the-badge&logo=swagger&logoColor=black"> <img src="https://img.shields.io/badge/IntelliJ IDEA-000000?style=for-the-badge&logo=intellijidea&logoColor=white"> | API 테스트 · 문서화 · 개발 환경 |

---

## 🗂 ERD

🔗 https://www.erdcloud.com/team/TX8NqPFWjGKtyHqQc
[![DSFest.png](https://i.postimg.cc/XqNDrmyn/DSFest.png)](https://postimg.cc/R68T8PRY)

---

## 🔀 Git Branch Convention

| Prefix | 설명 | 예시 |
| --- | --- | --- |
| `main` | 배포용 브랜치 | main |
| `feature/` | 새로운 기능 개발 | feature/1-guest-join |
| `fix/` | 버그 수정 (개발 중) | fix/2-login-error |
| `hotfix/` | 긴급 수정 (배포 후) | hotfix/3-server-down |

- 브랜치명은 소문자, 띄어쓰기 대신 하이픈(-), 이모지 사용 X
- 형식: `브랜치종류/이슈번호-기능내용` (예: `feature/1-guest-join`)

---

## ⚓ Git Commit Message Convention

| 타입 | 설명 |
| --- | --- |
| `Start` | 프로젝트 시작 |
| `Feat` | 새로운 기능 추가 |
| `Fix` | 버그 수정 |
| `Refactor` | 코드 리팩토링 |
| `Settings` | 설정 파일 변경 |
| `Comment` | 주석 추가 및 변경 |
| `Dependency` | 의존성 추가 |
| `Docs` | 문서 수정 |
| `Merge` | 브랜치 머지 |
| `Deploy` | 배포 |
| `Rename` | 파일 혹은 폴더명 수정 |
| `Remove` | 파일 삭제 |
| `Revert` | 이전 버전으로 롤백 |
| `Test` | 테스트 코드 작성 |

---

## 🏗 아키텍처

```
[사용자(모바일/PC)]
        |
        | https://youth-of-duksung.site
        v
+------------------------+
|   Gabia DNS            |
|  - @ / www -> Vercel   |
|  - api     -> EC2 EIP  |
+------------------------+
        |                          |
     (프론트)                    (API)
        v                          v
+----------------------+   +---------------------------------------+
| Vercel (React)       |   | EC2 (Public Subnet)                   |
| - HTTPS 자동         |   | - Elastic IP (고정)                   |
| - API 호출:          |   | - SG: 80/443 open, 22는 내 IP만       |
|   https://api...     |   | - 8080 외부 공개 X                    |
+----------------------+   |  +------------------+                 |
                           |  | Nginx (Host)     | :443 TLS 종료   |
                           |  | - reverse proxy  | -> :8080 전달   |
                           |  +------------------+                 |
                           |          |                            |
                           |          v                            |
                           |  +----------------------------------+ |
                           |  | Docker Compose                   | |
                           |  |  +------------------+            | |
                           |  |  | Spring Boot API  |            | |
                           |  |  | - /api/**        |            | |
                           |  |  | - listens :8080  |            | |
                           |  |  | - CORS: 프론트만 |            | |
                           |  |  +------------------+            | |
                           |  +----------------------------------+ |
                           +---------------------------------------+
                                          |
                   -----------------------|---------------------
                   |                                           |
                   v                                           v
      +-----------------------+                  +------------------------+
      | RDS MySQL (Private)   |                  | S3 Bucket              |
      | - Public access: No   |                  | - 공지/배너 이미지 저장 |
      | - EC2 SG만 허용       |                  | - 학번/이름 파일 금지   |
      +-----------------------+                  +------------------------+
```

---

## 🤖 Code Review

PR 생성 시 **CodeRabbit AI**가 자동으로 코드 리뷰를 수행합니다.
리뷰 코멘트를 확인하고 필요한 경우 반영 후 머지합니다.
`@coderabbitai` 멘션으로 추가 리뷰 요청이 가능합니다.

---

## 🔗 링크 (5.13까지)

| 구분 | 링크 |
| --- | --- |
| 배포 서버 | https://youth-of-duksung.site |
| 배포 Swagger | https://youth-of-duksung.site/swagger-ui/index.html |
