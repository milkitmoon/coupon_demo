# 1. 개요
- 간단한 쿠폰인증 서버를 개발하였습니다.
- 서버는 쿠폰을 발행합니다. (금액쿠폰과 할인쿠폰이 있음)
- 서버는 쿠폰을 사용인증하거나 인증된 쿠폰을 취소할 수 있습니다.
- 서버는 쿠폰을 폐기할 수 있습니다.
- 인증은 기본적으로 JWT를 사용하며 로그인 성공 후 JWT 토큰을 HTTP HEADER에 삽입하여야 합니다.


# 2. 기술명세
- 언어 : Java 1.8
- IDE : VSCode 1.5
- 프레임워크 : spring boot 2.3.1
- 의존성 & 빌드 관리 : gradle
- 인증 : spring security, jwt
- Persistence : JPA (Hibernate)
- Database : H2 (in memory)
- OAS : swagger

> swagger API명세 페이지 보기
- 어플리케이션 기동 후 아래와 같이 [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html) 접속하여 API페이지를 조회할 수 있습니다.
<img src="https://user-images.githubusercontent.com/61044774/97953836-0bf11f80-1de5-11eb-857c-c30ceb991fea.jpg" width="90%"></img>


> H2 database 웹콘솔 보기
- H2 웹console 접속경로는 다음과 같습니다. [http://localhost:8080/h2-console/](http://localhost:8080/h2-console/)
<img src="https://user-images.githubusercontent.com/61044774/85590819-b0b56080-b67f-11ea-8415-3eb50f5b82b8.jpg" width="90%"></img>

- Driver Class : org.h2.Driver
- JDBC URL : jdbc:h2:mem:testdb
- User Name : sa
- Password : [없음]


# 3. 쿠폰 테이블 정의

## COUPON_INFO (쿠폰기본정보)
- 쿠폰에 대한 기본적인 설정정보를 포함하고 있는 마스터 테이블이다. 쿠폰에 대한 구분이나 승인유효기간 등 쿠폰속성 및 승인에 대한 정보가 정의된다.
<img src="https://user-images.githubusercontent.com/61044774/97956635-85d8d700-1dec-11eb-81f3-2c02ab0c06b8.jpg" width="100%"></img>

## COUPON (쿠폰)
- 실제 쿠폰정보가 담겨있는 쿠폰이다. 쿠폰번호를 기준으로 쿠폰의 현재 상태(발행, 사용, 폐기 등) 및 쿠폰 기본정보가 정의된다.
쿠폰은 상기 쿠폰기본정보 테이블을 토대로 생성된다.
<img src="https://user-images.githubusercontent.com/61044774/97957118-b1a88c80-1ded-11eb-9a11-7a6cbd95e9e7.jpg" width="100%"></img>

## COUPON_LOG (쿠폰거래로그)
- 쿠폰의 거래가 발생될 때마다 생성되는 로그 정보이다. 쿠폰이 발행/사용/취소/폐기 등의 거래가 이루어 질때 로그가 생성된다.
<img src="https://user-images.githubusercontent.com/61044774/97957177-d997f000-1ded-11eb-8227-151b88e6f0c8.jpg" width="100%"></img>

## COUPON_NO_SEQ (쿠폰번호순번)
- 쿠폰번호 중복을 방지하고자 쿠폰기본정보 별로 일련번호 테이블을 활용한다.
<img src="https://user-images.githubusercontent.com/61044774/97956635-85d8d700-1dec-11eb-81f3-2c02ab0c06b8.jpg" width="100%"></img>

# 4. 실행

> Tips
- **만약 lombok 관련 오류가 발생하면 아래의 url을 참조해 주세요**  
[https://stackoverflow.com/questions/63418817/how-do-i-get-lombok-to-work-with-visual-studio-code](https://stackoverflow.com/questions/63418817/how-do-i-get-lombok-to-work-with-visual-studio-code)  
[https://planbsw.tistory.com/109](https://planbsw.tistory.com/109)


## 실행 하기

> 소스 main Application 실행하기
- com.milkit.app.DemoApplication 을 IDE에서 run하여 바로 실행할 수 있습니다.
 <img src="https://user-images.githubusercontent.com/61044774/91526490-b1e46180-e93e-11ea-9c03-6385d281d944.jpg" width="90%"></img>


# 5. 인증
> 서버에서 제공되는 api를 호출하기 위해서는 먼저 인증을 수행해야 합니다.
인증은 jwt 형식의 토큰방식으로 진행됩니다.
## 인증 요청
- http://localhost:8080/login URL로 POST로 인증정보를 전달합니다.
<img src="https://user-images.githubusercontent.com/61044774/93299272-c033e800-f82f-11ea-852d-9da348dfdf30.jpg" width="90%"></img>
  * 사용자 계정은 admin / test 혹은 test / test로 지정할 수 있습니다. (admin은 ROLE_ADMIN 권한, test는 ROLE_MEMBER 권한)
  * 사용자 계정은 POST Body에 다음과 같은 형식의 json 값을 설정합니다.
  ```javascript
  {
	"username" : "admin",
	"password" : "test"
  }
  ```
  * 사용자가 인증되었다면 서버는 Response body에 JWT Token 정보를 전달합니다.
  ```javascript
  {
    "code": "0",
    "message": "성공했습니다",
    "value": {
      "accessToken": "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VyTk0iOiLqtIDrpqzsnpAiLCJhdXRoUm9sZSI6IlJPTEVfQURNSU4iLCJuYW1lIjoiYWRtaW4iLCJleHAiOjE2MDAyMzQxMjgsImlhdCI6MTYwMDIzMjMyOH0.hYTzcG5nDhdVn4OVbrrH7ybSLwBxq1Fm2O9A60uk8Zw",
      "refreshToken": "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VyTk0iOiLqtIDrpqzsnpAiLCJhdXRoUm9sZSI6IlJPTEVfQURNSU4iLCJuYW1lIjoiYWRtaW4iLCJleHAiOjE2MDE0NDE5MzAsImlhdCI6MTYwMDIzMjMzMH0.MZLH17FUuUqYzlZDQ2AZDcRnSvxT2QJJeLHhiwtJFDo",
      "tokenType": "bearer"
    }
  }
  ```
---

## API 호출
- http://localhost:8080/api/api/coupon/publish 등과 같이 서버에서 제공하는 api를 호출하여 API 명세에 제공된 정보를 요청합니다.
<img src="https://user-images.githubusercontent.com/61044774/91528741-025dbe00-e943-11ea-81af-2e4ca5a1d261.jpg" width="90%"></img>
  * <span style="color:red">사용자는 API 호출 시 [4. 인증요청] 에서 응답받은 JWT Token 값을 HTTP Header의 Authorization 항목에 입력하여 전송하여야 합니다.</span>
    ex) Request HEADER의 Authorization 값 형식
    ```html
    Bearer eyJhbGciOiJIUzI1NiJ9.eyJ1c2VyTk0iOiLqtIDrpqzsnpAiLCJhdXRoUm9sZSI6IlJPTEVfQURNSU4iLCJuYW1lIjoiYWRtaW4iLCJleHAiOjE2MDAyMzkxODcsImlhdCI6MTYwMDIzNzM4N30.vp16ZPTySBEUJd3PxQd9ng3hnMBmOVoWrZksnXbw_5o
    ```
  * <span style="color:blue">서버는 API Request Header의 JWT Token을 확인하고 권한확인 및 접근제어를 수행합니다.</span>


# 6. API 명세
## 쿠폰발행
- 사용자는 쿠폰을 발급받고자 할때 서버에게 쿠폰발행을 요청합니다.

  * URL : POST http://localhost:8080/api/api/coupon/publish
  * 요청 Body


  ```javascript

  {
    "couponCD" : "000001",    /*  쿠폰코드  */
    "userID" : "milkit.moon"  /* 발행사용자정보 */
  }

  ```
  * 응답 Body
  ```javascript

  {
    "code": "0",
    "message": "성공했습니다",
    "value": {
      "couponCD": "000001",             /*  쿠폰코드  */
      "couponNO": "0000010000000012",   /*  쿠폰번호  */
      "userID": "milkit.moon",
      "pubDT": "20201027",
      "apprStartDT": "20201027",        /*  승인시작일자  */
      "apprEndDT": "20211027",          /*  승인종료일자  */
      "pubTime": "2020-10-27T08:35:39.675+00:00",
      "useTime": null,
      "faceAmt": 10000,                 /*  금액권쿠폰의 액면금액  */
      "dcRate": 0.0,
      "status": "2",                  /*  쿠폰상태 (1:등록, 2:발행, 3:사용, 4:폐기)  */
      "useYN": "Y",
      "couponNM": "1만원금액권쿠폰",   /*  쿠폰명  */
      "couponDiv": "10"               /*  쿠폰구분 (10:금액권, 20:할인권)  */
    }
  }

  ```

## 쿠폰사용
- 사용자는 발행된 쿠폰으로 서버에게 사용요청 할 수 있습니다.

  * URL : PUT http://localhost:8080/api/api/coupon/use
  * 요청 Body


  ```javascript

  {
    "couponNO" : "0000010000000012",   /*  쿠폰번호  */
    "userID" : "milkit.moon",          /*  사용자정보  */
    "reqUseAmt" : 7000                 /*  사용/할인 요청금액  */
  }

  ```
  * 응답 Body
  ```javascript

  {
    "code": "0",
    "message": "성공했습니다",
    "value": {
      "apprNO": "10000001603787772876",             /*  거래번호  */
      "apprTime": "2020-10-27T08:36:12.876+00:00",
      "faceAmt": 10000,                             /*  금액권쿠폰의 액면금액  */
      "useAmt": 7000,                               /*  사용된금액  */
      "changeAmt": 3000                             /*  거스름돈금액  */
    }
  }

  ```

> Tips : 해당쿠폰은 1만원권 금액쿠폰이고 7,000원을 사용했으며 3,000원을 거슬러주었다. (할인쿠폰의 경우 할인된 금액을 전달해 준다.)


## 쿠폰취소
- 사용자는 사용된 쿠폰을 취소하고자 한다. 취소된 쿠폰은 다시 사용할 수 있는 상태로 돌아간다.

  * URL : PUT http://localhost:8080/api/api/coupon/cancel
  * 요청 Body


  ```javascript

  {
    "couponNO" : "0000010000000012",    /*  쿠폰번호  */
    "userID" : "milkit.moon",           /*  사용자정보  */
    "apprNO" : "10000001603787772876"   /*  거래번호 (쿠폰사용 시 응답받은 거래번호)  */
  }

  ```
  * 응답 Body
  ```javascript

  {
    "code": "0",
    "message": "성공했습니다",
    "value": {
      "apprNO": "10000001603787812051",   /*  거래번호  */
      "apprTime": "2020-10-27T08:36:52.051+00:00",
      "faceAmt": 10000,
      "useAmt": -7000,                    /*  취소된금액 (사용된 금액의 -)  */
      "changeAmt": -3000                  /*  취소된거스름돈금액 (사용된 거스름돈금액의 -)  */
    }
  }

  ```

## 쿠폰폐기
- 사용자는 쿠폰을 폐기하고자 한다. 폐기된 쿠폰은 다시 사용할 수 없다. 또한 이미 사용된 쿠폰은 폐기할 수 없다.

  * URL : PUT http://localhost:8080/api/api/coupon/discard
  * 요청 Body


  ```javascript

  {
    "couponNO" : "0000010000000012",    /*  쿠폰번호  */
    "userID" : "milkit.moon"            /*  사용자정보  */
  }

  ```
  * 응답 Body
  ```javascript

  {
    "code": "0",
    "message": "성공했습니다",
    "value": {
      "apprNO": "10000001603787830671",             /*  거래번호  */
      "apprTime": "2020-10-27T08:37:10.676+00:00"
    }
  }

  ```

## 쿠폰조회
- 사용자는 쿠폰의 정보를 조회할 수 있습니다.

  * URL : GET http://localhost:8080/api/api/coupon/query?userID=[사용자정보]&couponNO=[쿠폰번호]
  * 요청 Body

  * 응답 Body
  ```javascript

  {
    "code": "0",
    "message": "성공했습니다",
    "value": {
      "couponCD": "000001",             /*  쿠폰코드  */
      "couponNO": "0000010000000012",   /*  쿠폰번호  */
      "userID": "milkit.moon",
      "pubDT": "20201027",
      "apprStartDT": "20201027",        /*  승인시작일자  */
      "apprEndDT": "20211027",          /*  승인종료일자  */
      "pubTime": "2020-10-27T08:35:39.675+00:00",
      "useTime": null,
      "faceAmt": 10000,                 /*  금액권쿠폰의 액면금액  */
      "dcRate": 0.0,
      "status": "2",                  /*  쿠폰상태 (1:등록, 2:발행, 3:사용, 4:폐기)  */
      "useYN": "Y",
      "couponNM": "1만원금액권쿠폰",   /*  쿠폰명  */
      "couponDiv": "10"               /*  쿠폰구분 (10:금액권, 20:할인권)  */
    }
  }

  ```
