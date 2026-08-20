# MeetMate Backend

1주 MVP를 위한 Spring Boot REST API 초기 골격입니다.

## 실행

로컬 환경은 별도 DB 설정 없이 H2 인메모리 DB로 실행됩니다.

```bash
./gradlew bootRun
```

- API 상태 확인: `GET http://localhost:8080/api/v1/health`
- Actuator 상태 확인: `GET http://localhost:8080/actuator/health`
- H2 콘솔: `http://localhost:8080/h2-console`
  - JDBC URL: `jdbc:h2:mem:meetmate`
  - 사용자명: `sa`
  - 비밀번호: 비워 둠

## 프로필

- `local` (기본): H2 인메모리 DB, 스키마는 앱 종료 시 삭제
- `prod`: MySQL 사용. 아래 환경 변수가 필요합니다.

```bash
DB_URL=jdbc:mysql://localhost:3306/meetmate
DB_USERNAME=...
DB_PASSWORD=...
SPRING_PROFILES_ACTIVE=prod
```

운영 환경은 Hibernate가 테이블을 변경하지 않도록 `ddl-auto=validate`로 설정되어 있습니다. 스키마 변경은 Flyway 같은 마이그레이션 도구를 추가한 뒤 관리하세요.

## 도메인 모델

- `Member`: 서비스 회원
- `Meeting`: 회원이 주최하는 모임
- `Participation`: 회원과 모임의 참가 관계. 같은 회원은 같은 모임에 한 번만 참가할 수 있습니다.

`Meeting`은 주최자(`Member`)를 참조하고, `Participation`이 회원과 모임의 다대다 관계를 상태(`PENDING`, `CONFIRMED`, `CANCELED`)와 함께 관리합니다.

## 코드 포맷

Spotless가 `google-java-format` 규칙으로 Java 코드를 통일합니다.

```bash
# 포맷 상태 검사 (파일을 수정하지 않음)
./gradlew spotlessCheck

# 포맷을 자동 적용
./gradlew spotlessApply

# 위 명령과 동일한 자동 포맷 별칭
./gradlew spotless
```

PR을 열거나 업데이트하면 GitHub Actions의 `Spotless Check`가 자동으로 `spotlessCheck`를 실행합니다. 이 검사를 병합 필수 조건으로 삼으려면 GitHub 저장소의 branch protection ruleset에서 `Spotless Check / Check code formatting`을 required status check으로 추가하세요.
