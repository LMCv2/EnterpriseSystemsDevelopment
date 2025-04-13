<%@taglib uri="/WEB-INF/tlds/taglib.tld" prefix="taglib" %>
<%@taglib prefix="c" uri="jakarta.tags.core" %>
<taglib:layout title="Fruit">
    <h2 class="font-bold">Update fruit quantity</h2>
    <form id="updateFruitForm" action="/stock/update/${stock.id}" method="post"  class="space-y-3">
      <div class="mb-3">
        <label for="fruitType" class="form-label">Fruit ID: ${stock.fruit.id}<br>Fruit Name: ${stock.fruit.name}<br/>${stock.location}</label>
        <input type="number" id="quantity" name="quantity" aria-describedby="helper-text-explanation" class="bg-gray-50 border border-gray-150 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5" placeholder="Input Quantity" value="${stock.quantity}" required />
    </div>
      <button type="submit" class="w-full rounded border border-gray-300 px-3 py-2 hover:bg-gray-100">Update</button>
    </form> 

    <div class="rounded shadow">
      <table class="w-full">
        <tr class="bg-gray-50">
          <th class="px-3 py-2 text-left">Id</th>
          <th class="px-3 py-2 text-left">Fruit</th>
          <th class="px-3 py-2 text-left">Location</th>
          <th class="px-3 py-2 text-left">Quantity</th>
          <th class="px-3 py-2 text-left">Action</th>
        </tr>
        <c:forEach var="fruitList" items="${fruitList.content}">
          <tr>
            <td class="px-3 py-2">${fruitList.fruit.id}</td>
            <td class="px-3 py-2">${fruitList.fruit.name}</td>
            <td class="px-3 py-2">${fruitList.location.name}</td>
            <td class="px-3 py-2">${fruitList.quantity}</td>
          </tr>
        </c:forEach>
      </table>
    </div>

    
</taglib:layout>
