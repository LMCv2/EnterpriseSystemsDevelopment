<%@taglib uri="/WEB-INF/tlds/taglib.tld" prefix="taglib" %>
<taglib:layout title="Account">
  <div class="container">
    <h2>Create Account</h2>
    <form id="updateFruitForm" action="/account/${account.username}" method="put" class="space-y-3">
      <div>
        <label for="username">Username:</label>
        <input type="text" id="username" name="username" required class="w-full rounded border border-gray-300 px-3 py-2" />
      </div>
      <div>
        <label for="password">Password:</label>
        <input type="password" id="password" name="password" required class="w-full rounded border border-gray-300 px-3 py-2" />
      </div>
      <div>
        <label for="role">Role:</label>
        <select id="role" name="role" required class="w-full rounded border border-gray-300 px-3 py-2">
          <option value="SHOP_STAFF">Shop Staff</option>
          <option value="SOURCE_WAREHOUSE_STAFF">Source Warehouse Staff</option>
          <option value="CENTRAL_WAREHOUSE_STAFF">Central Warehouse Staff</option>
          <option value="SENIOR_MANAGEMENT">Senior Management</option>
        </select>
      </div>
      <input type="submit" value="Create" class="w-full rounded border border-gray-300 px-3 py-2 hover:bg-gray-100" />
    </form>
  </div>
</taglib:layout>
