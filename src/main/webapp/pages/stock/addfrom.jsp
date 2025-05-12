<%@page pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="jakarta.tags.core" %>
<%@taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@taglib prefix="taglib" uri="/WEB-INF/tlds/taglib.tld" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<taglib:layout title="Create Event">
  <h1 class="mb-3 text-3xl">Create ${from_stocks.location.type=='SHOP'?'Borrow':'Reserve'} Event (${from_stocks.location.name} to ${to_stocks.location.name})</h1>
  <form method="post" class="max-w-xl space-y-3">
    <div>
      <label for="quantity">Quantity:</label>
      <input type="number" id="quantity" name="quantity" min="0" class="w-full rounded border border-gray-300 px-3 py-2" />
    </div>
    <input type="submit" value="Create" class="w-full rounded border border-gray-300 px-3 py-2 hover:bg-gray-100" />
  </form>
</taglib:layout>
