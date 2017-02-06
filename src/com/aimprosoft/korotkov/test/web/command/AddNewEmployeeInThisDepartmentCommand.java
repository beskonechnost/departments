package com.aimprosoft.korotkov.test.web.command;

import com.aimprosoft.korotkov.test.Path;
import com.aimprosoft.korotkov.test.exception.AppException;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AddNewEmployeeInThisDepartmentCommand extends Command {

    private static final long serialVersionUID = 675538193917671856L;

    private static final Logger LOG = Logger.getLogger(AddNewEmployeeInThisDepartmentCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, AppException {

        request.setAttribute("departmentId", request.getParameter("departmentId"));
        request.setAttribute("departmentName", request.getParameter("departmentName"));
        LOG.debug("department departmentName --> " + request.getParameter("departmentName"));

        LOG.debug("Command finished");
        return Path.PAGE_ADD_EMPLOYEE_IN_THIS_DEPARTMENT;
    }
}

