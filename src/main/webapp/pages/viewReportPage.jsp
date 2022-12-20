<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setBundle basename="resources"/>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>

<div class="col-lg-6 mx-auto p-4 py-md-5">
    <header class="d-flex align-items-center pb-0 mb-3 border-bottom">
        <span class="fs-4">${requestScope.report.topic}</span>
    </header>

    <span class="fs-5">
        <fmt:message key="speaker.name"/>: ${requestScope.report.speakerName} &nbsp&nbsp
    </span>
    <c:if test="${requestScope.isComing}">
        <c:choose>
            <c:when test="${not empty requestScope.report.speakerName}">
                <form method="post" action="controller">
                    <input type="hidden" name="action" value="set-or-remove-speaker">
                    <input type="hidden" name="todo" value="remove">
                    <input type="hidden" name="report-id" value="${requestScope.report.id}">
                    <button type="submit" class="btn btn-dark mt-3 mb-4"><fmt:message key="remove.speaker"/></button>
                </form>
            </c:when>

            <c:otherwise>
                <form method="post" action="controller">
                    <input type="hidden" name="action" value="set-or-remove-speaker">
                    <input type="hidden" name="todo" value="set">
                    <input type="hidden" name="report-id" value="${requestScope.report.id}">
                    <label>
                        <select name="user-id" class="form-select mt-2">
                            <c:forEach var="speaker" items="${requestScope.speakers}">
                                <option value="${speaker.id}">${speaker.name} ${speaker.surname}</option>
                            </c:forEach>
                        </select>
                    </label>
                    <button type="submit" class="btn btn-dark mt-3 mb-4"><fmt:message key="set.speaker"/></button>
                </form>
            </c:otherwise>
        </c:choose>
    </c:if>

    <c:if test="${not empty requestScope.message}">
        <span class="text-success"><fmt:message key="${requestScope.message}"/></span>
    </c:if>
    <c:if test="${not empty requestScope.error}">
        <span class="text-warning"><fmt:message key="${requestScope.error}"/></span>
    </c:if><br>

    <c:if test="${requestScope.isComing}">

        <form method="post" action="controller">
            <input type="hidden" name="action" value="change-topic">
            <input type="hidden" name="report-id" value="${requestScope.report.id}">
            <label class="fs-5" for="topic"><fmt:message key="change.topic"/>: </label>
            <input class="col-7"  name="topic" id="topic" required value="${requestScope.report.topic}"
                   pattern="^[0-9A-Za-zА-ЩЬЮЯҐІЇЄа-щьюяґіїє'.,;:+\-~`!@#$^&*()={}| ]{2,70}">
            <button type="submit" class="btn btn-dark mt-3 mb-4"><fmt:message key="submit"/></button>
        </form>


        <button class="btn btn-dark mt-3 mb-4" data-bs-toggle="modal" data-bs-target="#exampleModalDefault">
            <fmt:message key="delete"/>
        </button>

    </c:if>

    <br><br>
    <a class="link-dark" href="controller?action=search-event&event-id=${requestScope.report.eventId}">
        <fmt:message key="back.to.conference"/>
    </a>
</div>