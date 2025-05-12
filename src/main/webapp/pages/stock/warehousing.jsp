<%@page pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="jakarta.tags.core" %>
<%@taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@taglib prefix="taglib" uri="/WEB-INF/tlds/taglib.tld" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<taglib:layout title="Stock">
  <div class="mb-3 flex justify-between">
    <div class="flex items-center gap-2 rounded p-2 ring-1 ring-gray-300 focus-within:ring-2 focus-within:ring-amber-600">
      <div class="i-material-symbols-search?mask text-2xl text-gray-400"></div>
      <input type="search" class="outline-hidden placeholder:text-gray-400" placeholder="Search" />
    </div>
  </div>

  <div class="divide-y rounded shadow">
    <table class="w-full border-gray-200">
      <tbody class="divide-y">
        <tr class="border-gray-200 bg-stone-100">
          <th class="px-3 py-2 text-left">Id</th>
          <th class="px-3 py-2 text-left">Fruit Name</th>
          <th class="px-3 py-2 text-left">Inventory</th>
          <th class="px-3 py-2 text-left">Quantity</th>
          <th class="px-3 py-2 text-left">Action</th>
        </tr>
        <c:forEach items="${warehousingList.content}" var="fruit">
          <tr class="h-14 border-gray-200">
            <td class="px-3 py-2">${fruit[0].id}</td>
            <td class="px-3 py-2">${fruit[0].name}</td>
            <td class="px-3 py-2">${fruit[1]}</td>
            <td class="px-3 py-2">${fruit[2]}</td>
            <td><a class="inline-flex items-center gap-1 py-2 text-amber-600" href="${fruit[0].id}/addReservedNeeds?qty=${fruit[2] - fruit[1]}">
              <div class="i-material-symbols-add?mask"></div>
              <span class="hover:underline">Add ${fruit[2] - fruit[1]}</span>
            </a></td>
          </tr>
        </c:forEach>
      </tbody>
    </table>
    <div class="flex items-center justify-between p-3">
      <p>Showing ${warehousingList.number*accounts.size+1} to ${warehousingList.number*warehousingList.size+warehousingList.numberOfElements} of ${warehousingList.totalElements} results</p>
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
