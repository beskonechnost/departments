<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>

<html>

<c:set var="title" value="Update department" scope="page" />
<%@ include file="/WEB-INF/jspf/head.jspf" %>

<body>
<table id="main-container">

    <%@ include file="/WEB-INF/jspf/header.jspf" %>

    <tr>
        <td class="content">
            <%-- CONTENT --%>

            <form id="update_form" action="controller" method="post">
                <input type="hidden" name="command" value="UpdateDepartmentForm" />
                <input type="hidden" name="id" value="${departmentId}" />
                <input type="hidden" name="oldName" value="${departmentName}" />

                <div>
                    <p>Enter a new department name:</p>
                    <input name="name" value="${departmentName}">
                </div>

                <input type="submit" value="Update"><br/>
            </form>

            <%-- CONTENT --%>
        </td>
    </tr>

    <%@ include file="/WEB-INF/jspf/footer.jspf" %>

</table>
</body>
</html>