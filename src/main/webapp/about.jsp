<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setBundle basename="resources"/>

<!DOCTYPE html>
<html lang="en" xmlns="http://www.w3.org/1999/html">

<head>
    <meta charset="UTF-8">
    <title>Conference Smart App. <fmt:message key="about"/></title>
</head>

<body>
<menu>
    <strong>
        Conference Smart App
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
<header><fmt:message key="about"/> – Conference Smart App</header>
<br>
<br>
<p>
    Finding the right Conference venue in the right location at the right price to meet the budget can be very time-consuming
    and stressful for the Meeting booker, so why not use the FREE service provided by an experienced Conference Agency.
    <br>
    <br>
    An experienced conference agent will understand what it takes to make the meeting successful.
    They will take the brief and source a minimum of three options in a location of the client's choice.
    These days there are a number of other options available instead of hotels and conference centres,
    the choices may include unusual venues the client may not be familiar with, such as sporting venues, stately homes,
    castles and museums who all offer meeting facilities.
    <br>
    <br>
    No request is too large or small for an experienced Conference agency to handle, from an interview room,
    to a national conference, gala dinner, road show or exhibition. The Agent will source suitable venues to meet
    the requirements and budget, negotiate discounted meeting rates and favourable terms to ensure
</p>
<footer>
    <p>
        2022 © Conference Smart App
    </p>
</footer>
</body>
</html>