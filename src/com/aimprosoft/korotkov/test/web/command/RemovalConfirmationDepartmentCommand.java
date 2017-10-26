package com.aimprosoft.korotkov.test.web.command;

import com.aimprosoft.korotkov.test.Path;
import com.aimprosoft.korotkov.test.exception.AppException;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Андрей on 01.02.2017.
 */
public class RemovalConfirmationDepartmentCommand extends Command {

    private static final long serialVersionUID = 914348628666978597L;

    private static final Logger LOG = Logger.getLogger(RemovalConfirmationDepartmentCommand.class);

    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, AppException {


        request.setAttribute("deleteDepartmentId", request.getParameter("itemId"));
        LOG.debug("delete department Id --> " + request.getParameter("itemId"));
        request.setAttribute("deleteDepartmentName", request.getParameter("itemName"));

        LOG.debug("Command finished");
        return Path.PAGE_DELETE_DEPARTMENT;
    }
}
