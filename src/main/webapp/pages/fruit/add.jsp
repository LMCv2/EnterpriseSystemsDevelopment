<%@page pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="jakarta.tags.core" %>
<%@taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@taglib prefix="taglib" uri="/WEB-INF/tlds/taglib.tld" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<taglib:layout title="Edit Account">
  <div class="flex gap-3">
    <div>
      <form:form action="add" method="post" modelAttribute="addLocation" class="w-xl space-y-3 rounded-lg border border-gray-300 p-3">
        <div>
          <label for="location">Location:</label>
          <form:select id="location" path="location" class="outline-hidden mt-1 w-full rounded-lg px-3 py-2 ring-1 ring-gray-300 focus:ring-2 focus:ring-amber-600">
            <form:option value="" label="--Please Select--" />
            <form:options items="${location_items}" itemLabel="name" />
          </form:select>
        </div>
        <input type="submit" value="Save Changes" class="w-full rounded-lg border border-gray-300 px-3 py-2 hover:bg-gray-100" />
      </form:form>
    </div>
  </div>
</taglib:layout>
