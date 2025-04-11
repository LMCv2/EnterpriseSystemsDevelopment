<%@taglib uri="/WEB-INF/tlds/taglib.tld" prefix="taglib" %>
<%@taglib prefix="c" uri="jakarta.tags.core" %>
<taglib:layout title="User">
<h2>User List</h2>
<table class="w-full">
  <tr class="bg-gray-50">
    <th class="px-6 py-4">Username</th>
    <th class="px-6 py-4">Role</th>
    <th class="px-6 py-4">Button</th>
  </tr>
  <c:forEach var="account" items="${accounts.content}">
      <tr class="">
        <td class="px-6 py-4">${account.username}</td>
        <td class="px-6 py-4">${account.role}</td>
        <td class="px-6 py-4">Edit</td>
      </tr>
    </c:forEach>
</table>
</taglib:layout>
