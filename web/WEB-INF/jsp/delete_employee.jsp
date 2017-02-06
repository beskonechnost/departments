<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>

<html>

<c:set var="title" value="Delete department" scope="page" />
<%@ include file="/WEB-INF/jspf/head.jspf" %>

<body>
<table id="main-container">

    <%@ include file="/WEB-INF/jspf/header.jspf" %>

    <tr>
        <td class="content">
            <%-- CONTENT --%>

            <form id="delete_form" action="controller" method="post">
                <input type="hidden" name="command" value="DeleteEmployee" />
                <input type="hidden" name="departmentId" value="${departmentId}" />
                <input type="hidden" name="departmentName" value="${departmentName}" />
                <input type="hidden" name="deleteEmployeeId" value="${deleteEmployeeId}" />

                <div>
                    <p><b>Are you sure you want to delete employee ${employeeFirstName} ${employeeLastName}?</b></p>
                </div>

                <button onclick='history.back()' value="Back">Back</button>
                <input type="submit" value="Delete"><br/>
            </form>

            <%-- CONTENT --%>
        </td>
    </tr>

    <%@ include file="/WEB-INF/jspf/footer.jspf" %>

</table>
</body>
</html>