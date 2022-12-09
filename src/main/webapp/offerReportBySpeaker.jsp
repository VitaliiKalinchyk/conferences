<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<fmt:setBundle basename="resources"/>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>

<!DOCTYPE html>
<html lang="${sessionScope.locale}">

<head>
    <title>Conference Smart App. <fmt:message key="offer.report"/></title>
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
        <span class="fs-4"><fmt:message key="offer.report"/></span>
    </header>

    <c:set var="error" value="${requestScope.error}"/>

    <c:choose>
        <c:when test="${fn:contains(error, 'offer.forbidden')}">
            <span class="text-danger"><fmt:message key="${requestScope.error}"/></span>
        </c:when>

        <c:otherwise>

            <p class="fs-6"><fmt:message key="event"/>: ${param.title} </p>

            <form method="POST" action="controller">
                <input type="hidden" name="action" value="offer-report">
                <input type="hidden" name="event-id" value="${param.id}">
                <input type="hidden" name="title" value="${param.title}">

                <div class="form-group">
                    <c:if test="${not empty requestScope.message}">
                        <span class="text-success"><fmt:message key="${requestScope.message}"/></span>
                    </c:if><br>
                    <label class="form-label fs-5" for="topic"><fmt:message key="topic.name"/>: </label>
                    <input class="form-control" name="topic" id="topic" required value="">
                    <c:if test="${fn:contains(error, 'topic')}">
                        <span class="text-danger"><fmt:message key="${requestScope.error}"/></span>
                    </c:if><br>
                </div>

                <button type="submit" class="btn btn-dark mt-4 mb-4"><fmt:message key="submit"/></button>

            </form>
        </c:otherwise>
    </c:choose>
</div>

<jsp:include page="fragments/footer.jsp"/>

</body>
</html>