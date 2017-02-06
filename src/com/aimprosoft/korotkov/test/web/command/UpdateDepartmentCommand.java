package com.aimprosoft.korotkov.test.web.command;

import com.aimprosoft.korotkov.test.Path;
import com.aimprosoft.korotkov.test.exception.AppException;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Андрей on 31.01.2017.
 */
public class UpdateDepartmentCommand extends Command {

    private static final long serialVersionUID = 826338626917878516L;

    private static final Logger LOG = Logger.getLogger(UpdateDepartmentCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, AppException {

        request.setAttribute("departmentId", request.getParameter("itemId"));
        request.setAttribute("departmentName", request.getParameter("itemName"));

        LOG.debug("department id --> " + request.getParameter("itemId"));
        LOG.debug("department name --> " + request.getParameter("itemName"));

        LOG.debug("Command finished");
        return Path.PAGE_UPDATE_DEPARTMENT;
    }
}
