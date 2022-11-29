<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="ua.java.conferences.dto.response.UserResponseDTO" %>
<%UserResponseDTO user = (UserResponseDTO) request.getAttribute("user"); %>

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
<h3>User</h3>
Email: <%=user.getEmail()%>
Name: <%=user.getName()%>
Surname: <%=user.getSurname()%>
Role: <%=user.getRole()%>
<a href="controller?action=delete-user&user-id=<%=user.getId()%>">Delete</a>
<form action="controller">
    <input type="hidden" name="action" value="set-role">
    <input type="hidden" name="user-id" value=<%=user.getId()%>>
    <label for="role">Set a role:</label>
    <select id="role" name="role">
        <option value="VISITOR">VISITOR</option>
        <option value="SPEAKER">SPEAKER</option>
        <option value="MODERATOR">MODERATOR</option>
        <option value="ADMIN">ADMIN</option>
    </select>
    <input type="submit" value="Set Role">
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