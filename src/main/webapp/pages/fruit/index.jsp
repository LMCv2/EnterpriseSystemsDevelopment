<%@taglib uri="/WEB-INF/tlds/taglib.tld" prefix="taglib" %>
<%@taglib prefix="c" uri="jakarta.tags.core" %>
<taglib:layout title="Fruit">
    <div class="flex justify-between mb-3">
      <a href="/fruit/new" class="flex items-center rounded border border-gray-300 px-3 py-2 hover:bg-gray-100">
        <div class="i-material-symbols-add mr-3"></div>
        <span>Create</span>
      </a>
      <input type="text" class="border rounded"/>
    </div>
    <div class="rounded shadow">
      <table class="w-full border-separate border-spacing-2">
        <tr class="bg-gray-50">
          <th class="px-3 py-2 text-left">Id</th>
          <th class="px-3 py-2 text-left">Name</th>
          <th class="px-3 py-2 text-left">Action</th>
        </tr>
        <c:forEach var="fruit" items="${fruits.content}">
          <tr class="p-10">
            <td class="px-3 py-2">${fruit.id}</td>
            <td class="px-3 py-2">${fruit.name}</td>
            <td class="px-3 py-2">
              <a class="py-2.5 px-5 me-2 mb-2 text-sm font-medium text-gray-900 focus:outline-none bg-white rounded-full border border-gray-400 hover:bg-gray-100 hover:text-blue-700 focus:z-10 focus:ring-4 focus:ring-gray-100" href="/fruit/${fruit.id}">Edit</a>
            </td>
          </tr>
        </c:forEach>
      </table>
    </div>
</taglib:layout>
