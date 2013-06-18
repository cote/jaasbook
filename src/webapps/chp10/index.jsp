<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head><title>Chapter 10 Index</title></head>
<body>

<a href="admin">Admin Page</a> | 
<a href="customer">Customer Page</a> |
<a href="permission-check.jsp">Check Permissions</a> |
<a href="logout.jsp">Logout</a>

<p/>

<c:if test="${subject != null}">
<b>javax.security.auth.Subject logged in:</b>
<c:forEach items="${subject.principals}" var="principal">
 <p>
 Principal Name: ${principal.name}<br/>
 Principal Type: ${principal.class}
 </p>
</c:forEach>
<c:forEach items="${subject.publicCredentials}" var="credential">
 <p>
 Credential: ${credential}<br/>
 Credential Type: ${credential.class}
 </p>
</c:forEach>
</c:if>



</body>
</html>
