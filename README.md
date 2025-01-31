# 우주맛 (우리 주변의 맛집)

## Translations
Engligh ([README.en.md](README.en.md))  
한국어 (이 파일)

## 간단한 설명
모바일 프로그래밍 교과에서 팀 프로젝트로써 제작한 안드로이드 앱 + 백엔드 서버.

이 앱은 사용자 주변의 맛집을 등록하고, 리뷰를 남길 수 있는 앱입니다.
맛집에는 "#대화하기 좋은 #시끌벅적한 #한식" 과 같이 해시태그를 통해 그 맛집의 성격을 설명할 수 있습니다.
검색 기능은 이름, 주소, 해시태그 모두 통합되어 검색됩니다.
별점 기능은 맛, 가격, 친절, 청결로 나누어져 1~5점을 부여할 수 있습니다.

## 실행 방법
APK 파일을 다운로드 하기 위해서는 오른쪽의 Release 탭에서 다운로드 하거나, 최근 빌드로 들어가서 Artifact를 다운로드 할 수 있습니다.
현재 Freenom (무료 도메인 네임 제공자)이 서비스를 중단한 관계로 백엔드 서버는 꺼져 있습니다.
[RemoteService.java](app/src/main/java/cf/spacetaste/app/network/RemoteService.java)의 `SERVER_URL` 변수 값을 변경해야 할 수 있습니다.

## 빌드 방법
Gradle을 이용해 빌드할 수 있습니다.

다만 안드로이드 앱은 카카오 SDK가 빌드 키를 검사하는 관계로 아무나 빌드해서 테스트 하는것은 불가능합니다.
만약 사용을 원하시면 앱 키를 바꾸시기 바랍니다.

백엔드는 `backend` 디렉토리의 `docker-compose.yaml`을 적당한 위치로 복사한 뒤 실행할 수 있습니다.
해당 `docker-compose.yaml`파일을 사용하기에 앞서 아래 커맨드로 먼저 이미지를 생성해주어야 합니다.

`:app:build` -> 앱 빌드

`:backend:bootBuildImage` -> 도커 이미지 생성 (GraalVM Native 이용)

## 주요 외부 라이브러리
### 공통
* lombok: 데이터 클래스 관리에 사용

### 클라이언트측
* Android SDK: target SDK 32
* AndroidX: 각종 헬퍼 라이브러리
* Retrofit2: REST API 호출용으로 사용
* Glide: 서버로부터 이미지를 로드해서 표시하는데 사용
* Kakako SDK: 카카오 로그인과 지도 기능 구현에 사용

### 서버측
* Spring Boot: 웹 서비스 제작에 사용
* Spring Boot AOT: 네이티브로 컴파일하는데 사용
* myBatis: MariaDB 서버와 연동에 사용
* MariaDB JDBC Connector: MariaDB 서버와 연동에 사용
* okhttp: 토큰을 검증하기 위해서 카카오 REST API 호출에 사용
* auth0-jwt: 자체 액세스 토큰을 발급하고 검증하는데 사용

## 라이센스
MIT 라이센스가 부착되어 있습니다.
자세한 내용은 LICENSE.txt 참고 바랍니다.
