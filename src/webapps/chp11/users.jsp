<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<table class="data">
<tr><th>Username</th><th>Groups</th></tr>

<c:forEach items="${listUsersForm.userRows}" var="user">
<tr>
 <td>
   <a href="<%= request.getContextPath() %>/edit-user.do?id=${user.id}">
   <c:out value="${user.username}"/></a>
 </td>
 <td>
  <c:if test="${user.admin}">Admin</c:if>
  <c:if test="${user.customer}">Customer</c:if>
  <c:if test="${!user.admin && !user.customer}">&lt;None&gt;</c:if>
 </td>
</tr>
</c:forEach>

</table>