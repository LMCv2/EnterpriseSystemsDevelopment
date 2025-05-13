<%@page pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="jakarta.tags.core" %>
<%@taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@taglib prefix="taglib" uri="/WEB-INF/tlds/taglib.tld" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<taglib:layout title="Dashboard">
  <div class="flex w-full gap-3">
    <div class="mb-3 flex w-full justify-between rounded p-3 shadow">
      <div class="flex">
        <div class="i-material-symbols-account-circle?bg mr-3 text-5xl font-bold"></div>
        <div>
          <div class="text-lg font-bold">Welcome</div>
          <div class="text-gray-400">${current_account.username}!</div>
        </div>
      </div>
      <form action="/" method="post">
        <input type="hidden" name="action" value="signout" />
        <button type="submit" class="flex cursor-pointer items-center rounded border border-gray-200 px-3 py-2">
          <div class="i-material-symbols-logout?bg mr-3"></div>
          <span>Sign out</span>
        </button>
      </form>
    </div>
    <div class="mb-3 w-full rounded p-3 shadow">
      <div class="text-lg font-bold">#${timePeriod}</div>
      <div class="text-gray-400">Reserve the fruits from <fmt:formatDate type="date" pattern="yyyy-MM-dd" value="${timePeriodRange[0]}" /> to <fmt:formatDate type="date" pattern="yyyy-MM-dd" value="${timePeriodRange[1]}" /></div>
    </div>
  </div>

  <form action="/dashboard/" method="get" class="mb-3 flex justify-between gap-3 p-3 rounded shadow" oninput="this.submit()">
    <div class="flex-1">
      <label for="groupBy">Group By</label>
      <select name="groupBy" class="w-full rounded border border-gray-300 px-3 py-2 mt-1">
        <option value="shop" ${param.groupBy=="shop"||param.groupBy==null?"selected":""}>Shop</option>
        <option value="city" ${param.groupBy=="city"?"selected":""}>City</option>
        <option value="country" ${param.groupBy=="country"?"selected":""}>Country</option>
      </select>
    </div>
    <div class="flex-1">
      <label for="startDateString">Start date</label>
      <input type="date" name="startDateString" value="${startDate}" class="w-full rounded border border-gray-300 px-3 py-2 mt-1" /> 
    </div>
    <div class="flex-1">
      <label for="endDateString">End date</label>
      <input type="date" name="endDateString" value="${endDate}" class="w-full rounded border border-gray-300 px-3 py-2 mt-1" />
    </div>
  </form>

  <div class="mb-3 grid grid-cols-3 gap-3">
    <div class="rounded bg-white shadow">
      <div class="p-6">
        <div class="mb-1 text-sm text-gray-400">Reservation Number</div>
        <div class="mb-2 text-3xl font-bold">${dailyEventCounts.stream().sum()}</div>
        <div class="mb-2 flex items-center text-sm text-green-600">32k increase</div>
      </div>
      <canvas id="card1" width="120" height="28"></canvas>
    </div>
    <div class="rounded bg-white shadow">
      <div class="p-6">
        <div class="mb-1 text-sm text-gray-400">Reservation Quantity</div>
        <div class="mb-2 text-3xl font-bold">${dailyTotalQuantities.stream().sum()}</div>
        <div class="mb-2 flex items-center text-sm text-red-500">3% decrease</div>
      </div>
      <canvas id="card2" width="120" height="28"></canvas>
    </div>
    <div class="rounded bg-white shadow">
      <div class="p-6">
        <div class="mb-1 text-sm text-gray-400">Unfinished Reservations</div>
        <div class="mb-2 text-3xl font-bold">${pendingEventsCount}</div>
      </div>
    </div>
  </div>

  <nav class="mb-3 flex justify-center">
    <div class="flex space-x-1 rounded border border-gray-200 p-2">
      <a href="?type=reserveNeeds" class="rounded px-3 py-1.5 hover:bg-gray-100 ${param.type==null||param.type.equals('reserveNeeds')?'bg-gray-100 text-amber-600':''}">Reserve Needs</a>
      <a href="?type=seasonalConsumption" class="rounded px-3 py-1.5 hover:bg-gray-100 ${param.type.equals('seasonalConsumption')?'bg-gray-100 text-amber-600':''}">Consumption Records</a>
    </div>
  </nav>
  
  <c:if test="${param.type==null||param.type.equals('reserveNeeds')}">
    <div class="mb-3 divide-y divide-gray-200 rounded shadow">
      <div class="p-3 font-bold">Reserve Needs</div>
      <table class="w-full">
        <tbody class="divide-y">
          <tr class="border-gray-200 bg-stone-100">
            <th class="px-3 py-2 text-left">${param.groupBy=="city"?"City":param.groupBy=="country"?"Country":"Shop"}</th>
            <th class="px-3 py-2 text-left">Fruit</th>
            <th class="px-3 py-2 text-left">Quantity</th>
          </tr>
          <c:forEach var="record" items="${reserveNeeds.content}">
            <tr class="h-14 border-gray-200">
              <td class="px-3 py-2">${record.name}</td>
              <td class="px-3 py-2">${record.fruitName}</td>
              <td class="px-3 py-2">${record.quantity}</td>
            </tr>
          </c:forEach>
        </tbody>
      </table>
      <c:if test="${reserveNeeds.totalElements==0}"><div class="p-3 text-gray-400">No Records</div></c:if>
      <div class="flex items-center justify-between p-3">
        <p>Showing ${reserveNeeds.number*reserveNeeds.size+1} to ${reserveNeeds.number*reserveNeeds.size+reserveNeeds.numberOfElements} of ${reserveNeeds.totalElements} results</p>
        <div class="flex space-x-1">
          <c:if test="${reserveNeeds.hasPrevious()}">
            <a href="?page=${reserveNeeds.number}&groupBy=${param.groupBy}" class="rounded border border-gray-200 px-3 py-1 hover:bg-gray-100">Prev</a>
          </c:if>
          <c:if test="${reserveNeeds.hasNext()}">
            <a href="?page=${reserveNeeds.number+2}&groupBy=${param.groupBy}" class="rounded border border-gray-200 px-3 py-1 hover:bg-gray-100">Next</a>
          </c:if>
        </div>
      </div>
    </div>
  </c:if>

  <c:if test="${param.type.equals('seasonalConsumption')}">
    <div class="mb-3 divide-y divide-gray-200 rounded shadow">
      <div class="p-3 font-bold">Consumption Records</div>
      <table class="w-full">
        <tbody class="divide-y">
          <tr class="border-gray-200 bg-stone-100">
            <th class="px-3 py-2 text-left">${param.groupBy=="city"?"City":param.groupBy=="country"?"Country":"Shop"}</th>
            <th class="px-3 py-2 text-left">Year</th>
            <th class="px-3 py-2 text-left">Season</th>
            <th class="px-3 py-2 text-left">Fruit</th>
            <th class="px-3 py-2 text-left">Quantity</th>
          </tr>
          <c:forEach var="record" items="${seasonalConsumption.content}">
            <tr class="h-14 border-gray-200">
              <td class="px-3 py-2">${record.name}</td>
              <td class="px-3 py-2">${record.year}</td>
              <td class="px-3 py-2">${record.season}</td>
              <td class="px-3 py-2">${record.fruitName}</td>
              <td class="px-3 py-2">${record.quantity}</td>
            </tr>
          </c:forEach>
        </tbody>
      </table>
      <c:if test="${seasonalConsumption.totalElements==0}"><div class="p-3 text-gray-400">No Records</div></c:if>
      <div class="flex items-center justify-between p-3">
        <p>Showing ${seasonalConsumption.number*seasonalConsumption.size+1} to ${seasonalConsumption.number*seasonalConsumption.size+seasonalConsumption.numberOfElements} of ${seasonalConsumption.totalElements} results</p>
        <div class="flex space-x-1">
          <c:if test="${seasonalConsumption.hasPrevious()}">
            <a href="?page=${seasonalConsumption.number}&groupBy=${param.groupBy}" class="rounded border border-gray-200 px-3 py-1 hover:bg-gray-100">Prev</a>
          </c:if>
          <c:if test="${seasonalConsumption.hasNext()}">
            <a href="?page=${seasonalConsumption.number+2}&groupBy=${param.groupBy}" class="rounded border border-gray-200 px-3 py-1 hover:bg-gray-100">Next</a>
          </c:if>
        </div>
      </div>
    </div>
  </c:if>

  <div class="grid grid-cols-2 gap-3">
    <div class="rounded bg-white p-6 shadow">
      <div class="mb-2 font-semibold">Card4</div>
      <canvas id="ordersChart" height="200"></canvas>
    </div>
    <div class="rounded bg-white p-6 shadow">
      <div class="mb-2 font-semibold">Card5</div>
      <canvas id="customersChart" height="200"></canvas>
    </div>
  </div>

  <script>
    const ordersData = [2200, 3200, 4500, 3700, 5400, 6000, 7000, 9000, 8000, 8700, 9500, 9000];
    const customersData = [4000, 5200, 6300, 7200, 8300, 9500, 10500, 12000, 14000, 15000, 16500, 18000];
    const months = ["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"];

    new Chart(document.getElementById("card1"), {
      type: "line",
      data: {
        labels: ${dailyEventCounts},
        datasets: [
          {
            data: ${dailyEventCounts},
            borderColor: "#22c55e",
            backgroundColor: "rgba(34,197,94,0.15)",
            fill: true,
            tension: 0.4,
            pointRadius: 0,
            borderWidth: 2,
          },
        ],
      },
      options: {
        plugins: { legend: { display: false }, tooltip: { enabled: false } },
        scales: {
          x: { display: false, grid: { display: false } },
          y: { display: false, grid: { display: false } },
        },
        elements: { line: { borderJoinStyle: "round" } },
        layout: { padding: 0 },
        responsive: false,
      },
    });

    new Chart(document.getElementById("card2"), {
      type: "line",
      data: {
        labels: ${dailyTotalQuantities},
        datasets: [
          {
            data: ${dailyTotalQuantities},
            borderColor: "#ef4444",
            backgroundColor: "rgba(239,68,68,0.15)",
            fill: true,
            tension: 0.4,
            pointRadius: 0,
            borderWidth: 2,
          },
        ],
      },
      options: {
        plugins: { legend: { display: false }, tooltip: { enabled: false } },
        scales: {
          x: { display: false, grid: { display: false } },
          y: { display: false, grid: { display: false } },
        },
        elements: { line: { borderJoinStyle: "round" } },
        layout: { padding: 0 },
        responsive: false,
      },
    });

    new Chart(document.getElementById("ordersChart"), {
      type: "line",
      data: {
        labels: months,
        datasets: [
          {
            label: "Orders",
            data: ordersData,
            borderColor: "#f59e42",
            backgroundColor: "rgba(245, 158, 66, 0.1)",
            fill: true,
            tension: 0.4,
            pointRadius: 0,
          },
        ],
      },
      options: {
        plugins: { legend: { display: false } },
        scales: { y: { beginAtZero: true } },
      },
    });

    new Chart(document.getElementById("customersChart"), {
      type: "line",
      data: {
        labels: months,
        datasets: [
          {
            label: "Customers",
            data: customersData,
            borderColor: "#f59e42",
            backgroundColor: "rgba(245, 158, 66, 0.1)",
            fill: true,
            tension: 0.4,
            pointRadius: 0,
          },
        ],
      },
      options: {
        plugins: { legend: { display: false } },
        scales: { y: { beginAtZero: true } },
      },
    });
  </script>
</taglib:layout>
