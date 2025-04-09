<%@taglib uri="/WEB-INF/tlds/taglib.tld" prefix="taglib" %>
<%@taglib prefix="c" uri="jakarta.tags.core" %>
<taglib:body title="Home">
  <div class="flex h-screen items-center justify-center bg-stone-600">
    <div class="rounded bg-white p-6">
      <h1>Web System</h1>
      <form action="/login" method="post">
        <div>
          <label for="username">Username:</label>
          <input type="text" id="username" name="username" required />
        </div>
        <div>
          <label for="password">Password:</label>
          <input type="password" id="password" name="password" required />
        </div>
        <button type="submit">Login</button>
      </form>
      <p><a href="/sign-up.jsp">Create Account</a></p>
    </div>
  </div>
</taglib:body>
