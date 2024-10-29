# schedlr-api-integration


#Database schema

use schedlr;
CREATE TABLE users (
    userid INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL,
    email VARCHAR(100) NOT NULL,
    password VARCHAR(255) NOT NULL,
    account_type ENUM('personal', 'business') NOT NULL DEFAULT 'personal'
);
CREATE TABLE profiles (
    profile_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    face_book_access_token VARCHAR(255),
    face_book_access_token_expire_date DATETIME,
    linked_in_access_token longtext,
    linked_in_access_token_expire_date DATETIME,
    linked_in_person_id VARCHAR(100),
    insta_access_token VARCHAR(255),
    insta_access_token_expire_date DATETIME,
    twitter_access_token longtext,
    twitter_access_token_expire_date DATETIME,
    pinterest_access_token longtext,
    pinterest_access_token_expire_date DATETIME,
    CONSTRAINT FOREIGN KEY (user_id) REFERENCES users(userid)
);

CREATE TABLE post_upload (
    post_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    image VARCHAR(255),
    description TEXT,
    fbPost_id VARCHAR(100),
    insta_post_id VARCHAR(100),
    twitter_post_id VARCHAR(100),
    linked_in_post_id VARCHAR(100),
    post_upload_date datetime,
    CONSTRAINT FOREIGN KEY (user_id) REFERENCES users(userid)
);

CREATE TABLE schedule_post_upload (
    post_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    image VARCHAR(255),
    description TEXT,
    schedule_time DATETIME NOT NULL,
    fb BOOLEAN DEFAULT FALSE,
    insta BOOLEAN DEFAULT FALSE,
    twitter BOOLEAN DEFAULT FALSE,
    linked_in BOOLEAN DEFAULT FALSE,
    CONSTRAINT FOREIGN KEY (user_id) REFERENCES users(userid)
);
