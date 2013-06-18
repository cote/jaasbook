<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>

<html:errors/>

<html:form action="/save-note">

<%-- Note that we don't need to conditionally put in
     the values for pre-existing notes --%>
    <bean:message key="note.title.label"/> <html:text property="note.title"/><br/>
    <html:textarea property="note.content" cols="50" rows="10"/><br/>

    <c:if test="${noteForm.note.id != null}">
    <html:hidden property="note.id"/>
    <div class="note-date">
      <%-- Notice that we have to put 'noteForm' in front of the EL's below.
           This is because the JSTL tags are not aware of the Struts nesting. --%>
      <bean:message key="note.last.modified.label"/>
      <fmt:formatDate type="both" dateStyle="short" timeStyle="short" value="${noteForm.note.lastModified}"/>
      <br/>
      <bean:message key="note.created.label"/>
      <fmt:formatDate type="both" dateStyle="short" timeStyle="short" value="${noteForm.note.created}"/>
    </div>
    </c:if>

<html:submit property="submit" value="Save"/>

</html:form>

