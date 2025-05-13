<%@page pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="jakarta.tags.core" %>
<%@taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@taglib prefix="taglib" uri="/WEB-INF/tlds/taglib.tld" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<taglib:layout title="Edit Stock">
  <div class="flex gap-3">
    <div>
      <form:form action="edit" method="put" modelAttribute="stock" class="w-xl space-y-3 rounded border border-gray-300 p-3">
        <div>
          <label for="quantity">Quantity:</label>
          <form:input type="number" id="quantity" path="quantity" min="0" class="outline-hidden mt-1 w-full rounded px-3 py-2 ring-1 ring-gray-300 focus:ring-2 focus:ring-amber-600" placeholder="Input Quantity" />
        </div>
        <input type="submit" value="Save Changes" class="w-full rounded border border-gray-300 px-3 py-2 hover:bg-gray-100" />
      </form:form>
    </div>
    <div>
      <div class="flex w-64 flex-col gap-3 rounded border border-gray-300 p-3">
        <div>
          <p class="mb-1">Created at</p>
          <p><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${stock.createDate}" /></p>
        </div>
        <div>
          <p class="mb-1">Last modified at</p>
          <p><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${stock.lastModifiedDate}" /></p>
        </div>
      </div>
    </div>
  </div>
</taglib:layout>
