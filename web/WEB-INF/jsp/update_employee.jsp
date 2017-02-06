<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>

<html>

<c:set var="title" value="Update employee" scope="page" />
<%@ include file="/WEB-INF/jspf/head.jspf" %>

<body>
<table id="main-container">

    <%@ include file="/WEB-INF/jspf/header.jspf" %>

    <tr>
        <td class="content">

            <form id="update_form" action="controller" method="post">
                <input type="hidden" name="command" value="UpdateEmployeeForm" />
                <input type="hidden" name="departmentId" value="${departmentId}" />
                <input type="hidden" name="departmentName" value="${departmentName}" />
                <input type="hidden" name="employeeId" value="${employeeId}" />
                <input type="hidden" name="employeeFirstName" value="${employeeFirstName}" />
                <input type="hidden" name="employeeLastName" value="${employeeLastName}" />
                <input type="hidden" name="employeeBirthday" value="${employeeBirthday}" />
                <input type="hidden" name="employeePhone" value="${employeePhone}" />
                <input type="hidden" name="employeeEmail" value="${employeeEmail}" />

                <div>
                    <p>Enter a new employee first name:</p>
                    <input name="newFirstName" value="${employeeFirstName}">
                </div>

                <div>
                    <p>Enter a new employee last name:</p>
                    <input name="newLastName" value="${employeeLastName}">
                </div>

                <div>
                    <p>Enter a new employee birthday:</p>
                    <input name="newBirthday" type="date" value="${employeeBirthday}">
                </div>

                <div>
                    <p>Enter a new employee phone (in format +380XX-XX-XX-XXX):</p>
                    <input type="tel" pattern="+380[0-9]{2}-[0-9]{2}-[0-9]{2}-[0-9]{3}" value="${employeePhone}" name="newPhone" >
                </div>

                <div>
                    <p>Enter a new employee email:</p>
                    <input name="newEmail" type="email" value="${employeeEmail}">
                </div>

                <div>
                    <p>Select the desired department:</p>
                    <select name="newDepartmentName">
                        <option value="<c:out value="${departmentName}"/>"><c:out value="${departmentName}"/></option>
                        <c:set var="k1" value="0"/>
                        <c:forEach var="departments" items="${departments}">
                            <c:set var="k1" value="${k1+1}"/>
                            <option value="<c:out value="${departments.name}"/>"><c:out value="${departments.name}"/></option>
                        </c:forEach>
                    </select>
                </div>

                <input type="submit" value="Update"><br/>
            </form>

        </td>
    </tr>

    <%@ include file="/WEB-INF/jspf/footer.jspf" %>

</table>
</body>
</html>