<%@taglib uri="/WEB-INF/tlds/taglib.tld" prefix="taglib" %>
<taglib:layout title="Account">
  <div class="container">
    <h2>Update Account</h2>
    <form id="updateFruitForm" action="/account/${account.username}" method="put">
      <div class="mb-3">Password <input type="text" class="border" name="password" required /></div>
      <button type="submit" class="btn btn-primary">Update</button>
    </form>
  </div>
</taglib:layout>
