<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setBundle basename="resources"/>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>

<!DOCTYPE html>
<html lang="${sessionScope.locale}">

<head>
    <meta charset="UTF-8">
    <title>Conference Smart App. <fmt:message key="view.users"/></title>
</head>

<body>

<menu>
    <strong>
        Conference Smart App <fmt:message key="view.users"/>
    </strong>
    <a href="index.jsp"><fmt:message key="main"/></a>
    <a href="about.jsp"><fmt:message key="about"/></a>
    <a href="contacts.jsp"><fmt:message key="contacts"/></a>
    <a href="profile.jsp"><fmt:message key="profile"/></a>
    <a href="controller?action=sign-out"><fmt:message key="sign.out"/></a>
</menu>

<menu>
    <c:choose>
        <c:when test="${sessionScope.role eq 'ADMIN'}">
            <a href="viewUsers.jsp"><fmt:message key="view.users"/></a>
        </c:when>
        <c:when test="${sessionScope.role eq 'MODERATOR'}">
        </c:when>
        <c:when test="${sessionScope.role eq 'SPEAKER'}">
        </c:when>
        <c:when test="${sessionScope.role eq 'VISITOR'}">
        </c:when>
    </c:choose>
</menu>
<br>

<h3><fmt:message key="users"/></h3>
<c:if test="${not empty requestScope.message}">
    <fmt:message key="${requestScope.message}"/>
</c:if>

<form method="GET" action="controller">
    <input type="hidden" name="action" value="search-user">
    <c:if test="${not empty requestScope.error}">
        <fmt:message key="${requestScope.error}"/>
    </c:if>
    <br>
    <label for="email"><fmt:message key="search.user"/></label>
    <input type="email" name="email" id="email" required>
    <p>
        <input type="submit" value="<fmt:message key="search"/>">
    </p>
</form>
<br>
<br>

<footer>
    <p>
        2022 © Conference Smart App
    </p>
</footer>

</body>

</html>