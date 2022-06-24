DROP TABLE IF EXISTS image;
DROP TABLE IF EXISTS review;
DROP TABLE IF EXISTS place;
DROP TABLE IF EXISTS point_history;
DROP TABLE IF EXISTS users;

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
    FOREIGN KEY (user_id) REFERENCES users (user_id),
    INDEX e_idx (event_id)
);