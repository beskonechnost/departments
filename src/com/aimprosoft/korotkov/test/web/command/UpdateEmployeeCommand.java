package com.aimprosoft.korotkov.test.web.command;

import com.aimprosoft.korotkov.test.Path;
import com.aimprosoft.korotkov.test.db.dao.DaoDepartmentImpl;
import com.aimprosoft.korotkov.test.db.dao.DaoEmployeeImpl;
import com.aimprosoft.korotkov.test.db.entity.Department;
import com.aimprosoft.korotkov.test.exception.AppException;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by Андрей on 05.02.2017.
 */
public class UpdateEmployeeCommand extends Command {

    private static final long serialVersionUID = 358818664711878527L;

    private static final Logger LOG = Logger.getLogger(UpdateEmployeeCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, AppException {

        request.setAttribute("departmentId", request.getParameter("departmentId"));
        String stringId = request.getParameter("departmentId");
        LOG.debug("stringId ----------> "+stringId);
        int id = 0;
        if(!(stringId==null)) {
            id = Integer.parseInt(stringId);
        }
        if(id!=0){
            request.setAttribute("employees", DaoEmployeeImpl.getInstance().findEmployeesThisDepartment(id));
        }else{
            request.setAttribute("employees", DaoEmployeeImpl.getInstance().findAllEmployees());
        }
        request.setAttribute("visibleUpdate", request.getParameter("visibleUpdate"));
        request.setAttribute("departmentName", request.getParameter("departmentName"));
        request.setAttribute("departmentUpdateId", request.getParameter("departmentUpdateId"));
        LOG.debug("departmentUpdateId ----------> "+request.getParameter("departmentUpdateId"));




        request.setAttribute("employeeId", request.getParameter("employeeId"));
        LOG.debug("employeeId ----------> "+request.getParameter("employeeId"));
        request.setAttribute("employeeFirstName", request.getParameter("employeeFirstName"));
        LOG.debug("employeeFirstName ----------> "+request.getParameter("employeeFirstName"));
        request.setAttribute("employeeLastName", request.getParameter("employeeLastName"));
        LOG.debug("employeeLastName ----------> "+request.getParameter("employeeLastName"));
        request.setAttribute("employeeBirthday", request.getParameter("employeeBirthday"));
        LOG.debug("employeeBirthday ----------> "+request.getParameter("employeeBirthday"));
        request.setAttribute("employeePhone", request.getParameter("employeePhone"));
        LOG.debug("employeePhone ----------> "+request.getParameter("employeePhone"));
        request.setAttribute("employeeEmail", request.getParameter("employeeEmail"));
        LOG.debug("employeeEmail ----------> "+request.getParameter("employeeEmail"));

        List<Department> departments = DaoDepartmentImpl.getInstance().findDepartments();
        request.setAttribute("departments", departments);
        LOG.debug("departments ----------> "+departments);

        LOG.debug("Command finished");
        return Path.PAGE_EMPLOYEE;
    }
}
