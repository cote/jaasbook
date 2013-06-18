CREATE TABLE app_configuration
  (
  appName varchar(32) NOT NULL,
  loginModuleClass varchar(256) NOT NULL,
  controlFlag varchar(10)
  );
  
CREATE TABLE db_user
  (
  id varchar(64) NOT NULL,
  username varchar(32) NOT NULL,
  password varchar(32),
  PRIMARY KEY ( id )
  );

ALTER TABLE db_user ADD CONSTRAINT db_user_username UNIQUE (username);

CREATE TABLE principal
  (
  id varchar(64) NOT NULL,
  name varchar(32) NOT NULL,
  class varchar(256) NOT NULL,
  PRIMARY KEY ( id )
  );

CREATE TABLE principal_db_user
  (
  user_id varchar(32) NOT NULL,
  principal_id varchar(32) NOT NULL
  );
