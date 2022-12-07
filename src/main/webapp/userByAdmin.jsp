<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<fmt:setBundle basename="resources"/>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>

<!DOCTYPE html>
<html lang="${sessionScope.locale}">

<head>
    <title>Conference Smart App. <fmt:message key="view.user"/></title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="css/bootstrap.min.css">
    <script src="js/bootstrap.min.js"></script>
</head>

<body>

<jsp:include page="fragments/mainMenu.jsp"/>

<jsp:include page="fragments/menuChoice.jsp"/>

<div class="col-lg-5 mx-auto p-4 py-md-5">

    <header class="d-flex align-items-center pb-0 mb-3 border-bottom">
        <span class="fs-4"><fmt:message key="view.user"/></span>
    </header>

    <main>
        <p class="fs-5"><fmt:message key="email"/>: ${requestScope.user.email}</p>
        <p class="fs-5"><fmt:message key="name"/>: ${requestScope.user.name}</p>
        <p class="fs-5"><fmt:message key="surname"/>: ${requestScope.user.surname}</p>
        <p class="fs-5"><fmt:message key="role"/>: <fmt:message key="${requestScope.user.role}"/></p>
    </main>

    <form method="GET" action="controller">
        <input type="hidden" name="action" value="set-role">
        <input type="hidden" name="user-id" value=${requestScope.user.id}>
        <button type="submit" class="btn btn-dark mt-4 mb-4"><fmt:message key="set.role"/></button>
        <label>
            <select name="role" class="form-select">
                <option value="VISITOR"><fmt:message key="visitor"/></option>
                <option value="SPEAKER"><fmt:message key="speaker"/></option>
                <option value="MODERATOR"><fmt:message key="moderator"/></option>
                <option value="ADMIN"><fmt:message key="admin"/></option>
            </select>
        </label>
    </form>

    <button class="btn btn-dark mt-4 mb-4" data-bs-toggle="modal" data-bs-target="#exampleModalDefault">
        <fmt:message key="delete"/>
    </button>

    <div class="modal fade" id="exampleModalDefault" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content rounded-4 shadow">
                <div class="modal-header border-bottom-0">
                    <h1 class="modal-title fs-5 text-md-center"><fmt:message key="delete.account"/></h1>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body py-0">
                    <p><fmt:message key="delete.account.confirmation"/></p>
                </div>
                <div class="modal-footer flex-column border-top-0">
                    <form method="POST" action="controller">
                        <input type="hidden" name="action" value="delete-user">
                        <input type="hidden" name="user-id" value=${requestScope.user.id}>
                        <button type="submit" class="btn btn-dark mt-4 mb-4"><fmt:message key="yes"/></button>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>

<jsp:include page="fragments/footer.jsp"/>

</body>
</html>