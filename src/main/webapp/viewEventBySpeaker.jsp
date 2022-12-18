<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<fmt:setBundle basename="resources"/>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>

<!DOCTYPE html>
<html lang="${sessionScope.locale}">

<head>
    <title>Conference Smart App. <fmt:message key="event"/></title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="css/bootstrap.min.css">
    <script src="js/bootstrap.min.js"></script>
</head>

<body>

<jsp:include page="fragments/mainMenu.jsp"/>

<jsp:include page="fragments/menuChoice.jsp"/>

<div class="col-lg-7 mx-auto p-4 py-md-5">

    <header class="d-flex align-items-center pb-3 mb-3 border-bottom">
        <span class="fs-4">${requestScope.event.title}</span>
    </header>

    <c:choose>
        <c:when test="${not empty requestScope.error}">
            <span class="text-danger"><fmt:message key="${requestScope.error}"/></span>
<%--            <c:redirect url="controller?action=view-speakers-events"/>--%>
        </c:when>

        <c:otherwise>
            <p class="fs-5"><fmt:message key="date"/>: ${requestScope.event.date}&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
                <fmt:message key="location"/>: ${requestScope.event.location}</p>
            <p class="fs-5"><fmt:message key="description"/>: ${requestScope.event.description} </p>

            <c:if test="${requestScope.isComing}">
                <a class="btn btn-dark mt-4 mb-4"
                   href="controller?action=offer-report-page&event-id=${requestScope.event.id}">
                    <fmt:message key="offer.report"/>
                </a>
            </c:if>

            <div class="bd-example-snippet bd-code-snippet">
                <div class="bd-example">
                    <table class="table table-striped" aria-label="report-table">

                        <thead>
                        <tr>
                            <th scope="col"><fmt:message key="topic.name"/></th>
                            <th scope="col"><fmt:message key="speaker.name"/></th>
                            <c:if test="${requestScope.isComing}">
                                <th scope="col"><fmt:message key="action"/></th>
                            </c:if>
                        </tr>
                        </thead>

                        <tbody>
                        <c:forEach var="report" items="${requestScope.reports}">
                            <tr>
                                <td><c:out value="${report.topic}"/></td>

                                <td>
                                    <c:choose>
                                        <c:when test="${empty report.speakerName}">
                                            <span class="text-danger"><fmt:message key='not.available'/></span>
                                        </c:when>
                                        <c:otherwise>
                                            ${report.speakerName}
                                        </c:otherwise>
                                    </c:choose>
                                </td>

                                <td>
                                    <c:if test="${requestScope.isComing}">
                                        <c:choose>
                                            <c:when test="${empty report.speakerName}">
                                                <form method="POST" action="controller">
                                                    <input type="hidden" name="action" value="set-speaker-by-speaker">
                                                    <input type="hidden" name="event-id" value="${requestScope.event.id}">
                                                    <input type="hidden" name="report-id" value="${report.id}">
                                                    <button type="submit" class="btn btn-dark mt-0 mb-0">
                                                        <fmt:message key="set.for.report"/>
                                                    </button>
                                                </form>
                                            </c:when>

                                            <c:when test="${report.speakerId eq sessionScope.loggedUser.id}">
                                                <form method="POST" action="controller">
                                                    <input type="hidden" name="action" value="remove-speaker-by-speaker">
                                                    <input type="hidden" name="event-id" value="${requestScope.event.id}">
                                                    <input type="hidden" name="report-id" value="${report.id}">
                                                    <button type="submit" class="btn btn-dark mt-0 mb-0">
                                                        <fmt:message key="decline.report"/>
                                                    </button>
                                                </form>

                                            </c:when>
                                        </c:choose>
                                    </c:if>
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