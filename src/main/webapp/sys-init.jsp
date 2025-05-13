<%@page pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="jakarta.tags.core" %>
<%@taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@taglib prefix="taglib" uri="/WEB-INF/tlds/taglib.tld" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<taglib:page title="Home">
  <div class="flex h-screen items-center justify-center bg-stone-600 bg-hero-graph-paper-stone-600">
    <div class="rounded-lg bg-white p-6 space-y-3">
      <div class="flex items-center">
        <div class="i-fluent-emoji-shortcake?bg mr-3 text-3xl"></div>
        <span class="text-xl font-bold">Acer International Bakery - Create Admin Account</span>
      </div>
      <form action="/" method="post" class="space-y-3">
        <input type="hidden" name="action" value="sysinit" />
        <div>
          <label for="username">Username:</label>
          <input type="text" id="username" name="username" required class="outline-hidden mt-1 w-full rounded-lg px-3 py-2 ring-1 ring-gray-300 focus:ring-2 focus:ring-amber-600" />
        </div>
        <div>
          <label for="password">Password:</label>
          <input type="password" id="password" name="password" required class="outline-hidden mt-1 w-full rounded-lg px-3 py-2 ring-1 ring-gray-300 focus:ring-2 focus:ring-amber-600" />
        </div>
        <input type="submit" value="Create Account" class="w-full rounded-lg border border-gray-300 px-3 py-2 hover:bg-gray-100" />
      </form>
    </div>
  </div>
</taglib:page>
