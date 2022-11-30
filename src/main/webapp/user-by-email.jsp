<%@ page contentType="text/html;charset=UTF-8"%>

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
    <% if (session != null && session.getAttribute("role") == "ADMIN") out.print("<a href=\"controller?action=view-users\">View Users</a>"); %>
</menu>
<br>
<h3>User</h3>
Email: ${requestScope.user.email}
Name: ${requestScope.user.name}
Surname: ${requestScope.user.surname}
Role: ${requestScope.user.role}
<form method="POST" action="controller">
    <input type="hidden" name="action" value="delete-user">
    <input type="hidden" name="user-id" value=${requestScope.user.id}>
    <input type="submit" value="Delete">
</form>
<form method="POST" action="controller">
    <input type="hidden" name="action" value="set-role">
    <input type="hidden" name="user-id" value=${requestScope.user.id}>
    <label for="role">Set a role:</label>
    <select id="role" name="role">
        <option value="" selected disabled hidden>Choose role</option>
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