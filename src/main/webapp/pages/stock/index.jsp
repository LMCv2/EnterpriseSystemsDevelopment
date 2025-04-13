<%@taglib prefix="c" uri="jakarta.tags.core" %>
<%@taglib prefix="taglib" uri="/WEB-INF/tlds/taglib.tld" %>
<taglib:layout title="Stock">
  <div class="mb-3 flex justify-between">
    <input type="text" class="rounded border border-gray-300 px-3 py-2" />
  </div>
  <div class="rounded shadow">
    <table class="w-full">
      <tr class="bg-gray-50">
        <th class="px-3 py-2 text-left">Id</th>
        <th class="px-3 py-2 text-left">Fruit</th>
        <th class="px-3 py-2 text-left">Location</th>
        <th class="px-3 py-2 text-left">Quantity</th>
        <th class="px-3 py-2 text-left">Action</th>
      </tr>
      <c:forEach var="stock" items="${stocks.content}">
        <tr>
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
    </table>
  </div>
</taglib:layout>
