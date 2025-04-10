<%@taglib uri="/WEB-INF/tlds/taglib.tld" prefix="taglib" %>
<taglib:layout title="Home">
  <div class="container">
    <h2>Update fruit type</h2>
    <form id="updateFruitForm" action="/add-fruit" method="post">
      <div class="mb-3">
        <label for="fruitType" class="form-label">Fruit Type</label>
        <input
          type="text"
          class="form-control"
          id="fruitName"
          name="fruitName"
          required
        />
      </div>
      <button type="submit" class="btn btn-primary">Update</button>
    </form>
  </div>
</taglib:layout>