<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setBundle basename="resources"/>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>

<div class="col-lg-7 mx-auto p-4 py-md-5">
    <header class="d-flex align-items-center pb-3 mb-5 border-bottom">
        <span class="fs-4"><fmt:message key="view.up.events.by.visitor"/></span>
    </header>


    <form method="GET" action="controller" class="flex">
        <input type="hidden" name="action" value="view-upcoming-events">
        <label>
            <select name="sort" class="form-select mt-2">
                <option value="id"><fmt:message key="select.sort"/></option>
                <option value="title" ${param.sort eq "title" ? "selected" : ""}><fmt:message key="title"/></option>
                <option value="date" ${param.sort eq "date" ? "selected" : ""}><fmt:message key="date"/></option>
                <option value="location" ${param.sort eq "location" ? "selected" : ""}><fmt:message key="location"/></option>
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
                            <a class="link-dark" href=controller?action=view-event-by-visitor&event-id=${event.id}>
                                <fmt:message key="view"/>
                            </a>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>

    <c:set var="href" value="controller?action=view-upcoming-events&sort=${param.sort}&order=${param.order}&" scope="request"/>

    <jsp:include page="/fragments/pagination.jsp"/>
</div>