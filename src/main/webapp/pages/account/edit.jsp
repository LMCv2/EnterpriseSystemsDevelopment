<%@page pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="jakarta.tags.core" %>
<%@taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@taglib prefix="taglib" uri="/WEB-INF/tlds/taglib.tld" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<taglib:layout title="Edit Account">
  <div class="flex gap-3">
    <div>
      <form:form action="edit" method="put" modelAttribute="account" class="w-xl space-y-3 rounded-lg border border-gray-300 p-3">
        <div>
          <label for="username">Username:</label>
          <form:input type="text" id="username" path="username" disabled="true" class="outline-hidden mt-1 w-full rounded-lg px-3 py-2 ring-1 ring-gray-300 focus:ring-2 focus:ring-amber-600" />
        </div>
        <div>
          <label for="password">Password:</label>
          <form:input type="password" id="password" path="password" class="outline-hidden mt-1 w-full rounded-lg px-3 py-2 ring-1 ring-gray-300 focus:ring-2 focus:ring-amber-600" />
        </div>
        <div>
          <label for="role">Role:</label>
          <form:select id="role" path="role" class="outline-hidden mt-1 w-full rounded-lg px-3 py-2 ring-1 ring-gray-300 focus:ring-2 focus:ring-amber-600">
            <form:options items="${role_items}" />
          </form:select>
        </div>
        <div>
          <label for="location">Location:</label>
          <form:select id="location" path="location" class="outline-hidden mt-1 w-full rounded-lg px-3 py-2 ring-1 ring-gray-300 focus:ring-2 focus:ring-amber-600">
            <form:option value="" label="--Please Select--" />
            <form:options items="${location_items}" itemLabel="name" />
          </form:select>
        </div>
        <div class="pt-2">
          <label class="inline-flex cursor-pointer items-center">
            <form:checkbox id="deleted" path="deleted" class="peer hidden" />
            <div class="relative h-6 w-11 rounded-full bg-gray-200 after:absolute after:left-[2px] after:top-[2px] after:h-5 after:w-5 after:rounded-full after:border after:border-gray-300 after:bg-white after:transition-all after:content-[''] peer-checked:bg-amber-600 peer-checked:after:translate-x-[100%] peer-checked:after:border-white"></div>
            <span class="ml-3">Delete</span>
          </label>
        </div>
        <input type="submit" value="Save Changes" class="w-full rounded-lg border border-gray-300 px-3 py-2 hover:bg-gray-100" />
      </form:form>
    </div>
    <div>
      <div class="flex w-64 flex-col gap-3 rounded-lg border border-gray-300 p-3">
        <div>
          <p class="mb-1">Created at</p>
          <p><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${account.createDate}" /></p>
        </div>
        <div>
          <p class="mb-1">Last modified at</p>
          <p><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${account.lastModifiedDate}" /></p>
        </div>
      </div>
    </div>
  </div>
</taglib:layout>
