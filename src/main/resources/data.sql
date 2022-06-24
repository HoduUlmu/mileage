INSERT INTO users(user_id, email, points)
values (UUID_TO_BIN('3f06af63-a93c-11e4-9797-00505690773f'), 'kim@naver.com', 0),
       (UUID_TO_BIN('2be2042f-5df1-4173-80ec-21c324e18779'), 'triple@naver.com', 0);

INSERT INTO place(place_id)
values (UUID_TO_BIN('ef105b41-1baf-4ac3-b459-adb47b2b5806')),
       (UUID_TO_BIN('62422ed1-17e1-493d-ad7c-e7351231d7e5'));