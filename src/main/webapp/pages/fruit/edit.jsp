<%@taglib prefix="taglib" uri="/WEB-INF/tlds/taglib.tld" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<taglib:layout title="Edit Fruit">
  <form:form action="${fruit.id}" method="put" modelAttribute="fruit" class="max-w-xl space-y-3">
    <div>
      <label for="name">Name:</label>
      <form:input type="text" id="name" path="name" class="w-full rounded border border-gray-300 px-3 py-2" />
    </div>
    <input type="submit" value="Save Changes" class="w-full rounded border border-gray-300 px-3 py-2 hover:bg-gray-100" />
  </form:form>
</taglib:layout>
