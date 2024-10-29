CREATE TABLE "user" (
    id SERIAL PRIMARY KEY,
    login VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    tel_number VARCHAR(20) NOT NULL,
    bonus_point INT DEFAULT 0,
    role_id INT NOT NULL REFERENCES role(roleId) ON DELETE CASCADE
);

CREATE TABLE album (
    id SERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    release_date DATE
);

CREATE TABLE audio_track (
    id SERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    artist_id INT NOT NULL REFERENCES artist(id) ON DELETE CASCADE,
    genre_id INT NOT NULL REFERENCES genre(id) ON DELETE CASCADE,
    album_id INT NOT NULL REFERENCES album(id) ON DELETE CASCADE,
    price DECIMAL(10, 2) NOT NULL
);

CREATE TABLE "order" (
    id SERIAL PRIMARY KEY,
    user_id INT NOT NULL REFERENCES "user"(id) ON DELETE CASCADE,
    total_price DECIMAL(10, 2) NOT NULL,
    order_date DATE NOT NULL,
    status VARCHAR(20) NOT NULL
);

CREATE TABLE order_item (
    id SERIAL PRIMARY KEY,
    order_id INT NOT NULL REFERENCES "order"(id) ON DELETE CASCADE,
    audio_track_id INT NOT NULL REFERENCES audio_track(id) ON DELETE CASCADE,
    price DECIMAL(10, 2) NOT NULL,
    user_id INT NOT NULL REFERENCES "user"(id) ON DELETE CASCADE
);

CREATE TABLE review (
    review_id SERIAL PRIMARY KEY,
    user_id INT NOT NULL REFERENCES "user"(id) ON DELETE CASCADE,
    audio_track_id INT NOT NULL REFERENCES audio_track(id) ON DELETE CASCADE,
    rating INT NOT NULL CHECK (rating >= 1 AND rating <= 5),
    comment TEXT NOT NULL,
    review_date DATE NOT NULL
);

CREATE TABLE artist (
    id SERIAL PRIMARY KEY,
    artist_name VARCHAR(255) NOT NULL
);

CREATE TABLE genre (
    id SERIAL PRIMARY KEY,
    genre_name VARCHAR(255) NOT NULL
);

CREATE TABLE role (
    roleId SERIAL PRIMARY KEY,
    role_name VARCHAR(255) NOT NULL UNIQUE
);
