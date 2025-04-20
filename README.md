Schedlr is an AI-powered social media management tool designed to simplify and automate content creation, scheduling, and performance tracking. It helps users—especially influencers, marketers, and small businesses—manage posts across multiple platforms like LinkedIn, Instagram, and Twitter from a single dashboard. Key features include:

AI-generated content assistant

Multi-platform post scheduling

Post performance analytics

Built-in marketplace for content exchange

Profile and team collaboration tools

Schedlr is built using ReactJS (frontend), Spring Boot (backend), MySQL (database)










# schedlr-api-integration


#Database schema
use schedlr;
CREATE TABLE users (
userid INT AUTO_INCREMENT PRIMARY KEY,
username VARCHAR(50) NOT NULL,
email VARCHAR(100) NOT NULL,
password VARCHAR(255) NOT NULL,
account_type ENUM('PERSONAL', 'INFLUENCER') NOT NULL DEFAULT 'personal'
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

use schedlr;
CREATE TABLE post_upload (
post_id INT AUTO_INCREMENT PRIMARY KEY,
user_id INT NOT NULL,
image LONGBLOB,
description TEXT,
fb_post_id VARCHAR(100),
pinterest_post_id VARCHAR(100),
twitter_post_id VARCHAR(100),
linkedin_post_id VARCHAR(100),
post_upload_date datetime,
CONSTRAINT FOREIGN KEY (user_id) REFERENCES users(userid)
);

CREATE TABLE schedlr.schedule_post_upload (
post_id INT AUTO_INCREMENT PRIMARY KEY,
user_id INT NOT NULL,
image longblob,
description TEXT,
schedule_time DATETIME NOT NULL,
fb BOOLEAN DEFAULT FALSE,
pinterest BOOLEAN DEFAULT FALSE,
twitter BOOLEAN DEFAULT FALSE,
linkedin BOOLEAN DEFAULT FALSE,
CONSTRAINT FOREIGN KEY (user_id) REFERENCES users(userid)
);

CREATE TABLE schedlr.influencers (
influencer_id INT AUTO_INCREMENT PRIMARY KEY,
userid INT NOT NULL,
profile_pic longblob NOT NULL,
bio longtext NOT NULL,
linkedin_followers INT DEFAULT 0,
pinterest_followers INT DEFAULT 0,
twitter_followers INT DEFAULT 0,
linkedin_profile VARCHAR(255),
pinterest_profile VARCHAR(255),
twitter_profile VARCHAR(255),
price_per_photo INT NOT NULL,
price_per_video INT NOT NULL,
price_per_tweet INT NOT NULL,
tags JSON,
FOREIGN KEY (userid) REFERENCES users(userid) ON DELETE CASCADE
);

CREATE TABLE schedlr.collaboration (
id INT AUTO_INCREMENT PRIMARY KEY,
user_id INT NOT NULL,
influencer_id INT NOT NULL,
message longtext,
status ENUM('PENDING', 'ACCEPTED', 'REJECTED', 'PAYMENT_PENDING', 'COMPLETED') DEFAULT 'PENDING',
collaboration_token VARCHAR(255) NOT NULL UNIQUE,
FOREIGN KEY (user_id) REFERENCES schedlr.users(userid),
FOREIGN KEY (influencer_id) REFERENCES schedlr.users(userid),
CONSTRAINT check_user_influencer CHECK (user_id <> influencer_id)
);

# To install java in AWS EC2 Amazon
sudo dnf install java-17-amazon-corretto

# To run the jar file continuosly in the AWS EC2 Instance
nohup java -jar jarfile.jar > output.log 2>&1 &
