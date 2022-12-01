<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<fmt:setBundle basename="resources"/>
<%String name = (String) request.getAttribute("name");%>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <title>Conference Smart App. <fmt:message key="sign.in"/></title>
</head>

<body>
<header>
    Conference Smart App <fmt:message key="sign.in"/>
</header>
<hr>
<form method="POST" action="controller">
    <c:set var="error" value="${requestScope.error}"/>
    <input type="hidden" name="action" value="sign-in">
    <c:if test="${fn:contains(error, 'email')}">
        <fmt:message key="${requestScope.error}"/>
    </c:if>
    <br>
    <label for="email" ><fmt:message key="email"/>: </label>
    <input type="email" name="email" id="email" required value="${requestScope.email}">
    <br>
    <c:if test="${fn:contains(error, 'pass')}">
        <fmt:message key="${requestScope.error}"/>
    </c:if>
    <br>
    <label for="password" ><fmt:message key="password"/>: </label>
    <input type="password" name="password" id="password" required>
    <br>
    <p><input type="submit" value="<fmt:message key="sign.in"/>"></p>
</form>
<p>
    <fmt:message key="forgot.password"/>
    <a href="reset-password.jsp"><fmt:message key="reset.password"/></a>
</p>
<p>
    <fmt:message key="no.account"/>
    <a href="sign-up.jsp"><fmt:message key="sign.up"/></a>
</p>
<br>
<a href="index.jsp"><fmt:message key="to.main"/></a>
<footer>
    <p>
        2022 © Conference Smart App
    </p>
</footer>
</body>

</html>