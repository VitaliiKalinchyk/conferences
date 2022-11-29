<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="ua.java.conferences.dto.request.UserRequestDTO" %>
<%@ page import="ua.java.conferences.exceptions.ServiceException" %>
<%@ page import="ua.java.conferences.exceptions.IncorrectFormatException.Message" %>
<%UserRequestDTO user = (UserRequestDTO) request.getAttribute("user");%>
<%ServiceException error = (ServiceException) request.getAttribute("error");%>
<%String message = error != null ? error.getMessage() : "";%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Conference Smart App</title>
</head>

<body>
<header>
    Create Your Conference Smart App Account
</header>
<p>
    Already have a Conference Smart App Account?
    <a href="controller?action=sign-in-page"> Sign In</a>
</p>
<form method="POST" action="controller">
    <input type="hidden" name="action" value="sign-up">
    <%=message.equals(Message.ENTER_CORRECT_EMAIL) ? Message.ENTER_CORRECT_EMAIL : ""%>
    <%=message.contains("Duplicate") ? "This email is already in use" : ""%>
    <br>
    <label for="email" >Email*: </label>
    <input type="email" name="email" id="email" required value=<%=user != null ? user.getEmail() : ""%>>
    <br>
    <%=message.equals(Message.ENTER_CORRECT_PASSWORD) ? Message.ENTER_CORRECT_PASSWORD : ""%>
    <br>
    <label for="password" >Password*: </label>
    <input type="password" name="password" id="password" title="Password should contain at least one uppercase letter, one lowercase letter, one digit and has length from 8 to 20 characters" required>
    <br>
    <%=message.equals(Message.ENTER_CORRECT_NAME) ? Message.ENTER_CORRECT_NAME : ""%>
    <br>
    <label for="name" >Name*: </label>
    <input type="text" name="name" id="name" title="Name can consists only letters and an apostrophe" required value=<%=user != null ? user.getName() : ""%>>
    <br>
    <%=message.equals(Message.ENTER_CORRECT_SURNAME) ? Message.ENTER_CORRECT_SURNAME : ""%>
    <br>
    <label for="surname" >Surname*: </label>
    <input type="text" name="surname" id="surname" title="Surname can consists only letters and an apostrophe" required value=<%=user != null ? user.getSurname() : ""%>>
    <br>
    <br>
    <label for="notification" >Email notification: </label>
    <input type="checkbox" name="notification" id="notification" checked>
    <br>
    <br>
    <input type="submit" value="Signup">
</form>
<footer>
    <p>
        2022 © Conference Smart App
    </p>
</footer>
</body>

</html>