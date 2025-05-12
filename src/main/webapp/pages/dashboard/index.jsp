<%@taglib prefix="c" uri="jakarta.tags.core" %>
<%@taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@taglib prefix="taglib" uri="/WEB-INF/tlds/taglib.tld" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<taglib:layout title="Dashboard">
  
  <div class="mb-3 flex justify-between rounded p-3 shadow">
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
  
  <div class="mb-3">
    <p>Expected shipping date (approximately 3 days): <fmt:formatDate type="date" pattern="yyyy-MM-dd" value="${reservation_schedules[0].deliveredDate}" /></p>
    <p>Reserve the fruits from <fmt:formatDate type="date" pattern="yyyy-MM-dd" value="${reservation_schedules[0].startDate}" /> to <fmt:formatDate type="date" pattern="yyyy-MM-dd" value="${reservation_schedules[0].endDate}" /></p>
    <p>Next reserve Date: <fmt:formatDate type="date" pattern="yyyy-MM-dd" value="${reservation_schedules[0].nextReservedDate}" /></p>
  </div>
  <button data-micromodal-trigger="modal">Open</button>
  <div class="aria-hidden:hidden" id="modal" aria-hidden="true">
    <div class="fixed inset-0 flex items-center justify-center bg-gray-950/50" data-micromodal-close>
      <div class="space-y-6 rounded bg-white p-6">
        <header class="flex">
          <h3 class="font-semibold">Modal Title</h3>
        </header>
        <main>
          <p>Hi there, I'm some modal content!</p>
        </main>
        <footer class="flex gap-3">
          <button class="rounded bg-amber-600 px-3 py-1.5 text-white hover:bg-amber-700">Approve</button>
          <button class="rounded border border-gray-300 px-3 py-1.5 hover:bg-gray-100" data-micromodal-close>Cancel</button>
        </footer>
      </div>
    </div>
  </div>

  <div class="grid grid-cols-3 gap-3 mb-3">
    <div class="bg-white rounded shadow">
      <div class="p-6">
        <div class="text-gray-400 text-sm mb-1">Card1</div>
        <div class="text-3xl font-bold mb-2">1234</div>
        <div class="text-green-600 text-sm flex items-center mb-2">
          32k increase
        </div>
      </div>
      <canvas id="card1" width="120" height="28"></canvas>
    </div>
    <div class="bg-white rounded shadow">
      <div class="p-6">
        <div class="text-gray-400 text-sm mb-1">Card2</div>
        <div class="text-3xl font-bold mb-2">1234</div>
        <div class="text-red-500 text-sm flex items-center mb-2">
          3% decrease
        </div>
      </div>
      <canvas id="card2" width="120" height="28"></canvas>
    </div>
    <div class="bg-white rounded shadow">
      <div class="p-6">
        <div class="text-gray-400 text-sm mb-1">Card3</div>
        <div class="text-3xl font-bold mb-2">1234</div>
        <div class="text-green-600 text-sm flex items-center mb-2">
          7% increase
        </div> 
      </div>
      <canvas id="card3" width="120" height="28"></canvas>
    </div>
  </div>

  <div class="grid grid-cols-2 gap-3">
    <div class="bg-white rounded shadow p-6">
      <div class="font-semibold mb-2">Card4</div>
      <canvas id="ordersChart" height="200"></canvas>
    </div>
    <div class="bg-white rounded shadow p-6">
      <div class="font-semibold mb-2">Card5</div>
      <canvas id="customersChart" height="200"></canvas>
    </div>
  </div>

  <script>
    const ordersData = [2200, 3200, 4500, 3700, 5400, 6000, 7000, 9000, 8000, 8700, 9500, 9000];
    const customersData = [4000, 5200, 6300, 7200, 8300, 9500, 10500, 12000, 14000, 15000, 16500, 18000];
    const months = ['Jan','Feb','Mar','Apr','May','Jun','Jul','Aug','Sep','Oct','Nov','Dec'];

    new Chart(document.getElementById('card1'), {
      type: 'line',
      data: {
        labels: Array(12).fill(''),
        datasets: [{
          data: [2, 4, 3, 6, 8, 7, 9, 8, 10, 9, 8, 9],
          borderColor: '#22c55e',
          backgroundColor: 'rgba(34,197,94,0.15)',
          fill: true,
          tension: 0.4,
          pointRadius: 0,
          borderWidth: 2
        }]
      },
      options: {
        plugins: { legend: { display: false }, tooltip: { enabled: false } },
        scales: {
          x: { display: false, grid: { display: false } },
          y: { display: false, grid: { display: false } }
        },
        elements: { line: { borderJoinStyle: 'round' } },
        layout: { padding: 0 },
        responsive: false
      }
    });

    new Chart(document.getElementById('card2'), {
      type: 'line',
      data: {
        labels: Array(12).fill(''),
        datasets: [{
          data: [8, 7, 6, 5, 6, 7, 8, 7, 6, 5, 4, 3],
          borderColor: '#ef4444',
          backgroundColor: 'rgba(239,68,68,0.15)',
          fill: true,
          tension: 0.4,
          pointRadius: 0,
          borderWidth: 2
        }]
      },
      options: {
        plugins: { legend: { display: false }, tooltip: { enabled: false } },
        scales: {
          x: { display: false, grid: { display: false } },
          y: { display: false, grid: { display: false } }
        },
        elements: { line: { borderJoinStyle: 'round' } },
        layout: { padding: 0 },
        responsive: false
      }
    });

    new Chart(document.getElementById('card3'), {
      type: 'line',
      data: {
        labels: Array(12).fill(''),
        datasets: [{
          data: [2, 3, 4, 5, 6, 7, 8, 7, 8, 9, 10, 11],
          borderColor: '#22c55e',
          backgroundColor: 'rgba(34,197,94,0.15)',
          fill: true,
          tension: 0.4,
          pointRadius: 0,
          borderWidth: 2
        }]
      },
      options: {
        plugins: { legend: { display: false }, tooltip: { enabled: false } },
        scales: {
          x: { display: false, grid: { display: false } },
          y: { display: false, grid: { display: false } }
        },
        elements: { line: { borderJoinStyle: 'round' } },
        layout: { padding: 0 },
        responsive: false
      }
    });

    new Chart(document.getElementById('ordersChart'), {
      type: 'line',
      data: {
        labels: months,
        datasets: [{
          label: 'Orders',
          data: ordersData,
          borderColor: '#f59e42',
          backgroundColor: 'rgba(245, 158, 66, 0.1)',
          fill: true,
          tension: 0.4,
          pointRadius: 0
        }]
      },
      options: {
        plugins: { legend: { display: false } },
        scales: { y: { beginAtZero: true } }
      }
    });

    new Chart(document.getElementById('customersChart'), {
      type: 'line',
      data: {
        labels: months,
        datasets: [{
          label: 'Customers',
          data: customersData,
          borderColor: '#f59e42',
          backgroundColor: 'rgba(245, 158, 66, 0.1)',
          fill: true,
          tension: 0.4,
          pointRadius: 0
        }]
      },
      options: {
        plugins: { legend: { display: false } },
        scales: { y: { beginAtZero: true } }
      }
    });
  </script>
</taglib:layout>
