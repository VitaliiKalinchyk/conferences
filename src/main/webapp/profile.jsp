<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="ua.java.conferences.dto.response.UserResponseDTO" %>
<%UserResponseDTO user = (UserResponseDTO) session.getAttribute("user");%>

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
    <a href="controller?action=default">Main</a>
    <a href="controller?action=about">About us</a>
    <a href="controller?action=contacts">Contacts</a>
    <a href="controller?action=profile">Profile</a>
    <a href="controller?action=sign-out">Sign Out</a>
    change language here
</menu>
<menu>
    <% if (session != null && session.getAttribute("role") == "ADMIN") out.print("<a href=\"controller?action=view-users\">View Users</a>"); %>
</menu>
<br>
<h3>Profile Info</h3>
<a href="controller?action=edit-profile-page">Edit profile</a>
<br>
<h4>Email:</h4>
<%=user.getEmail()%>
<h4>Name:</h4>
<%=user.getName()%>
<h4>Surname:</h4>
<%=user.getSurname()%>
<h4>Email Notification:</h4>
<%=user.isNotification() ? "Yes" : "No"%>
<br>
<br>
<footer>
    <p>
        2022 © Conference Smart App
    </p>
</footer>
</body>

</html>