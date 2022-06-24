# TRIPLE 마일리지 서비스 과제

## 서버 구동 시 2명의 유저와 2 곳의 장소 추가

USER UUID : 

'3f06af63-a93c-11e4-9797-00505690773f', 

'2be2042f-5df1-4173-80ec-21c324e18779'

PLACE UUID : 

'ef105b41-1baf-4ac3-b459-adb47b2b5806',

'62422ed1-17e1-493d-ad7c-e7351231d7e5'

## MYSQL DB NAME : mileage

부득이하게 로컬 DB로 진행했습니다. 양해바랍니다.

| API             | URL                        | METHOD | REQUEST                                                      | RESPONSE                                 |
| --------------- | -------------------------- | ------ | ------------------------------------------------------------ | ---------------------------------------- |
| review_add      | /events                    | POST   | {<br/>"type": "REVIEW",<br/>"action": "ADD",<br/>"reviewId": "240a0658-dc5f-4878-9381-ebb7b2667772",<br />"content": "좋아요!",<br/>"attachedPhotoIds": ["e4d1a64e-a531-46de-88d0-ff0ed70c0bb8", "afb0cef2-851d-4a50-bb07-9cc15cbdc332", "93a76329-a1ef-49cf-b540-375e26963b38", "fd509c04-755e-445f-b2de-c8be40ceabef"],<br/>"userId": "3f06af63-a93c-11e4-9797-00505690773f",<br/>"placeId": "ef105b41-1baf-4ac3-b459-adb47b2b5806"<br/>} |                                          |
| review_mod      | /events                    | POST   | {<br/>"type": "REVIEW",<br/>"action": "MOD",<br/>"reviewId": "240a0658-dc5f-4878-9381-ebb7b2667772",<br/>"content": "",<br/>"attachedPhotoIds": ["e4d1a64e-a531-46de-88d0-ff0ed70c0bb8", "7c22181d-8586-436a-aa64-2d337eb90493"],<br/>"userId": "3f06af63-a93c-11e4-9797-00505690773f",<br/>"placeId": "ef105b41-1baf-4ac3-b459-adb47b2b5806"<br/>} |                                          |
| review_delete   | /events                    | POST   | {<br/>"type": "REVIEW",<br/>"action": "DELETE",<br/>"reviewId": "240a0658-dc5f-4878-9381-ebb7b2667772",<br/>"content": "좋아요!",<br/>"attachedPhotoIds": ["e4d1a64e-a531-46de-88d0-ff0ed70c0bb8", "afb0cef2-851d-4a50-bb07-9cc15cbdc332"],<br/>"userId": "3f06af63-a93c-11e4-9797-00505690773f",<br/>"placeId": "ef105b41-1baf-4ac3-b459-adb47b2b5806"<br/>} |                                          |
| get_user_points | /points?userId={user_UUID} | GET    |                                                              | { "userId":{user_UUID},<br />''point": 0 |

## DB SCHEMA

```sql
CREATE TABLE users
(
    user_id     BINARY(16) NOT NULL PRIMARY KEY,
    email       VARCHAR(255) NOT NULL,
    points       BIGINT,
    created_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    modified_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE place
(
    place_id    BINARY(16) NOT NULL PRIMARY KEY,
    created_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    modified_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE review
(
    review_id   BINARY(16) NOT NULL PRIMARY KEY,
    content     VARCHAR(255),
    given_point BIGINT NOT NULL,
    place_id    BINARY(16) NOT NULL,
    user_id     BINARY(16) NOT NULL,
    created_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    modified_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (place_id) REFERENCES place (place_id),
    FOREIGN KEY (user_id) REFERENCES users (user_id),
    INDEX p_idx (place_id)
);

CREATE TABLE image
(
    image_id    BINARY(16) NOT NULL PRIMARY KEY,
    review_id   BINARY(16) NOT NULL,
    created_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    modified_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (review_id) REFERENCES review (review_id),
    INDEX r_idx (review_id)
);

CREATE TABLE point_history
(
    history_id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    user_id BINARY(16) NOT NULL,
    event_id BINARY(16),
    event_type VARCHAR(20) NOT NULL,
    event_action VARCHAR(10),
    point_change BIGINT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users (user_id)
);
```



## 컨셉 및 설명

/events url 하나로 모든 요청이 온다고 가정했습니다. (user 포인트 조회 제외)

type의 경우 요청이 왔을 때 type을 미리 확인 후 어노테이션 기반으로 핸들러 매칭을 합니다.

action의 경우 미리 map에 넣어둔 후 맞는 action에 따라 메소드를 호출합니다.

event 컨트롤러단에서 aop를 이용해 validation을 진행합니다.



동작은 type이 REVIEW일 시

filter를 통해 type 파악 -> 어노테이션 기반 핸들러 매핑 ->  EventController의 reivewEvents() 호출 및 Validation -> ActionMapper를 통해 action에 맞는 ReviewController의 메소드 호출 -> 서비스 메소드 호출

입니다.



## add, mod, delete

이미지의 경우 여러 개의 이미지가 저장되거나 삭제되므로 bulk 연산을 사용합니다.

조회가 자주 일어나는 place_id에 인덱스를 생성했습니다.



## 개선사항

@Async와 CompletetableFuture를 활용한 비동기 처리를 통해 이미지 테이블에 대한 동작과 리뷰 테이블에 대한 동작을 수행한다면 응답시간이 더욱 빨라질 것이라 생각합니다.



## 트러블 슈팅

- PK

문제 상황:

 PK로 BINARY UUID, Auto_Increment BIGINT 중 PK 선택



의견: 

1. UUID가 PK일 경우 순차적인 UUID가 아니라면 Mysql에 insert 시 성능이 좋지 않고 또한 api를 통해 전달되는 값은 pk가 아닌 것이 좋습니다. 
2. UUID를 유니크 키로 Auto_Increment를 PK로 설정할 시 api의 동작이 UUID 기반이라 DB에서 쿼리가 추가로 나가게 된다는 단점



선택:

UUID를 PK로 선정했습니다.



- TYPE 및 ACTION 처리

문제 상황 : URL 하나를 통해 들어오는 요청이 다양해 Controller 단에서 url로 구분하여 매핑할 수 없음

해결 과정 : 

1. IF문으로 처리할 시 TYPE과 ACTION의 개수가 늘어나면 처리 속도가 느려질 수 있음
2. ACTION의 경우 Spring이 핸들러 매핑을 수행하는 것을 본따 미리 map에 action에 대한 메소드를 넣어두고 요청이 올 때 꺼내 호출, 동적 메소드 호출에 따른 추가 오류 부담해야함
3. Filter 단에서 Type 파악, Controller 단에서 Type 파악 -> filter 단에서 파악
   - Controller 단에서 파악할 경우 후 처리가 복잡해짐 더해서 validation 과정 역시 복잡해짐
   - 어노테이션 기반으로 핸들러 매핑을 새로 정의한 후 Filter에서 Type을 파악하면 이후 처리가 깔끔함
   - 단 두 동작 모두 request body를 두번 읽는다는 단점이 있음