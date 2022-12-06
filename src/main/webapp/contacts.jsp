<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<fmt:setBundle basename="resources"/>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>

<!DOCTYPE html>
<html lang="${sessionScope.locale}">

<head>
    <title>Conference Smart App. <fmt:message key="contacts"/></title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="css/bootstrap.min.css">
    <script src="js/bootstrap.min.js"></script>
</head>

<body>

<jsp:include page="fragments/mainMenu.jsp"/>

<div class="col-lg-8 mx-auto p-4 py-md-5">

    <header class="d-flex mt-3 align-items-center pb-3 mb-5 border-bottom">
        <span class="fs-4"><fmt:message key="contacts"/></span>
    </header>



<header><fmt:message key="contacts"/> – Conference Smart App</header>
<hr>
<p>
    York House, 18 York Road, Maidenhead, Berkshire, SL6 1SF, UK
    <br>
    Registered in England & Wales Company number 02118204
    <br>
    +44 (0) 1628 773300 enquiries@conferencecontacts.co.uk
</p>

<jsp:include page="fragments/footer.jsp"/>

</body>

</html>