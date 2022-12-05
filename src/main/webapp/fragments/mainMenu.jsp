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
  <form method="post">
    <label>
      <select name="locale" onchange='submit();'>
        <option value="en" ${sessionScope.locale eq 'en' ? 'selected' : ''}><fmt:message key="en"/></option>
        <option value="uk_UA" ${sessionScope.locale eq 'uk_UA' ? 'selected' : ''}><fmt:message key="ua"/></option>
      </select>
    </label>
  </form>
</menu>