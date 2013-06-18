<%@ taglib uri="auth-tags" prefix="auth" %>
<html>
<head><title>Index</title></head>
<body>

<a href="admin">Admin Page</a> | 
<a href="customer">Customer Page</a> |
<a href="logout.jsp">Logout</a>

<auth:roles roles="customer">
<p>Only the <b>customer</b> role sees this.</p>
</auth:roles>

<auth:roles roles="admin,superadmin">
<p>Only the <b>admin</b> role sees this.</p>
</auth:roles>

<p>Principals: <%= request.getUserPrincipal() %>.</p>

</body>
</html>
