<%@taglib prefix="taglib" uri="/WEB-INF/tlds/taglib.tld" %>
<taglib:layout title="Stock">
  <h1 class="mb-3 text-3xl">Consumption of ${stock.fruit.name}</h1>
  <form id="updateFruitForm" action="/stock/update/${stock.id}" method="post" class="max-w-xl space-y-3">
    <div>
      <label for="fruitType">Quantity:</label>
      <input type="number" id="quantity" name="quantity" min="0" max="${stock.quantity}" aria-describedby="helper-text-explanation" class="bg-gray-50 border border-gray-150 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5" placeholder="Input Quantity" value="${stock.quantity}" required />
    </div>
    <input type="submit" value="Save Changes" class="w-full rounded border border-gray-300 px-3 py-2 hover:bg-gray-100" />
  </form>
</taglib:layout>
