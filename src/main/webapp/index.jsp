<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="resources"/>

<!DOCTYPE html>
<html lang="${sessionScope.locale}">

<tags:title title="main"/>

<body>

<jsp:include page="fragments/mainMenu.jsp"/>

<jsp:include page="fragments/menuChoice.jsp"/><br><br>

<figure class="text-center">
    <img src="img/gartner-conf-e1551786210967.jpg" class="figure-img img-fluid rounded" alt="<fmt:message key="pic"/>">
    <figcaption class="figure-caption"><fmt:message key="pic.description"/></figcaption>
</figure>

<jsp:include page="fragments/footer.jsp"/>

</body>
</html>