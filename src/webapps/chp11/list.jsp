<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<html:link action="/add-note">Add</html:link>
<hr class="title-line"/>
<html:errors/>
<%--
 We can't put <c:if/> around this because the XML
 will become invalid. But, it doesn't hurt to just
 throw the form in there in all instances. It's
 just a little anoying.
--%>
<html:form action="/delete-notes">

<table class="notes-table">
<c:choose>

  <c:when test="${empty notesForm.notes}">
  <tr><td colspan="3" class="empty-notes">No notes.</td></tr>
  </c:when>

  <c:otherwise>
  <tr><td><input type="submit" value="Delete"/></td><th>Title</th><th>Last Modified</th></tr>
  <c:forEach items="${notesForm.notes}" var="note">
    <c:url var="viewUrl" value="/view-note.do">
      <c:param name="id">${note.id}</c:param>
    </c:url>
           
    <tr>
      <td>
	<html:multibox property="ids" value="${note.id}"/>
      </td>
      <td>
        <a href="${viewUrl}"><c:out value="${note.title}"/></a>
      </td>
      <td>
        <fmt:formatDate type="both" dateStyle="short" timeStyle="short" value="${note.created}"/>
      </td>
    </tr>
  </c:forEach>
  <tr><td>
    <html:submit property="submit" value="Delete"/>
  </td><td></td><td></td></tr>
  </c:otherwise>
</c:choose>
</table>


</html:form>

