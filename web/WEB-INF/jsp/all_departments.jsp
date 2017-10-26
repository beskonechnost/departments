<%@ page pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>

<html>

<c:set var="title" value="Departments" scope="page" />
<%@ include file="/WEB-INF/jspf/head.jspf" %>

<body>
<%@ include file="/WEB-INF/jspf/header.jspf" %>
<table id="main-container">

    <tr>
        <td class="content">
            <%-- CONTENT --%>

            <form id="make_departments" action="controller">
                <table id="list_departments_table">
                    <thead>
                    <tr>
                        <td>№</td>
                        <td>Name department</td>
                        <td>List employees</td>
                        <td>Update department</td>
                        <td>Delete department</td>
                    </tr>
                    </thead>

                    <c:set var="k" value="0"/>
                    <c:forEach var="item" items="${departments}">
                        <c:set var="k" value="${k+1}"/>
                        <tr>
                            <td>${k}</td>
                            <td>${item.name}</td>
                            <td>
                                <form action="controller" method="post">
                                    <input type="hidden" name="departmentId" value="${item.id}" />
                                    <input type="hidden" name="departmentName" value="${item.name}" />
                                    <input type="hidden" name="command" value="ListEmployees" />
                                    <input type="submit" value="List">
                                </form>
                            </td>
                            <td>
                                <form action="controller" method="post">
                                    <input type="hidden" name="departmentId" value="${item.id}" />
                                    <input type="hidden" name="command" value="AllDepartments" />
                                    <input type="submit" value="Update">
                                </form>
                            </td>
                            <td>
                                <form action="controller" method="post">
                                    <input type="hidden" name="itemId" value="${item.id}" />
                                    <input type="hidden" name="itemName" value="${item.name}" />
                                    <input type="hidden" name="command" value="RemovalConfirmationDepartment" />
                                    <input type="submit" value="Delete">
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </form>
        </td>
        <td class="content">
            <fieldset >
                <legend>Add new department</legend>
                <form id="add_form" action="controller" method="post">
                    <input type="hidden" name="command" value="AddDepartment" />

                    <div>
                        <b>Enter the name of a new department: </b>
                        <input name="nameNewDepartment">
                    </div>
                    <input type="submit" value="Add New Department"><br/>
                </form>
            </fieldset><br>
            <c:if test="${updateDepartmentId>0}">
            <fieldset >
                <legend>Update department</legend>
                <form id="update_form" action="controller" method="post">
                    <input type="hidden" name="command" value="UpdateDepartmentForm" />
                    <input type="hidden" name="id" value="${updateDepartmentId}" />
                    <input type="hidden" name="oldName" value="${updateDepartmentName}" />

                    <div>
                        <p>Enter a new department name:</p>
                        <input name="name" value="${updateDepartmentName}">
                    </div>
                    <input type="hidden" name="departmentId" value="${0}" />
                    <input type="submit" value="Update"><br/>
                </form>
            </fieldset>
            </c:if>
        </td>
    </tr>

    <%@ include file="/WEB-INF/jspf/footer.jspf" %>

</table>
</body>
</html>