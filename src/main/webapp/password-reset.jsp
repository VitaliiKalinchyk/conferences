<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="ua.java.conferences.exceptions.ServiceException" %>
<%@ page import="ua.java.conferences.exceptions.IncorrectPasswordException" %>
<%ServiceException error = (ServiceException) request.getAttribute("error");%>

<!DOCTYPE html>
<html lang="en">

<head>
    <title>Title</title>
</head>

<body>
<header>
    Conference Smart App Password Reset
</header>
<hr>
<form method="POST" action="action">
    <input type="hidden" name="action" value="password-reset">
    <%=error instanceof IncorrectPasswordException ? "Wrong password" : ""%>
    <label for="password" >Password: </label>
    <input type="password" name="password" id="password" required>
    <p><input type="submit" value="Reset Password"></p>
</form>
<footer>
    <p>
        2022 © Conference Smart App
    </p>
</footer>
</body>

</html>
