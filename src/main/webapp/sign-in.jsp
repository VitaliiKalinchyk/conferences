<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="ua.java.conferences.exceptions.ServiceException" %>
<%@ page import="ua.java.conferences.exceptions.IncorrectPasswordException" %>
<%@ page import="ua.java.conferences.exceptions.IncorrectEmailException" %>
<%String name = (String) request.getAttribute("name");%>
<%ServiceException error = (ServiceException) request.getAttribute("error");%>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <title>Conference Smart App</title>
</head>

<body>
<header>
    Conference Smart App Sign In
</header>
<hr>
<form method="POST" action="action">
    <input type="hidden" name="action" value="sign-in">
    <%=error instanceof IncorrectEmailException ? "Wrong email" : ""%>
    <label for="email" >Email: </label>
    <input type="email" name="email" id="email" required value=<%=name != null ? name : ""%>>
    <%=error instanceof IncorrectPasswordException ? "Wrong password" : ""%>
    <label for="password" >Password: </label>
    <input type="password" name="password" id="password" required>
    <p><input type="submit" value="Sign In"></p>
</form>
<p>
    Forgot Your Password?
    <a href="reset-password.jsp">Reset Password</a>
</p>
<p>
    Don't have a Conference Smart App Account?
    <a href="sign-up.jsp">Create Account</a>
</p>
<footer>
    <p>
        2022 © Conference Smart App
    </p>
</footer>
</body>

</html>