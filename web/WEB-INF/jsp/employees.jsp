<%@ page pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>

<html>

<c:set var="title" value="All employees" scope="page" />
<%@ include file="/WEB-INF/jspf/head.jspf" %>

<body>
<%@ include file="/WEB-INF/jspf/header.jspf" %>
<table id="main-container">
  <tr>

  </tr>
  <tr>
    <td class="content">
      <%-- CONTENT --%>

      <form id="make_employees" action="controller">



        <div>
          <b>"Employees:"</b>
        </div>

        <table id="list_employees_table">
          <thead>
          <tr>
            <td>№</td>
            <td>First Name</td>
            <td>Last Name</td>
            <td>Birthday</td>
            <td>Phone</td>
            <td>Email</td>
            <td>Update</td>
            <td>Delete</td>
          </tr>
          </thead>

          <c:set var="k" value="0"/>
          <c:forEach var="item" items="${employees}">
            <c:set var="k" value="${k+1}"/>
            <tr>
              <td><${k}></td>
              <td>${item.firstName}</td>
              <td>${item.lastName}</td>
              <td>${item.birthday}</td>
              <td>${item.phone}</td>
              <td>${item.email}</td>
              <td>
                <form action="controller" method="post">
                  <input type="hidden" name="departmentId" value="${departmentId}" />
                  <input type="hidden" name="departmentName" value="${departmentName}" />
                  <input type="hidden" name="employeeId" value="${item.id}" />
                  <input type="hidden" name="employeeFirstName" value="${item.firstName}" />
                  <input type="hidden" name="employeeLastName" value="${item.lastName}" />
                  <input type="hidden" name="employeeBirthday" value="${item.birthday}" />
                  <input type="hidden" name="employeePhone" value="${item.phone}" />
                  <input type="hidden" name="employeeEmail" value="${item.email}" />
                  <input type="hidden" name="command" value="UpdateEmployee" />
                  <input type="submit" value="Update">
                </form>
              </td>
              <td>
                <form action="controller" method="post">
                  <input type="hidden" name="departmentId" value="${departmentId}" />
                  <input type="hidden" name="departmentName" value="${departmentName}" />
                  <input type="hidden" name="employeeId" value="${item.id}" />
                  <input type="hidden" name="employeeFirstName" value="${item.firstName}" />
                  <input type="hidden" name="employeeLastName" value="${item.lastName}" />
                  <input type="hidden" name="command" value="RemovalConfirmationEmployee" />
                  <input type="submit" value="Delete">
                </form>
              </td>
            </tr>
          </c:forEach>
        </table>
      </form>
    </td>

    <td class="content">
      <div>
      <b>"${no}"</b>
      </div>
        <fieldset >
          <legend>Add employee:</legend>
          <form id="add_form" action="controller" method="post">
            <input type="hidden" name="command" value="AddEmployee" />
            <input type="hidden" name="departmentId" value="${departmentId}" />
            <input type="hidden" name="departmentName" value="${departmentName}" />

            <div>
              <b>First name:</b>
              <input name="firstName">
            </div>

            <div>
              <b>Last name:</b>
              <input name="lastName">
            </div>

            <div>
              <b>Birthday:</b>
              <input type="date" name="birthday">
            </div>

            <div>
              <b>Phone (in format +380XX-XX-XX-XXX ):</b>
              <input type="tel" name="phone" pattern="+380[0-9]{2}-[0-9]{2}-[0-9]{2}-[0-9]{3}">
            </div>

            <div>
              <b>Email:</b>
              <input type="email" name="email">
            </div>
            <c:if test="${departmentId>0}">
              <div>
                <b>Department - ${departmentName}</b>
              </div>
            </c:if>
            <c:if test="${departmentId==0}">
              <div>
                <p>Select the desired department:</p>
                <select name="departmentName">
                  <option value="<c:out value="${departmentName}"/>"><c:out value="${departmentName}"/></option>
                  <c:set var="k1" value="0"/>
                  <c:forEach var="departments" items="${departments}">
                    <c:set var="k1" value="${k1+1}"/>
                    <option value="<c:out value="${departments.name}"/>"><c:out value="${departments.name}"/></option>
                  </c:forEach>
                </select>
              </div>
            </c:if>
            <input type="submit" value="Add"><br/>
          </form>
        </fieldset>
    </td>
  </tr>

  <%@ include file="/WEB-INF/jspf/footer.jspf" %>

</table>
</body>
</html>