<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setBundle basename="resources"/>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>

<nav class="navbar navbar-expand-md navbar-light bg-light">
    <div class="container-fluid">
        <a class="navbar-brand" ><span class="mb-0 h4 invisible">Conference Smart App</span></a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarCollapse2"
                aria-controls="navbarCollapse" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarCollapse2">
            <ul class="navbar-nav me-auto mx-4 mb-4 mb-md-0">
                <li class="nav-item">
                    <a class="nav-link text-decoration-underline" aria-current="page"
                       href="controller?action=view-events"><fmt:message key="events"/></a>
                </li>
                <li class="nav-item">
                    <a class="nav-link text-decoration-underline" aria-current="page" href="createEvent.jsp">
                        <fmt:message key="create.event"/>
                    </a>
                </li>
                <li class="nav-item">
                    <a class="nav-link text-decoration-underline" aria-current="page" href="searchEvent.jsp">
                        <fmt:message key="search.event"/>
                    </a>
                </li>
            </ul>
        </div>
    </div>
</nav>
