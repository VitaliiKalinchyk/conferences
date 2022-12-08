<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<fmt:setBundle basename="resources"/>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>

<!DOCTYPE html>
<html lang="${sessionScope.locale}">

<head>
    <title>Conference Smart App. <fmt:message key="view.users"/></title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="css/bootstrap.min.css">
    <script src="js/bootstrap.min.js"></script>
</head>

<body>

<jsp:include page="fragments/mainMenu.jsp"/>

<jsp:include page="fragments/menuChoice.jsp"/>

<div class="col-lg-7 mx-auto p-4 py-md-5">

    <header class="d-flex align-items-center pb-3 mb-5 border-bottom">
        <span class="fs-4"><fmt:message key="users"/></span>
    </header>

    <div class="form-check">
        <input class="form-check-input" type="radio" name="flexRadioDefault" id="flexRadioDefault1">
        <label class="form-check-label" for="flexRadioDefault1">
            Default radio
        </label>
    </div>
    <div class="form-check">
        <input class="form-check-input" type="radio" name="flexRadioDefault" id="flexRadioDefault2" checked>
        <label class="form-check-label" for="flexRadioDefault2">
            Default checked radio
        </label>
    </div>

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
                            <a href=controller?action=search-user&email=${user.email}><fmt:message key="edit"/></a>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>

    <jsp:include page="fragments/footer.jsp"/>

</body>
</html>