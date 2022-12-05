INSERT INTO USER_TBL (name, email, flag) VALUES ('Tommy', 'tommy@gmail.com', true);
INSERT INTO USER_TBL (name, email, flag) VALUES ('Ben', 'ben@gmail.com', true);

INSERT INTO COURSE_TBL (count, name, user_id) VALUES (1, 'Ben', 2);
INSERT INTO COURSE_TBL (count, name, user_id) VALUES (0, 'Tommy', 1);

INSERT INTO BLOG_POST_TBL (name, description, url, image, course_id) VALUES ('Java', 'java description', 'url', 'image', 1);