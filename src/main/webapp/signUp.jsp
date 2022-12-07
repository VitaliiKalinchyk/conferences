<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<fmt:setBundle basename="resources"/>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>

<!DOCTYPE html>
<html lang="${sessionScope.locale}">

<head>
    <title>Conference Smart App. <fmt:message key="sign.up"/></title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="css/bootstrap.min.css">
    <script src="js/bootstrap.min.js"></script>
</head>

<body>

<jsp:include page="fragments/emptyMenu.jsp"/>

<div class="col-lg-5 mx-auto p-4 py-md-5">

    <header class="d-flex align-items-center pb-0 mb-3 border-bottom">
        <span class="fs-4"><fmt:message key="sign.up"/></span>
    </header>

    <form method="POST" action="controller">
        <input type="hidden" name="action" value="sign-up">
        <c:set var="error" value="${requestScope.error}"/>

        <div class="form-group">
            <br>
            <label class="form-label fs-5" for="email"><fmt:message key="email"/>*: </label>
            <input class="form-control" type="email" name="email" id="email" required
                   value="${requestScope.user.email}">
            <c:if test="${fn:contains(error, 'email')}">
                <span class="text-danger"><fmt:message key="${requestScope.error}"/></span>
            </c:if>
            <br>
        </div>

        <div class="form-group">
            <label class="form-label fs-5" for="password"><fmt:message key="password"/>*: </label>
            <input class="form-control" type="password" name="password" id="password"
                   title="<fmt:message key="password.requirements"/>" required>
            <c:if test="${fn:contains(error, 'pass')}">
                <span class="text-danger"><fmt:message key="${requestScope.error}"/></span>
            </c:if>
            <br>
        </div>

        <div class="form-group">
            <label class="form-label fs-5" for="confirm-password"><fmt:message key="confirm.password"/>*: </label>
            <input class="form-control" type="password" name="confirm-password" id="confirm-password"
                   title="<fmt:message key="password.requirements"/>" required>
            <br>
        </div>

        <div class="form-group">
            <label class="form-label fs-5" for="name"><fmt:message key="name"/>*: </label>
            <input class="form-control" type="text" name="name" id="name"
                   title="<fmt:message key="name.requirements"/>" required value="${requestScope.user.name}">
            <c:if test="${fn:contains(error, '.name')}">
                <span class="text-danger"><fmt:message key="${requestScope.error}"/></span>
            </c:if>
            <br>
        </div>

        <div class="form-group">
            <label class="form-label fs-5" for="surname"><fmt:message key="surname"/>*: </label>
            <input class="form-control" type="text" name="surname" id="surname"
                   title="<fmt:message key="surname.requirements"/>" required value="${requestScope.user.surname}">
            <c:if test="${fn:contains(error, 'surname')}">
                <span class="text-danger"><fmt:message key="${requestScope.error}"/></span>
            </c:if>
            <br>
        </div>

        <div class="form-group">
            <label class="form-label fs-5" for="notification"><fmt:message key="notification"/>: </label>
            <input type="checkbox" name="notification" id="notification" checked>
            <br>
        </div>

        <button type="submit" class="btn btn-dark mt-4 mb-4"><fmt:message key="sign.up"/></button>
    </form>

    <p class="fs-6 col-md-8">
        <fmt:message key="have.account"/>
        <a href="signIn.jsp" class="link-dark"><fmt:message key="sign.in"/></a>
    </p><br><br>

    <p class="fs-6 col-md-8"><a href="index.jsp" class="link-dark"><fmt:message key="to.main"/></a></p>
</div>

<jsp:include page="fragments/footer.jsp"/>

</body>
</html>