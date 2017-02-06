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
 * Created by Андрей on 05.02.2017.
 */
public class AddDepartmentCommand extends Command {

    private static final long serialVersionUID = 92741626638876441L;

    private static final Logger LOG = Logger.getLogger(AddEmployeeCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, AppException {

        String name = request.getParameter("nameNewDepartment");


        if(name.isEmpty() || name==null){
            throw new AppException("The value of name new department can not be empty");
        }

        if(name.length()<2||name.length()>45){
            throw new AppException("Department name can not be shorter than two or longer than 45 characters");
        }

        DBManager.getInstance().addDepartment(name);

        LOG.debug("Command finished");
        return Path.ALL_DEPARTMENTS_COMMAND;
    }
}
