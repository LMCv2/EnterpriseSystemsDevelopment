<%@taglib uri="/WEB-INF/tlds/taglib.tld" prefix="taglib" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="c1" uri="http://java.sun.com/jsp/jstl/core" %>
<taglib:body title="Home">
  <h1>Web System</h1>
  <form action="account?action=login" method="post">
    <div>
      <label for="username">Username:</label>
      <input type="text" id="username" name="username" required>
    </div>
    <div>
      <label for="password">Password:</label>
      <input type="password" id="password" name="password" required>
    </div>
    <button type="submit">Login</button>
  </form>
  <p><a href="/create-account.jsp">Create Account</a></p>
  <c:forEach var="i" begin="1" end="3">
    ${i}
  </c:forEach>
  <c1:forEach var="i" begin="1" end="3">
    ${i}
  </c1:forEach>
</taglib:body>
