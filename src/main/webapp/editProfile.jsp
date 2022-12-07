<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<fmt:setBundle basename="resources"/>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>

<!DOCTYPE html>
<html lang="${sessionScope.locale}">

<head>
    <title>Conference Smart App. <fmt:message key="edit.profile"/></title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="css/bootstrap.min.css">
    <script src="js/bootstrap.min.js"></script>
</head>

<body>

<jsp:include page="fragments/emptyMenu.jsp"/>

<div class="col-lg-5 mx-auto p-4 py-md-5">

    <header class="d-flex align-items-center pb-0 mb-3 border-bottom">
        <span class="fs-4"><fmt:message key="edit.profile"/></span>
    </header>

    <form method="POST" action="controller">
        <input type="hidden" name="action" value="edit-profile">
        <c:set var="error" value="${requestScope.error}"/>
        <c:set var="emailValue" value="${requestScope.user.email eq null ?
                                sessionScope.loggedUser.email : requestScope.user.email}"/>
        <c:set var="nameValue" value="${requestScope.user.name eq null ?
                                sessionScope.loggedUser.name : requestScope.user.name}"/>
        <c:set var="surnameValue" value="${requestScope.user.surname eq null ?
                                sessionScope.loggedUser.surname : requestScope.user.surname}"/>
        <c:set var="notification" value="${requestScope.user.notification eq null ?
                                sessionScope.loggedUser.notification : requestScope.user.notification}"/>

        <div class="form-group">
            <c:if test="${not empty requestScope.message}">
                <span class="text-success"><fmt:message key="${requestScope.message}"/></span>
            </c:if><br>
            <label class="form-label fs-5" for="email"><fmt:message key="email"/>: </label>
            <input class="form-control" type="email" name="email" id="email" required value="${emailValue}">
            <c:if test="${fn:contains(error, 'email')}">
                <span class="text-danger"><fmt:message key="${requestScope.error}"/></span>
            </c:if><br>
        </div>

        <div class="form-group">
            <label class="form-label fs-5" for="name"><fmt:message key="name"/>*: </label>
            <input class="form-control" name="name" id="name" title="<fmt:message key="name.requirements"/>"
                   required value="${nameValue}">
            <c:if test="${fn:contains(error, '.name')}">
                <span class="text-danger"><fmt:message key="${requestScope.error}"/></span>
            </c:if><br>
        </div>

        <div class="form-group">
            <label class="form-label fs-5" for="surname"><fmt:message key="surname"/>*: </label>
            <input class="form-control" name="surname" id="surname" title="<fmt:message key="surname.requirements"/>"
                   required value="${surnameValue}">
            <c:if test="${fn:contains(error, 'surname')}">
                <span class="text-danger"><fmt:message key="${requestScope.error}"/></span>
            </c:if><br>
        </div>

        <div class="form-group">
            <label class="form-label fs-5" for="notification"><fmt:message key="notification"/>: </label>
            <input type="checkbox" name="notification" id="notification" ${notification ? "checked" : ""}>
            <br>
        </div>

        <button type="submit" class="btn btn-dark mt-4 mb-4"><fmt:message key="submit"/></button>
    </form>

    <p class="fs-6 col-md-8">
        <a href="changePassword.jsp" class="link-dark"><fmt:message key="change.password"/></a>
    </p><br><br>

    <p class="fs-6 col-md-8"><a href="profile.jsp" class="link-dark"><fmt:message key="to.profile"/></a></p>
</div>

<jsp:include page="fragments/footer.jsp"/>

</body>
</html>