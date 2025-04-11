<%@taglib uri="/WEB-INF/tlds/taglib.tld" prefix="taglib" %>
<taglib:layout title="Fruit">
  <div class="container">
    <h2 class="font-bold">Update fruit type</h2>
    <form id="updateFruitForm" action="/fruit/update/${fruit.id}" method="post"  class="space-y-3">
      <div class="mb-3">
        <label for="fruitType" class="form-label">Fruit Type</label>
        <input type="text" id="fruitName" name="fruitName" class="w-full rounded border border-gray-300 px-3 py-2" required/>
      </div>
      <button type="submit" class="w-full rounded border border-gray-300 px-3 py-2 hover:bg-gray-100">Update</button>
    </form> 
    <form id="updateFruitForm" action="/fruit/delete/${fruit.id}" method="get"  class="my-3">
      <input type="hidden" name="_method" value="${fruit.id}"/>
      <button type="submit" class="w-full rounded border border-gray-300 px-3 py-2 bg-gray-100">Delete</button>
    </form> 
  </div>
</taglib:layout>