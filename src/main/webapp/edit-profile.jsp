<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setBundle basename="resources"/>
<%@ page import="ua.java.conferences.dto.response.UserResponseDTO" %>
<%@ page import="ua.java.conferences.exceptions.ServiceException" %>
<%@ page import="ua.java.conferences.exceptions.IncorrectFormatException.Message" %>
<%UserResponseDTO user = (UserResponseDTO) session.getAttribute("user"); %>
<%String attributeEmail = (String) request.getAttribute("email"); %>
<%String attributeName = (String) request.getAttribute("name"); %>
<%String attributeSurname = (String) request.getAttribute("surname"); %>
<%Boolean attributeNotification = (Boolean) request.getAttribute("notification"); %>
<%String email = attributeEmail != null ? attributeEmail :user.getEmail(); %>
<%String name = attributeName != null ? attributeName :user.getName(); %>
<%String surname = attributeSurname != null ? attributeSurname :user.getSurname(); %>
<%Boolean notification = attributeNotification != null ? attributeNotification :user.isNotification(); %>
<%ServiceException error = (ServiceException) request.getAttribute("error"); %>
<%String message = error != null ? error.getMessage() : ""; %>

<!DOCTYPE html>
<html lang="en" xmlns="http://www.w3.org/1999/html">

<head>
    <meta charset="UTF-8">
    <title>Conference Smart App. <fmt:message key="edit.profile"/></title>
</head>

<body>
<menu>
    <strong>
        Conference Smart App <fmt:message key="edit.profile"/>
    </strong>
    <a href="index.jsp"><fmt:message key="main"/></a>
    <a href="about.jsp"><fmt:message key="about"/></a>
    <a href="contacts.jsp"><fmt:message key="contacts"/></a>
    <c:choose>
        <c:when test="${sessionScope.user eq null}">
            <a href="sign-in.jsp"><fmt:message key="sign.in"/></a>
            <a href="sign-up.jsp"><fmt:message key="sign.up"/></a>
        </c:when>
        <c:otherwise>
            <a href="controller?action=profile"><fmt:message key="profile"/></a>
            <a href="controller?action=sign-out"><fmt:message key="sign.out"/></a>
        </c:otherwise>
    </c:choose>
    change language here
</menu>
<br>
<h3><fmt:message key="edit.profile"/></h3>
<br>
<c:if test="${not empty requestScope.message}">
    <fmt:message key="${requestScope.message}"/>
</c:if>
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
    <input type="checkbox" name="notification" id="notification" <%=notification ? "checked" : "" %>>
    <br>
    <br>
    <input type="submit" value="Submit">
</form>
<br>
<br>
<a href="controller?action=change-password-page">Change Password</a>
<br>
<br>
<a href="controller?action=profile">Back to profile</a>
<br>
<br>
<footer>
    <p>
        2022 © Conference Smart App
    </p>
</footer>
</body>

</html>