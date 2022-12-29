<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page isErrorPage="true" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="resources"/>

<!DOCTYPE html>
<html lang="${sessionScope.locale}">

<tags:title title="oops"/>

<body>

<jsp:include page="fragments/emptyMenu.jsp"/>

<div class="col-lg-5 mx-auto p-4 py-md-5">
    <p class="fs-4 col-md-8 text-error"><fmt:message key="global.error"/></p><br><br><br>

    <p class="fs-6 col-md-8"><a href="index.jsp" class="link-dark"><fmt:message key="to.main"/></a></p>
</div>

<jsp:include page="fragments/footer.jsp"/>

</body>
</html>