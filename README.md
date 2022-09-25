##### 다우기술 기술과제 제출


jdk : 17
database : h2 database
framework :  spring boot ver. 2.6.11


 h2 데이터베이스 실행.
 배치파일경로를 application.yml 파일 client.savefilepath 로 변경가능 기본(C:\DATA_SAVE)
 접속허용 IP 설정 application.yml 파일 client.ip 로 변경가능.
 
 1.
 localhost:8080/login -> 로그인시도후 token 발급
  post userId   : 10
       password : test
      
 2. 토큰발급 후 header X-AUTH-TOKEN 에 값셋팅 후  api 기능 수행.
 
