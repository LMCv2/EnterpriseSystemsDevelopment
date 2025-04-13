<%@taglib prefix="taglib" uri="/WEB-INF/tlds/taglib.tld" %>
<%@taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="now" value="<%=new java.util.Date()%>" />
<taglib:layout title="Event">
  <div class="mb-3 flex justify-between">
    <input type="text" class="rounded border border-gray-300 px-3 py-2" />
  </div>
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
          <th class="px-3 py-2 text-left">Create at</th>
          <th class="px-3 py-2 text-left">Arrival at</th>
        </tr>
        <c:forEach var="event" items="${events.content}">
          <tr class="border-gray-200">
            <td class="px-3 py-2">${event.id}</td>
            <td class="px-3 py-2">${event.fruit.name}</td>
            <td class="px-3 py-2">${event.quantity}</td>
            <td class="px-3 py-2">${event.eventType.label}</td>
            <td class="px-3 py-2">${event.fromLocation.name}</td>
            <td class="px-3 py-2">${event.toLocation.name}</td>
            <td class="px-3 py-2">${event.status.label}</td>
            <td class="px-3 py-2"><fmt:formatDate type="both" dateStyle="short" timeStyle="short" value="${event.eventDate}" /></td>
            <td class="px-3 py-2"><fmt:formatDate type="both" dateStyle="short" timeStyle="short" value="${event.arrivalDate}" /></td>
          </tr>
        </c:forEach>
      </tbody>
    </table>
    <div class="flex items-center justify-between p-3">
      <p>Showing ${events.number*events.size+1} to ${events.number*events.size+events.numberOfElements} of ${events.totalElements} results</p>
      <div class="flex space-x-1">
        <c:if test="${events.hasPrevious()}">
          <a href="?page=${events.number}" class="rounded border border-gray-200 px-3 py-1 hover:bg-gray-100">Prev</a>
        </c:if>
        <c:if test="${events.hasNext()}">
          <a href="?page=${events.number+2}" class="rounded border border-gray-200 px-3 py-1 hover:bg-gray-100">Next</a>
        </c:if>
      </div>
    </div>
  </div>
</taglib:layout>
