<%@taglib uri="/WEB-INF/tlds/taglib.tld" prefix="taglib" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<taglib:layout title="Fruit">
  <h1 class="mb-3 text-3xl">Create Fruit</h1>
  <form:form action="new" method="post" modelAttribute="fruit" class="max-w-xl space-y-3">
    <div>
      <label for="name">Name:</label>
      <form:input type="text" id="name" path="name" class="w-full rounded border border-gray-300 px-3 py-2" />
    </div>
    <input type="submit" value="Create" class="w-full rounded border border-gray-300 px-3 py-2 hover:bg-gray-100" />
  </form:form>
</taglib:layout>
