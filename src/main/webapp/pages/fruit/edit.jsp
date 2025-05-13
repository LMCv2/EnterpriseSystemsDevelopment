<%@page pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="jakarta.tags.core" %>
<%@taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@taglib prefix="taglib" uri="/WEB-INF/tlds/taglib.tld" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<taglib:layout title="Edit Fruit">
  <div class="flex gap-3">
    <div>
      <form:form action="edit" method="put" modelAttribute="fruit" class="w-xl space-y-3 rounded border border-gray-300 p-3">
        <div>
          <label for="name">Name:</label>
          <form:input type="text" id="name" path="name" class="outline-hidden mt-1 w-full rounded px-3 py-2 ring-1 ring-gray-300 focus:ring-2 focus:ring-amber-600" />
        </div>
        <div class="flex items-center gap-2">
          <form:checkbox id="deleted" path="deleted" class="rounded border border-gray-300 px-3 py-2" />
          <label for="deleted">Delete</label>
        </div>
        <input type="submit" value="Save Changes" class="w-full rounded border border-gray-300 px-3 py-2 hover:bg-gray-100" />
      </form:form>
    </div>
    <div>
      <div class="flex w-64 flex-col gap-3 rounded border border-gray-300 p-3">
        <div>
          <p class="mb-1">Created at</p>
          <p><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${fruit.createDate}" /></p>
        </div>
        <div>
          <p class="mb-1">Last modified at</p>
          <p><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${fruit.lastModifiedDate}" /></p>
        </div>
      </div>
    </div>
  </div>
</taglib:layout>
