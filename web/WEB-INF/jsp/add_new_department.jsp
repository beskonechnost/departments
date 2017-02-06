<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>

<html>

<c:set var="title" value="Add department" scope="page" />
<%@ include file="/WEB-INF/jspf/head.jspf" %>

<body>
<table id="main-container">

    <%@ include file="/WEB-INF/jspf/header.jspf" %>

    <tr>
        <td class="content">
            <%-- CONTENT --%>

            <form id="add_form" action="controller" method="post">
                <input type="hidden" name="command" value="AddDepartment" />

                <div>
                    <b>Enter the name of a new department: </b>
                    <input name="nameNewDepartment">
                </div>

                <button onclick='history.back()' value="Back">Back</button>
                <input type="submit" value="Add"><br/>
            </form>

            <%-- CONTENT --%>
        </td>
    </tr>

    <%@ include file="/WEB-INF/jspf/footer.jspf" %>

</table>
</body>
</html>