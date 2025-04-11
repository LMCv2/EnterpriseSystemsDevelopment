<%@taglib prefix="c" uri="jakarta.tags.core" %>
<jsp:include page="layout_header.jsp"/>

<div class="flex h-screen pt-16">
  <nav class="flex h-full w-56 flex-col p-3 shadow">
    <c:forEach var="permission" items="${permissions}">
      <a href="/${permission}" class="px-3 py-3 capitalize hover:bg-gray-100">${permission}</a>
    </c:forEach>
  </nav>
  <div class="flex-1 p-3">

  <!-- </div>
</div> -->
