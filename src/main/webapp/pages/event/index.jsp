<%@taglib prefix="taglib" uri="/WEB-INF/tlds/taglib.tld" %>
<%@taglib prefix="c" uri="jakarta.tags.core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="now" value="<%=new java.util.Date()%>" />
<taglib:layout title="Event">
  <div class="mb-3 flex justify-between">
    <input type="text" class="rounded border border-gray-300 px-3 py-2" />
  </div>

  <nav class="flex justify-center">
    <div class="mb-3 flex space-x-1 rounded border border-gray-200 p-2">
      <a href="?status=all" class="rounded px-3 py-1 hover:bg-gray-100 ${param.status==null||param.status.equals('all')?'bg-gray-100 text-amber-600':''}">All</a>
      <c:forEach items="${status_items}" var="item">
        <a href="?status=${item.key.toLowerCase()}" class="rounded px-3 py-1 hover:bg-gray-100 ${item.key.toLowerCase()==param.status?'bg-gray-100 text-amber-600':''}">${item.value}</a>
      </c:forEach>
    </div>
  </nav>

  <div class="divide-y rounded shadow">
    <table class="w-full border-gray-200">
      <tbody class="divide-y">
        <tr class="border-gray-200 bg-stone-100">
          <th class="px-3 py-2 text-left">Id</th>
          <th class="px-3 py-2 text-left">Fruit</th>
          <th class="px-3 py-2 text-left">Quantity</th>
          <th class="px-3 py-2 text-left">EventType</th>
          <th class="px-3 py-2 text-left">Origin</th>
          <th class="px-3 py-2 text-left">Destination</th>
          <th class="px-3 py-2 text-left">Status</th>
          <th class="px-3 py-2 text-left">Create At</th>
          <th class="px-3 py-2 text-left">Action</th>
        </tr>
        <c:forEach var="event" items="${events.content}">
          <tr class="h-14 border-gray-200">
            <td class="px-3 py-2">${event.id}</td>
            <td class="px-3 py-2">${event.fruit.name}</td>
            <td class="px-3 py-2">${event.quantity}</td>
            <td class="px-3 py-2">${event.eventType.label}</td>
            <td class="px-3 py-2">${event.fromLocation.name}</td>
            <td class="px-3 py-2">${event.toLocation.name}</td>
            <td class="px-3 py-2">
              <div class="flex">
                <c:if test="${event.status=='PENDING'}">
                  <div class="flex items-center gap-1 rounded border border-blue-200 bg-blue-100 px-2 py-1 text-sm text-blue-500">
                    <div class="i-material-symbols-autorenew-rounded?mask"></div>
                    <span>${event.status.label}</span>
                  </div>
                </c:if>
                <c:if test="${event.status=='SHIPPED'}">
                  <div class="flex items-center gap-1 rounded border border-yellow-200 bg-yellow-100 px-2 py-1 text-sm text-yellow-500">
                    <div class="i-material-symbols-delivery-truck-speed-rounded?mask"></div>
                    <span>${event.status.label}</span>
                  </div>
                </c:if>
                <c:if test="${event.status=='DELIVERED'}">
                  <div class="flex items-center gap-1 rounded border border-green-200 bg-green-100 px-2 py-1 text-sm text-green-500">
                    <div class="i-material-symbols-check-circle?mask"></div>
                    <span>${event.status.label}</span>
                  </div>
                </c:if>
                <c:if test="${event.status=='REJECTED'}">
                  <div class="flex items-center gap-1 rounded border border-red-200 bg-red-100 px-2 py-1 text-sm text-red-500">
                    <div class="i-material-symbols-cancel?mask"></div>
                    <span>${event.status.label}</span>
                  </div>
                </c:if>
                <c:if test="${event.status=='CONFIRMED'}">
                  <div class="flex items-center gap-1 rounded border border-green-200 bg-green-100 px-2 py-1 text-sm text-green-500">
                    <div class="i-material-symbols-check-circle?mask"></div>
                    <span>${event.status.label}</span>
                  </div>
                </c:if>
              </div>
            </td>
            <td class="px-3 py-2">
              <fmt:formatDate type="both" dateStyle="short" timeStyle="short" value="${event.eventDate}" />
            </td>
            <td class="flex px-3 py-2">
              <a class="inline-block rounded-full p-2 hover:bg-gray-100" href="${event.id}">
                <div class="i-material-symbols-edit?bg text-xl"></div>
              </a>
            </td>
          </tr>
        </c:forEach>
      </tbody>
    </table>
    <div class="flex items-center justify-between p-3">
      <p>Showing ${events.number*events.size+1} to ${events.number*events.size+events.numberOfElements} of ${events.totalElements} results</p>
      <div class="flex space-x-1">
        <c:if test="${events.hasPrevious()}">
          <a href="?page=${events.number}${param.status==null?'':'&status='.concat(param.status)}" class="rounded border border-gray-200 px-3 py-1 hover:bg-gray-100">Prev</a>
        </c:if>
        <c:if test="${events.hasNext()}">
          <a href="?page=${events.number+2}${param.status==null?'':'&status='.concat(param.status)}" class="rounded border border-gray-200 px-3 py-1 hover:bg-gray-100">Next</a>
        </c:if>
      </div>
    </div>
  </div>
</taglib:layout>
