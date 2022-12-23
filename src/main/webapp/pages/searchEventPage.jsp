<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="resources"/>

<div class="col-lg-5 mx-auto p-4 py-md-5">
  <header class="d-flex align-items-center pb-3 mb-5 border-bottom">
    <span class="fs-4"><fmt:message key="search.event"/></span>
  </header>

  <form method="GET" action="controller">
    <input type="hidden" name="action" value="search-event">

    <div class="form-group">
      <c:if test="${not empty requestScope.message}">
        <span class="text-success"><fmt:message key="${requestScope.message}"/></span>
      </c:if><br>
      <label class="form-label fs-5" for="title"><fmt:message key="search.event"/></label>
      <input class="form-control" name="title" id="title" value="${requestScope.title}"
             pattern="^[0-9A-Za-zА-ЩЬЮЯҐІЇЄа-щьюяґіїє'.,;:+\-~`!@#$^&*()={}| ]{2,70}" required>
      <c:if test="${not empty requestScope.error}">
        <span class="text-danger"><fmt:message key="${requestScope.error}"/></span>
    </c:if><br>
    </div>

    <button type="submit" class="btn btn-dark mt-4 mb-4"><fmt:message key="search"/></button>

  </form>
</div>