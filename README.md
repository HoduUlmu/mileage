# TRIPLE 마일리지 서비스 과제

## 서버 구동 시 2명의 유저와 2 곳의 장소 추가

## MYSQL DB NAME : mileage

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



## add, mod, delete

이미지의 경우 여러 개의 이미지가 저장되거나 삭제되므로 bulk 연산을 사용합니다.

조회가 자주 일어나는 place_id에 인덱스를 생성했습니다.



## 개선사항

@Async와 CompletetableFuture를 활용한 비동기 처리를 통해 이미지 테이블에 대한 동작과 리뷰 테이블에 대한 동작을 수행한다면 응답시간이 더욱 빨라질 것이라 생각합니다.



## 트러블 슈팅

- PK

BINARY UUID, Auto_Increment BIGINT 둘 중 어느걸 PK로 사용할지 고민했습니다.

UUID가 PK일 경우 순차적인 UUID가 아니라면 Mysql에 insert 시 성능이 좋지 않고 또한 api를 통해 전달되는 값은 pk가 아닌 것이 좋습니다.

이에 따라 UUID를 유니크 키로 Auto_Increment를 PK로 설정할까 했지만 이럴 경우 api의 동작이 모두 UUID 기반으로 이루어지기 때문에 DB에서 쿼리가 추가로 나가게 된다는 단점이 있어 UUID를 PK로 선정했습니다.

