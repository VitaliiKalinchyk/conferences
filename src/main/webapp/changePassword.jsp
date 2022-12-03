<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setBundle basename="resources"/>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>


<!DOCTYPE html>
<html lang="${sessionScope.locale}">

<head>
    <title>Conference Smart App. <fmt:message key="change.password"/></title>
</head>

<body>

<header>
    Conference Smart App <fmt:message key="change.password"/>
</header>
<hr>

<form method="POST" action="controller">
    <input type="hidden" name="action" value="change-password">
    <c:if test="${not empty requestScope.message}">
        <fmt:message key="${requestScope.message}"/>
    </c:if>
    <c:if test="${not empty requestScope.error}">
        <fmt:message key="${requestScope.error}"/>
    </c:if>
    <br>
    <br>
    <label for="old-password" ><fmt:message key="old.password"/>*: </label>
    <input type="password" name="old-password" id="old-password" required>
    <br>
    <br>
    <label for="password" ><fmt:message key="new.password"/>*: </label>
    <input type="password" name="password" id="password" title="<fmt:message key="password.requirements"/>" required>
    <br>
    <br>
    <label for="confirm-password" ><fmt:message key="confirm.password"/>*: </label>
    <input type="password" name="confirm-password" id="confirm-password" title="<fmt:message key="password.requirements"/>" required>
    <p><input type="submit" value=<fmt:message key="change.password"/>></p>
</form>

<br>
<a href="profile.jsp"><fmt:message key="to.profile"/></a>

<footer>
    <p>
        2022 © Conference Smart App
    </p>
</footer>

</body>

</html>