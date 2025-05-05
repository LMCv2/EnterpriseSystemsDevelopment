<%@taglib prefix="taglib" uri="/WEB-INF/tlds/taglib.tld" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<taglib:layout title="Stock">
  <h2 class="font-bold">Update fruit quantity</h2>
  <div class="divide-y rounded shadow">
    <table class="w-full border-gray-200">
      <tbody class="divide-y">
        <tr class="border-gray-200 bg-stone-100">
          <th class="px-3 py-2 text-left">Id</th>
          <th class="px-3 py-2 text-left">Fruit Name</th>
          <th class="px-3 py-2 text-left">City Name</th>
          <th class="px-3 py-2 text-left">Warehouse Name</th>
          <th class="px-3 py-2 text-left">Quantity</th>
        </tr>
        <c:forEach items="${stocksNeeds.content}" var="fruitStat">
          <tr class="h-14 border-gray-200">
            <td class="px-3 py-2">${fruitStat[0].id}</td>
            <td class="px-3 py-2">${fruitStat[0].name}</td>
            <td class="px-3 py-2">${fruitStat[1].cityName}</td>
            <td class="px-3 py-2">${fruitStat[1].name}</td>
            <td class="px-3 py-2">${fruitStat[2]}</td>
          </tr>
        </c:forEach>
      </tbody>
    </table>
  </div>
</taglib:layout>
