<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<fmt:setBundle basename="resources"/>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>

<nav class="navbar navbar-expand-md navbar-light bg-light">
    <div class="container-fluid">
        <a class="navbar-brand" ><span class="mb-0 h4 invisible">Conference Smart App</span></a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarCollapse" aria-controls="navbarCollapse" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse">
            <ul class="navbar-nav me-auto mx-4 mb-4 mb-md-0">
                <li class="nav-item">
                    <a class="nav-link active text-decoration-underline" aria-current="page" href="viewUsers.jsp"><fmt:message key="view.users"/></a>
                </li>
            </ul>
        </div>
    </div>
</nav>


<%--<nav class="navbar navbar-expand-lg navbar-dark bg-dark">--%>
<%--    <div class="container-fluid">--%>
<%--        <div class="collapse navbar-collapse justify-content-md-center">--%>
<%--            <ul class="navbar-nav">--%>
<%--                <li class="nav-item">--%>
<%--                    <a class="nav-link" href="viewUsers.jsp"><fmt:message key="view.users"/></a>--%>
<%--                </li>--%>
<%--            </ul>--%>
<%--        </div>--%>
<%--    </div>--%>
<%--</nav>--%>
