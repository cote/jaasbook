<html>
<head><title>Customer Page</title></head>
<body>

<h1>Customer Page</h1>

<p>Username: <%= request.getRemoteUser() %></p>

<p>Servlet Principal: <%= request.getUserPrincipal() %></p>

<p><a href="../">Home</a></p>

</body>
</html>
