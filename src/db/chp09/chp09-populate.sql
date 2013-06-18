INSERT INTO app_configuration VALUES
('chp09', 'chp09.TomcatLoginModule', 'REQUIRED');

INSERT INTO db_user VALUES 
('admin-user-id', 'admin', 'secret');

INSERT INTO db_user VALUES 
('customer-user-id', 'customer', 'secret');

INSERT INTO principal VALUES 
('admin-principal-id', 
'admin', 
'chp04.UserGroupPrincipal');

INSERT INTO principal_db_user VALUES
('admin-user-id','admin-principal-id');

INSERT INTO principal VALUES 
('customer-principal-id', 
'customer', 
'chp04.UserGroupPrincipal');

INSERT INTO principal_db_user VALUES
('customer-user-id','customer-principal-id')