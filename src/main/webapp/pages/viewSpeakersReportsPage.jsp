<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setBundle basename="resources"/>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>

<div class="col-lg-7 mx-auto p-4 py-md-5">

    <header class="d-flex align-items-center pb-3 mb-5 border-bottom">
        <span class="fs-4"><fmt:message key="view.speakers.reports"/></span>
    </header>

    <c:if test="${empty requestScope.reports}">
        <p class="fs-6"><fmt:message key="zero.reports"/></p>
    </c:if>

    <div class="bd-example-snippet bd-code-snippet">
        <div class="bd-example">
            <table class="table table-striped" aria-label="user-table">
                <thead>
                <tr>
                    <th scope="col"><fmt:message key="topic.name"/></th>
                    <th scope="col"><fmt:message key="event"/></th>
                    <th scope="col"><fmt:message key="date"/></th>
                    <th scope="col"><fmt:message key="location"/></th>
                    <th scope="col"><fmt:message key="action"/></th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="report" items="${requestScope.reports}">
                    <tr>
                        <td><c:out value="${report.topic}"/></td>
                        <td><c:out value="${report.title}"/></td>
                        <td><c:out value="${report.date}"/></td>
                        <td><c:out value="${report.location}"/></td>
                        <td>
                            <form method="POST" action="controller">
                                <input type="hidden" name="action" value="remove-speaker-by-speaker">
                                <input type="hidden" name="report-id" value="${report.id}">
                                <button type="submit" class="btn btn-dark mt-0 mb-0">
                                    <fmt:message key="decline.report"/>
                                </button>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>