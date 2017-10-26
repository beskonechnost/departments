package com.aimprosoft.korotkov.test.web.command;

import com.aimprosoft.korotkov.test.Path;
import com.aimprosoft.korotkov.test.db.dao.DaoDepartmentImpl;
import com.aimprosoft.korotkov.test.db.entity.Department;
import com.aimprosoft.korotkov.test.exception.AppException;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Андрей on 31.01.2017.
 */
public class UpdateDepartmentFormCommand extends Command {

    private static final long serialVersionUID = 69722626917876479L;

    private static final Logger LOG = Logger.getLogger(UpdateDepartmentFormCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, AppException {

        int id = Integer.parseInt(request.getParameter("id"));
        LOG.debug("id --> "+ id);
        String oldName = request.getParameter("oldName");
        LOG.debug("oldName --> "+ oldName);
        String newName = request.getParameter("name");
        LOG.debug("newName --> "+ newName);

        boolean flagErrorUpdateDepartment = false;
        String errorUpdateDepartment = null;

        if(newName==null || newName.isEmpty()){
            flagErrorUpdateDepartment = true;
            errorUpdateDepartment = "Department name can not be empty";
        }

        if(newName.length()<2||newName.length()>45){
            flagErrorUpdateDepartment = true;
            errorUpdateDepartment = "Department name can not be shorter than two or longer than 45 characters";
        }

        if(flagErrorUpdateDepartment){
            request.setAttribute("updateDepartmentName", request.getParameter("name"));
            LOG.debug("updateDepartmentName -- > "+request.getParameter("name"));
            request.setAttribute("visibleUpdateDepartment", request.getParameter("visibleUpdateDepartment"));
            LOG.debug("visibleUpdateDepartment -- > "+request.getParameter("visibleUpdateDepartment"));
            request.setAttribute("errorUpdateDepartment", errorUpdateDepartment);
        }else {
            if (!oldName.equals(newName)) {
                request.setAttribute("visibleUpdateDepartment", 0);
                Department department = new Department(id, newName);
                DaoDepartmentImpl.getInstance().updateDepartment(department);
            }
        }
        LOG.debug("Command finished");
        return Path.ALL_DEPARTMENTS_COMMAND;
    }
}
