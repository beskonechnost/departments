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
                    <b>${no}</b>
                </div>

                <button onclick='history.back()' value="Back">Back</button>
            </form>

        </td>
    </tr>

    <%@ include file="/WEB-INF/jspf/footer.jspf" %>

</table>
</body>
</html>