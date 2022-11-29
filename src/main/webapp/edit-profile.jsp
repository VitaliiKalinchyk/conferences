<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="ua.java.conferences.dto.response.UserResponseDTO" %>
<%@ page import="ua.java.conferences.exceptions.ServiceException" %>
<%@ page import="ua.java.conferences.exceptions.IncorrectFormatException.Message" %>
<%UserResponseDTO user = (UserResponseDTO) session.getAttribute("user"); %>
<%String attributeEmail = (String) request.getAttribute("email"); %>
<%String attributeName = (String) request.getAttribute("name"); %>
<%String attributeSurname = (String) request.getAttribute("surname"); %>
<%String email = attributeEmail != null ? attributeEmail :user.getEmail(); %>
<%String name = attributeName != null ? attributeName :user.getName(); %>
<%String surname = attributeSurname != null ? attributeSurname :user.getSurname(); %>
<%ServiceException error = (ServiceException) request.getAttribute("error"); %>
<%String message = error != null ? error.getMessage() : ""; %>

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
<br>
<h3>Edit Info</h3>
<br>
<form method="POST" action="controller">
    <input type="hidden" name="action" value="edit-profile">
    <%=message.equals(Message.ENTER_CORRECT_EMAIL) ? Message.ENTER_CORRECT_EMAIL : ""%>
    <%=message.contains("Duplicate") ? "This email is already in use" : ""%>
    <br>
    <label for="email" >Email*: </label>
    <input type="email" name="email" id="email" required value=<%=email%>>
    <br>
    <%=message.equals(Message.ENTER_CORRECT_NAME) ? Message.ENTER_CORRECT_NAME : ""%>
    <br>
    <label for="name" >Name*: </label>
    <input type="text" name="name" id="name" title="Name can consists only letters and an apostrophe" required value=<%=name%>>
    <br>
    <%=message.equals(Message.ENTER_CORRECT_SURNAME) ? Message.ENTER_CORRECT_SURNAME : ""%>
    <br>
    <label for="surname" >Surname*: </label>
    <input type="text" name="surname" id="surname" title="Surname can consists only letters and an apostrophe"  required value=<%=surname%>>
    <br>
    <br>
    <label for="notification" >Email notification: </label>
    <input type="checkbox" name="notification" id="notification" checked>
    <br>
    <br>
    <input type="submit" value="Submit">
</form>
<br>
<br>
<a href="controller?action=change-password-page">Change Password</a>
<br>
<br>
<footer>
    <p>
        2022 © Conference Smart App
    </p>
</footer>
</body>

</html>