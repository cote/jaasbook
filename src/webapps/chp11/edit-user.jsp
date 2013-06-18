<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html:form action="save-user">
<p><b>Username:</b> <html:text value="userRow.username"/></p>
<p><b>Groups:</b>
  Admin: <html:checkbox property="userRow.admin"/>
  Customer: <html:checkbox property="userRow.customer"/>
</p>
<html:submit value="Save"/>
</html:form>