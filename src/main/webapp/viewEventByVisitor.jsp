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

    <p class="fs-6"><fmt:message key="date"/>: ${requestScope.event.date} </p>
    <p class="fs-6"><fmt:message key="location"/>: ${requestScope.event.location} </p>
    <p class="fs-6"><fmt:message key="description"/>: ${requestScope.event.description} </p>


    <c:if test="${requestScope.isComing}">
        <c:choose>
            <c:when test="${requestScope.isRegistered}">
                <button class="btn btn-dark mt-4 mb-4" data-bs-toggle="modal" data-bs-target="#exampleModalDefault2">
                    <fmt:message key="cancel.registration"/>
                </button>
            </c:when>
            <c:otherwise>
                <button class="btn btn-dark mt-4 mb-4" data-bs-toggle="modal" data-bs-target="#exampleModalDefault1">
                    <fmt:message key="register"/>
                </button>
            </c:otherwise>
        </c:choose>
    </c:if>

    <div class="bd-example-snippet bd-code-snippet">
        <div class="bd-example">
            <table class="table table-striped" aria-label="report-table">
                <thead>
                <tr>
                    <th scope="col"><fmt:message key="topic.name"/></th>
                    <th scope="col"><fmt:message key="speaker.name"/></th>
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
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>

    <jsp:include page="fragments/footer.jsp"/>

    <div class="modal fade" id="exampleModalDefault1" tabindex="-1" aria-labelledby="exampleModalLabel1" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content rounded-4 shadow">
                <div class="modal-header border-bottom-0">
                    <h1 class="modal-title fs-5 text-md-center"><fmt:message key="register.for.event"/></h1>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body py-0">
                    <p><fmt:message key="register.for.event.confirmation"/></p>
                </div>
                <div class="modal-footer flex-column border-top-0">
                    <form method="POST" action="controller">
                        <input type="hidden" name="action" value="register-for-event">
                        <input type="hidden" name="event-id" value="${requestScope.event.id}">
                        <button type="submit" class="btn btn-dark mt-4 mb-4"><fmt:message key="register"/></button>
                    </form>
                </div>
            </div>
        </div>
    </div>

    <div class="modal fade" id="exampleModalDefault2" tabindex="-1" aria-labelledby="exampleModalLabel2" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content rounded-4 shadow">
                <div class="modal-header border-bottom-0">
                    <h1 class="modal-title fs-5 text-md-center"><fmt:message key="cancel.registration"/></h1>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body py-0">
                    <p><fmt:message key="cancel.registration.confirmation"/></p>
                </div>
                <div class="modal-footer flex-column border-top-0">
                    <form method="POST" action="controller">
                        <input type="hidden" name="action" value="cancel-registration">
                        <input type="hidden" name="event-id" value="${requestScope.event.id}">
                        <button type="submit" class="btn btn-dark mt-4 mb-4"><fmt:message key="cancel.registration"/></button>
                    </form>
                </div>
            </div>
        </div>
    </div>

</body>
</html>