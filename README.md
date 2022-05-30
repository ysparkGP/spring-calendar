# Spring-Calendar API 개발 프로젝트

---
## 목차
### 1. 개발 목적
### 2. 개발 요구사항
### 3. 기술 스택
### 4. 패키징 구조
### 5. 클래스 다이어그램
### 6. 주요 기능
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
* ### API(API와 DTO 관련 클래스들)
![api 패키징1](https://user-images.githubusercontent.com/64354998/170936347-302d169e-f55e-4188-99a8-323fa9e3ffbc.PNG)
###
![api 패키징2](https://user-images.githubusercontent.com/64354998/170937000-76a0cf0c-17ab-438f-a02d-6ad8f5ec89af.PNG)
* ### Batch(배치 관련 클래스들)
![batch 패키징1](https://user-images.githubusercontent.com/64354998/170936898-37efc6f2-7250-400c-813d-63cc2e181494.PNG)
* ### Core(엔티티와 DTO 관련 클래스들)
![core 패키징1](https://user-images.githubusercontent.com/64354998/170937279-7b444901-40c0-42bb-a28f-fdbe5aab478f.PNG)

---
## 5. 클래스 다이어그램
![](https://user-images.githubusercontent.com/64354998/170933966-a9c78426-3417-45b7-a71a-b59f905bee37.PNG)

---
## 6. 주요 기능
#### 1. 세션으로 유저 인증
![AuthUserResolver](https://user-images.githubusercontent.com/64354998/170941796-774fdc24-217b-46be-9939-e1958981d85b.PNG)
![Webconfig](https://user-images.githubusercontent.com/64354998/170941911-02bab4ef-80f6-4d3e-9e98-c0b0dce7a553.PNG)
##### 인증(세션이 있는)된 유저임을 인증하기 위하여 HandlerMethodArgumentResolver를 상속후 세션 값을 확인하여 AuthUser 리턴
#### 2. 약속 매칭 이메일 전송 기능
![emailservice](https://user-images.githubusercontent.com/64354998/170943723-e34468a4-4e87-498f-b0b2-e3b4d080aeb7.PNG)
![emailstuff](https://user-images.githubusercontent.com/64354998/170943808-d339f3f0-27a4-4aed-99a8-a31c1da1d9e0.PNG)
##### MimeMessagePreparator, MimeMessageHelper, JavaMailSender를 이용하여  약속 매칭을 위한 이메일 전송, 상대방이 수락을 누른다면 생성된 Engagement 엔티티의 상태를 담당하는 Enum타입인 RequestStatus는 ACCEPTED로 변경되고 거절을 누른다면 REJECTED로, 메일을 보지않는다면 REQUESTED로 유지가 되어 DB에 영속화
#### 3. 캘린더 공유 요청 기능
![sharecontroller](https://user-images.githubusercontent.com/64354998/170946151-952cb6a1-4394-4a13-a8fa-3d1fd919d192.PNG)
![shareservice](https://user-images.githubusercontent.com/64354998/170946503-c13c900b-3a30-4fe3-bb46-99e72f3e9025.PNG)
##### 공유응답을 담당하는 api가 호출되면 ShareService의 replyToShareRequest 메서드가 호출되고 인자로 넘어온 replyReq의 type을 보고 db에 영속화된 Share 엔티티의 상태를 Engagement와 똑같은 방식으로 수락, 거절, 보류 관리
#### 4. 캘린더 공유 조회 기능
![sharecontroller2](https://user-images.githubusercontent.com/64354998/170947220-4566da8a-1bf4-4130-b2ac-b7428c27334f.PNG)
![shareservice2](https://user-images.githubusercontent.com/64354998/170947241-9e582ab3-d8d9-4516-930b-0ecea1445a5c.PNG)
##### 공유요청을 담당하는 api가 호출되면 ShareService의 findSharedUserIdsByUser 메서드가 호출되고 인자로 넘어온 AuthUser로 양방향 공유 관계와 단방향 공유 관계의 User들을 모두 찾아 리턴
#### 5. 알림 배치 시스템
![batch](https://user-images.githubusercontent.com/64354998/170948483-e305b687-227a-4df8-9df1-66d752204b03.PNG)
##### Engagement와 Schedule 배치 시스템을 위하여 JdbcCursorItemReader 클래스를 구현하여 직접 sql문을 작성
