<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="resources"/>

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
            <p class="fs-6"><fmt:message key="event"/>: ${requestScope.event.title} </p>

            <form method="POST" action="controller">
                <input type="hidden" name="action" value="offer-report">
                <input type="hidden" name="event-id" value="${requestScope.event.id}">
                <input type="hidden" name="title" value="${requestScope.event.title}">

                <div class="form-group">
                    <c:if test="${not empty requestScope.message}">
                        <span class="text-success"><fmt:message key="${requestScope.message}"/></span>
                    </c:if><br>
                    <label class="form-label fs-5" for="topic"><fmt:message key="topic.name"/>: </label>
                    <input class="form-control" name="topic" id="topic"
                           pattern="^[0-9A-Za-zА-ЩЬЮЯҐІЇЄа-щьюяґіїє'.,;:+\-~`!@#$^&*()={}| ]{2,70}" required value="">
                    <c:if test="${fn:contains(error, 'topic')}">
                        <span class="text-danger"><fmt:message key="${requestScope.error}"/></span>
                    </c:if><br>
                </div>

                <button type="submit" class="btn btn-dark mt-4 mb-4"><fmt:message key="submit"/></button>

            </form>

        <a class="link-dark" href=controller?action=view-event-by-speaker&event-id=${requestScope.event.id}>
            <fmt:message key="back.to.conference"/>
        </a>
        </c:otherwise>
    </c:choose>
</div>