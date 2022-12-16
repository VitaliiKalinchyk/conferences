<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<fmt:setBundle basename="resources"/>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>

<!DOCTYPE html>
<html lang="${sessionScope.locale}">

<c:set var="keyForTitle" value="${param.past eq 'past' ? 'view.visitors.past.events' : 'view.visitors.events'}"/>

<head>
    <title>Conference Smart App. <fmt:message key="${keyForTitle}"/></title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="css/bootstrap.min.css">
    <script src="js/bootstrap.min.js"></script>
</head>

<body>

<jsp:include page="fragments/mainMenu.jsp"/>

<jsp:include page="fragments/menuChoice.jsp"/>

<div class="col-lg-7 mx-auto p-4 py-md-5">

    <header class="d-flex align-items-center pb-3 mb-5 border-bottom">
        <span class="fs-4"><fmt:message key="${keyForTitle}"/></span>
    </header>

    <c:choose>
        <c:when test="${empty requestScope.events}">
            <p class="fs-6"><fmt:message key="zero.events"/></p>
        </c:when>
        <c:otherwise>
            <div class="bd-example-snippet bd-code-snippet">
                <div class="bd-example">
                    <table class="table table-striped" aria-label="user-table">
                        <thead>
                        <tr>
                            <th scope="col"><fmt:message key="title"/></th>
                            <th scope="col"><fmt:message key="date"/></th>
                            <th scope="col"><fmt:message key="location"/></th>
                            <th scope="col"><fmt:message key="action"/></th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="event" items="${requestScope.events}">
                            <tr>
                                <td><c:out value="${event.title}"/></td>
                                <td><c:out value="${event.date}"/></td>
                                <td><c:out value="${event.location}"/></td>
                                <td>
                                    <a class="link-dark" href=controller?action=view-event-by-visitor&event-id=${event.id}>
                                        <fmt:message key="view"/>
                                    </a>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </c:otherwise>
    </c:choose>
</div>

<jsp:include page="fragments/footer.jsp"/>

</body>
</html>