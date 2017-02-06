<%@ page pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>

<html>

<c:set var="title" value="Departments" scope="page" />
<%@ include file="/WEB-INF/jspf/head.jspf" %>

<body>
<table id="main-container">

    <%@ include file="/WEB-INF/jspf/header.jspf" %>

    <tr>
        <td class="content">
            <%-- CONTENT --%>

            <form id="make_departments" action="controller">

                <div>
                    <form action="controller" method="post">
                        <b>Add new department - </b>
                        <input type="hidden" name="command" value="AddNewDepartment" />
                        <input type="submit" value="Add">
                    </form>
                </div>

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
                            <td><c:out value="${k}"/></td>
                            <td>${item.name}</td>
                            <td>
                                <form action="controller" method="post">
                                    <input type="hidden" name="itemId" value="${item.id}" />
                                    <input type="hidden" name="itemName" value="${item.name}" />
                                    <input type="hidden" name="command" value="ListEmployeesThisDepartment" />
                                    <input type="submit" value="List">
                                </form>
                            </td>
                            <td>
                                <form action="controller" method="post">
                                    <input type="hidden" name="itemId" value="${item.id}" />
                                    <input type="hidden" name="itemName" value="${item.name}" />
                                    <input type="hidden" name="command" value="UpdateDepartment" />
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
    </tr>

    <%@ include file="/WEB-INF/jspf/footer.jspf" %>

</table>
</body>
</html>