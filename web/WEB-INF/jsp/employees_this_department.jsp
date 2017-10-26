<%@ page pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>

<html>

<c:set var="title" value="All employees" scope="page" />
<%@ include file="/WEB-INF/jspf/head.jspf" %>

<body>
<table id="main-container">

    <%@ include file="/WEB-INF/jspf/header.jspf" %>

    <tr>
        <td class="content">
            <%-- CONTENT --%>

            <form id="make_employees" action="controller">

                <div>
                    <form action="controller" method="post">
                        <input type="hidden" name="departmentId" value="${departmentId}" />
                        <input type="hidden" name="departmentName" value="${departmentName}" />
                        <b>Add new employee in the department "${departmentName}"</b>
                        <input type="hidden" name="command" value="AddNewEmployeeInThisDepartment" />
                        <input type="submit" value="Add">
                    </form>
                </div>

                <div>
                    <b>All employees of the "${departmentName}" department:</b>
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
                            <td><c:out value="${k}"/></td>
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
    </tr>

    <%@ include file="/WEB-INF/jspf/footer.jspf" %>

</table>
</body>
</html>