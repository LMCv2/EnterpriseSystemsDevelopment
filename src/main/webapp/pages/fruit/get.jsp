<%@taglib prefix="c" uri="jakarta.tags.core" %>
<%@taglib prefix="taglib" uri="/WEB-INF/tlds/taglib.tld" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<taglib:layout title="View Fruit">
  <div class="mb-3 max-w-xl space-y-3">
    <div>
      <label for="name">Name:</label>
      <input type="text" id="name" path="name" class="w-full rounded border border-gray-300 px-3 py-2" value="${fruit.name}" />
    </div>
  </div>

  <div class="divide-y rounded shadow">
    <table class="w-full border-gray-200">
      <tbody class="divide-y">
        <tr class="border-gray-200 bg-stone-100">
          <th class="px-3 py-2 text-left">Id</th>
          <th class="px-3 py-2 text-left">Location</th>
          <th class="px-3 py-2 text-left">Quantity</th>
        </tr>
        <c:forEach var="stock" items="${stocks.content}">
          <tr class="h-14 border-gray-200">
            <td class="px-3 py-2">${stock.location.id}</td>
            <td class="px-3 py-2">${stock.location.name}</td>
            <td class="px-3 py-2">${stock.quantity}</td>
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
