<html>
<head><title>Logout</title></head>
<body>

<%
try
  {
  session.invalidate();
  }
catch(IllegalStateException e)
  {
  // we don't care
  }
%>

<p>You've been logged out.</p>
<p><a href="<%= request.getContextPath() %>/index.jsp">Home</a></p>

</body>
</html>
