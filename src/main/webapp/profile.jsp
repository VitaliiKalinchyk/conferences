<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="resources"/>

<!DOCTYPE html>
<html lang="${sessionScope.locale}">

<tags:title title="profile"/>

<body>

<jsp:include page="fragments/mainMenu.jsp"/>

<jsp:include page="fragments/menuChoice.jsp"/>

<div class="col-lg-7 mx-auto p-4 py-md-5">
    <tags:header value="profile.info"/>

    <main>
        <p class="fs-6"><fmt:message key="email"/>:</p>
        <p class="fs-5">${sessionScope.loggedUser.email}</p>

        <p class="fs-6"><fmt:message key="name"/>:</p>
        <p class="fs-5">${sessionScope.loggedUser.name}</p>

        <p class="fs-6"><fmt:message key="surname"/>:</p>
        <p class="fs-5">${sessionScope.loggedUser.surname}</p><br>
    </main>

    <a href="editProfile.jsp" class="btn btn-dark mt-0 mb-1"><fmt:message key="edit.profile"/></a>

</div>

<jsp:include page="fragments/footer.jsp"/>

</body>
</html>