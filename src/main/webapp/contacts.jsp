<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setBundle basename="resources"/>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>

<!DOCTYPE html>
<html lang="${sessionScope.locale}">

<head>
    <meta charset="UTF-8">
    <title>Conference Smart App. <fmt:message key="contacts"/></title>
</head>

<body>

<menu>
    <strong>
        Conference Smart App
    </strong>
    <a href="index.jsp"><fmt:message key="main"/></a>
    <a href="about.jsp"><fmt:message key="about"/></a>
    <a href="contacts.jsp"><fmt:message key="contacts"/></a>
    <c:choose>
        <c:when test="${sessionScope.loggedUser eq null}">
            <a href="signIn.jsp"><fmt:message key="sign.in"/></a>
            <a href="signUp.jsp"><fmt:message key="sign.up"/></a>
        </c:when>
        <c:otherwise>
            <a href="profile.jsp"><fmt:message key="profile"/></a>
            <a href="controller?action=sign-out"><fmt:message key="sign.out"/></a>
        </c:otherwise>
    </c:choose>
    <form method="POST">
        <label>
            <select name="locale" onchange='submit();'>
                <option value="en" ${sessionScope.locale == 'en' ? 'selected' : ''}><fmt:message key="en"/></option>
                <option value="uk_UA" ${sessionScope.locale == 'uk_UA' ? 'selected' : ''}><fmt:message key="ua"/></option>
            </select>
        </label>
    </form>
</menu>

<header><fmt:message key="contacts"/> – Conference Smart App</header>
<hr>
<p>
    York House, 18 York Road, Maidenhead, Berkshire, SL6 1SF, UK
    <br>
    Registered in England & Wales Company number 02118204
    <br>
    +44 (0) 1628 773300 enquiries@conferencecontacts.co.uk
</p>

<footer>
    <p>
        2022 © Conference Smart App
    </p>
</footer>

</body>

</html>