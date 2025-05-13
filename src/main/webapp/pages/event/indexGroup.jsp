<%@page pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="jakarta.tags.core" %>
<%@taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@taglib prefix="taglib" uri="/WEB-INF/tlds/taglib.tld" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<taglib:layout title="Event">
  <c:if test="${error != null}">
    <div class="fixed inset-0 z-40 aria-hidden:hidden" id="modal-error" aria-hidden="true">
      <div class="flex h-full items-center justify-center bg-gray-950/50" data-micromodal-close>
        <div class="space-y-6 rounded-lg bg-white p-6">
          <header class="flex">
            <h3 class="font-semibold">Error</h3>
          </header>
          <main>
            <p>${error}</p>
          </main>
          <footer class="flex gap-3">
            <button class="rounded-lg bg-amber-600 px-3 py-1.5 text-white hover:bg-amber-700" data-micromodal-close>Confirm</button>
          </footer>
        </div>
      </div>
    </div>
    <script>
      MicroModal.show('modal-error');
    </script>
  </c:if>

  <c:if test="${current_account.role=='CENTRAL_WAREHOUSE_STAFF'||current_account.role=='ADMIN'}">
    <nav class="mb-3 flex justify-center">
      <div class="flex space-x-1 rounded-lg border border-gray-200 p-2">
        <a href="?type=event" class="rounded-lg px-3 py-1.5 hover:bg-gray-100 ${param.type==null||param.type.equals('event')?'bg-gray-100 text-amber-600':''}">Event</a>
        <a href="?type=eventgroup" class="rounded-lg px-3 py-1.5 hover:bg-gray-100 ${param.type.equals('eventgroup')?'bg-gray-100 text-amber-600':''}">Event Group</a>
      </div>
    </nav>
  </c:if>
  
  <nav class="mb-3 flex justify-center">
    <div class="flex space-x-1 rounded-lg border border-gray-200 p-2">
      <a href="?status=all${param.type==null?'':'&type='.concat(param.type)}" class="rounded-lg px-3 py-1.5 hover:bg-gray-100 ${param.status==null||param.status.equals('all')?'bg-gray-100 text-amber-600':''}">All</a>
      <a href="?status=pending${param.type==null?'':'&type='.concat(param.type)}" class="rounded-lg px-3 py-1.5 hover:bg-gray-100 ${param.status.equals('pending')?'bg-gray-100 text-amber-600':''}">Pending</a>
      <a href="?status=shipped${param.type==null?'':'&type='.concat(param.type)}" class="rounded-lg px-3 py-1.5 hover:bg-gray-100 ${param.status.equals('shipped')?'bg-gray-100 text-amber-600':''}">Shipped</a>
      <a href="?status=delivered${param.type==null?'':'&type='.concat(param.type)}" class="rounded-lg px-3 py-1.5 hover:bg-gray-100 ${param.status.equals('delivered')?'bg-gray-100 text-amber-600':''}">Delivered</a>
      <a href="?status=rejected${param.type==null?'':'&type='.concat(param.type)}" class="rounded-lg px-3 py-1.5 hover:bg-gray-100 ${param.status.equals('rejected')?'bg-gray-100 text-amber-600':''}">Rejected</a>
    </div>
  </nav>

  <div class="divide-y divide-gray-200 rounded-lg border border-gray-200">
    <div class="justify-right flex p-3">
      <div class="flex items-center gap-2 rounded p-2 ring-1 ring-gray-300 focus-within:ring-2 focus-within:ring-amber-600">
        <div class="i-material-symbols-search?mask text-2xl text-gray-400"></div>
        <input type="search" class="outline-hidden placeholder:text-gray-400" placeholder="Search" />
      </div>
    </div>
    <table class="w-full">
      <tbody class="divide-y">
        <tr class="border-gray-200 bg-stone-100">
          <th class="px-3 py-2 text-left">Id</th>
          <th class="px-3 py-2 text-left">Fruit</th>
          <th class="px-3 py-2 text-left">Quantity</th>
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
            <td class="px-3 py-2">${event[0].fromLocation.name}</td>
            <td class="px-3 py-2">${event[0].throughLocation.name}</td>
            <td class="px-3 py-2">
              <div class="flex">
                <c:if test="${event[0].status=='PENDING'}">
                  <div class="flex items-center gap-1 rounded border border-blue-200 bg-blue-100 px-2 py-1 text-sm text-blue-500">
                    <div class="i-material-symbols-autorenew-rounded?mask"></div>
                    <span>Pending</span>
                  </div>
                </c:if>
                <c:if test="${event[0].status=='SHIPPEDCENTRAL'}">
                  <div class="flex items-center gap-1 rounded border border-yellow-200 bg-yellow-100 px-2 py-1 text-sm text-yellow-500">
                    <div class="i-material-symbols-delivery-truck-speed-rounded?mask"></div>
                    <span>Shipped</span>
                  </div>
                </c:if>
                <c:if test="${event[0].status=='DELIVEREDCENTRAL'||event[0].status=='SHIPPED'||event[0].status=='DELIVERED'}">
                  <div class="flex items-center gap-1 rounded border border-green-200 bg-green-100 px-2 py-1 text-sm text-green-500">
                    <div class="i-material-symbols-check-circle?mask"></div>
                    <span>Delivered</span>
                  </div>
                </c:if>
                <c:if test="${event[0].status=='REJECTED'}">
                  <div class="flex items-center gap-1 rounded border border-red-200 bg-red-100 px-2 py-1 text-sm text-red-500">
                    <div class="i-material-symbols-cancel?mask"></div>
                    <span>Rejected</span>
                  </div>
                </c:if>
              </div>
            </td>
            <td class="px-3 py-2"><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${event[0].createDate}" /></td>
            <td class="flex gap-3 px-3 py-2">

              <!-- when pending -->
              <c:if test="${event[0].status=='PENDING'&&(current_account.role=='SOURCE_WAREHOUSE_STAFF'||current_account.role=='ADMIN')}">
                <button class="inline-flex items-center gap-1 py-2 text-amber-600" data-micromodal-trigger="modal-${event[0].id}-approve">
                  <div class="i-material-symbols-check?mask"></div>
                  <span class="hover:underline">Approve</span>
                </button>
                <button class="inline-flex items-center gap-1 py-2 text-red-600" data-micromodal-trigger="modal-${event[0].id}-reject">
                  <div class="i-material-symbols-close?mask"></div>
                  <span class="hover:underline">Reject</span>
                </button>
                <div class="fixed inset-0 z-40 aria-hidden:hidden" id="modal-${event[0].id}-approve" aria-hidden="true">
                  <div class="flex h-full items-center justify-center bg-gray-950/50" data-micromodal-close>
                    <div class="space-y-6 rounded-lg bg-white p-6">
                      <header class="flex">
                        <h3 class="font-semibold">Change Event Status</h3>
                      </header>
                      <main>
                        <p>Are you sure delivering the goods?</p>
                      </main>
                      <footer class="flex gap-3">
                        <form:form action="groupApprove" method="put">
                          <c:forEach var="e" items="${event}" varStatus="status">
                            <input type="hidden" name="events" value="${e.id}">
                          </c:forEach>
                          <button type="submit" class="rounded-lg bg-amber-600 px-3 py-1.5 text-white hover:bg-amber-700">
                            Approve
                          </button>
                        </form:form>
                        <button class="rounded-lg border border-gray-300 px-3 py-1.5 hover:bg-gray-100" data-micromodal-close>Cancel</button>
                      </footer>
                    </div>
                  </div>
                </div>
                <div class="fixed inset-0 z-40 aria-hidden:hidden" id="modal-${event[0].id}-reject" aria-hidden="true">
                  <div class="flex h-full items-center justify-center bg-gray-950/50" data-micromodal-close>
                    <div class="space-y-6 rounded-lg bg-white p-6">
                      <header class="flex">
                        <h3 class="font-semibold">Change Event Status</h3>
                      </header>
                      <main>
                        <p>Are you sure rejecting the goods?</p>
                      </main>
                      <footer class="flex gap-3">
                        <form:form action="groupReject" method="put">
                          <c:forEach var="e" items="${event}" varStatus="status">
                            <input type="hidden" name="events" value="${e.id}">
                          </c:forEach>
                          <button type="submit" class="rounded-lg bg-amber-600 px-3 py-1.5 text-white hover:bg-amber-700">
                            Reject
                          </button>
                        </form:form>
                        <button class="rounded-lg border border-gray-300 px-3 py-1.5 hover:bg-gray-100" data-micromodal-close>Cancel</button>
                      </footer>
                    </div>
                  </div>
                </div>
              </c:if>

              <!-- when shipped -->
              <c:if test="${event[0].status=='SHIPPEDCENTRAL'&&(current_account.role=='CENTRAL_WAREHOUSE_STAFF'||current_account.role=='ADMIN')}">
                <button class="inline-flex items-center gap-1 py-2 text-amber-600" data-micromodal-trigger="modal-${event[0].id}-receive">
                  <div class="i-material-symbols-check?mask"></div>
                  <span class="hover:underline">Receive</span>
                </button>
                <div class="fixed inset-0 z-40 aria-hidden:hidden" id="modal-${event[0].id}-receive" aria-hidden="true">
                  <div class="flex h-full items-center justify-center bg-gray-950/50" data-micromodal-close>
                    <div class="space-y-6 rounded-lg bg-white p-6">
                      <header class="flex">
                        <h3 class="font-semibold">Change Event Status</h3>
                      </header>
                      <main>
                        <p>Are you sure you already received the goods?</p>
                      </main>
                      <footer class="flex gap-3">
                        <form:form action="groupReceive" method="put">
                          <c:forEach var="e" items="${event}" varStatus="status">
                            <input type="hidden" name="events" value="${e.id}">
                          </c:forEach>
                          <button type="submit" class="rounded-lg bg-amber-600 px-3 py-1.5 text-white hover:bg-amber-700">
                            Receive
                          </button>
                        </form:form>
                        <button class="rounded-lg border border-gray-300 px-3 py-1.5 hover:bg-gray-100" data-micromodal-close>Cancel</button>
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
    <c:if test="${events.totalElements==0}"><div class="p-3 text-gray-400">No Records</div></c:if>
    <div class="flex items-center justify-between p-3">
      <p>Showing ${events.totalElements==0?0:events.number*events.size+1} to ${events.number*events.size+events.numberOfElements} of ${events.totalElements} results</p>
      <div class="flex space-x-1">
        <c:if test="${events.hasPrevious()}">
          <a href="?page=${events.number}${param.type==null?'':'&type='.concat(param.type)}${param.status==null?'':'&status='.concat(param.status)}" class="rounded-lg border border-gray-200 px-3 py-1 hover:bg-gray-100">Prev</a>
        </c:if>
        <c:if test="${events.hasNext()}">
          <a href="?page=${events.number+2}${param.type==null?'':'&type='.concat(param.type)}${param.status==null?'':'&status='.concat(param.status)}" class="rounded-lg border border-gray-200 px-3 py-1 hover:bg-gray-100">Next</a>
        </c:if>
      </div>
    </div>
  </div>
</taglib:layout>
