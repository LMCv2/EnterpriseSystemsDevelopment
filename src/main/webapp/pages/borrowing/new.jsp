<%@taglib uri="/WEB-INF/tlds/taglib.tld" prefix="taglib" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<taglib:layout title="Borrowing">
  <div class="container">
    <h2>Borrow</h2>
    <p for="fruitType" >Fruit ID: ${stock.fruit.id}<br>Fruit Name: ${stock.fruit.name}<br>Current stock:</p>
    <form:form action="/borrowing/new" method="post" class="space-y-3">
      <div>
        <form:input type="number" id="quantity" name="quantity" aria-describedby="helper-text-explanation" class="bg-gray-50 border border-gray-150 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5" placeholder="Input Quantity" value="${stock.quantity}" required />
      </div>
      <div>
        <label for="lenderShop">Lender Shop:</label>
        <form:select id="lenderShop" path="lenderShop" class="w-full rounded border border-gray-300 px-3 py-2">
          <form:options items="" />
        </form:select>
      </div>
      <input type="submit" value="Create" class="w-full rounded border border-gray-300 px-3 py-2 hover:bg-gray-100" />
    </form:form>
  </div>
</taglib:layout>
