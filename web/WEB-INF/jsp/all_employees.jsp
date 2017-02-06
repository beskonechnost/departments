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

                <table id="list_employees_table">
                    <thead>
                    <tr>
                        <td>№</td>
                        <td>First Name</td>
                        <td>Last Name</td>
                        <td>Birthday</td>
                        <td>Phone</td>
                        <td>Email</td>
                        <td>Department</td>
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
                            <td>${item.nameDepartment}</td>
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