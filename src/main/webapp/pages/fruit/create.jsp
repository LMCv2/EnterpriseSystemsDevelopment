<%@page pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="jakarta.tags.core" %>
<%@taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@taglib prefix="taglib" uri="/WEB-INF/tlds/taglib.tld" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<taglib:layout title="Create Fruit">
  <form:form action="create" method="post" modelAttribute="fruit" class="max-w-xl space-y-3">
    <div>
      <label for="name">Name:</label>
      <form:input type="text" id="name" path="name" class="outline-hidden mt-1 w-full rounded px-3 py-2 ring-1 ring-gray-300 focus:ring-2 focus:ring-amber-600" />
    </div>
    <input type="submit" value="Create" class="w-full rounded border border-gray-300 px-3 py-2 hover:bg-gray-100" />
  </form:form>
</taglib:layout>
