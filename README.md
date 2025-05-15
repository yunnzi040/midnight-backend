# 이미지 생성 및 앨범 관리 API 서버

## 프로젝트 개요
이 프로젝트는 Spring Boot 기반의 API 서버로, 사용자 인증, 이미지 생성 및 관리 기능을 제공합니다. 외부 AI 서버와 연동하여 텍스트 프롬프트로부터 이미지를 생성하고, 사용자별로 생성된 이미지를 관리할 수 있습니다.

## 기술 스택
- **Java 17**
- **Spring Boot 3.4.5**
- **Spring Security**: 사용자 인증 및 권한 관리
- **Spring Data JPA**: 데이터베이스 연동
- **MySQL**: 데이터베이스
- **JWT**: 토큰 기반 인증
- **Gradle**: 빌드 도구

## 주요 기능

### 사용자 관리
- 회원가입
- 로그인 (JWT 토큰 발급)
- 토큰 갱신 (Refresh Token)

### 이미지 생성 및 관리
- 텍스트 프롬프트를 통한 AI 이미지 생성
- 생성된 이미지 저장
- 사용자별 이미지 앨범 관리
- 이미지와 함께 감정, 문장 등 메타데이터 저장

## API 엔드포인트

### 인증 API
- `POST /user/register`: 회원가입
- `POST /user/login`: 로그인 (액세스 토큰, 리프레시 토큰 발급)
- `POST /user/refresh`: 토큰 갱신

### 이미지 API
- `POST /image/generate-image`: AI를 통한 이미지 생성
- `POST /image/post`: 생성된 이미지에 메타데이터 추가
- `GET /image/album`: 사용자의 이미지 앨범 조회

## 설치 및 실행 방법

### 사전 요구사항
- JDK 17
- MySQL 서버
- AI 이미지 생성 서버 (http://172.16.16.215:8000/generate-image)

### 설정

1. 프로젝트 클론
```bash
git clone <repository-url>
cd <project-folder>
```

2. 데이터베이스 설정
- MySQL 서버에 `yunz1test` 데이터베이스 생성
- `src/main/resources/application.yml` 파일에서 데이터베이스 접속 정보 설정

3. 애플리케이션 실행
```bash
./gradlew bootRun
```

## API 사용 예시

### 회원가입
```http
POST http://localhost:8000/user/register
Content-Type: application/json

{
  "username": "testuser",
  "password": "Password123!",
  "email": "test@example.com"
}
```

### 로그인
```http
POST http://localhost:8000/user/login
Content-Type: application/json

{
  "email": "test@example.com",
  "password": "Password123!"
}
```

### 이미지 생성
```http
POST http://localhost:8000/image/generate-image
Content-Type: application/json
Authorization: Bearer <JWT_TOKEN>

{
  "prompt": "A beautiful sunset over the ocean",
  "user_id": 1
}
```

## 참고 사항
- 외부 AI 서버와 연동하여 이미지를 생성합니다 (http://172.16.16.215:8000/generate-image)
- 모든 API 요청에는 JWT 토큰 기반 인증이 필요합니다 (로그인/회원가입 제외)
- 이미지는 바이트 배열 형태로 저장 및 전송됩니다
