package com.aimprosoft.korotkov.test.web.command;

import com.aimprosoft.korotkov.test.Path;
import com.aimprosoft.korotkov.test.exception.AppException;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AddNewDepartmentCommand extends Command {

    private static final long serialVersionUID = 712848192917387416L;

    private static final Logger LOG = Logger.getLogger(AddNewDepartmentCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, AppException {

        LOG.debug("Command finished");
        return Path.PAGE_ADD_NEW_DEPARTMENT;
    }
}
