<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setBundle basename="resources"/>

<!DOCTYPE html>
<html lang="en">

<head>
    <title>Conference Smart App. <fmt:message key="reset.password"/></title>
</head>

<body>
<header>
    Conference Smart App <fmt:message key="reset.password"/>
</header>
<hr>
<form method="POST" action="controller">
    <input type="hidden" name="action" value="password-reset">
    <c:if test="${not empty requestScope.message}">
        <fmt:message key="${requestScope.message}"/>
    </c:if>
    <c:if test="${not empty requestScope.error}">
        <fmt:message key="${requestScope.error}"/>
    </c:if>
    <br>
    <label for="email" ><fmt:message key="email"/>: </label>
    <input type="email" name="email" id="email" required>
    <p><input type="submit" value="<fmt:message key="reset.password"/>"></p>
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