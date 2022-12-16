<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<fmt:setBundle basename="resources"/>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<jsp:useBean id="now" class="java.util.Date"/>
<fmt:formatDate value="${now}" pattern="yyyy-MM-dd" var="nowFormatted"/>
<c:set var="isFuture" value="${nowFormatted le requestScope.event.date}"/>

<!DOCTYPE html>
<html lang="${sessionScope.locale}">

<head>
    <title>Conference Smart App. <fmt:message key="view.event"/></title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="css/bootstrap.min.css">
    <script src="js/bootstrap.min.js"></script>
</head>

<body>

<jsp:include page="fragments/mainMenu.jsp"/>

<jsp:include page="fragments/menuChoice.jsp"/>

<div class="col-lg-5 mx-auto p-4 py-md-5">

    <header class="d-flex align-items-center pb-0 mb-3 border-bottom">
        <span class="fs-4">${requestScope.event.title}</span>
    </header>

    <main>
        <p class="fs-5"><fmt:message key="date"/>: ${requestScope.event.date}&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
            <fmt:message key="location"/>: ${requestScope.event.location}</p>
        <p class="fs-5"><fmt:message key="description"/>: ${requestScope.event.description}</p>
        Number of reports: ${requestScope.event.reports}
        Number of registrations: ${requestScope.event.registrations}
        Number of visitors: ${requestScope.event.visitors}
    </main>

    <c:if test="${isFuture}">
        <a href="controller?action=edit-event-page&event-id=${requestScope.event.id}"
           class="btn btn-dark mt-4 mb-4"><fmt:message key="edit.event"/></a>

        <button class="btn btn-dark mt-4 mb-4" data-bs-toggle="modal" data-bs-target="#exampleModalDefault">
            <fmt:message key="delete"/>
        </button>
    </c:if>

</div>

<jsp:include page="fragments/footer.jsp"/>

<jsp:include page="fragments/deleteEventModal.jsp"/>

</body>
</html>