<%@taglib uri="/WEB-INF/tlds/taglib.tld" prefix="taglib" %>
<%@taglib prefix="c" uri="jakarta.tags.core" %>
<taglib:layout title="Fruit">
      <div class="flex justify-between mb-3">
        <a href="#" class="flex items-center rounded border border-gray-300 px-3 py-2 hover:bg-gray-100">
          <div class="i-material-symbols-add?bg mr-3"></div>
          <span>Create</span>
        </a>
        <input type="text" class="border rounded"/>
      </div>
        <div class="rounded shadow">
          <table class="w-full border-separate border-spacing-2">
            <tr class="bg-gray-50">
              <th class="px-3 py-2 text-left">Id</th>
              <th class="px-3 py-2 text-left">Fruit</th>
              <th class="px-3 py-2 text-left">Quantity</th>
              <th class="px-3 py-2 text-left">Borrower Shop</th>
              <th class="px-3 py-2 text-left">Lender Shop</th>
            </tr>
            <c:forEach var="borrowing" items="${borrowing.content}">
              <tr class="p-10">
                <td class="px-3 py-2">${borrowing.id}</td>
                <td class="px-3 py-2">${borrowing.fruit.name}</td>
                <td class="px-3 py-2">${borrowing.quantity}</td>
                <td class="px-3 py-2">${borrowing.borrowerShop.name}</td>
                <td class="px-3 py-2">${borrowing.lenderShop.name}</td>
              </tr>
            </c:forEach>
          </table>
        </div>
</taglib:layout>