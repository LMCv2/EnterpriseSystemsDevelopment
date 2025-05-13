<%@page pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="jakarta.tags.core" %>
<%@taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@taglib prefix="taglib" uri="/WEB-INF/tlds/taglib.tld" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<taglib:layout title="Dashboard">
  <div class="flex w-full gap-3">
    <div class="mb-3 flex w-full justify-between rounded-lg border border-gray-200 p-3">
      <div class="flex">
        <div class="i-material-symbols-account-circle?bg mr-3 text-5xl font-bold"></div>
        <div>
          <div class="text-lg font-bold">Welcome</div>
          <div class="text-gray-400">${current_account.username}!</div>
        </div>
      </div>
      <form action="/" method="post">
        <input type="hidden" name="action" value="signout" />
        <button type="submit" class="flex cursor-pointer items-center rounded-lg border border-gray-200 px-3 py-2 hover:bg-gray-100">
          <div class="i-material-symbols-logout?bg mr-3"></div>
          <span>Sign out</span>
        </button>
      </form>
    </div>
    <div class="mb-3 w-full rounded-lg border border-gray-200 p-3">
      <div class="text-lg font-bold">#${timePeriod}</div>
      <div class="text-gray-400">Reserve the fruits from <fmt:formatDate pattern="yyyy-MM-dd" value="${timePeriodRange[0]}" /> to <fmt:formatDate pattern="yyyy-MM-dd" value="${timePeriodRange[1]}" /></div>
    </div>
  </div>

  <form action="/dashboard/" method="get" class="mb-3 flex justify-between gap-3 rounded-lg border border-gray-200 p-3" oninput="this.submit()">
    <div class="flex-1">
      <label for="groupBy">Group By</label>
      <select name="groupBy" class="outline-hidden mt-1 w-full rounded-lg px-3 py-2 ring-1 ring-gray-300 focus:ring-2 focus:ring-amber-600">
        <option value="shop" ${param.groupBy=="shop"||param.groupBy==null?"selected":""}>Shop</option>
        <option value="city" ${param.groupBy=="city"?"selected":""}>City</option>
        <option value="country" ${param.groupBy=="country"?"selected":""}>Country</option>
      </select>
    </div>
    <div class="flex-1">
      <label for="startDateString">Start date</label>
      <input type="date" name="startDateString" value="${startDate}" class="outline-hidden mt-1 w-full rounded-lg px-3 py-2 ring-1 ring-gray-300 focus:ring-2 focus:ring-amber-600" />
    </div>
    <div class="flex-1">
      <label for="endDateString">End date</label>
      <input type="date" name="endDateString" value="${endDate}" class="outline-hidden mt-1 w-full rounded-lg px-3 py-2 ring-1 ring-gray-300 focus:ring-2 focus:ring-amber-600" />
    </div>
  </form>

  <div class="mb-3 grid grid-cols-3 gap-3">
    <div class="rounded-lg border border-gray-200">
      <div class="m-6 mb-0">
        <div class="mb-1 text-sm text-gray-400">Reservation Number</div>
        <div class="mb-2 text-3xl font-bold">${dailyEventCounts.stream().sum()}</div>
      </div>
      <canvas id="dailyEventCountsChart" class="w-full" height="24"></canvas>
    </div>
    <div class="rounded-lg border border-gray-200">
      <div class="m-6 mb-0">
        <div class="mb-1 text-sm text-gray-400">Reservation Quantity</div>
        <div class="mb-2 text-3xl font-bold">${dailyTotalQuantities.stream().sum()}</div>
      </div>
      <canvas id="dailyTotalQuantities" class="w-full" height="24"></canvas>
    </div>
    <div class="rounded-lg border border-gray-200">
      <div class="m-6">
        <div class="mb-1 text-sm text-gray-400">Unfinished Reservations</div>
        <div class="mb-2 text-3xl font-bold">${pendingEventsCount}</div>
      </div>
    </div>
  </div>

  <nav class="mb-3 flex justify-center">
    <div class="flex space-x-1 rounded-lg border border-gray-200 p-2">
      <a href="?type=reserveNeeds" class="rounded-lg px-3 py-1.5 hover:bg-gray-100 ${param.type==null||param.type.equals('reserveNeeds')?'bg-gray-100 text-amber-600':''}">Reserve Needs</a>
      <a href="?type=seasonalConsumption" class="rounded-lg px-3 py-1.5 hover:bg-gray-100 ${param.type.equals('seasonalConsumption')?'bg-gray-100 text-amber-600':''}">Consumption Records</a>
      <a href="?type=deliveryForecast" class="rounded-lg px-3 py-1.5 hover:bg-gray-100 ${param.type.equals('deliveryForecast')?'bg-gray-100 text-amber-600':''}">Delivery Forecast</a>
    </div>
  </nav>

  <c:if test="${param.type==null||param.type.equals('reserveNeeds')}">
    <div class="mb-3 divide-y divide-gray-200 rounded-lg border border-gray-200">
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
        <p>Showing ${reserveNeeds.totalElements==0?0:reserveNeeds.number*reserveNeeds.size+1} to ${reserveNeeds.number*reserveNeeds.size+reserveNeeds.numberOfElements} of ${reserveNeeds.totalElements} results</p>
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
    <div class="mb-3 divide-y divide-gray-200 rounded-lg border border-gray-200">
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
        <p>Showing ${seasonalConsumption.totalElements==0?0:seasonalConsumption.number*seasonalConsumption.size+1} to ${seasonalConsumption.number*seasonalConsumption.size+seasonalConsumption.numberOfElements} of ${seasonalConsumption.totalElements} results</p>
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

  <c:if test="${param.type.equals('deliveryForecast')}">
    <div class="mb-3 divide-y divide-gray-200 rounded-lg border border-gray-200">
      <div class="p-3 font-bold">Delivery Forecast</div>
      <table class="w-full">
        <tbody class="divide-y">
          <tr class="border-gray-200 bg-stone-100">
            <th class="px-3 py-2 text-left">Fruit</th>
            <th class="px-3 py-2 text-left">Origin</th>
            <th class="px-3 py-2 text-left">Destination</th>
            <th class="px-3 py-2 text-left">Average delivery time (days)</th>
            <th class="px-3 py-2 text-left">Standard Deviation (Days)</th>
          </tr>
          <c:forEach var="record" items="${deliveryForecasts.content}">
            <tr class="h-14 border-gray-200">
              <td class="px-3 py-2">${record.fruit.name}</td>
              <td class="px-3 py-2">${record.sourceCountry.name}</td>
              <td class="px-3 py-2">${record.targetCountry.name}</td>
              <td class="px-3 py-2">${record.averageDeliveryDays}</td>
              <td class="px-3 py-2">${record.standardDeviationDays}</td>
            </tr>
          </c:forEach>
        </tbody>
      </table>
      <c:if test="${deliveryForecasts.totalElements==0}"><div class="p-3 text-gray-400">No Records</div></c:if>
      <div class="flex items-center justify-between p-3">
        <p>Showing ${deliveryForecasts.totalElements==0?0:deliveryForecasts.number*deliveryForecasts.size+1} to ${deliveryForecasts.number*deliveryForecasts.size+deliveryForecasts.numberOfElements} of ${deliveryForecasts.totalElements} results</p>
        <div class="flex space-x-1">
          <c:if test="${deliveryForecasts.hasPrevious()}">
            <a href="?page=${deliveryForecasts.number}&groupBy=${param.groupBy}" class="rounded border border-gray-200 px-3 py-1 hover:bg-gray-100">Prev</a>
          </c:if>
          <c:if test="${deliveryForecasts.hasNext()}">
            <a href="?page=${deliveryForecasts.number+2}&groupBy=${param.groupBy}" class="rounded border border-gray-200 px-3 py-1 hover:bg-gray-100">Next</a>
          </c:if>
        </div>
      </div>
    </div>
  </c:if>

  <div class="flex gap-3">
    <div class="flex-1 divide-y divide-gray-200 rounded-lg border border-gray-200">
      <div class="p-3 font-bold">Fruit reservation Distribution</div>
      <canvas id="reservationDistributionChart" class="p-3" height="360"></canvas>
    </div>
    <div class="flex-1 divide-y divide-gray-200 rounded-lg border border-gray-200">
      <div class="p-3 font-bold">Fruit Borrowing Distribution</div>
      <canvas id="borrowingDistributionChart" class="p-3" height="360"></canvas>
    </div>
  </div>

  <script>
    new Chart(document.getElementById("dailyEventCountsChart"), {
      type: "line",
      data: {
        labels: ${dailyEventCounts},
        datasets: [
          {
            data: ${dailyEventCounts},
            borderColor: "rgb(225, 113, 0)",
            backgroundColor: "rgba(225, 113, 0, 0.1)",
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

    new Chart(document.getElementById("dailyTotalQuantities"), {
      type: "line",
      data: {
        labels: ${dailyTotalQuantities},
        datasets: [
          {
            data: ${dailyTotalQuantities},
            borderColor: "rgb(225, 113, 0)",
            backgroundColor: "rgba(225, 113, 0, 0.1)",
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

    new Chart(document.getElementById("reservationDistributionChart"), {
      type: "doughnut",
      data: {
        labels: ${fruitReservationLabels},
        datasets: [
          {
            data: ${fruitReservationData},
            backgroundColor: [
              'rgb(255, 99, 132)',
              'rgb(54, 162, 235)',
              'rgb(255, 205, 86)',
              'rgb(75, 192, 192)',
              'rgb(153, 102, 255)',
              'rgb(255, 159, 64)',
              'rgb(201, 203, 207)'
            ]
          }
        ]
      },
      options: {
        responsive: true,
        plugins: {
          legend: {
            position: 'right'
          }
        }
      }
    });

    new Chart(document.getElementById("borrowingDistributionChart"), {
      type: "doughnut",
      data: {
        labels: ${fruitBorrowingLabels},
        datasets: [
          {
            data: ${fruitBorrowingData},
            backgroundColor: [
              'rgb(255, 99, 132)',
              'rgb(54, 162, 235)',
              'rgb(255, 205, 86)',
              'rgb(75, 192, 192)',
              'rgb(153, 102, 255)',
              'rgb(255, 159, 64)',
              'rgb(201, 203, 207)'
            ]
          }
        ]
      },
      options: {
        responsive: true,
        plugins: {
          legend: {
            position: 'right'
          }
        }
      }
    });
  </script>
</taglib:layout>
