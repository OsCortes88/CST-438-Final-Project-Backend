create table user_table
(
    id identity primary key,
    email      varchar(25) unique,
    first_name varchar(25),
    last_name  varchar(25),
    password   varchar(100),
    role       varchar(25)
);
CREATE TABLE genre (
    id INT,
    genre VARCHAR(45),
    popularity INT,
    PRIMARY KEY (id)
);

CREATE TABLE purchase_site (
    gameId INT,
    site VARCHAR(200),
    PRIMARY KEY (gameId)
);

CREATE TABLE screenshot (
    gameId INT,
    image_url VARCHAR(200),
    PRIMARY KEY (gameId)
);

CREATE TABLE trailer (
    gameId INT,
    name VARCHAR(45),
    preview VARCHAR(1000),
    PRIMARY KEY (gameId)
);

CREATE TABLE video_game (
    gameId INT,
    name VARCHAR(45),
    description VARCHAR(5000),
    released VARCHAR(45),
    background_image VARCHAR(200),
    rating DECIMAL,
    playtime INT,
    esrb VARCHAR(45),
    PRIMARY KEY (gameId)
);

CREATE TABLE wish_list (
    userId INT,
    gameId INT,
--     TODO: (CARLOS OR FERNANDO) Add remaining fields related to the game (except vendors, trailers, and screenshots)
    PRIMARY KEY (userId),
    FOREIGN KEY (gameId) REFERENCES video_game (gameId) on delete cascade
);