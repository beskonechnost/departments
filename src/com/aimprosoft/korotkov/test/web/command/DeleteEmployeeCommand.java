package com.aimprosoft.korotkov.test.web.command;

import com.aimprosoft.korotkov.test.Path;
import com.aimprosoft.korotkov.test.db.dao.DaoEmployeeImpl;
import com.aimprosoft.korotkov.test.exception.AppException;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DeleteEmployeeCommand extends Command {

    private static final long serialVersionUID = 374198623899178682L;

    private static final Logger LOG = Logger.getLogger(DeleteEmployeeCommand.class);

    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, AppException {

        int id = Integer.parseInt(request.getParameter("deleteEmployeeId"));
        LOG.debug("delete employee id -----------> "+ id);
        DaoEmployeeImpl.getInstance().deleteEmployee(id);

        LOG.debug("delete employee departmentId -----------> "+ request.getParameter("departmentId"));
        LOG.debug("delete employee departmentName -----------> "+ request.getParameter("departmentName"));
        if(Integer.parseInt(request.getParameter("departmentId"))>0){
            request.setAttribute("employees", DaoEmployeeImpl.getInstance().findEmployeesThisDepartment(Integer.parseInt(request.getParameter("departmentId"))));
        }else{
         request.setAttribute("employees", DaoEmployeeImpl.getInstance().findAllEmployees());
        }
        LOG.debug("Command finished");
        return Path.PAGE_EMPLOYEE;
    }
}
