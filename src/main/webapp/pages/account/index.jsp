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

  <nav class="flex justify-center">
    <div class="mb-3 flex space-x-1 rounded border border-gray-200 p-2">
      <a href="?role=all" class="rounded px-3 py-1.5 hover:bg-gray-100 ${param.role==null||param.role.equals('all')?'bg-gray-100 text-amber-600':''}">All</a>
      <c:forEach items="${role_items}" var="item">
        <a href="?role=${item.key.toLowerCase()}" class="rounded px-3 py-1.5 hover:bg-gray-100 ${item.key.toLowerCase()==param.role?'bg-gray-100 text-amber-600':''}">${item.value}</a>
      </c:forEach>
    </div>
  </nav>

  <div class="divide-y rounded shadow">
    <table class="w-full border-gray-200">
      <tbody class="divide-y">
        <tr class="border-gray-200 bg-stone-100">
          <th class="px-3 py-2 text-left">Username</th>
          <th class="px-3 py-2 text-left">Role</th>
          <th class="px-3 py-2 text-left">Location</th>
          <th class="px-3 py-2 text-left">Action</th>
        </tr>
        <c:forEach var="account" items="${accounts.content}">
          <tr class="h-14 border-gray-200">
            <td class="px-3 py-2">${account.username}</td>
            <td class="px-3 py-2">${account.role.label}</td>
            <td class="px-3 py-2">${account.location.name}</td>
            <td class="flex gap-3 px-3 py-2">
              <a class="inline-flex items-center gap-1 p-2 text-amber-600" href="${account.username}">
                <div class="i-material-symbols-edit?mask"></div>
                <span class="hover:underline">Edit</span>
              </a>
            </td>
          </tr>
        </c:forEach>
      </tbody>
    </table>
    <div class="flex items-center justify-between p-3">
      <p>Showing ${accounts.number*accounts.size+1} to ${accounts.number*accounts.size+accounts.numberOfElements} of ${accounts.totalElements} results</p>
      <div class="flex space-x-1">
        <c:if test="${accounts.hasPrevious()}">
          <a href="?page=${accounts.number}${param.role==null?'':'&role='.concat(param.role)}" class="rounded border border-gray-200 px-3 py-1 hover:bg-gray-100">Prev</a>
        </c:if>
        <c:if test="${accounts.hasNext()}">
          <a href="?page=${accounts.number+2}${param.role==null?'':'&role='.concat(param.role)}" class="rounded border border-gray-200 px-3 py-1 hover:bg-gray-100">Next</a>
        </c:if>
      </div>
    </div>
  </div>
</taglib:layout>
