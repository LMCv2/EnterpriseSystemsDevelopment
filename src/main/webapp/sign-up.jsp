<%@page pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="jakarta.tags.core" %>
<%@taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@taglib prefix="taglib" uri="/WEB-INF/tlds/taglib.tld" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<taglib:page title="Home">
  <h1>Create Account</h1>
  <form action="/" method="post">
    <input type="hidden" name="action" value="signup">
    <div>
      <label for="username">Username:</label>
      <input type="text" id="username" name="username" required>
    </div>
    <div>
      <label for="password">Password:</label>
      <input type="password" id="password" name="password" required>
    </div>
    <div>
      <select id="role" name="role" required>
        <option value="" disabled selected>Select Role</option>
        <option value="SHOP_STAFF">Shop Staff</option>
        <option value="SOURCE_WAREHOUSE_STAFF">Source Warehouse Staff</option>
        <option value="CENTRAL_WAREHOUSE_STAFF">Central Warehouse Staff</option>
        <option value="SENIOR_MANAGEMENT">Senior Management</option>
      </select>
    </div>
    <button type="submit">Create Account</button>
  </form>
</taglib:page>
