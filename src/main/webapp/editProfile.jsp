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
    <title>Conference Smart App. <fmt:message key="edit.profile"/></title>
</head>

<body>

<menu>
    <strong>
        Conference Smart App <fmt:message key="edit.profile"/>
    </strong>
    <a href="index.jsp"><fmt:message key="main"/></a>
    <a href="about.jsp"><fmt:message key="about"/></a>
    <a href="contacts.jsp"><fmt:message key="contacts"/></a>
    <c:choose>
        <c:when test="${sessionScope.loggedUser eq null}">
            <a href="signIn.jsp"><fmt:message key="sign.in"/></a>
            <a href="signUp.jsp"><fmt:message key="sign.up"/></a>
        </c:when>
        <c:otherwise>
            <a href="profile.jsp"><fmt:message key="profile"/></a>
            <a href="controller?action=sign-out"><fmt:message key="sign.out"/></a>
        </c:otherwise>
    </c:choose>
</menu>

<br>
<h3><fmt:message key="edit.profile"/></h3>
<br>
<c:if test="${not empty requestScope.message}">
    <fmt:message key="${requestScope.message}"/>
</c:if>
<br>

<form method="POST" action="controller">
    <input type="hidden" name="action" value="edit-profile">
    <c:set var="error" value="${requestScope.error}"/>
    <c:set var="emailValue" value="${requestScope.user.email eq null ? sessionScope.loggedUser.email : requestScope.user.email}"/>
    <c:set var="nameValue" value="${requestScope.user.name eq null ? sessionScope.loggedUser.name : requestScope.user.name}"/>
    <c:set var="surnameValue" value="${requestScope.user.surname eq null ? sessionScope.loggedUser.surname : requestScope.user.surname}"/>
    <c:set var="notification" value="${requestScope.user.notification eq null ? sessionScope.loggedUser.notification : requestScope.user.notification}"/>
    <c:if test="${fn:contains(error, 'email')}">
        <fmt:message key="${requestScope.error}"/>
    </c:if>
    <br>
    <label for="email" ><fmt:message key="email"/>*: </label>
    <input type="email" name="email" id="email" required value="${emailValue}">
    <br>
    <c:if test="${fn:contains(error, '.name')}">
        <fmt:message key="${requestScope.error}"/>
    </c:if>
    <br>
    <label for="name" ><fmt:message key="name"/>*: </label>
    <input name="name" id="name" title="<fmt:message key="name.requirements"/>" required value="${nameValue}">
    <br>
    <c:if test="${fn:contains(error, 'surname')}">
        <fmt:message key="${requestScope.error}"/>
    </c:if>
    <br>
    <label for="surname" ><fmt:message key="surname"/>*: </label>
    <input name="surname" id="surname" title="<fmt:message key="surname.requirements"/>" required value="${surnameValue}">
    <br>
    <br>
    <label for="notification" ><fmt:message key="notification"/>: </label>
    <input type="checkbox" name="notification" id="notification" ${notification ? "checked" : ""}>
    <br>
    <br>
    <input type="submit" value="<fmt:message key="submit"/>">
</form>

<br>
<br>
<a href="changePassword.jsp"><fmt:message key="change.password"/></a>

<br>
<br>
<a href="profile.jsp"><fmt:message key="to.profile"/></a>
<br>
<br>

<footer>
    <p>
        2022 © Conference Smart App
    </p>
</footer>

</body>

</html>