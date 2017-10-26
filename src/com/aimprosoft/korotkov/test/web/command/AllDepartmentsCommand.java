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
import java.util.List;

/**
 * Created by Андрей on 30.01.2017.
 */
public class AllDepartmentsCommand extends Command {

    private static final long serialVersionUID = 6944286214073878561L;

    private static final Logger LOG = Logger.getLogger(AllDepartmentsCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, AppException {
        List<Department> departments = DaoDepartmentImpl.getInstance().findDepartments();
        LOG.trace("Found in DB: departmentsList --> " + departments);
        request.setAttribute("departments", departments);
        request.setAttribute("visibleUpdateDepartment", request.getParameter("visibleUpdateDepartment"));

        int id;
        if(!(request.getParameter("departmentId")==null)) {
            id = Integer.parseInt(request.getParameter("departmentId"));
        }else {id = 0;}
        if(!(id==0)){
            Department updateDepartment = DaoDepartmentImpl.getInstance().getDepartmentById(id);
            request.setAttribute("updateDepartmentName", updateDepartment.getName());
            request.setAttribute("updateDepartmentId", updateDepartment.getId());
        }

        LOG.debug("Command finished");
        return Path.PAGE_ALL_DEPARTMENTS;
    }
}
