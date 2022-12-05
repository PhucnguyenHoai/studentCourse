INSERT INTO USER_TBL (name, email, flag) VALUES ('Tommy', 'tommy@gmail.com', true);
INSERT INTO USER_TBL (name, email, flag) VALUES ('Ben', 'ben@gmail.com', true);

INSERT INTO COURSE_TBL (count, name, user_id) VALUES (0, 'Ben', 2);
INSERT INTO COURSE_TBL (count, name, user_id) VALUES (0, 'Tommy', 1);

INSERT INTO BLOG_POST_TBL (name, description, url, image, course_id) VALUES ('Java', 'java description', 'url', 'image', 1);

INSERT INTO CONTENT_TBL (type, content, blog_post_id) VALUES ('code', 'Content', 1);

INSERT INTO PROXY_TBL (address, port, protocol, status, user_id) VALUES ('192.168.1.1', 80,'http', 200, 1);