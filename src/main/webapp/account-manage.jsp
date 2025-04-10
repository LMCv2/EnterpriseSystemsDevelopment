<%@taglib uri="/WEB-INF/tlds/taglib.tld" prefix="taglib" %>
<%@taglib prefix="c" uri="jakarta.tags.core" %>
<taglib:page title="Home">
  <h1>For Senior Management -> User account management</h1>
  <hr>
  <div">
    <jsp:include page="sign-up.jsp"/>
  </div>
    <div>
        <form method="post" action="logout">
            <button type="submit">Logout</button>
        </form>
    </div>
  <hr>
</taglib:page>