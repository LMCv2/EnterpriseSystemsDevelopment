<%@taglib prefix="taglib" uri="/WEB-INF/tlds/taglib.tld" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<taglib:layout title="Create Account">
  <form:form action="new" method="post" modelAttribute="account" class="max-w-xl space-y-3">
    <div>
      <label for="username">Username:</label>
      <form:input type="text" id="username" path="username" class="w-full rounded border border-gray-300 px-3 py-2" />
    </div>
    <div>
      <label for="password">Password:</label>
      <form:input type="password" id="password" path="password" class="w-full rounded border border-gray-300 px-3 py-2" />
    </div>
    <div>
      <label for="role">Role:</label>
      <form:select id="role" path="role" class="w-full rounded border border-gray-300 px-3 py-2">
        <form:options items="${role_items}" />
      </form:select>
    </div>
    <div>
      <label for="location">Location:</label>
      <form:select id="location" path="location" class="w-full rounded border border-gray-300 px-3 py-2">
        <form:option value="" label="--Please Select--"/>
        <form:options items="${location_items}" itemLabel="name" />
      </form:select>
    </div>
    <input type="submit" value="Create" class="w-full rounded border border-gray-300 px-3 py-2 hover:bg-gray-100" />
  </form:form>
</taglib:layout>
