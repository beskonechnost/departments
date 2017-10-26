package com.aimprosoft.korotkov.test.web.command;

import com.aimprosoft.korotkov.test.Path;
import com.aimprosoft.korotkov.test.exception.AppException;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Андрей on 05.02.2017.
 */
public class RemovalConfirmationEmployeeCommand extends Command {

    private static final long serialVersionUID = 284175628638718562L;

    private static final Logger LOG = Logger.getLogger(RemovalConfirmationEmployeeCommand.class);

    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, AppException {

        request.setAttribute("departmentId", request.getParameter("departmentId"));
        LOG.debug("delete employee departmentId --> " + request.getParameter("departmentId"));
        request.setAttribute("departmentName", request.getParameter("departmentName"));
        LOG.debug("delete employee departmentName --> " + request.getParameter("departmentName"));
        request.setAttribute("employees", request.getParameter("employees"));

        request.setAttribute("deleteEmployeeId", request.getParameter("employeeId"));
        LOG.debug("delete employee Id --> " + request.getParameter("employeeId"));
        request.setAttribute("employeeFirstName", request.getParameter("employeeFirstName"));
        request.setAttribute("employeeLastName", request.getParameter("employeeLastName"));

        LOG.debug("Command finished");
        return Path.PAGE_DELETE_EMPLOYEE;
    }
}
