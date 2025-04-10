<%@taglib uri="/WEB-INF/tlds/taglib.tld" prefix="taglib" %>
<taglib:page title="Home">
  <div class="flex h-screen items-center justify-center bg-stone-600">
    <div class="rounded bg-white p-6">
      <h1>Web System - Create Admin Account</h1>
      <form action="/" method="post">
        <input type="hidden" name="action" value="sysinit" />
        <div>
          <label for="username">Username:</label>
          <input type="text" id="username" name="username" required />
        </div>
        <div>
          <label for="password">Password:</label>
          <input type="password" id="password" name="password" required />
        </div>
        <button type="submit">Create Account</button>
      </form>
    </div>
  </div>
</taglib:page>
