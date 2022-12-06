<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<fmt:setBundle basename="resources"/>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>

<!DOCTYPE html>
<html lang="${sessionScope.locale}">

<head>
    <title>Conference Smart App. <fmt:message key="about"/></title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="css/bootstrap.min.css">
    <script src="js/bootstrap.min.js"></script>
</head>

<body>

<jsp:include page="fragments/mainMenu.jsp"/>
<br>
<br>
<br>
<span class="mb-0 mx-5 my=4 h6"><fmt:message key="about"/></span>
<hr>
<br>

<div class="bd-example-snippet bd-code-snippet col2"><div class="bd-example">
    <p>Finding the right Conference venue in the right location at the right price to meet the budget can be very time-consuming
        and stressful for the Meeting booker, so why not use the FREE service provided by an experienced Conference Agency.</p>
    <p>An experienced conference agent will understand what it takes to make the meeting successful.
        They will take the brief and source a minimum of three options in a location of the client's choice.
        These days there are a number of other options available instead of hotels and conference centres,
        the choices may include unusual venues the client may not be familiar with, such as sporting venues, stately homes,
        castles and museums who all offer meeting facilities.</p>
    <p>No request is too large or small for an experienced Conference agency to handle, from an interview room,
        to a national conference, gala dinner, road show or exhibition. The Agent will source suitable venues to meet
        the requirements and budget, negotiate discounted meeting rates and favourable terms to ensure</p>
</div></div>

<jsp:include page="fragments/footer.jsp"/>

</body>

</html>