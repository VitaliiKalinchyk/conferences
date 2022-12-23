<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="resources"/>

<div class="col-lg-5 mx-auto p-4 py-md-5">
  <header class="d-flex align-items-center pb-3 mb-5 border-bottom">
    <span class="fs-4"><fmt:message key="search.users"/></span>
  </header>

  <form method="GET" action="controller">
    <input type="hidden" name="action" value="search-user">

    <div class="form-group">
      <c:if test="${not empty requestScope.message}">
        <span class="text-success"><fmt:message key="${requestScope.message}"/></span>
      </c:if><br>

      <label class="form-label fs-5" for="email"><fmt:message key="search.user"/></label>
      <input class="form-control" type="email" name="email" id="email"
             pattern="[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$" required>
      <c:if test="${not empty requestScope.error}">
        <span class="text-danger"><fmt:message key="${requestScope.error}"/></span>
    </c:if><br>
    </div>

    <button type="submit" class="btn btn-dark mt-4 mb-4"><fmt:message key="search"/></button>
  </form>
</div>