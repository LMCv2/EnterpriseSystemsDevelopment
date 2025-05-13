<%@page pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="jakarta.tags.core" %>
<%@taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@taglib prefix="taglib" uri="/WEB-INF/tlds/taglib.tld" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<taglib:layout title="Edit Location">
  <div class="flex gap-3">
    <div>
      <form:form action="edit" method="put" modelAttribute="location" class="w-xl space-y-3 rounded border border-gray-300 p-3">
        <div>
          <label for="name">Name:</label>
          <form:input type="text" id="name" path="name" class="w-full rounded border border-gray-300 px-3 py-2" />
        </div>
        <div>
          <label for="country">Country:</label>
          <form:input type="text" id="country" path="country" class="w-full rounded border border-gray-300 px-3 py-2" />
        </div>
        <div>
          <label for="city">City:</label>
          <form:input type="text" id="city" path="city" class="w-full rounded border border-gray-300 px-3 py-2" />
        </div>
        <div>
          <label for="type">Type:</label>
          <form:select id="type" path="type" class="w-full rounded border border-gray-300 px-3 py-2">
            <form:options items="${locationType_items}" />
          </form:select>
        </div>
        <div class="flex items-center gap-2">
          <form:checkbox id="deleted" path="deleted" class="rounded border border-gray-300 px-3 py-2" />
          <label for="deleted">Delete</label>
        </div>
        <input type="submit" value="Save Changes" class="w-full rounded border border-gray-300 px-3 py-2 hover:bg-gray-100" />
      </form:form>
    </div>
    <div>
      <div class="rounded border border-gray-300 p-3">
        <p>Created at</p>
        <p><fmt:formatDate type="both" dateStyle="short" timeStyle="short" value="${location.createDate}" /></p>
        <p>Last modified at</p>
        <p><fmt:formatDate type="both" dateStyle="short" timeStyle="short" value="${location.lastModifiedDate}" /></p>
      </div>
    </div>
  </div>
</taglib:layout>
