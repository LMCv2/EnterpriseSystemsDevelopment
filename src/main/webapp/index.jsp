<!doctype html>
<html>
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <%-- <script src="https://cdn.jsdelivr.net/npm/@tailwindcss/browser@4"></script> --%>
  </head>
  <body>
    <h1>Web System</h1>
    <form action="/login" method="post">
      <div>
        <label for="username">Username:</label>
        <input type="text" id="username" name="username" required>
      </div>
      <div>
        <label for="password">Password:</label>
        <input type="password" id="password" name="password" required>
      </div>
      <button type="submit">Login</button>
    </form>
    <p><a href="/create-account.jsp">Create Account</a></p>
  </body>
</html>
