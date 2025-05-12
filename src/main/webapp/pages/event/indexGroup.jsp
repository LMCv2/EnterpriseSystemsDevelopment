<%@taglib prefix="c" uri="jakarta.tags.core" %>
<%@taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@taglib prefix="taglib" uri="/WEB-INF/tlds/taglib.tld" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<script>
  <c:if test="${error != null}">alert("${error}");</c:if>;
</script>
<taglib:layout title="Event">
  <div class="mb-3 flex justify-between">
    <div class="flex items-center gap-2 rounded p-2 ring-1 ring-gray-300 focus-within:ring-2 focus-within:ring-amber-600">
      <div class="i-material-symbols-search?mask text-2xl text-gray-400"></div>
      <input type="search" class="outline-hidden placeholder:text-gray-400" placeholder="Search" />
    </div>
  </div>

  <nav class="mb-3 flex justify-center">
    <div class="flex space-x-1 rounded border border-gray-200 p-2">
      <a href="?type=event" class="rounded px-3 py-1.5 hover:bg-gray-100 ${param.type==null||param.type.equals('event')?'bg-gray-100 text-amber-600':''}">Event</a>
      <a href="?type=eventgroup" class="rounded px-3 py-1.5 hover:bg-gray-100 ${param.type.equals('eventgroup')?'bg-gray-100 text-amber-600':''}">Event Group</a>
    </div>
  </nav>
  
  <nav class="mb-3 flex justify-center">
    <div class="flex space-x-1 rounded border border-gray-200 p-2">
      <a href="?status=all" class="rounded px-3 py-1.5 hover:bg-gray-100 ${param.status==null||param.status.equals('all')?'bg-gray-100 text-amber-600':''}">All</a>
      <c:forEach items="${status_items}" var="item">
        <a href="?status=${item.key.toLowerCase()}" class="rounded px-3 py-1.5 hover:bg-gray-100 ${item.key.toLowerCase()==param.status?'bg-gray-100 text-amber-600':''}">${item.value}</a>
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
            <td class="px-3 py-2">${event[0].id}</td>
            <td class="px-3 py-2">${event[0].fruit.name}</td>
            <td class="px-3 py-2">${event.stream().map(e -> e.quantity).sum()}</td>
            <td class="px-3 py-2">${event[0].eventType.label}</td>
            <td class="px-3 py-2">${event[0].fromLocation.name}</td>
            <td class="px-3 py-2">${event[0].throughLocation.name}</td>
            <td class="px-3 py-2">
              <div class="flex">
                <c:if test="${event[0].status=='PENDING'}">
                  <div class="flex items-center gap-1 rounded border border-blue-200 bg-blue-100 px-2 py-1 text-sm text-blue-500">
                    <div class="i-material-symbols-autorenew-rounded?mask"></div>
                    <span>${event[0].status.label}</span>
                  </div>
                </c:if>
                <c:if test="${event[0].status=='SHIPPED'}">
                  <div class="flex items-center gap-1 rounded border border-yellow-200 bg-yellow-100 px-2 py-1 text-sm text-yellow-500">
                    <div class="i-material-symbols-delivery-truck-speed-rounded?mask"></div>
                    <span>${event[0].status.label}</span>
                  </div>
                </c:if>
                <c:if test="${event[0].status=='DELIVERED'}">
                  <div class="flex items-center gap-1 rounded border border-green-200 bg-green-100 px-2 py-1 text-sm text-green-500">
                    <div class="i-material-symbols-check-circle?mask"></div>
                    <span>${event[0].status.label}</span>
                  </div>
                </c:if>
                <c:if test="${event[0].status=='REJECTED'}">
                  <div class="flex items-center gap-1 rounded border border-red-200 bg-red-100 px-2 py-1 text-sm text-red-500">
                    <div class="i-material-symbols-cancel?mask"></div>
                    <span>${event[0].status.label}</span>
                  </div>
                </c:if>
                <c:if test="${event[0].status=='CONFIRMED'}">
                  <div class="flex items-center gap-1 rounded border border-green-200 bg-green-100 px-2 py-1 text-sm text-green-500">
                    <div class="i-material-symbols-check-circle?mask"></div>
                    <span>${event[0].status.label}</span>
                  </div>
                </c:if>
              </div>
            </td>
            <td class="px-3 py-2">
              <fmt:formatDate type="both" dateStyle="short" timeStyle="short" value="${event[0].createDate}" />
            </td>
            <td class="flex gap-3 px-3 py-2">
              <button class="inline-flex items-center gap-1 py-2 text-amber-600" data-micromodal-trigger="modal-${event[0].id}">
                <div class="i-material-symbols-edit?mask"></div>
                <span class="hover:underline">Edit</span>
              </button>
              <div class="aria-hidden:hidden" id="modal-${event[0].id}" aria-hidden="true">
                <div class="fixed inset-0 flex items-center justify-center bg-gray-950/50" data-micromodal-close>
                  <c:choose>
                    <c:when test="${current_account.getLocation().type=='SHOP' && event[0].status =='DELIVERED' && event[0].toLocation.type=='SHOP'}">
                      <div class="space-y-6 rounded bg-white p-6">
                        <header class="flex">
                          <h3 class="font-semibold">Change Event Status</h3>
                        </header>
                        <main>
                          <p>Are you sure you already received the goods?</p>
                        </main>
                        <footer class="flex gap-3">
                          <a class="rounded bg-amber-600 px-3 py-1.5 text-white hover:bg-amber-700" href="${event.id}">Confirm</a>
                          <button class="rounded border border-gray-300 px-3 py-1.5 hover:bg-gray-100" data-micromodal-close>Cancel</button>
                        </footer>
                      </div>
                    </c:when>
                    <c:when test="${current_account.getLocation().type=='SOURCE_WAREHOUSE' && event[0].status =='PENDING' && event[0].toLocation.type=='CENTRAL_WAREHOUSE'}">
                      <div class="space-y-6 rounded bg-white p-6">
                        <header class="flex">
                          <h3 class="font-semibold">Change Event Status</h3>
                        </header>
                        <main>
                          <p>Are you sure delivering the goods?</p>
                        </main>
                        <footer class="flex gap-3">
                          <a class="rounded bg-amber-600 px-3 py-1.5 text-white hover:bg-amber-700" href="${event.id}">Confirm</a>
                          <a class="rounded bg-red-600 px-3 py-1.5 text-white hover:bg-red-700" href="${event.id}?action=reject">Reject</a>
                          <button class="rounded border border-gray-300 px-3 py-1.5 hover:bg-gray-100" data-micromodal-close>Cancel</button>
                        </footer>
                      </div>
                    </c:when>
                    <c:when test="${current_account.getLocation().type=='CENTRAL_WAREHOUSE' && event[0].status =='SHIPPED' && event[0].toLocation.type=='CENTRAL_WAREHOUSE'}">
                      <div class="space-y-6 rounded bg-white p-6">
                        <header class="flex">
                          <h3 class="font-semibold">Change Event Status</h3>
                        </header>
                        <main>
                          <p>Are you sure delivering the goods?</p>
                        </main>
                        <footer class="flex gap-3">
                          <a class="rounded bg-amber-600 px-3 py-1.5 text-white hover:bg-amber-700" href="${event.id}">Confirm</a>
                          <a class="rounded bg-red-600 px-3 py-1.5 text-white hover:bg-red-700" href="${event.id}?action=reject">Reject</a>
                          <button class="rounded border border-gray-300 px-3 py-1.5 hover:bg-gray-100" data-micromodal-close>Cancel</button>
                        </footer>
                      </div>
                    </c:when>
                    <c:when test="${current_account.getLocation().type=='SHOP' && event[0].status =='PENDING' && event[0].toLocation.type=='SHOP' && event[0].fromLocation.id == current_account.getLocation().id}">
                      <div class="space-y-6 rounded bg-white p-6">
                        <header class="flex">
                          <h3 class="font-semibold">Change Event Status</h3>
                        </header>
                        <main>
                          <p>Are you sure delivering the goods?</p>
                        </main>
                        <footer class="flex gap-3">
                          <a class="rounded bg-amber-600 px-3 py-1.5 text-white hover:bg-amber-700" href="${event.id}">Confirm</a>
                          <a class="rounded bg-red-600 px-3 py-1.5 text-white hover:bg-red-700" href="${event.id}?action=reject">Reject</a>
                          <button class="rounded border border-gray-300 px-3 py-1.5 hover:bg-gray-100" data-micromodal-close>Cancel</button>
                        </footer>
                      </div>
                    </c:when>
                    <c:otherwise>
                      <div class="space-y-6 rounded bg-white p-6">
                        <header class="flex">
                          <h3 class="font-semibold">Error</h3>
                        </header>
                        <main>
                          <p>You a not permitted to do the action</p>
                        </main>
                        <footer class="flex gap-3">
                          <button class="rounded border border-gray-300 px-3 py-1.5 hover:bg-gray-100" data-micromodal-close>Cancel</button>
                        </footer>
                      </div>
                    </c:otherwise>
                  </c:choose>
                </div>
              </div>
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
