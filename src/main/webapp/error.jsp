<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setBundle basename="resources"/>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>

<!DOCTYPE html>
<html lang="${sessionScope.locale}">

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title><fmt:message key="oops"/></title>
</head>

<body>

<p><fmt:message key="global.error"/></p>
<br>
<br>
<br>
<br>
<a href="index.jsp"><fmt:message key="to.main"/></a>

</body>

</html>