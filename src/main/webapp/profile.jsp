<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<fmt:setBundle basename="resources"/>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>

<!DOCTYPE html>
<html lang="${sessionScope.locale}">

<head>
    <meta charset="UTF-8">
    <title>Conference Smart App. <fmt:message key="profile"/></title>
</head>

<body>

<menu>
    <strong>
        Conference Smart App
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
<h3><fmt:message key="profile.info"/></h3>
<a href="editProfile.jsp"><fmt:message key="edit.profile"/></a>
<br>

<h4><fmt:message key="email"/>:</h4>
${sessionScope.loggedUser.email}
<h4><fmt:message key="name"/>:</h4>
${sessionScope.loggedUser.name}
<h4><fmt:message key="surname"/>:</h4>
${sessionScope.loggedUser.surname}
<h4><fmt:message key="notification"/>:</h4>
<c:choose>
    <c:when test="${sessionScope.loggedUser.notification}">
        <fmt:message key="yes"/>
    </c:when>
    <c:otherwise>
        <fmt:message key="no"/>
    </c:otherwise>
</c:choose>
<br>
<br>

<footer>
    <p>
        2022 © Conference Smart App
    </p>
</footer>

</body>

</html>