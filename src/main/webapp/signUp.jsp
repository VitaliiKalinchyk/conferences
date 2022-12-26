<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="resources"/>

<!DOCTYPE html>
<html lang="${sessionScope.locale}">

<head>
    <title>Conference Smart App. <fmt:message key="sign.up"/></title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="css/bootstrap.min.css">
    <script src="js/showPass.js"></script>
    <script src="js/bootstrap.min.js"></script>
    <script src="https://www.google.com/recaptcha/api.js"></script>
</head>

<body>

<jsp:include page="fragments/mainMenu.jsp"/>

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
            <input class="form-control" type="email" name="email" id="email"
                   pattern="[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$" required value="${requestScope.user.email}">
            <c:if test="${fn:contains(error, 'email')}">
                <span class="text-danger"><fmt:message key="${requestScope.error}"/></span>
            </c:if>
            <br>
        </div>

        <div class="form-group">
            <label class="form-label fs-5" for="password"><fmt:message key="password"/>*: </label>
            <input class="form-control" type="password" name="password" id="password"
                   title="<fmt:message key="password.requirements"/>"
                   pattern="^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9]).{8,20}$" required>
            <c:if test="${fn:contains(error, 'pass')}">
                <span class="text-danger"><fmt:message key="${requestScope.error}"/></span>
            </c:if><br>
        </div>

        <div class="form-group">
            <label class="form-label fs-5" for="confirm-password"><fmt:message key="confirm.password"/>*: </label>
            <input class="form-control" type="password" name="confirm-password" id="confirm-password"
                   pattern="^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9]).{8,20}$"
                   title="<fmt:message key="password.requirements"/>" required><br>
            <div class="form-check">
                <input class="form-check-input" type="checkbox" id="flexCheckDefault"
                       onclick="showPass('password'); showPass('confirm-password');">
                <label class="form-check-label" for="flexCheckDefault">
                    <fmt:message key="show.password"/>
                </label>
            </div><br>
        </div>

        <div class="form-group">
            <label class="form-label fs-5" for="name"><fmt:message key="name"/>*: </label>
            <input class="form-control" type="text" name="name" id="name"
                   title="<fmt:message key="name.requirements"/>" pattern="^[A-Za-zА-ЩЬЮЯҐІЇЄа-щьюяґіїє'\- ]{1,30}"
                   required value="${requestScope.user.name}">
            <c:if test="${fn:contains(error, '.name')}">
                <span class="text-danger"><fmt:message key="${requestScope.error}"/></span>
            </c:if>
            <br>
        </div>

        <div class="form-group">
            <label class="form-label fs-5" for="surname"><fmt:message key="surname"/>*: </label>
            <input class="form-control" type="text" name="surname" id="surname"
                   title="<fmt:message key="surname.requirements"/>" pattern="^[A-Za-zА-ЩЬЮЯҐІЇЄа-щьюяґіїє'\- ]{1,30}"
                   required value="${requestScope.user.surname}">
            <c:if test="${fn:contains(error, 'surname')}">
                <span class="text-danger"><fmt:message key="${requestScope.error}"/></span>
            </c:if>
            <br>
        </div>

        <div class="form-group">
            <label class="form-label fs-5" for="notification"><fmt:message key="notification"/>: </label>
            <input type="checkbox" name="notification" id="notification" checked>
            <br><br>
        </div>

        <div class="g-recaptcha" data-sitekey="6LecrqsjAAAAACSNHc7GqpvVkK6-fKxvSgT7Sx5e"></div>
        <c:if test="${fn:contains(error, 'captcha')}">
            <span class="text-danger"><fmt:message key="${requestScope.error}"/></span>
        </c:if><br>

        <button type="submit" class="btn btn-dark mt-4 mb-4"><fmt:message key="sign.up"/></button>
    </form>

    <p class="fs-6 col-md-8">
        <fmt:message key="have.account"/>
        <a href="signIn.jsp" class="link-dark"><fmt:message key="sign.in"/></a>
    </p>
</div>

<jsp:include page="fragments/footer.jsp"/>

</body>
</html>