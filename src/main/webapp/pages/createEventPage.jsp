<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<fmt:setBundle basename="resources"/>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>

<div class="col-lg-5 mx-auto p-4 py-md-5">

    <header class="d-flex align-items-center pb-0 mb-3 border-bottom">
        <span class="fs-4"><fmt:message key="create.event"/></span>
    </header>

    <form method="POST" action="controller">
        <input type="hidden" name="action" value="create-event">
        <c:set var="error" value="${requestScope.error}"/>

        <div class="form-group">
            <label class="form-label fs-5" for="title"><fmt:message key="title"/>*: </label>
            <input class="form-control" name="title" id="title"
                   pattern="^[0-9A-Za-zА-ЩЬЮЯҐІЇЄа-щьюяґіїє'.,;:+\-~`!@#$^&*()={}| ]{2,70}"
                   required value="${requestScope.event.title}">
            <c:if test="${fn:contains(error, 'title')}">
                <span class="text-danger"><fmt:message key="${requestScope.error}"/></span>
            </c:if><br>
        </div>

        <div class="form-group">
            <label class="form-label fs-5" for="date"><fmt:message key="date"/>*: </label>
            <input class="form-control" type="date" name="date" id="date"
                   required value="${requestScope.event.date}"/>
            <c:if test="${fn:contains(error, 'date')}">
                <span class="text-danger"><fmt:message key="${requestScope.error}"/></span>
            </c:if><br>
        </div>

        <div class="form-group">
            <label class="form-label fs-5" for="location"><fmt:message key="location"/>*: </label>
            <input class="form-control" name="location" id="location"
                   pattern="^[0-9A-Za-zА-ЩЬЮЯҐІЇЄа-щьюяґіїє'.,;:+\-~`!@#$^&*()={}| ]{2,70}"
                   required value="${requestScope.event.location}">
            <c:if test="${fn:contains(error, 'location')}">
                <span class="text-danger"><fmt:message key="${requestScope.error}"/></span>
            </c:if><br>
        </div>

        <div class="form-group">
            <label class="form-label fs-5" for="description"><fmt:message key="description"/>*: </label>
            <input class="form-control" name="description" id="description"
                   pattern="^[0-9A-Za-zА-ЩЬЮЯҐІЇЄа-щьюяґіїє'.,;:+\-~`!@#$^&*()={}| ]{1,200}"
                   required value="${requestScope.event.description}">
            <c:if test="${fn:contains(error, 'description')}">
                <span class="text-danger"><fmt:message key="${requestScope.error}"/></span>
            </c:if><br>
        </div>

        <button type="submit" class="btn btn-dark mt-4 mb-4"><fmt:message key="submit"/></button>
    </form>
</div>