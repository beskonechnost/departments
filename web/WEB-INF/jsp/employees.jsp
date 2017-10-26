<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<html>

<c:set var="title" value="Employees" scope="page" />
<link rel="stylesheet" type="text/css" media="screen" href="style/style.css"/>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<body>
<tr>
  <div id="rightHeader">
    <a href="controller?command=AllDepartments">All departments</a>
    <a href="controller?command=ListEmployees">All employees</a>
  </div>
</tr>
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
              <td>${k}</td>
              <td>${item.firstName}</td>
              <td>${item.lastName}</td>
              <td>${item.birthday}</td>
              <td>${item.phone}</td>
              <td>${item.email}</td>
              <td>
                <form action="controller" method="post">
                  <input type="hidden" name="employees" value="${employees}" />
                  <input type="hidden" name="departmentUpdateId" value="${item.idDepartment}" />
                  <input type="hidden" name="departmentId" value="${departmentId}" />
                  <input type="hidden" name="departmentName" value="${departmentName}" />
                  <input type="hidden" name="employeeId" value="${item.id}" />
                  <input type="hidden" name="employeeFirstName" value="${item.firstName}" />
                  <input type="hidden" name="employeeLastName" value="${item.lastName}" />
                  <input type="hidden" name="employeeBirthday" value="${item.birthday}" />
                  <input type="hidden" name="employeePhone" value="${item.phone}" />
                  <input type="hidden" name="employeeEmail" value="${item.email}" />
                  <input type="hidden" name="visibleUpdate" value="${1}" />
                  <input type="hidden" name="command" value="UpdateEmployee" />
                  <input type="submit" value="Update">
                </form>
              </td>
              <td>
                <form action="controller" method="post">
                  <input type="hidden" name="employees" value="${employees}" />
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
      <c:if test="${visibleUpdate!=1}">
        <fieldset >
          <legend>Add employee:</legend>
          <form id="add_form" action="controller" method="post">
            <input type="hidden" name="command" value="AddEmployee" />
            <input type="hidden" name="departmentId" value="${departmentId}" />
            <input type="hidden" name="departmentName" value="${departmentName}" />
            <input type="hidden" name="departments" value="${departments}" />

            <div>
              <b>First name:</b>
              <input name="firstName" value="<c:out value="${firstNameNewEmployee}"></c:out>">
            </div>

            <div>
              <b>Last name:</b>
              <input name="lastName" value="<c:out value="${lastNameNewEmployee}"></c:out>">
            </div>

            <div>
              <b>Birthday:</b>
              <input type="date" name="birthday" value="<c:out value="${birthdayNewEmployee}"></c:out>">
            </div>

            <div>
              <b>Phone (in format +380XX-XX-XX-XXX ):</b>
              <input type="tel" name="phone" pattern="+380[0-9]{2}-[0-9]{2}-[0-9]{2}-[0-9]{3}" value="<c:out value="${phoneNewEmployee}"></c:out>">
            </div>

            <div>
              <b>Email:</b>
              <input type="email" name="email"  value="<c:out value="${emailNewEmployee}"></c:out>">
            </div>
            <c:if test="${departmentId>0}">
              <div>
                <b>Department - ${departmentName}</b>
                <input type="hidden" name="departmentNameAdd" value="<c:out value="${departmentName}"></c:out>">
              </div>
            </c:if>
            <c:if test="${departmentId==0}">
              <div>
                <p>Select the desired department:</p>
                <select name="departmentNameAdd">
                  <option value="<c:out value="${departmentName}"/>"><c:out value="${departmentName}"/></option>

                  <c:if test="${departmentNameNewEmployee!=null}">
                    <c:set var="k2" value="0"/>
                    <c:forEach var="namesDep" items="${namesDep}">
                      <c:set var="k2" value="${k2+1}"/>
                      <c:if test="${namesDep==departmentNameNewEmployee}">
                        <option selected value="<c:out value="${namesDep}"/>"><c:out value="${namesDep}"/></option>
                      </c:if>
                      <option value="<c:out value="${namesDep}"/>"><c:out value="${namesDep}"/></option>
                    </c:forEach>
                  </c:if>
                  <c:if test="${departmentNameNewEmployee==null}">
                    <c:set var="k2" value="0"/>
                    <c:forEach var="departments" items="${departments}">
                      <c:set var="k2" value="${k2+1}"/>
                      <option value="<c:out value="${departments.name}"/>"><c:out value="${departments.name}"/></option>
                    </c:forEach>
                  </c:if>

                </select>
              </div>
            </c:if>
            <b><font size="3" color="red" face="Arial">${errorAddEmployee}</font></b><br>
            <input type="submit" value="Add"><br/>
          </form>
        </fieldset>
        </c:if>

      <c:if test="${visibleUpdate==1}">
        <fieldset >
          <legend>Update employee:</legend>
          <form id="update_form" action="controller" method="post">
            <input type="hidden" name="command" value="UpdateEmployeeForm" />
            <input type="hidden" name="departmentId" value="${departmentId}" />
            <input type="hidden" name="departmentName" value="${departmentName}" />
            <input type="hidden" name="employeeId" value="${employeeId}" />
            <input type="hidden" name="employeeFirstName" value="${employeeFirstName}" />
            <input type="hidden" name="employeeLastName" value="${employeeLastName}" />
            <input type="hidden" name="employeeBirthday" value="${employeeBirthday}" />
            <input type="hidden" name="employeePhone" value="${employeePhone}" />
            <input type="hidden" name="employeeEmail" value="${employeeEmail}" />
            <input type="hidden" name="departmentUpdateId" value="${departmentUpdateId}" />
            <input type="hidden" name="visibleUpdate" value="${1}" />

            <div>
              <p>Enter a new employee first name:</p>
              <input name="newFirstName" value="<c:out value="${employeeFirstName}"></c:out>">
            </div>

            <div>
              <p>Enter a new employee last name:</p>
              <input name="newLastName" value="<c:out value="${employeeLastName}"></c:out>">
            </div>

            <div>
              <p>Enter a new employee birthday:</p>
              <input name="newBirthday" type="date" value="<c:out value="${employeeBirthday}"></c:out>">
            </div>

            <div>
              <p>Enter a new employee phone (in format +380XX-XX-XX-XXX):</p>
              <input type="tel" pattern="+380[0-9]{2}-[0-9]{2}-[0-9]{2}-[0-9]{3}" value="<c:out value="${employeePhone}"></c:out>" name="newPhone" >
            </div>

            <div>
              <p>Enter a new employee email:</p>
              <input name="newEmail" type="email" value="<c:out value="${employeeEmail}"></c:out>">
            </div>

            <div>
              <p>Select the desired department:</p>
              <select name="newDepartmentName">
                <option value="<c:out value="${departmentName}"/>"><c:out value="${departmentName}"/></option>
                <c:set var="k1" value="0"/>
                <c:forEach var="departments" items="${departments}">
                  <c:set var="k1" value="${k1+1}"/>
                  <c:if test="${departments.id==departmentUpdateId}">
                    <option selected value="<c:out value="${departments.name}"/>"><c:out value="${departments.name}"/></option>
                  </c:if>
                  <c:if test="${departments.id!=departmentUpdateId}">
                    <option value="<c:out value="${departments.name}"/>"><c:out value="${departments.name}"/></option>
                  </c:if>
                </c:forEach>
              </select>
            </div>

            <b><font size="3" color="red" face="Arial">${errorUpdateEmployee}</font></b><br>
            <input type="submit" value="Update"><br/>
          </form>
        </fieldset>
      </c:if>
  </td>
  </tr>

</table>
</body>
</html>