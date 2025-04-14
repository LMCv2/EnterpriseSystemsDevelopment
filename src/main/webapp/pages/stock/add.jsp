<%@taglib prefix="c" uri="jakarta.tags.core" %>
  <%@taglib prefix="taglib" uri="/WEB-INF/tlds/taglib.tld" %>
    <taglib:layout title="Stock">
      <div class="mb-3 flex justify-between">
        <input type="text" class="rounded border border-gray-300 px-3 py-2" />
      </div>
      <div class="rounded shadow">
        <table class="w-full">
          <tr class="bg-gray-50">
            <th class="px-3 py-2 text-left">Id</th>
            <th class="px-3 py-2 text-left">Fruit</th>
            <th class="px-3 py-2 text-left">Location</th>
            <th class="px-3 py-2 text-left">Quantity</th>
            <th class="px-3 py-2 text-left">Action</th>
          </tr>
          <c:forEach var="stock" items="${stocks.content}">
            <tr>
              <td class="px-3 py-2">${stock.fruit.id}</td>
              <td class="px-3 py-2">${stock.fruit.name}</td>
              <td class="px-3 py-2">${stock.location.name}</td>
              <td class="px-3 py-2">${stock.quantity}</td>
              <td class="flex px-3 py-2">
                <a class="inline-block rounded-full p-2 hover:bg-gray-100" href="">
                  <div class="i-material-symbols-edit?bg text-xl"></div>
                  <%-- chick it to Apply for reservation --%>
                </a>
              </td>
            </tr>
          </c:forEach>
        </table>
        <h1>reserve [date box for 14 days, assume 3 day arrives?]</h1>

        <table class="w-full">
          <tr class="bg-gray-50">
            <th class="px-3 py-2 text-left">Id</th>
            <th class="px-3 py-2 text-left">Fruit</th>
            <th class="px-3 py-2 text-left">Location</th>
            <th class="px-3 py-2 text-left">Quantity</th>
            <th class="px-3 py-2 text-left">Reserve</th>
            <th class="px-3 py-2 text-left">Action</th>
          </tr>
          <c:forEach var="reservation_stocks" items="${reservation_stocks.content}">
            <form action="/stock/reserve/${reservation_stocks.id}" method="post">
              <tr>
                <td class="px-3 py-2">${reservation_stocks.fruit.id}</td>
                <td class="px-3 py-2">${reservation_stocks.fruit.name}</td>
                <td class="px-3 py-2">${reservation_stocks.location.name}</td>
                <td class="px-3 py-2">${reservation_stocks.quantity}</td>
                <td class="px-3 py-2">
                  <input type="number" id="reservation_number" name="reservation_number" value="0" />
                </td>
                <td class="flex px-3 py-2">
                  <button class="inline-block rounded-full p-2 hover:bg-gray-100" type="submit">
                    <div class="i-material-symbols-edit?bg text-xl"></div>
                    <%-- chick it to Apply for reservation --%>
                  </button>
                </td>
              </tr>
            </form>
          </c:forEach>
        </table>
        <h1>borrow</h1>
        <table class="w-full">
          <tr class="bg-gray-50">
            <th class="px-3 py-2 text-left">Id</th>
            <th class="px-3 py-2 text-left">Fruit</th>
            <th class="px-3 py-2 text-left">Location</th>
            <th class="px-3 py-2 text-left">Quantity</th>
            <th class="px-3 py-2 text-left">Action</th>
          </tr>
          <c:forEach var="borrowing_stocks" items="${borrowing_stocks.content}">
            <tr>
              <td class="px-3 py-2">${borrowing_stocks.fruit.id}</td>
              <td class="px-3 py-2">${borrowing_stocks.fruit.name}</td>
              <td class="px-3 py-2">${borrowing_stocks.location.name}</td>
              <td class="px-3 py-2">${borrowing_stocks.quantity}</td>
              <td class="flex px-3 py-2">
                <a class="inline-block rounded-full p-2 hover:bg-gray-100" href="">
                  <div class="i-material-symbols-edit?bg text-xl"></div>
                  <%-- chick it to Apply for reservation --%>
                </a>
              </td>
            </tr>
          </c:forEach>
        </table>
      </div>
    </taglib:layout>