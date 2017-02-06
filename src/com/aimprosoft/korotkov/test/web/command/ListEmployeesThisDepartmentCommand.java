package com.aimprosoft.korotkov.test.web.command;

import com.aimprosoft.korotkov.test.Path;
import com.aimprosoft.korotkov.test.db.DBManager;
import com.aimprosoft.korotkov.test.db.entity.EmployeeDep;
import com.aimprosoft.korotkov.test.exception.AppException;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by Андрей on 01.02.2017.
 */
public class ListEmployeesThisDepartmentCommand extends Command {

    private static final long serialVersionUID = 387729626642878371L;

    private static final Logger LOG = Logger.getLogger(ListEmployeesThisDepartmentCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, AppException {

        String id = request.getParameter("itemId");
        LOG.debug("itemId --> " + id);
        List<EmployeeDep> employees;
        String forward;
        if(id==null||id.isEmpty()){
            employees = DBManager.getInstance().findAllEmployees();
            request.setAttribute("departmentName", request.getParameter("itemName"));
            LOG.debug("department name --> " + request.getParameter("itemName"));
            request.setAttribute("employees", employees);
            LOG.debug("department employees --> " + employees);
            forward = Path.PAGE_ALL_EMPLOYEES;
        }else{
            int idDepartment = Integer.parseInt(id);
            employees = DBManager.getInstance().findEmployeesThisDepartment(idDepartment);
            request.setAttribute("departmentId", request.getParameter("itemId"));
            request.setAttribute("departmentName", request.getParameter("itemName"));
            LOG.debug("department name --> " + request.getParameter("itemName"));
            if(employees.isEmpty()){
                String no = "In this department no employees!";
                request.setAttribute("no", no);
                forward = Path.PAGE_THIS_DEPARTMENT_NO_EMPLOYEES;
            }else {
                request.setAttribute("employees", employees);
                LOG.debug("department employees --> " + employees);
                forward = Path.PAGE_EMPLOYEES_THIS_DEPARTMENT;
            }
        }

        LOG.debug("Command finished");
        return forward;
    }
}
