<%@taglib uri="/WEB-INF/tlds/taglib.tld" prefix="taglib" %>
<%@taglib prefix="c" uri="jakarta.tags.core" %>
<taglib:layout title="Fruit">
  <div class="container">
    <h2>Fruits List</h2>
    <ul>
      <c:forEach var="fruit" items="${fruits}">
        <c:out value="name: ${fruit}"/><br/>
      </c:forEach>
    </ul>${fruit}
  </div>
</taglib:layout>
