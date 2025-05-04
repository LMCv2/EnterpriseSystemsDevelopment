<%@taglib prefix="c" uri="jakarta.tags.core" %>
<%@taglib prefix="taglib" uri="/WEB-INF/tlds/taglib.tld" %>
<taglib:layout title="Stock">
  <div class="mb-3 flex justify-between">
    <div class="flex items-center gap-2 rounded p-2 ring-1 ring-gray-300 focus-within:ring-2 focus-within:ring-amber-600">
      <div class="i-material-symbols-search?mask text-2xl text-gray-400"></div>
      <input type="search" class="outline-hidden placeholder:text-gray-400" placeholder="Search" />
    </div>
  </div>

  <c:if test="${locationType_items!=null}">
    <nav class="mb-3 flex justify-center">
      <div class="flex space-x-1 rounded border border-gray-200 p-2">
        <a href="?type=all" class="rounded px-3 py-1.5 hover:bg-gray-100 ${param.type==null||param.type.equals('all')?'bg-gray-100 text-amber-600':''}">All</a>
        <c:forEach items="${locationType_items}" var="item">
          <a href="?type=${item.key.toLowerCase()}" class="rounded px-3 py-1.5 hover:bg-gray-100 ${item.key.toLowerCase()==param.type?'bg-gray-100 text-amber-600':''}">${item.value}</a>
        </c:forEach>
      </div>
    </nav>
  </c:if>

  <div class="divide-y rounded shadow">
    <table class="w-full border-gray-200">
      <tbody class="divide-y">
        <tr class="border-gray-200 bg-stone-100">
          <th class="px-3 py-2 text-left">Id</th>
          <th class="px-3 py-2 text-left">Location</th>
          <th class="px-3 py-2 text-left">Fruit</th>
          <th class="px-3 py-2 text-left">Quantity</th>
          <th class="px-3 py-2 text-left">Action</th>
        </tr>
        <c:forEach var="stock" items="${stocks.content}">
          <tr class="h-14 border-gray-200">
            <td class="px-3 py-2">${stock.id}</td>
            <td class="px-3 py-2">${stock.location.name}</td>
            <td class="px-3 py-2">${stock.fruit.name}</td>
            <td class="px-3 py-2">${stock.quantity}</td>
            <td class="flex gap-3 px-3 py-2">
              <c:if test="${stock.location.type=='SOURCE_WAREHOUSE'}">
                <a class="inline-flex items-center gap-1 py-2 text-amber-600" href="${stock.id}/edit">
                  <div class="i-material-symbols-edit?mask"></div>
                  <span class="hover:underline">Edit</span>
                </a>
              </c:if>
              <c:if test="${stock.location.type=='SHOP'}">
                <a class="inline-flex items-center gap-1 py-2 text-amber-600" href="${stock.id}/add">
                  <div class="i-material-symbols-add?mask"></div>
                  <span class="hover:underline">Add</span>
                </a>
                <a class="inline-flex items-center gap-1 py-2 text-amber-600" href="${stock.id}/remove">
                  <div class="i-material-symbols-remove?mask"></div>
                  <span class="hover:underline">Remove</span>
                </a>
              </c:if>
            </td>
          </tr>
        </c:forEach>
      </tbody>
    </table>
    <div class="flex items-center justify-between p-3">
      <p>Showing ${stocks.number*stocks.size+1} to ${stocks.number*stocks.size+stocks.numberOfElements} of ${stocks.totalElements} results</p>
      <div class="flex space-x-1">
        <c:if test="${stocks.hasPrevious()}">
          <a href="?page=${stocks.number}${param.type==null?'':'&type='.concat(param.type)}" class="rounded border border-gray-200 px-3 py-1 hover:bg-gray-100">Prev</a>
        </c:if>
        <c:if test="${stocks.hasNext()}">
          <a href="?page=${stocks.number+2}${param.type==null?'':'&type='.concat(param.type)}" class="rounded border border-gray-200 px-3 py-1 hover:bg-gray-100">Next</a>
        </c:if>
      </div>
    </div>
  </div>
</taglib:layout>
