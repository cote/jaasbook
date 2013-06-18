<%@ taglib uri="auth-tags" prefix="auth" %>
<html>
<head><title>Index</title></head>
<body>

<a href="admin">Admin Page</a> | 
<a href="customer">Customer Page</a> |
<a href="logout.jsp">Logout</a>

<% if (request.isUserInRole("customer")) { %>
<p>Only the <b>customer</b> role sees this.</p>
<% } %>

<% if (request.isUserInRole("admin") || request.isUserInRole("superadmin")) { %>
<p>Only the <b>admin</b> role sees this.</p>
<% } %>

<p>Principals: <%= request.getUserPrincipal() %>.</p>

</body>
</html>
