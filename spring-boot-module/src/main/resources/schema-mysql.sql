DROP TABLE users;
DROP TABLE items;
DROP TABLE roles;

CREATE TABLE IF NOT EXISTS users(
id BIGINT  auto_increment PRIMARY KEY,
username VARCHAR(70) NOT NULL UNIQUE,
password VARCHAR(70) NOT NULL,
role_id BIGINT NOT NULL,
deleted BOOLEAN DEFAULT FALSE NOT NULL
);

CREATE TABLE IF NOT EXISTS roles(
id BIGINT auto_increment PRIMARY KEY,
name VARCHAR(255) NOT NULL,
status VARCHAR(31) NOT NULL DEFAULT 'READY'
);

CREATE TABLE IF NOT EXISTS items(
id BIGINT  auto_increment PRIMARY KEY,
name VARCHAR(80) ,
status VARCHAR(10),
deleted BOOLEAN DEFAULT FALSE NOT NULL
);

ALTER TABLE users ADD FOREIGN KEY(role_id) REFERENCES roles(id);

INSERT INTO roles (name) VALUES('Customer');
INSERT INTO roles (name) VALUES('Administrator');

INSERT INTO users (username,password,role_id)
VALUES('user','$2a$04$ujFLCfnVxKEbf08RnrtqHuRZ1oCpymraVCN.X2TIW6z27OHElSy7S',(SELECT id FROM roles WHERE name='Customer'));

INSERT INTO users (username,password,role_id)
VALUES('admin','$2a$04$57WmpuHQm.MN.v/5JgPgO.aLkT3tSggZ9.P4/Dwt8aqUj2dhKc8h2',(SELECT id FROM roles WHERE name='Administrator'));

INSERT INTO items (name,status) VALUES('item_1','READY');
INSERT INTO items (name,status) VALUES('item_2','READY');
INSERT INTO items (name,status) VALUES('item_3','READY');
INSERT INTO items (name,status) VALUES('item_4','READY');
INSERT INTO items (name,status) VALUES('item_5','READY');
INSERT INTO items (name,status) VALUES('item_6','READY');
INSERT INTO items (name,status) VALUES('item_7','READY');
INSERT INTO items (name,status) VALUES('item_8','READY');


