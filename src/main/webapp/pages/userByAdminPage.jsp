<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<fmt:setBundle basename="resources"/>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>

<div class="col-lg-5 mx-auto p-4 py-md-5">

    <header class="d-flex align-items-center pb-0 mb-3 border-bottom">
        <span class="fs-4"><fmt:message key="view.user"/></span>
    </header>

    <c:set var="user" value="${requestScope.user}"/>

    <main>
        <p class="fs-5"><fmt:message key="email"/>: ${requestScope.user.email}</p>
        <p class="fs-5"><fmt:message key="name"/>: ${requestScope.user.name}</p>
        <p class="fs-5"><fmt:message key="surname"/>: ${requestScope.user.surname}</p>
        <p class="fs-5"><fmt:message key="role"/>: <fmt:message key="${requestScope.user.role}"/></p>
    </main>

    <form method="POST" action="controller">
        <input type="hidden" name="action" value="set-role">
        <input type="hidden" name="email" value=${requestScope.user.email}>
        <label>
            <select name="role" class="form-select mt-2">
                <option value="VISITOR" ${requestScope.user.role eq 'VISITOR' ? 'selected' : ''}>
                    <fmt:message key="VISITOR"/>
                </option>
                <option value="SPEAKER" ${requestScope.user.role eq 'SPEAKER' ? 'selected' : ''}>
                    <fmt:message key="SPEAKER"/>
                </option>
                <option value="MODERATOR" ${requestScope.user.role eq 'MODERATOR' ? 'selected' : ''}>
                    <fmt:message key="MODERATOR"/>
                </option>
                <option value="ADMIN" ${requestScope.user.role eq 'ADMIN' ? 'selected' : ''}>
                    <fmt:message key="ADMIN"/>
                </option>
            </select>
        </label>
        <button type="submit" class="btn btn-dark mt-3 mb-4"><fmt:message key="set.role"/></button>
    </form>

    <button class="btn btn-dark mt-4 mb-4" data-bs-toggle="modal" data-bs-target="#exampleModalDefault">
        <fmt:message key="delete"/>
    </button>
</div>