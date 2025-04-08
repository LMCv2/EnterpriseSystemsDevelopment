<%@taglib uri="/WEB-INF/tlds/taglib.tld" prefix="taglib" %>
<taglib:body title="Home">
  <h1>Create Account</h1>
    <form action="account?action=create-account" method="post">
      <div>
        <label for="username">Username:</label>
        <input type="text" id="username" name="username" required>
      </div>
      <div>
        <label for="password">Password:</label>
        <input type="password" id="password" name="password" required>
      </div>
      <button type="submit">Create Account</button>
  </form>
</taglib:body>
