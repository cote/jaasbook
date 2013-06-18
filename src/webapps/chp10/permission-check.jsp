<%@ taglib uri="perm-tags" prefix="perm" %>
<html>
<head><title>Permission Check</title></head>
<body>
<perm:granted type="java.io.FilePermission"
               name="/tmp/test.txt"
               actions="read,write">
Granted FilePermission to read and write to /tmp/test.txt
</perm:granted>
<perm:notGranted type="java.io.FilePermission"
                  name="/tmp/test.txt"
                  actions="read,write">
Not granted FilePermission to read and write to /tmp/test.txt
</perm:notGranted>
</body>
</html>
