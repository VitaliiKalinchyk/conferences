<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setBundle basename="resources"/>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>

<div class="col-lg-8 mx-auto p-4 py-md-5">
    <header class="d-flex align-items-center pb-3 mb-5 border-bottom">
        <span class="fs-4"><fmt:message key="events"/></span>
    </header>

    <c:if test="${empty requestScope.events}">
        <p class="fs-6"><fmt:message key="zero.events"/></p>
    </c:if>

    <form method="GET" action="controller" class="flex">
        <input type="hidden" name="action" value="view-events">
        <label>
            <select name="date" class="form-select mt-2">
                <option><fmt:message key="select.date"/></option>
                <option value="upcoming" ${param.date eq "upcoming" ? "selected" : ""}><fmt:message key="upcoming"/></option>
                <option value="passed" ${param.date eq "passed" ? "selected" : ""}><fmt:message key="passed"/></option>
            </select>
        </label>
        <label>
            <select name="sort" class="form-select mt-2">
                <option value="id"><fmt:message key="select.sort"/></option>
                <option value="title" ${param.sort eq "title" ? "selected" : ""}><fmt:message key="title"/></option>
                <option value="date" ${param.sort eq "date" ? "selected" : ""}><fmt:message key="date"/></option>
                <option value="location" ${param.sort eq "location" ? "selected" : ""}><fmt:message key="location"/></option>
                <option value="reports" ${param.sort eq "reports" ? "selected" : ""}><fmt:message key="reports"/></option>
                <option value="registrations" ${param.sort eq "registrations" ? "selected" : ""}><fmt:message key="registrations"/></option>
                <option value="visitors" ${param.sort eq "visitors" ? "selected" : ""}><fmt:message key="visitors"/></option>
            </select>
        </label>
        <label>
            <select name="order" class="form-select mt-2">
                <option value="ASC"><fmt:message key="select.order"/></option>
                <option value="ASC" ${param.order eq "ASC" ? "selected" : ""}><fmt:message key="asc"/></option>
                <option value="DESC" ${param.order eq "DESC" ? "selected" : ""}><fmt:message key="desc"/></option>
            </select>
        </label>
        <input type="hidden" name="offset" value="0">
        <label for="records"><fmt:message key="number.records"/></label>
        <input class="col-1" type="number" min="1" value="${not empty requestScope.records ? requestScope.records : "5"}"
               name="records" id="records">&nbsp&nbsp&nbsp&nbsp&nbsp
        <button type="submit" class="btn btn-dark mt-4 mb-4"><fmt:message key="submit"/></button>
    </form>

    <div class="bd-example-snippet bd-code-snippet">
        <div class="bd-example">
            <table class="table table-striped" aria-label="user-table">
                <thead>
                <tr>
                    <th scope="col"><fmt:message key="title"/></th>
                    <th scope="col"><fmt:message key="date"/></th>
                    <th scope="col"><fmt:message key="location"/></th>
                    <th scope="col"><fmt:message key="reports"/></th>
                    <th scope="col"><fmt:message key="registrations"/></th>
                    <th scope="col"><fmt:message key="visitors"/></th>
                    <th scope="col"><fmt:message key="action"/></th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="event" items="${requestScope.events}">
                    <tr>
                        <td><c:out value="${event.title}"/></td>
                        <td><c:out value="${event.date}"/></td>
                        <td><c:out value="${event.location}"/></td>
                        <td><c:out value="${event.reports}"/></td>
                        <td><c:out value="${event.registrations}"/></td>
                        <td><c:out value="${event.visitors}"/></td>
                        <td>
                            <a class="link-dark" href=controller?action=search-event&event-id=${event.id}>
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
           value="controller?action=view-events&date=${param.date}&sort=${param.sort}&order=${param.order}&"/>

    <jsp:include page="/fragments/pagination.jsp"/>
</div>