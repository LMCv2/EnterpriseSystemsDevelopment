<%@taglib prefix="taglib" uri="/WEB-INF/tlds/taglib.tld" %>
<taglib:layout title="Dashboard">
  <p>Hello, ${current_account.username}!</p>
  <div class="mb-4">
    <p>Today's date: <input type="date" class="rounded border border-gray-300 px-3 py-1.5" value=""/></p>
    <p>Expiration Date: </p>
    <p>Pending Delivered Date (3 days): <span id="deliveredDate"></span></p>
    <p>Reserve the fruits from <span id="reserveFrom"></span> to <span id="reserveTo"></span></p>
  </div>

  <script>
    document.addEventListener('DOMContentLoaded', function() {
      const today = new Date();
      const threeDaysLater = new Date(today);
      threeDaysLater.setDate(today.getDate() + 3);
      const fourteenDaysLater = new Date(today);
      fourteenDaysLater.setDate(today.getDate() + 14);
      function formatDate(date) {
        return date.toISOString().split('T')[0];
      }
      document.getElementById('deliveredDate').textContent = formatDate(threeDaysLater);
      document.getElementById('reserveFrom').textContent = formatDate(threeDaysLater);
      document.getElementById('reserveTo').textContent = formatDate(fourteenDaysLater);
      document.querySelector('input[type="date"]').value = formatDate(today);
    });
  </script>
  <button data-micromodal-trigger="modal">Open</button>
  <div class="aria-hidden:hidden" id="modal" aria-hidden="true">
    <div class="fixed inset-0 flex items-center justify-center bg-gray-950/50" data-micromodal-close>
      <div class="space-y-6 rounded bg-white p-6">
        <header class="flex">
          <h3 class="font-semibold">Modal Title</h3>
        </header>s
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
</taglib:layout>
