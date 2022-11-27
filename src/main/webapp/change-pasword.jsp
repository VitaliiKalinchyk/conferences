<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="ua.java.conferences.exceptions.ServiceException" %>
<%@ page import="ua.java.conferences.exceptions.IncorrectPasswordException" %>
<%ServiceException error = (ServiceException) request.getAttribute("error");%>

<!DOCTYPE html>
<html lang="en">

<head>
    <title>Conference Smart App</title>
</head>

<body>
<header>
    Conference Smart App Password Reset
</header>
<hr>
<form method="POST" action="action">
    <input type="hidden" name="action" value="change-password">
    <%=error instanceof IncorrectPasswordException ? "Wrong password" : ""%>
    <br>
    <label for="password" >Password: </label>
    <input type="password" name="password" id="password" required>
    <p><input type="submit" value="Change Password"></p>
</form>
<footer>
    <p>
        2022 © Conference Smart App
    </p>
</footer>
</body>

</html>