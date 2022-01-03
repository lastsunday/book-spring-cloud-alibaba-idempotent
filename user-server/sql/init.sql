CREATE database spring_cloud_alibaba_practice_idempotent;
USE spring_cloud_alibaba_practice_idempotent;

CREATE TABLE user (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
	tenant_id BIGINT,
    name VARCHAR(255)
);

INSERT INTO user(id,tenant_id,name) VALUES(1,1,"Tom");
INSERT INTO user(id,tenant_id,name) VALUES(2,2,"Tom");

CREATE TABLE user_addr(
	id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT,
    name VARCHAR(255)
);

INSERT INTO user_addr(id,user_id,name) VALUES(1,1,"Tenant 1 addr");
INSERT INTO user_addr(id,user_id,name) VALUES(2,2,"Tenant 2 addr");

CREATE TABLE product(
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    good_id BIGINT,
    good_name VARCHAR(255),
    num BIGINT,
    version int DEFAULT 0
);
INSERT INTO product(good_id,good_name,num) VALUES(1,'testA',100);