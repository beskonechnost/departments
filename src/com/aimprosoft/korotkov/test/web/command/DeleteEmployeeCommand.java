package com.aimprosoft.korotkov.test.web.command;

import com.aimprosoft.korotkov.test.Path;
import com.aimprosoft.korotkov.test.db.DBManager;
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
        DBManager.getInstance().deleteEmployee(id);

        request.setAttribute("itemId", request.getParameter("departmentId"));
        LOG.debug("delete employee departmentId -----------> "+ request.getParameter("departmentId"));
        request.setAttribute("itemName", request.getParameter("departmentName"));
        LOG.debug("delete employee departmentName -----------> "+ request.getParameter("departmentName"));
        LOG.debug("Command finished");
        return Path.EMPLOYEE_THIS_DEPARTMENT_COMMAND;
    }
}
