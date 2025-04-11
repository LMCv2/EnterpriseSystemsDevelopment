<%@taglib uri="/WEB-INF/tlds/taglib.tld" prefix="taglib" %>
<%@taglib prefix="c" uri="jakarta.tags.core" %>
<taglib:layout title="Account">
  <div class="flex justify-between mb-3">
    <input type="text" class="border rounder"/>
    <button class="border px-3 py-2 rounder">Create</button>
  </div>
  <div class="rounded shadow">
    <table class="w-full">
      <tr class="bg-gray-50">
        <th class="px-3 py-2 text-left">Username</th>
        <th class="px-3 py-2 text-left">Role</th>
        <th class="px-3 py-2 text-left">Action</th>
      </tr>
      <c:forEach var="account" items="${accounts.content}">
        <tr class="">
          <td class="px-3 py-2">${account.username}</td>
          <td class="px-3 py-2">${account.role}</td>
          <td class="px-3 py-2">
            <a href="/account/${account.username}">Edit</a>
          </td>
        </tr>
      </c:forEach>
    </table>
  </div>
</taglib:layout>
