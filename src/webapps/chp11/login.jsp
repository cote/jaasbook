<html>
<head><title>Login</title></head>
<link rel="stylesheet" href="<%= request.getContextPath() %>/style.css"/>
<body>

<form method="POST" action="<%= request.getContextPath() %>/j_security_check">
<p>Username: <input type="text" name="j_username"/></p>
<p>Password: <input type="password" name="j_password"/></p>
<input type="submit" value="Login"/>
</form>

</body>
</html>
