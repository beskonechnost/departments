<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<html>

<c:set var="title" value="Delete department" scope="page" />
<link rel="stylesheet" type="text/css" media="screen" href="style/style.css"/>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<body>
<table id="main-container">
    <tr>
        <div id="rightHeader">
            <a href="controller?command=AllDepartments">All departments</a>
            <a href="controller?command=ListEmployees">All employees</a>
        </div>
    </tr>
    <tr>
        <td class="content">

            <form id="delete_form" action="controller" method="post">
                <input type="hidden" name="command" value="DeleteEmployee" />
                <input type="hidden" name="departmentId" value="${departmentId}" />
                <input type="hidden" name="departmentName" value="${departmentName}" />
                <input type="hidden" name="deleteEmployeeId" value="${deleteEmployeeId}" />
                <input type="hidden" name="employees" value="${employees}" />

                <div>
                    <p><b>Are you sure you want to delete employee ${employeeFirstName} ${employeeLastName}?</b></p>
                </div>

                <button onclick='history.back()' value="Back">Back</button>
                <input type="submit" value="Delete"><br/>
            </form>

            <%-- CONTENT --%>
        </td>
    </tr>

</table>
</body>
</html>