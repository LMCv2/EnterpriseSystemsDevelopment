<%@taglib prefix="c" uri="jakarta.tags.core" %>
<jsp:include page="layout_header.jsp"/>

<div class="flex h-screen pt-16">
  <nav class="flex h-full w-56 flex-col p-3 shadow">
    <c:if test="${permissions.contains('dashboard')}">
      <a href="/dashboard/" class="flex items-center px-3 py-3 hover:bg-gray-100 ${pageContext.request.requestURI.contains('/dashboard/')?'bg-gray-100 text-amber-600':''}">
        <div class="i-material-symbols-dashboard?mask mr-3 ${pageContext.request.requestURI.contains('/dashboard/')?'text-amber-600':''}"></div>
        <span>Dashboard</span>
      </a>
    </c:if>
    <c:if test="${permissions.contains('stock')}">
      <a href="/stock/" class="flex items-center px-3 py-3 hover:bg-gray-100 ${pageContext.request.requestURI.contains('/stock/')?'bg-gray-100 text-amber-600':''}">
        <div class="i-material-symbols-garage-home?mask mr-3 ${pageContext.request.requestURI.contains('/stock/')?'text-amber-600':''}"></div>
        <span>Stock</span>
      </a>
    </c:if>
    <c:if test="${permissions.contains('event')}">
      <a href="/event/" class="flex items-center px-3 py-3 hover:bg-gray-100 ${pageContext.request.requestURI.contains('/event/')?'bg-gray-100 text-amber-600':''}">
        <div class="i-material-symbols-event-note?mask mr-3 ${pageContext.request.requestURI.contains('/event/')?'text-amber-600':''}"></div>
        <span>Event</span>
      </a>
    </c:if>
    <c:if test="${permissions.contains('fruit')}">
      <a href="/fruit/" class="flex items-center px-3 py-3 hover:bg-gray-100 ${pageContext.request.requestURI.contains('/fruit/')?'bg-gray-100 text-amber-600':''}">
        <div class="i-material-symbols-package-2?mask mr-3 ${pageContext.request.requestURI.contains('/fruit/')?'text-amber-600':''}"></div>
        <span>Fruit</span>
      </a>
    </c:if>
    <c:if test="${permissions.contains('location')}">
      <a href="/location/" class="flex items-center px-3 py-3 hover:bg-gray-100 ${pageContext.request.requestURI.contains('/location/')?'bg-gray-100 text-amber-600':''}">
        <div class="i-material-symbols-location-automation?mask mr-3 ${pageContext.request.requestURI.contains('/location/')?'text-amber-600':''}"></div>
        <span>Location</span>
      </a>
    </c:if>
    <c:if test="${permissions.contains('account')}">
      <a href="/account/" class="flex items-center px-3 py-3 hover:bg-gray-100 ${pageContext.request.requestURI.contains('/account/')?'bg-gray-100 text-amber-600':''}">
        <div class="i-material-symbols-account-circle?mask mr-3 ${pageContext.request.requestURI.contains('/account/')?'text-amber-600':''}"></div>
        <span>Account</span>
      </a>
    </c:if>
  </nav>
  <div class="flex-1 p-3">

  <!-- </div>
</div> -->
