<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setBundle basename="resources"/>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <title>Conference Smart App. <fmt:message key="view.user"/></title>
</head>

<body>
<menu>
    <strong>
        Conference Smart App <fmt:message key="view.user"/>
    </strong>
    <a href="index.jsp"><fmt:message key="main"/></a>
    <a href="about.jsp"><fmt:message key="about"/></a>
    <a href="contacts.jsp"><fmt:message key="contacts"/></a>
    <a href="controller?action=profile"><fmt:message key="profile"/></a>
    <a href="controller?action=sign-out"><fmt:message key="sign.out"/></a>
    change language here
</menu>
<menu>
    <a href="controller?action=view-users"><fmt:message key="view.users"/></a>
</menu>
<br>
<h3><fmt:message key="user"/></h3>
Email: ${requestScope.user.email}
Name: ${requestScope.user.name}
Surname: ${requestScope.user.surname}
Role: ${requestScope.user.role}
<form method="POST" action="controller">
    <input type="hidden" name="action" value="delete-user">
    <input type="hidden" name="user-id" value=${requestScope.user.id}>
    <input type="submit" value="<fmt:message key="delete"/>">
</form>
<form method="POST" action="controller">
    <input type="hidden" name="action" value="set-role">
    <input type="hidden" name="user-id" value=${requestScope.user.id}>
    <label for="role"><fmt:message key="set.role"/>:</label>
    <select id="role" name="role">
        <option value="" selected disabled hidden><fmt:message key="choose.role"/></option>
        <option value="VISITOR"><fmt:message key="visitor"/></option>
        <option value="SPEAKER"><fmt:message key="speaker"/></option>
        <option value="MODERATOR"><fmt:message key="moderator"/></option>
        <option value="ADMIN"><fmt:message key="admin"/></option>
    </select>
    <input type="submit" value="<fmt:message key="set.role"/>">
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