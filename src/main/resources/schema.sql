DROP TABLE review IF EXISTS;
DROP TABLE place IF EXISTS;
DROP TABLE image IF EXISTS;
DROP TABLE users IF EXISTS;

CREATE TABLE users(
    user_id BINARY(16) NOT NULL PRIMARY KEY,
    email VARCHAR(255) NOT NULL,
    point BIGINT
);

CREATE TABLE place(
    place_id BINARY(16) NOT NULL PRIMARY KEY
);

CREATE TABLE image(
    image_id BINARY(16) NOT NULL PRIMARY KEY
);

CREATE TABLE review(
    review_id BINARY(16) NOT NULL PRIMARY KEY,
    content VARCHAR(255),
    place_id BINARY(16) NOT NULL,
    user_id BINARY(16) NOT NULL,
    FOREIGN KEY (place_id) REFERENCES place(place_id),
    FOREIGN KEY (user_id) REFERENCES users(user_id)
);