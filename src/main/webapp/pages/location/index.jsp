<%@taglib prefix="c" uri="jakarta.tags.core" %>
<%@taglib prefix="taglib" uri="/WEB-INF/tlds/taglib.tld" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<taglib:layout title="Location">
  <div class="mb-3 flex justify-between">
    <div class="flex items-center gap-2 rounded p-2 ring-1 ring-gray-300 focus-within:ring-2 focus-within:ring-amber-600">
      <div class="i-material-symbols-search?mask text-2xl text-gray-400"></div>
      <input type="search" class="outline-hidden placeholder:text-gray-400" placeholder="Search" />
    </div>
    <a href="create" class="flex items-center rounded border border-gray-300 px-3 py-2 hover:bg-gray-100">
      <div class="i-material-symbols-add?bg mr-3"></div>
      <span>Create</span>
    </a>
  </div>

  <nav class="mb-3 flex justify-center">
    <div class="flex space-x-1 rounded border border-gray-200 p-2">
      <a href="?type=all" class="rounded px-3 py-1.5 hover:bg-gray-100 ${param.type==null||param.type.equals('all')?'bg-gray-100 text-amber-600':''}">All</a>
      <c:forEach items="${locationType_items}" var="item">
        <a href="?type=${item.key.toLowerCase()}" class="rounded px-3 py-1.5 hover:bg-gray-100 ${item.key.toLowerCase()==param.type?'bg-gray-100 text-amber-600':''}">${item.value}</a>
      </c:forEach>
    </div>
  </nav>

  <div class="divide-y rounded shadow">
    <table class="w-full border-gray-200">
      <tbody class="divide-y">
        <tr class="border-gray-200 bg-stone-100">
          <th class="px-3 py-2 text-left">Id</th>
          <th class="px-3 py-2 text-left">Name</th>
          <th class="px-3 py-2 text-left">CityName</th>
          <th class="px-3 py-2 text-left">Type</th>
          <th class="px-3 py-2 text-left">Action</th>
        </tr>
        <c:forEach var="location" items="${locations.content}">
          <tr class="h-14 border-gray-200">
            <td class="px-3 py-2">${location.id}</td>
            <td class="px-3 py-2">${location.name}</td>
            <td class="px-3 py-2">${location.cityName}</td>
            <td class="px-3 py-2">${location.type.label}</td>
            <td class="flex gap-3 px-3 py-2">
              <a class="inline-flex items-center gap-1 py-2 text-amber-600" href="${location.id}/edit">
                <div class="i-material-symbols-edit?mask"></div>
                <span class="hover:underline">Edit</span>
              </a>
              <form:form action="${location.id}/delete" method="delete">
                <button type="submit" class="inline-flex items-center gap-1 py-2 text-red-600">
                  <div class="i-material-symbols-delete?mask"></div>
                  <span class="hover:underline">Delete</span>
                </button>
              </form:form>
            </td>
          </tr>
        </c:forEach>
      </tbody>
    </table>
    <div class="flex items-center justify-between p-3">
      <p>Showing ${locations.number*locations.size+1} to ${locations.number*locations.size+locations.numberOfElements} of ${locations.totalElements} results</p>
      <div class="flex space-x-1">
        <c:if test="${locations.hasPrevious()}">
          <a href="?page=${locations.number}${param.type==null?'':'&type='.concat(param.type)}" class="rounded border border-gray-200 px-3 py-1 hover:bg-gray-100">Prev</a>
        </c:if>
        <c:if test="${locations.hasNext()}">
          <a href="?page=${locations.number+2}${param.type==null?'':'&type='.concat(param.type)}" class="rounded border border-gray-200 px-3 py-1 hover:bg-gray-100">Next</a>
        </c:if>
      </div>
    </div>
  </div>
</taglib:layout>
