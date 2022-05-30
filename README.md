# Spring-Calendar API 개발 프로젝트

---
## 목차
### 1. 개발 목적
### 2. 개발 요구사항
### 3. 기술 스택
### 4. 패키징 구조
### 5. 클래스 다이어그램
### 6. 시퀀스 다이어그램
### 7. Postman과 호스트 서버를 이용한 결과물
### 8. 추후 개선사항

---
## 1. 개발 목적
#### 단순한 게시판 또는 TODO 리스트와 같은 CRUD 기능을 위한 API를 넘어서 상대방과 관계를 맺고 차단함과 배치 시스템을 이용하여 할 일에 대한 메일을 쏴주는 기능을 구현하고 이해함을 목적으로 개발하였습니다. 또한 도메인 설계하는 방법을 고민하고 자바의 코드 추상화와 코드 리팩토링 연습도 목적으로 두었습니다.

--- 
## 2. 개발 요구사항
* #### 2-1. 회원 👨‍💼
  * 간단한 회원가입 기능(email, password, name, birthday)
  * 비밀번호는 암호화하여 저장
  * 가입을 해야 서비스 이용 가능
  * 자신의 캘린더만 조회 가능
* #### 2-2. 이벤트 👨‍👨‍👧‍👦
  * 기간으로 등록
  * 참석자는 여러명 가능
  * 등록하면 참석자에게 초대 메일이 전송
  * 메일을 받은 참석자는 메일을 통해 수락, 거절 가능
  * 수락 여부 파악 가능
  * 시간이 겹치지 않는 회원만 초대 가능
* #### 2-3. 할 일 📆
  * 할 일은 자신만 등록 가능
  * 시간 등록은 선택적, 디폴트는 자정
* #### 2-4. 알림 ⏰
  * 할 일과 비슷하나 알림 반복기능이 추가
    * 반복기능
      * 반복 주기에 따라 입력 데이터가 다름(일, 주, 개월)
      * 반복 주기는 일별로만 설정 가능
* #### 2-5. 공유 🔄
  * 자신과 상대방의 캘린더를 공유
  * 양방향 관계가 성립된다면 서로의 캘린더를 공유
  * 단방향 관계가 성립된다면 자신의 캘린더만 보여주고 상대방 캘린더는 볼 수 없음

---
## 3. 기술 스택
* #### Java JDK 11
* #### Spring Boot 2.5.2
  * Spring MVC
  * Spring JPA
  * Spring Batch
  * Spring Mail
* #### 기타
  * thymeleaf(템플릿 엔진)
  * jbcrypt(패스워드 암호화)
  * lombok
  * junit(테스트)
  * gradle(프로젝트 관리)
  * docker
  * mysql
  * git
  * postman

---
## 4. 패키징 구조
* ### API
![api 패키징1](https://user-images.githubusercontent.com/64354998/170936347-302d169e-f55e-4188-99a8-323fa9e3ffbc.PNG)
###
![api 패키징2](https://user-images.githubusercontent.com/64354998/170937000-76a0cf0c-17ab-438f-a02d-6ad8f5ec89af.PNG)
* ### Batch
![batch 패키징1](https://user-images.githubusercontent.com/64354998/170936898-37efc6f2-7250-400c-813d-63cc2e181494.PNG)
* ### Core
![core 패키징1](https://user-images.githubusercontent.com/64354998/170937279-7b444901-40c0-42bb-a28f-fdbe5aab478f.PNG)

---
## 5. 클래스 다이어그램
![](https://user-images.githubusercontent.com/64354998/170933966-a9c78426-3417-45b7-a71a-b59f905bee37.PNG)
