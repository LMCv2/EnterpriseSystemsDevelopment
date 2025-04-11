<%@taglib uri="/WEB-INF/tlds/taglib.tld" prefix="taglib" %>
<%@taglib prefix="c" uri="jakarta.tags.core" %>
<taglib:layout title="Fruit">
    <div class="container">
        <div class="rounded shadow">
          <table class="w-full border-separate border-spacing-2">
            <tr class="bg-gray-50">
              <th class="px-3 py-2 text-left">Id</th>
              <th class="px-3 py-2 text-left">Fruit</th>
              <th class="px-3 py-2 text-left">Quantity</th>
              <th class="px-3 py-2 text-left">Action</th>
            </tr>
            <c:forEach var="stock" items="${stock.content}">
              <tr class="p-10">
                <td class="px-3 py-2">${stock.id}</td>
                <td class="px-3 py-2">${stock.fruit.name}</td>
                <td class="px-3 py-2">${stock.quantity}</td>
                <td class="px-3 py-2">
                  <a class="py-2.5 px-5 me-2 mb-2 text-sm font-medium text-gray-900 focus:outline-none bg-white rounded-full border border-gray-400 hover:bg-gray-100 hover:text-blue-700 focus:z-10 focus:ring-4 focus:ring-gray-100" href="/stock/${stock.id}">Update</a>
                </td>
              </tr>
            </c:forEach>
          </table>
        </div>
      </div>
</taglib:layout>