<%@page pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="jakarta.tags.core" %>
<%@taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@taglib prefix="taglib" uri="/WEB-INF/tlds/taglib.tld" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<taglib:layout title="Replenish">
  <nav class="mb-3 flex justify-center">
    <div class="flex space-x-1 rounded-lg border border-gray-200 p-2">
      <a href="?type=reservation" class="rounded-lg px-3 py-1.5 hover:bg-gray-100 ${param.type==null||param.type.equals('reservation')?'bg-gray-100 text-amber-600':''}">Reservation</a>
      <a href="?type=borrowing" class="rounded-lg px-3 py-1.5 hover:bg-gray-100 ${param.type.equals('borrowing')?'bg-gray-100 text-amber-600':''}">Borrowing</a>
    </div>
  </nav>

  <div class="divide-y divide-gray-200 rounded-lg border border-gray-200">
    <div class="justify-right flex p-3">
      <div class="flex items-center gap-2 rounded p-2 ring-1 ring-gray-300 focus-within:ring-2 focus-within:ring-amber-600">
        <div class="i-material-symbols-search?mask text-2xl text-gray-400"></div>
        <input type="search" class="outline-hidden placeholder:text-gray-400" placeholder="Search" />
      </div>
    </div>
    <table class="w-full">
      <tbody class="divide-y">
        <tr class="border-gray-200 bg-stone-100">
          <th class="px-3 py-2 text-left">Id</th>
          <th class="px-3 py-2 text-left">Fruit</th>
          <th class="px-3 py-2 text-left">Location</th>
          <th class="px-3 py-2 text-left">Quantity</th>
          <th class="px-3 py-2 text-left">Action</th>
        </tr>
        <c:forEach var="stock" items="${stocks.content}">
          <tr class="h-14 border-gray-200">
            <td class="px-3 py-2">${stock.id}</td>
            <td class="px-3 py-2">${stock.fruit.name}</td>
            <td class="px-3 py-2">${stock.location.name}</td>
            <td class="px-3 py-2">${stock.quantity}</td>
            <td class="flex gap-3 px-3 py-2">
              <a class="inline-flex items-center gap-1 py-2 text-amber-600" href="from/${stock.id}">
                <div class="i-material-symbols-check?mask"></div>
                <span class="hover:underline">Select</span>
              </a>
            </td>
          </tr>
        </c:forEach>
      </tbody>
    </table>
    <c:if test="${stocks.totalElements==0}"><div class="p-3 text-gray-400">No Records</div></c:if>
    <div class="flex items-center justify-between p-3">
      <p>Showing ${stocks.totalElements==0?0:stocks.number*stocks.size+1} to ${stocks.number*stocks.size+stocks.numberOfElements} of ${stocks.totalElements} results</p>
      <div class="flex space-x-1">
        <c:if test="${stocks.hasPrevious()}">
          <a href="?page=${stocks.number}${param.type==null?'':'&type='.concat(param.type)}" class="rounded-lg border border-gray-200 px-3 py-1 hover:bg-gray-100">Prev</a>
        </c:if>
        <c:if test="${stocks.hasNext()}">
          <a href="?page=${stocks.number+2}${param.type==null?'':'&type='.concat(param.type)}" class="rounded-lg border border-gray-200 px-3 py-1 hover:bg-gray-100">Next</a>
        </c:if>
      </div>
    </div>
  </div>
</taglib:layout>
