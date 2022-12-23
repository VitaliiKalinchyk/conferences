<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="resources"/>

<div class="col-lg-7 mx-auto p-4 py-md-5">
    <header class="d-flex align-items-center pb-3 mb-3 border-bottom">
        <span class="fs-4"><fmt:message key="your.events"/></span>
    </header>

    <c:if test="${empty requestScope.events}">
        <p class="fs-6"><fmt:message key="zero.events"/></p>
    </c:if>

    <form method="GET" action="controller" class="flex">
        <div class="d-flex justify-content-between">
            <input type="hidden" name="action" value="view-speakers-events">
            <input type="hidden" name="offset" value="0">
            <div class="flex-column">
                <label>
                    <select name="date" class="form-select mt-2" onchange='submit();'>
                        <option><fmt:message key="select.date"/></option>
                        <option value="upcoming" ${param.date eq "upcoming" ? "selected" : ""}>
                            <fmt:message key="upcoming"/>
                        </option>
                        <option value="passed" ${param.date eq "passed" ? "selected" : ""}>
                            <fmt:message key="passed"/>
                        </option>
                    </select>
                </label>
            </div>
            <div class="flex-column">
                <label for="records"><fmt:message key="number.records"/></label>
                <input class="col-2" type="number" min="1" name="records" id="records"
                       value="${not empty requestScope.records ? requestScope.records : "5"}">&nbsp&nbsp&nbsp&nbsp&nbsp
                <button type="submit" class="btn btn-dark mt-2 mb-3"><fmt:message key="submit"/></button>
            </div>
        </div>
    </form>

    <div class="bd-example-snippet bd-code-snippet">
        <div class="bd-example">
            <table class="table table-striped" aria-label="user-table">
                <thead>
                <tr>
                    <c:set var="base" value="controller?action=view-speakers-events&date=${param.date}&"/>
                    <c:set var="byTitle" value="sort=title&"/>
                    <c:set var="byDate" value="sort=date&"/>
                    <c:set var="byLocation" value="sort=location&"/>
                    <c:set var="titleOrder"
                           value="order=${param.sort ne 'title' || param.order eq 'DESC' ? 'ASC' : 'DESC'}"/>
                    <c:set var="dateOrder"
                           value="order=${param.sort ne 'date' || param.order eq 'DESC' ? 'ASC' : 'DESC'}"/>
                    <c:set var="locationOrder"
                           value="order=${param.sort ne 'location' || param.order eq 'DESC' ? 'ASC' : 'DESC'}"/>
                    <c:set var="limits" value="&offset=0&records=${param.records}"/>

                    <th scope="col">
                        <fmt:message key="title"/>
                        <a href="${base.concat(byTitle).concat(titleOrder).concat(limits)}">
                            <i class="bi bi-arrow-down-up link-dark"></i>
                        </a>
                    </th>
                    <th scope="col">
                        <fmt:message key="date"/>
                        <a href="${base.concat(byDate).concat(dateOrder).concat(limits)}">
                            <i class="bi bi-arrow-down-up link-dark"></i>
                        </a>
                    </th>
                    <th scope="col">
                        <fmt:message key="location"/>
                        <a href="${base.concat(byLocation).concat(locationOrder).concat(limits)}">
                            <i class="bi bi-arrow-down-up link-dark"></i>
                        </a>
                    </th>
                    <th scope="col"><fmt:message key="action"/></th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="event" items="${requestScope.events}">
                    <tr>
                        <td><c:out value="${event.title}"/></td>
                        <td><c:out value="${event.date}"/></td>
                        <td><c:out value="${event.location}"/></td>
                        <td>
                            <a class="link-dark" href=controller?action=view-event-by-speaker&event-id=${event.id}>
                                <fmt:message key="view"/>
                            </a>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>


    <c:set var="href" scope="request"
           value="controller?action=view-speakers-events&date=${param.date}&sort=${param.sort}&order=${param.order}&"/>

    <jsp:include page="/fragments/pagination.jsp"/>
</div>