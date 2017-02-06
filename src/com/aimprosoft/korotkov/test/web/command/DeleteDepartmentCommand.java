package com.aimprosoft.korotkov.test.web.command;

import com.aimprosoft.korotkov.test.Path;
import com.aimprosoft.korotkov.test.db.DBManager;
import com.aimprosoft.korotkov.test.exception.AppException;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Андрей on 31.01.2017.
 */
public class DeleteDepartmentCommand extends Command {

    private static final long serialVersionUID = 682248626968378594L;

    private static final Logger LOG = Logger.getLogger(DeleteDepartmentCommand.class);

    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, AppException {

        int id = Integer.parseInt(request.getParameter("deleteDepartmentId"));
        LOG.debug("delete department id -----------> "+ id);
        DBManager.getInstance().deleteDepartmentAndAllItsEmployees(id);

        LOG.debug("Command finished");
        return Path.ALL_DEPARTMENTS_COMMAND;
    }
}
