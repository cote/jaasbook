-- these tables depend on chp04-tables.sql
CREATE TABLE permission
  (
  id varchar(64) NOT NULL,
  permissionClass varchar(256) NOT NULL,
  name varchar(64),
  actions varchar(256),
  PRIMARY KEY ( id  )
  );

CREATE TABLE principal_permission
  (
  principal_id varchar(64) NOT NULL,
  permission_id varchar(64) NOT NULL
  );
