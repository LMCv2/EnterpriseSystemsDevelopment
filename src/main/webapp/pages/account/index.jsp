<%@taglib uri="/WEB-INF/tlds/taglib.tld" prefix="taglib" %>
<%@taglib prefix="c" uri="jakarta.tags.core" %>
<taglib:layout title="User">
  <div class="container">
    <h2>User List</h2>
    <ul>
      <c:forEach var="account" items="${accounts.content}">
        ${account}<br/>
      </c:forEach>
    </ul>
  </div>
</taglib:layout>
