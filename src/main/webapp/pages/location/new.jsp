<%@taglib uri="/WEB-INF/tlds/taglib.tld" prefix="taglib" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<taglib:layout title="Account">
  <h2>Create Account</h2>
  <form:form modelAttribute="account" action="/account/new" method="post" class="space-y-3">
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
        <form:options items="${account_role_type}" />
      </form:select>
    </div>
    <input type="submit" value="Create" class="w-full rounded border border-gray-300 px-3 py-2 hover:bg-gray-100" />
  </form:form>
</taglib:layout>
