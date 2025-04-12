<%@taglib uri="/WEB-INF/tlds/taglib.tld" prefix="taglib" %>
<%@taglib prefix="c" uri="jakarta.tags.core" %>
<taglib:layout title="Fruit">
  <div class="mb-3 flex justify-between">
    <input type="text" class="rounded border" />
    <a href="/fruit/new" class="flex items-center rounded border border-gray-300 px-3 py-2 hover:bg-gray-100">
      <div class="i-material-symbols-add?bg mr-3"></div>
      <span>Create</span>
    </a>
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
            <a class="inline-block rounded-full p-2 hover:bg-gray-100" href="/fruit/${fruit.id}">
              <div class="i-material-symbols-edit?bg text-xl"></div>
            </a>
          </td>
        </tr>
      </c:forEach>
    </table>
  </div>
</taglib:layout>
