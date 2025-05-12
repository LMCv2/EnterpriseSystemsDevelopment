<%@page pageEncoding="UTF-8" %>
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

  <c:if test="${current_account.role=='CENTRAL_WAREHOUSE_STAFF'||current_account.role=='ADMIN'}">
    <nav class="mb-3 flex justify-center">
      <div class="flex space-x-1 rounded border border-gray-200 p-2">
        <a href="?type=event" class="rounded px-3 py-1.5 hover:bg-gray-100 ${param.type==null||param.type.equals('event')?'bg-gray-100 text-amber-600':''}">Event</a>
        <a href="?type=eventgroup" class="rounded px-3 py-1.5 hover:bg-gray-100 ${param.type.equals('eventgroup')?'bg-gray-100 text-amber-600':''}">Event Group</a>
      </div>
    </nav>
  </c:if>

  <nav class="mb-3 flex justify-center">
    <div class="flex space-x-1 rounded border border-gray-200 p-2">
      <a href="?status=all${param.type==null?'':'&type='.concat(param.type)}" class="rounded px-3 py-1.5 hover:bg-gray-100 ${param.status==null||param.status.equals('all')?'bg-gray-100 text-amber-600':''}">All</a>
      <c:forEach items="${status_items}" var="item">
        <a href="?status=${item.key.toLowerCase()}${param.type==null?'':'&type='.concat(param.type)}" class="rounded px-3 py-1.5 hover:bg-gray-100 ${item.key.toLowerCase()==param.status?'bg-gray-100 text-amber-600':''}">${item.value}</a>
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
          <th class="px-3 py-2 text-left">Through</th>
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
            <td class="px-3 py-2">${event.throughLocation.name}</td>
            <td class="px-3 py-2">${event.toLocation.name}</td>
            <td class="px-3 py-2">
              <div class="flex">
                <c:if test="${event.status=='PENDING'}">
                  <div class="flex items-center gap-1 rounded border border-blue-200 bg-blue-100 px-2 py-1 text-sm text-blue-500">
                    <div class="i-material-symbols-autorenew-rounded?mask"></div>
                    <span>${event.status.label}</span>
                  </div>
                </c:if>
                <c:if test="${event.status=='SHIPPED'||event.status=='SHIPPEDCENTRAL'||event.status=='DELIVEREDCENTRAL'}">
                  <div class="flex items-center gap-1 rounded border border-yellow-200 bg-yellow-100 px-2 py-1 text-sm text-yellow-500">
                    <div class="i-material-symbols-delivery-truck-speed-rounded?mask"></div>
                    <span>${event.status.label}</span>
                  </div>
                </c:if>
                <c:if test="${event.status=='DELIVERED'||event.status=='CONFIRMED'}">
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
              </div>
            </td>
            <td class="px-3 py-2"><fmt:formatDate type="both" dateStyle="short" timeStyle="short" value="${event.createDate}" /></td>
            <td class="flex gap-3 px-3 py-2">

              <!-- when pending -->
              <c:if test="${event.status=='PENDING'&&event.eventType=='BORROWING'&&(current_account.role=='SHOP_STAFF'||current_account.role=='ADMIN')}">
                <button class="inline-flex items-center gap-1 py-2 text-amber-600" data-micromodal-trigger="modal-${event.id}-approve">
                  <div class="i-material-symbols-check?mask"></div>
                  <span class="hover:underline">Approve</span>
                </button>
                <button class="inline-flex items-center gap-1 py-2 text-red-600" data-micromodal-trigger="modal-${event.id}-reject">
                  <div class="i-material-symbols-close?mask"></div>
                  <span class="hover:underline">Reject</span>
                </button>
                <div class="fixed inset-0 z-40 aria-hidden:hidden" id="modal-${event.id}-approve" aria-hidden="true">
                  <div class="flex h-full items-center justify-center bg-gray-950/50" data-micromodal-close>
                    <div class="space-y-6 rounded bg-white p-6">
                      <header class="flex">
                        <h3 class="font-semibold">Change Event Status</h3>
                      </header>
                      <main>
                        <p>Are you sure delivering the goods?</p>
                      </main>
                      <footer class="flex gap-3">
                        <form:form action="approve" method="put">
                          <input type="hidden" name="event" value="${event.id}">
                          <button type="submit" class="rounded bg-amber-600 px-3 py-1.5 text-white hover:bg-amber-700">
                            Approve
                          </button>
                        </form:form>
                        <button class="rounded border border-gray-300 px-3 py-1.5 hover:bg-gray-100" data-micromodal-close>Cancel</button>
                      </footer>
                    </div>
                  </div>
                </div>
                <div class="fixed inset-0 z-40 aria-hidden:hidden" id="modal-${event.id}-reject" aria-hidden="true">
                  <div class="flex h-full items-center justify-center bg-gray-950/50" data-micromodal-close>
                    <div class="space-y-6 rounded bg-white p-6">
                      <header class="flex">
                        <h3 class="font-semibold">Change Event Status</h3>
                      </header>
                      <main>
                        <p>Are you sure rejecting the goods?</p>
                      </main>
                      <footer class="flex gap-3">
                        <form:form action="reject" method="put">
                          <input type="hidden" name="event" value="${event.id}">
                          <button type="submit" class="rounded bg-amber-600 px-3 py-1.5 text-white hover:bg-amber-700">
                            Reject
                          </button>
                        </form:form>
                        <button class="rounded border border-gray-300 px-3 py-1.5 hover:bg-gray-100" data-micromodal-close>Cancel</button>
                      </footer>
                    </div>
                  </div>
                </div>
              </c:if>

              <!-- when delivered to warehouse -->
              <c:if test="${event.status=='DELIVEREDCENTRAL'&&(current_account.role=='CENTRAL_WAREHOUSE_STAFF'||current_account.role=='ADMIN')}">
                <button class="inline-flex items-center gap-1 py-2 text-amber-600" data-micromodal-trigger="modal-${event.id}-delivering">
                  <div class="i-material-symbols-check?mask"></div>
                  <span class="hover:underline">Delivering</span>
                </button>
                <div class="fixed inset-0 z-40 aria-hidden:hidden" id="modal-${event.id}-delivering" aria-hidden="true">
                  <div class="flex h-full items-center justify-center bg-gray-950/50" data-micromodal-close>
                    <div class="space-y-6 rounded bg-white p-6">
                      <header class="flex">
                        <h3 class="font-semibold">Change Event Status</h3>
                      </header>
                      <main>
                        <p>Are you sure delivering the goods?</p>
                      </main>
                      <footer class="flex gap-3">
                        <form:form action="delivering" method="put">
                          <input type="hidden" name="event" value="${event.id}">
                          <button type="submit" class="rounded bg-amber-600 px-3 py-1.5 text-white hover:bg-amber-700">
                            Delivering
                          </button>
                        </form:form>
                        <button class="rounded border border-gray-300 px-3 py-1.5 hover:bg-gray-100" data-micromodal-close>Cancel</button>
                      </footer>
                    </div>
                  </div>
                </div>
              </c:if>

              <!-- when shipped -->
              <c:if test="${event.status=='SHIPPED'&&(current_account.role=='SHOP_STAFF'||current_account.role=='ADMIN')}">
                <button class="inline-flex items-center gap-1 py-2 text-amber-600" data-micromodal-trigger="modal-${event.id}-receive">
                  <div class="i-material-symbols-check?mask"></div>
                  <span class="hover:underline">Receive</span>
                </button>
                <div class="fixed inset-0 z-40 aria-hidden:hidden" id="modal-${event.id}-receive" aria-hidden="true">
                  <div class="flex h-full items-center justify-center bg-gray-950/50" data-micromodal-close>
                    <div class="space-y-6 rounded bg-white p-6">
                      <header class="flex">
                        <h3 class="font-semibold">Change Event Status</h3>
                      </header>
                      <main>
                        <p>Are you sure you already received the goods?</p>
                      </main>
                      <footer class="flex gap-3">
                        <form:form action="receive" method="put">
                          <input type="hidden" name="event" value="${event.id}">
                          <button type="submit" class="rounded bg-amber-600 px-3 py-1.5 text-white hover:bg-amber-700">
                            Receive
                          </button>
                        </form:form>
                        <button class="rounded border border-gray-300 px-3 py-1.5 hover:bg-gray-100" data-micromodal-close>Cancel</button>
                      </footer>
                    </div>
                  </div>
                </div>
              </c:if>
               
            </td>
          </tr>
        </c:forEach>
      </tbody>
    </table>
    <div class="flex items-center justify-between p-3">
      <p>Showing ${events.number*events.size+1} to ${events.number*events.size+events.numberOfElements} of ${events.totalElements} results</p>
      <div class="flex space-x-1">
        <c:if test="${events.hasPrevious()}">
          <a href="?page=${events.number}${param.type==null?'':'&type='.concat(param.type)}${param.status==null?'':'&status='.concat(param.status)}" class="rounded border border-gray-200 px-3 py-1 hover:bg-gray-100">Prev</a>
        </c:if>
        <c:if test="${events.hasNext()}">
          <a href="?page=${events.number+2}${param.type==null?'':'&type='.concat(param.type)}${param.status==null?'':'&status='.concat(param.status)}" class="rounded border border-gray-200 px-3 py-1 hover:bg-gray-100">Next</a>
        </c:if>
      </div>
    </div>
  </div>
</taglib:layout>
