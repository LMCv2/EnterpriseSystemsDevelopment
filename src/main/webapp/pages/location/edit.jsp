<%@taglib prefix="taglib" uri="/WEB-INF/tlds/taglib.tld" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<taglib:layout title="Location">
  <h1 class="mb-3 text-3xl">Edit Location</h1>
  <form:form action="${location.id}" method="put" modelAttribute="location" class="max-w-xl space-y-3">
    <div>
      <label for="name">Name:</label>
      <form:input type="text" id="name" path="name" class="w-full rounded border border-gray-300 px-3 py-2" />
    </div>
    <div>
      <label for="cityName">CityName:</label>
      <form:input type="text" id="cityName" path="cityName" class="w-full rounded border border-gray-300 px-3 py-2" />
    </div>
    <div>
      <label for="type">Type:</label>
      <form:select id="type" path="type" class="w-full rounded border border-gray-300 px-3 py-2">
        <form:options items="${locationType_items}" />
      </form:select>
    </div>
    <input type="submit" value="Save Changes" class="w-full rounded border border-gray-300 px-3 py-2 hover:bg-gray-100" />
  </form:form>
</taglib:layout>
