<%@taglib uri="/WEB-INF/tlds/taglib.tld" prefix="taglib" %>
<taglib:layout title="Fruit">
  <h2 class="font-bold">Add fruit</h2>
  <form id="updateFruitForm" action="/fruit/add-fruit" method="Post" class="space-y-3">
    <div>
      <label for="fruitName">Fruit Name:</label>
      <input type="text" id="fruitName" name="fruitName" required class="w-full rounded border border-gray-300 px-3 py-2" />
    </div>
    <input type="submit" value="Create" class="w-full rounded border border-gray-300 px-3 py-2 hover:bg-gray-100" />
  </form>
</taglib:layout>
