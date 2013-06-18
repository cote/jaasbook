<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<html>
<head>
<title><tiles:getAsString name="title"/></title>
<link rel="stylesheet" href="style.css"/>
</head>
<body>

<h1><tiles:getAsString name="title"/></h1>

<tiles:insert attribute="body"/>

<hr/>
<a href="<%= request.getContextPath() %>/">Home</a> |
<a href="<%= request.getContextPath() %>/list-users.do">Users
</body>
</html>
