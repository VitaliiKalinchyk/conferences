<%@ attribute name="title" required="true" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="resources"/>

<head>
    <title>Conference Smart App. <fmt:message key="${title}"/></title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="css/bootstrap.min.css">
    <link rel="stylesheet" href="css/bootstrap-icons.css">
    <link rel="stylesheet" href="css/my.css">
    <script src="js/bootstrap.min.js"></script>
    <script src="js/activation.js"></script>
    <script src="js/showPass.js"></script>
    <script src="https://www.google.com/recaptcha/api.js"></script>
</head>