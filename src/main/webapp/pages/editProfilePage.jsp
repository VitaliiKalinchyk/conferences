<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="resources"/>

<div class="col-lg-5 mx-auto p-4 py-md-5">
    <header class="d-flex align-items-center pb-0 mb-3 border-bottom">
        <span class="fs-4"><fmt:message key="edit.profile"/></span>
    </header>

    <form method="POST" action="controller">
        <input type="hidden" name="action" value="edit-profile">
        <c:set var="error" value="${requestScope.error}"/>
        <c:set var="titleValue" value="${requestScope.user.email eq null ?
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
            <input class="form-control" type="email" name="email" id="email"
                   pattern="[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$" required value="${titleValue}">
            <c:if test="${fn:contains(error, 'email')}">
                <span class="text-danger"><fmt:message key="${requestScope.error}"/></span>
            </c:if><br>
        </div>

        <div class="form-group">
            <label class="form-label fs-5" for="name"><fmt:message key="name"/>*: </label>
            <input class="form-control" name="name" id="name" pattern="^[A-Za-zА-ЩЬЮЯҐІЇЄа-щьюяґіїє'\- ]{1,30}"
                   title="<fmt:message key="name.requirements"/>" required value="${nameValue}">
            <c:if test="${fn:contains(error, '.name')}">
                <span class="text-danger"><fmt:message key="${requestScope.error}"/></span>
            </c:if><br>
        </div>

        <div class="form-group">
            <label class="form-label fs-5" for="surname"><fmt:message key="surname"/>*: </label>
            <input class="form-control" name="surname" id="surname" pattern="^[A-Za-zА-ЩЬЮЯҐІЇЄа-щьюяґіїє'\- ]{1,30}"
                   title="<fmt:message key="surname.requirements"/>" required value="${surnameValue}">
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
    </p>
</div>