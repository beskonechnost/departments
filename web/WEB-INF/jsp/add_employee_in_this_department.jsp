<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>

<html>

<c:set var="title" value="Add employee" scope="page" />
<%@ include file="/WEB-INF/jspf/head.jspf" %>

<body>
<table id="main-container">

    <%@ include file="/WEB-INF/jspf/header.jspf" %>

    <tr>
        <td class="content">

            <form id="add_form" action="controller" method="post">
                <input type="hidden" name="command" value="AddEmployee" />
                <input type="hidden" name="departmentId" value="${departmentId}" />
                <input type="hidden" name="departmentName" value="${departmentName}" />

                <div>
                    <b>First name:</b>
                    <input name="firstName">
                </div>

                <div>
                    <b>Last name:</b>
                    <input name="lastName">
                </div>

                <div>
                    <b>Birthday:</b>
                    <input type="date" name="birthday">
                </div>

                <div>
                    <b>Phone (in format +380XX-XX-XX-XXX ):</b>
                    <input type="tel" name="phone" pattern="+380[0-9]{2}-[0-9]{2}-[0-9]{2}-[0-9]{3}">
                </div>

                <div>
                    <b>Email:</b>
                    <input type="email" name="email">
                </div>

                <div>
                    <b>Department - ${departmentName}</b>
                </div>

                <input type="submit" value="Add"><br/>
            </form>

            <%-- CONTENT --%>
        </td>
    </tr>

    <%@ include file="/WEB-INF/jspf/footer.jspf" %>

</table>
</body>
</html>