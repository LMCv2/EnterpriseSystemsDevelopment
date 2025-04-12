<%@taglib uri="/WEB-INF/tlds/taglib.tld" prefix="taglib" %>
<%@taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="now" value="<%=new java.util.Date()%>" />
<taglib:layout title="Event">
  <div class="mb-3 flex justify-between">
    <input type="text" class="rounded border" />
    <a href="/event/new" class="flex items-center rounded border border-gray-300 px-3 py-2 hover:bg-gray-100">
      <div class="i-material-symbols-add?bg mr-3"></div>
      <span>Create</span>
    </a>
  </div>
  <div class="rounded shadow">
    <table class="w-full">
      <tr class="bg-gray-50">
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
        <tr>
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
    </table>
  </div>
</taglib:layout>