INSERT INTO permission VALUES
('file-perm-id','java.io.FilePermission','/tmp/test.txt','read,write')

INSERT INTO principal_permission VALUES
('admin-principal-id','file-perm-id')
