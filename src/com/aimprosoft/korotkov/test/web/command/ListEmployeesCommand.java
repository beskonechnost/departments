package com.aimprosoft.korotkov.test.web.command;

import com.aimprosoft.korotkov.test.Path;
import com.aimprosoft.korotkov.test.db.dao.DaoDepartmentImpl;
import com.aimprosoft.korotkov.test.db.dao.DaoEmployeeImpl;
import com.aimprosoft.korotkov.test.db.entity.Department;
import com.aimprosoft.korotkov.test.db.entity.Employee;
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
public class ListEmployeesCommand extends Command {

    private static final long serialVersionUID = 387729626642878371L;

    private static final Logger LOG = Logger.getLogger(ListEmployeesCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, AppException {


        String id1 = request.getParameter("departmentId");
        int id = 0;
        if(!(id1==null)) {
            id = Integer.parseInt(id1);
        }
        LOG.debug("id ------------> " + id);
        List<Employee> employees;
        String forward;
        if(id==0){
            request.setAttribute("departmentId", 0);
            LOG.debug("department id --> " + request.getParameter("departmentId"));
            employees = DaoEmployeeImpl.getInstance().findAllEmployees();
            List<Department> departments = DaoDepartmentImpl.getInstance().findDepartments();
            request.setAttribute("departments", departments);
            request.setAttribute("employees", employees);
            if(employees==null || employees.isEmpty()){
                String no = "There are no employees in the office. Add it? ";
                request.setAttribute("no", no);
                forward = Path.PAGE_EMPLOYEE;
            }else{
                String no = "Add new employee in the office? ";
                request.setAttribute("no", no);
                forward = Path.PAGE_EMPLOYEE;
            }
        }else{
            employees = DaoEmployeeImpl.getInstance().findEmployeesThisDepartment(id);
            request.setAttribute("departmentId", request.getParameter("departmentId"));
            request.setAttribute("departmentName", request.getParameter("departmentName"));
            LOG.debug("department id --> " + request.getParameter("departmentId"));
            request.setAttribute("employees", employees);
            LOG.debug("department employees --> " + employees);
            if(employees.isEmpty()){
                String no = "In this department no employees! Add employees? ";
                request.setAttribute("no", no);
                forward = Path.PAGE_EMPLOYEE;
            }else {
                String no = "Add new employee in the department? ";
                request.setAttribute("no", no);
                forward = Path.PAGE_EMPLOYEE;
            }
        }

        LOG.debug("Command finished");
        return forward;
    }
}
