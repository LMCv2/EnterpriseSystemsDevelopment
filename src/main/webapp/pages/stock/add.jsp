<%@taglib prefix="c" uri="jakarta.tags.core" %>
<%@taglib prefix="taglib" uri="/WEB-INF/tlds/taglib.tld" %>
<taglib:layout title="Stock">
  <div class="mb-3 flex justify-between">
    <input type="text" class="rounded border border-gray-300 px-3 py-2" />
  </div>

  <nav class="flex justify-center">
    <div class="mb-3 flex space-x-1 rounded border border-gray-200 p-2">
      <a href="?type=reservation" class="rounded px-3 py-1.5 hover:bg-gray-100 ${param.type==null||param.type.equals('reservation')?'bg-gray-100 text-amber-600':''}">Reservation</a>
      <a href="?type=borrowing" class="rounded px-3 py-1.5 hover:bg-gray-100 ${param.type.equals('borrowing')?'bg-gray-100 text-amber-600':''}">Borrowing</a>
    </div>
  </nav>

  <div class="divide-y rounded shadow">
    <table class="w-full border-gray-200">
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
            <td class="flex px-3 py-2">
              <a class="inline-block rounded-full p-2 hover:bg-gray-100" href="from/${stock.id}">
                <div class="i-material-symbols-check?bg text-xl"></div>
              </a>
            </td>
          </tr>
        </c:forEach>
      </tbody>
    </table>
    <div class="flex items-center justify-between p-3">
      <p>Showing ${stocks.number*stocks.size+1} to ${stocks.number*stocks.size+stocks.numberOfElements} of ${stocks.totalElements} results</p>
      <div class="flex space-x-1">
        <c:if test="${stocks.hasPrevious()}">
          <a href="?page=${stocks.number}${param.type==null?'':'&type='.concat(param.type)}" class="rounded border border-gray-200 px-3 py-1 hover:bg-gray-100">Prev</a>
        </c:if>
        <c:if test="${stocks.hasNext()}">
          <a href="?page=${stocks.number+2}${param.type==null?'':'&type='.concat(param.type)}" class="rounded border border-gray-200 px-3 py-1 hover:bg-gray-100">Next</a>
        </c:if>
      </div>
    </div>
  </div>
</taglib:layout>
