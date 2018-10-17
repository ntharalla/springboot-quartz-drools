CREATE SEQUENCE HIBERNATE_SEQUENCE
  START WITH 1
  INCREMENT BY 1
  MINVALUE 1
  CACHE 20;

CREATE TABLE starter_test
(
  ID                  NUMERIC(19) PRIMARY KEY NOT NULL,
  COLUMN1			  VARCHAR(255) NOT NULL,
  COLUMN2             VARCHAR(255) DEFAULT NULL

);


insert into starter_test(id,column1,column2) values(nextval('hibernate_sequence'),'test1','test1');