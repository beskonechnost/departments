<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>

<html>

<c:set var="title" value="Start" />
<%@ include file="/WEB-INF/jspf/head.jspf" %>

<table id="main-container">
  <tr >
    <td class="content center">
      <form id="start_form" action="controller" method="post">
        <input type="hidden" name="command" value="AllDepartments"/>
        <fieldset >
          <legend>WELCOME</legend>
          <H1><b>Welcome to my test application<br/>
            The application is open and does not require authorization.<br/></b></H1>
        </fieldset><br/>
        <input type="submit" value="Come in">
      </form>
    </td>
  </tr>
  <%@ include file="/WEB-INF/jspf/footer.jspf"%>

</table>
</body>
</html>
