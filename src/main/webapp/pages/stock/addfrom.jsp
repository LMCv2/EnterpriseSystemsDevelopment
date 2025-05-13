<%@page pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="jakarta.tags.core" %>
<%@taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@taglib prefix="taglib" uri="/WEB-INF/tlds/taglib.tld" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<taglib:layout title="Create Event">
  <c:if test="${error != null}">
    <div class="fixed inset-0 z-40 aria-hidden:hidden" id="modal-error" aria-hidden="true">
      <div class="flex h-full items-center justify-center bg-gray-950/50" data-micromodal-close>
        <div class="space-y-6 rounded bg-white p-6">
          <header class="flex">
            <h3 class="font-semibold">Error</h3>
          </header>
          <main>
            <p>${error}</p>
          </main>
          <footer class="flex gap-3">
            <button class="rounded bg-amber-600 px-3 py-1.5 text-white hover:bg-amber-700" data-micromodal-close>Confirm</button>
          </footer>
        </div>
      </div>
    </div>
    <script>
      MicroModal.show('modal-error');
    </script>
  </c:if>
  
  <h1 class="mb-3 text-3xl">Create ${from_stocks.location.type=='SHOP'?'Borrow':'Reserve'} Event (${from_stocks.location.name} to ${to_stocks.location.name})</h1>
  <form method="post" class="max-w-xl space-y-3">
    <div>
      <label for="quantity">Quantity:</label>
      <input type="number" id="quantity" name="quantity" min="0" class="w-full rounded border border-gray-300 px-3 py-2" />
    </div>
    <input type="submit" value="Create" class="w-full rounded border border-gray-300 px-3 py-2 hover:bg-gray-100" />
  </form>
</taglib:layout>
