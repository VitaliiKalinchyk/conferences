<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="ua.java.conferences.exceptions.*" %>
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
<form method="POST" action="controller">
    <input type="hidden" name="action" value="password-reset">
    <%=error instanceof IncorrectEmailException ? "Wrong email " : ""%>
    <%=error instanceof NoSuchUserException ? "Not registered email " : ""%>
    <br>
    <label for="email" >Enter your email: </label>
    <input type="email" name="email" id="email" required>
    <p><input type="submit" value="Reset Password"></p>
</form>
<footer>
    <p>
        2022 © Conference Smart App
    </p>
</footer>
</body>

</html>