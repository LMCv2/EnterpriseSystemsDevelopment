<%@taglib prefix="c" uri="jakarta.tags.core" %>
<%@taglib prefix="taglib" uri="/WEB-INF/tlds/taglib.tld" %>
<taglib:layout title="Account">
  <div class="mb-3 flex justify-between">
    <input type="text" class="rounded border border-gray-300 px-3 py-2" />
    <a href="new" class="flex items-center rounded border border-gray-300 px-3 py-2 hover:bg-gray-100">
      <div class="i-material-symbols-add?bg mr-3"></div>
      <span>Create</span>
    </a>
  </div>
  <div class="rounded shadow">
    <table class="w-full">
      <tr class="bg-gray-50">
        <th class="px-3 py-2 text-left">Username</th>
        <th class="px-3 py-2 text-left">Role</th>
        <th class="px-3 py-2 text-left">Location</th>
        <th class="px-3 py-2 text-left">Action</th>
      </tr>
      <c:forEach var="account" items="${accounts.content}">
      <tr>
        <td class="px-3 py-2">${account.username}</td>
        <td class="px-3 py-2">${account.role.label}</td>
        <td class="px-3 py-2">${account.location.name}</td>
        <td class="flex px-3 py-2">
          <a class="inline-block rounded-full p-2 hover:bg-gray-100" href="${account.username}">
            <div class="i-material-symbols-edit?bg text-xl"></div>
          </a>
        </td>
      </tr>
      </c:forEach>
    </table>
  </div>
</taglib:layout>
