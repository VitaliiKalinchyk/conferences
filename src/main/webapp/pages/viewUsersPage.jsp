<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setBundle basename="resources"/>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>

<div class="col-lg-7 mx-auto p-4 py-md-5">
    <header class="d-flex align-items-center pb-3 mb-5 border-bottom">
        <span class="fs-4"><fmt:message key="users"/></span>
    </header>

    <form method="GET" action="controller" class="flex">
        <input type="hidden" name="action" value="view-users">
        <label>
            <select name="role" class="form-select mt-2">
                <option><fmt:message key="select.role"/></option>
                <option value="4" ${param.role eq "4" ? "selected" : ""}><fmt:message key="VISITOR"/></option>
                <option value="3" ${param.role eq "3" ? "selected" : ""}><fmt:message key="SPEAKER"/></option>
                <option value="2" ${param.role eq "2" ? "selected" : ""}><fmt:message key="MODERATOR"/></option>
                <option value="1" ${param.role eq "1" ? "selected" : ""}><fmt:message key="ADMIN"/></option>
            </select>
        </label>
        <label>
            <select name="sort" class="form-select mt-2">
                <option value="id"><fmt:message key="select.sort"/></option>
                <option value="email" ${param.sort eq "email" ? "selected" : ""}><fmt:message key="email"/></option>
                <option value="name" ${param.sort eq "name" ? "selected" : ""}><fmt:message key="name"/></option>
                <option value="surname" ${param.sort eq "surname" ? "selected" : ""}><fmt:message key="surname"/></option>
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
                    <th scope="col"><fmt:message key="id"/></th>
                    <th scope="col"><fmt:message key="email"/></th>
                    <th scope="col"><fmt:message key="name"/></th>
                    <th scope="col"><fmt:message key="surname"/></th>
                    <th scope="col"><fmt:message key="role"/></th>
                    <th scope="col"><fmt:message key="action"/></th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="user" items="${requestScope.users}">
                    <tr>
                        <td><c:out value="${user.id}"/></td>
                        <td><c:out value="${user.email}"/></td>
                        <td><c:out value="${user.name}"/></td>
                        <td><c:out value="${user.surname}"/></td>
                        <td><fmt:message key="${user.role}"/></td>
                        <td>
                            <a class="link-dark" href=controller?action=search-user&email=${user.email}>
                                <fmt:message key="edit"/>
                            </a>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>

    <c:set var="href" value="controller?action=view-users&role=${param.role}&sort=${param.sort}&order=${param.order}&" scope="request"/>

    <jsp:include page="/fragments/pagination.jsp"/>
</div>