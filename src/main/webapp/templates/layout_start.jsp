<%@taglib prefix="c" uri="jakarta.tags.core" %>
<jsp:include page="layout_header.jsp"/>

<div class="flex h-screen pt-16">
  <nav class="flex h-full w-56 flex-col p-3 shadow">
    <c:if test="${permissions.contains('dashboard')}">
      <a href="/dashboard" class="flex items-center px-3 py-3 hover:bg-gray-100">
        <div class="i-material-symbols-dashboard mr-3"></div>
        <span>Dashboard</span>
      </a>
    </c:if>
    <c:if test="${permissions.contains('event')}">
      <a href="/event" class="flex items-center px-3 py-3 hover:bg-gray-100">
        <div class="i-material-symbols-event-note mr-3"></div>
        <span>Event</span>
      </a>
    </c:if>
    <c:if test="${permissions.contains('stock')}">
      <a href="/stock" class="flex items-center px-3 py-3 hover:bg-gray-100">
        <div class="i-material-symbols-garage-home mr-3"></div>
        <span>Stock</span>
      </a>
    </c:if>
    <c:if test="${permissions.contains('fruit')}">
      <a href="/fruit" class="flex items-center px-3 py-3 hover:bg-gray-100">
        <div class="i-material-symbols-package-2 mr-3"></div>
        <span>Fruit</span>
      </a>
    </c:if>
    <c:if test="${permissions.contains('location')}">
      <a href="/location" class="flex items-center px-3 py-3 hover:bg-gray-100">
        <div class="i-material-symbols-location-automation mr-3"></div>
        <span>Location</span>
      </a>
    </c:if>
    <c:if test="${permissions.contains('account')}">
      <a href="/account" class="flex items-center px-3 py-3 hover:bg-gray-100">
        <div class="i-material-symbols-account-circle mr-3"></div>
        <span>Account</span>
      </a>
    </c:if>
  </nav>
  <div class="flex-1 p-3">

  <!-- </div>
</div> -->
