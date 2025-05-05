<%@taglib prefix="taglib" uri="/WEB-INF/tlds/taglib.tld" %>
<%@taglib prefix="c" uri="jakarta.tags.core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<taglib:layout title="Dashboard">
  <p>Hello, ${current_account.username}!</p>
  <div class="mb-4">
    <p>Today's date: <input type="date" class="rounded border border-gray-300 px-3 py-1.5" value=""/></p>
    <p>Reserve Expiration Date and Pending Delivery Date (3 days): <fmt:formatDate type="date" pattern="yyyy-MM-dd" value="${reservation_schedules[0].deliveredDate}" /></p>
    <p>Reserve the fruits from <fmt:formatDate type="date" pattern="yyyy-MM-dd" value="${reservation_schedules[0].startDate}" /> to <fmt:formatDate type="date" pattern="yyyy-MM-dd" value="${reservation_schedules[0].endDate}" /></p>
    <p>Next reserve Date: <fmt:formatDate type="date" pattern="yyyy-MM-dd" value="${reservation_schedules[0].nextReservedDate}" /></p>
  </div>
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
