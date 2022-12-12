INSERT INTO USER_TBL (name, email, flag) VALUES ('Tommy', 'tommy@gmail.com', true);
INSERT INTO USER_TBL (name, email, flag) VALUES ('Ben', 'ben@gmail.com', true);
INSERT INTO USER_TBL (name, email, flag) VALUES ('Fat', 'fat@gmail.com', false);

INSERT INTO COURSE_TBL (count, name, user_id, flag) VALUES (0, 'Ben', 2, true);
INSERT INTO COURSE_TBL (count, name, user_id, flag) VALUES (0, 'Tommy', 1, false);
INSERT INTO COURSE_TBL (count, name, user_id, flag) VALUES (0, 'Fat', 3, true);

INSERT INTO BLOG_POST_TBL (name, description, url, image, course_id) VALUES ('Java', 'java description', 'url', 'image', 1);

INSERT INTO CONTENT_TBL (type, content, blog_post_id) VALUES ('code', 'Content', 1);

INSERT INTO PROXY_TBL (address, port, protocol, status, user_id) VALUES ('192.168.1.1', 80,'http', 200, 1);

--cf login -u knkinhu@gmail.com -p phuc55
--cf push example -p build/libs/example-1.0-SNAPSHOT.jar
--cf push example -p /Users/nguyenphuc/Downloads/example/build/libs/example-1.0-SNAPSHOT.jar -b https://github.com/cloudfoundry/java-buildpack.git
--cf set-env example JBP_CONFIG_SPRING_AUTO_RECONFIGURATION: '{enabled: false}'
--cf delete -r my-example-app

--git config --global user.name "your_github_username"
--git config --global user.email "your_github_email" git config -l
--$ Username for 'https://github.com' : username
--$ Password for 'https://github.com' : give your personal access token here
--git config --global credential.helper cache