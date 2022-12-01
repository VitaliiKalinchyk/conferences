<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<fmt:setBundle basename="resources"/>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <title>Conference Smart App. <fmt:message key="sign.up"/></title>
</head>

<body>
<header>
    Conference Smart App <fmt:message key="sign.up"/>
</header>
<p>
    <fmt:message key="have.account"/>
    <a href="sign-in.jsp"><fmt:message key="sign.in"/></a>
</p>
<form method="POST" action="controller">
    <input type="hidden" name="action" value="sign-up">
    <c:set var="error" value="${requestScope.error}"/>
    <c:if test="${fn:contains(error, 'email')}">
        <fmt:message key="${requestScope.error}"/>
    </c:if>
    <br>
    <label for="email" ><fmt:message key="email"/>*: </label>
    <input type="email" name="email" id="email" required value="${requestScope.user.email}">
    <br>
    <c:if test="${fn:contains(error, 'pass')}">
        <fmt:message key="${requestScope.error}"/>
    </c:if>
    <br>
    <label for="password" ><fmt:message key="password"/>*: </label>
    <input type="password" name="password" id="password" title="<fmt:message key="password.requirements"/>" required>
    <br>
    <br>
    <label for="confirm-password" ><fmt:message key="confirm.password"/>*: </label>
    <input type="password" name="confirm-password" id="confirm-password" title="<fmt:message key="password.requirements"/>" required>
    <br>
    <br>
    <c:if test="${fn:contains(error, '.name')}">
        <fmt:message key="${requestScope.error}"/>
    </c:if>    <br>
    <label for="name" ><fmt:message key="name"/>*: </label>
    <input type="text" name="name" id="name" title="<fmt:message key="name.requirements"/>" required value="${requestScope.user.name}">
    <br>
    <c:if test="${fn:contains(error, 'surname')}">
        <fmt:message key="${requestScope.error}"/>
    </c:if>
    <br>
    <label for="surname" ><fmt:message key="surname"/>*: </label>
    <input type="text" name="surname" id="surname" title="<fmt:message key="surname.requirements"/>" required value="${requestScope.user.surname}">
    <br>
    <br>
    <label for="notification" ><fmt:message key="notification"/>: </label>
    <input type="checkbox" name="notification" id="notification" checked>
    <br>
    <br>
    <input type="submit" value="<fmt:message key="sign.up"/>">
</form>
<br>
<a href="index.jsp"><fmt:message key="to.main"/></a>
<footer>
    <p>
        2022 © Conference Smart App
    </p>
</footer>
</body>

</html>