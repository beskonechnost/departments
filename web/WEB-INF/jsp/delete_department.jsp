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
                <input type="hidden" name="command" value="DeleteDepartment" />
                <input type="hidden" name="deleteDepartmentId" value="${deleteDepartmentId}" />

                <div>
                    <p><b>Are you sure you want to delete "${deleteDepartmentName}" department and all its employees?</b></p>
                </div>

                <button  value="Back">Back</button>
                <input type="submit" value="Delete"><br/>
            </form>

            <%-- CONTENT --%>
        </td>
    </tr>

    <%@ include file="/WEB-INF/jspf/footer.jspf" %>

</table>
</body>
</html>