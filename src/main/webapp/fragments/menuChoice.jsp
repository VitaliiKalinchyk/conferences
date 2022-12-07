<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<fmt:setBundle basename="resources"/>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>

<c:choose>
  <c:when test="${sessionScope.role eq 'ADMIN'}">
    <jsp:include page="adminMenu.jsp"/>
  </c:when>
  <c:when test="${sessionScope.role eq 'MODERATOR'}">
  </c:when>
  <c:when test="${sessionScope.role eq 'SPEAKER'}">
  </c:when>
  <c:when test="${sessionScope.role eq 'VISITOR'}">
  </c:when>
</c:choose>