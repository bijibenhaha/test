CREATE TABLE IF NOT EXISTS t_user (
    id           BIGINT       NOT NULL AUTO_INCREMENT,
    username     VARCHAR(32) NOT NULL,
    age          INT,
    phone        VARCHAR(32),
    create_time  TIMESTAMP    DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id)
);
