# Web Automation Sample

## application.properties setup
```
id = musinsa account id
password = musinsa accoun password
env= 테스트 환경 (Dev, QA, Stage, Prod ...)
id= 무신사 아이디
password= 무신사 패스워드
url= 무신사 홈페이지 경로 https://www.musinsa.com/

* 테스트 데이터는 추후 POI 등을 이용해서 별도 엑셀 파일로 관리하도록 수정
name= 회원정보 이름
email= 회원정보 이메일
phone= 회원정보 전화번호
birthday= 회원정보 생년월일
```

## Run
```
./gradlew clean
./gradlew build
./gradlew test -Psuite
```