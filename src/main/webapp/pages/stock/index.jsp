<%@taglib prefix="c" uri="jakarta.tags.core" %>
<%@taglib prefix="taglib" uri="/WEB-INF/tlds/taglib.tld" %>
<taglib:layout title="Stock">
  <div class="mb-3 flex justify-between">
    <input type="text" class="rounded border border-gray-300 px-3 py-2" />
  </div>

  <c:if test="${locationType_items!=null}">
    <nav class="flex justify-center">
      <div class="mb-3 flex space-x-1 rounded border border-gray-200 p-1">
        <a href="?type=all" class="rounded px-3 py-2 hover:bg-gray-100 ${param.type==null||param.type.equals('all')?'text-amber-600':''}">All</a>
        <c:forEach items="${locationType_items}" var="item">
          <a href="?type=${item.key.toLowerCase()}" class="rounded px-3 py-2 hover:bg-gray-100 ${item.key.toLowerCase()==param.type?'text-amber-600':''}">${item.value}</a>
        </c:forEach>
      </div>
    </nav>
  </c:if>

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
          <tr class="border-gray-200">
            <td class="px-3 py-2">${stock.fruit.id}</td>
            <td class="px-3 py-2">${stock.fruit.name}</td>
            <td class="px-3 py-2">${stock.location.name}</td>
            <td class="px-3 py-2">${stock.quantity}</td>
            <td class="flex px-3 py-2">
              <a class="inline-block rounded-full p-2 hover:bg-gray-100" href="${stock.id}/add">
                <div class="i-material-symbols-add?bg text-xl"></div>
              </a>
              <a class="inline-block rounded-full p-2 hover:bg-gray-100" href="${stock.id}/remove">
                <div class="i-material-symbols-remove?bg text-xl"></div>
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
          <a href="?page=${stocks.number}" class="rounded border border-gray-200 px-3 py-1 hover:bg-gray-100">Prev</a>
        </c:if>
        <c:if test="${stocks.hasNext()}">
          <a href="?page=${stocks.number+2}" class="rounded border border-gray-200 px-3 py-1 hover:bg-gray-100">Next</a>
        </c:if>
      </div>
    </div>
  </div>
</taglib:layout>
