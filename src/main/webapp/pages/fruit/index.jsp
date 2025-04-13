<%@taglib prefix="c" uri="jakarta.tags.core" %>
<%@taglib uri="/WEB-INF/tlds/taglib.tld" prefix="taglib" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<taglib:layout title="Fruit">
  <div class="mb-3 flex justify-between">
    <input type="text" class="rounded border border-gray-300 px-3 py-2" />
    <a href="new" class="flex items-center rounded border border-gray-300 px-3 py-2 hover:bg-gray-100">
      <div class="i-material-symbols-add?bg mr-3"></div>
      <span>Create</span>
    </a>
  </div>
  <div class="rounded shadow">
    <table class="w-full">
      <tr class="bg-gray-50">
        <th class="px-3 py-2 text-left">Id</th>
        <th class="px-3 py-2 text-left">Name</th>
        <th class="px-3 py-2 text-left">Action</th>
      </tr>
      <c:forEach var="fruit" items="${fruits.content}">
        <tr>
          <td class="px-3 py-2">${fruit.id}</td>
          <td class="px-3 py-2">${fruit.name}</td>
          <td class="flex px-3 py-2">
            <a class="inline-block rounded-full p-2 hover:bg-gray-100" href="${fruit.id}">
              <div class="i-material-symbols-edit?bg text-xl"></div>
            </a>
            <form:form action="${fruit.id}" method="delete">
              <button type="submit" class="inline-block rounded-full p-2 hover:bg-gray-100">
                <div class="i-material-symbols-delete?bg text-xl"></div>
              </button>
            </form:form>
          </td>
        </tr>
      </c:forEach>
    </table>
  </div>
</taglib:layout>
