<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<div class="note">

  <c:out value="${noteForm.note.content}" escapeXml="false" default="(No content.)"/>

  <div class="note-date">
    Last Modified: <fmt:formatDate type="both" dateStyle="short" timeStyle="short" value="${noteForm.note.lastModified}"/>
    <br/>
    Created: <fmt:formatDate type="both" dateStyle="short" timeStyle="short" value="${noteForm.note.created}"/>
  </div>
</div>

<c:url var="editUrl" value="/edit-note.do">
  <c:param name="id">${noteForm.id}</c:param>
</c:url>
<a href="${editUrl}">Edit</a>| <a href="index.jsp">List Notes</a>
