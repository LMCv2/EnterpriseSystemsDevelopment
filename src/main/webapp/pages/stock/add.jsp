<%@taglib uri="/WEB-INF/tlds/taglib.tld" prefix="taglib" %>
<taglib:layout title="Fruit">
  <h2 class="font-bold">Update fruit quantity</h2>
  <form id="updateFruitForm" action="/stock/update/${stock.id}" method="post"  class="space-y-3">
    <div class="mb-3">
      <label for="fruitType" class="form-label">Fruit ID: ${stock.fruit.id}<br>Fruit Name: ${stock.fruit.name}</label>
      <input type="number" id="quantity" name="quantity" aria-describedby="helper-text-explanation" class="bg-gray-50 border border-gray-150 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5" placeholder="Input Quantity" value="${stock.quantity}" required />
  </div>
    <button type="submit" class="w-full rounded border border-gray-300 px-3 py-2 hover:bg-gray-100">Update</button>
  </form> 
</taglib:layout>
