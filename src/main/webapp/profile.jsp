<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <title>Conference Smart App</title>
</head>

<body>
<menu>
    <strong>
        Conference Smart App
    </strong>
    <a href="index.jsp">Main</a>
    <a href="about.jsp">About us</a>
    <a href="contacts.jsp">Contacts</a>
    <a href="controller?action=profile">Profile</a>
    <a href="controller?action=sign-out">Sign Out</a>
    change language here
</menu>
<menu>
    <c:choose>
        <c:when test="${sessionScope.role eq 'ADMIN'}">
            <a href="controller?action=view-users">View Users</a>
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
<h3>Profile Info</h3>
<a href="controller?action=edit-profile-page">Edit profile</a>
<br>
<h4>Email:</h4>
${sessionScope.user.email}
<h4>Name:</h4>
${sessionScope.user.name}
<h4>Surname:</h4>
${sessionScope.user.surname}
<h4>Email Notification:</h4>
<c:out value="${sessionScope.user.notification ? 'Yes': 'No'}"/>
<br>
<br>
<footer>
    <p>
        2022 © Conference Smart App
    </p>
</footer>
</body>

</html>