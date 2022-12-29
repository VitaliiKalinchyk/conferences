<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<jsp:useBean id="now" class="java.util.Date"/>
<fmt:formatDate value="${now}" pattern="yyyy-MM-dd" var="nowFormatted"/>

<c:out value="${nowFormatted}"/>