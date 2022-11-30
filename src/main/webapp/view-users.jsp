<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="ua.java.conferences.exceptions.ServiceException" %>
<%@ page import="ua.java.conferences.exceptions.NoSuchUserException" %>
<%ServiceException error = (ServiceException) request.getAttribute("error");%>

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
<h3>Users</h3>
${message}
<form method="POST" action="controller">
    <input type="hidden" name="action" value="search-user">
    <%=error instanceof NoSuchUserException ? "Wrong email" : ""%>
    <br>
    <label for="email">Search user by email</label>
    <input type="email" name="email" id="email" required>
    <p><input type="submit" value="Search"></p>
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