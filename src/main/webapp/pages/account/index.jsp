<%@taglib uri="/WEB-INF/tlds/taglib.tld" prefix="taglib" %>
<%@taglib prefix="c" uri="jakarta.tags.core" %>
<taglib:layout title="Account">
  <div class="flex justify-between mb-3">
    <input type="text" class="border rounded"/>
    <a href="/account/new" class="flex items-center rounded border border-gray-300 px-3 py-2 hover:bg-gray-100">
      <div class="i-material-symbols-add mr-3"></div>
      <span>Create</span>
    </a>
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
            <a class="py-2.5 px-5 me-2 mb-2 text-sm font-medium text-gray-900 focus:outline-none bg-white rounded-full border border-gray-400 hover:bg-gray-100 hover:text-blue-700 focus:z-10 focus:ring-4 focus:ring-gray-100" href="/account/${account.username}">Edit</a>
          </td>
        </tr>
      </c:forEach>
    </table>
  </div>
</taglib:layout>
