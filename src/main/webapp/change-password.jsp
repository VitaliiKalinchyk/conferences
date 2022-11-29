<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="ua.java.conferences.exceptions.ServiceException" %>
<%@ page import="ua.java.conferences.exceptions.IncorrectFormatException.Message" %>
<%@ page import="ua.java.conferences.exceptions.IncorrectPasswordException" %>
<%@ page import="ua.java.conferences.exceptions.NoSuchUserException" %>
<%ServiceException error = (ServiceException) request.getAttribute("error");%>
<%String message = error != null ? error.getMessage() != null ? error.getMessage() : "" : "";%>

<!DOCTYPE html>
<html lang="en">

<head>
    <title>Conference Smart App</title>
</head>

<body>
<header>
    Conference Smart App Change Password
</header>
<hr>
<form method="POST" action="controller">
    <input type="hidden" name="action" value="change-password">
    <%=error instanceof IncorrectPasswordException ? "Pls, check your old password spelling" : ""%>
    <%=error instanceof NoSuchUserException ? "Seems you doesn't have an account anymore" : ""%>
    <br>
    <label for="old-password" >Old Password: </label>
    <input type="password" name="old-password" id="old-password" required>
    <br>
    <br>
    <%=message.equals(Message.ENTER_CORRECT_PASSWORD) ? Message.ENTER_CORRECT_PASSWORD : ""%>
    <br>
    <label for="password" >Password: </label>
    <input type="password" name="password" id="password" title="Password should contain at least one uppercase letter, one lowercase letter, one digit and has length from 8 to 20 characters" required>
    <p><input type="submit" value="Change Password"></p>
</form>
<footer>
    <p>
        2022 © Conference Smart App
    </p>
</footer>
</body>

</html>