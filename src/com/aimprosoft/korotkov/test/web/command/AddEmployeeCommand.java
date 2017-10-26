package com.aimprosoft.korotkov.test.web.command;

import com.aimprosoft.korotkov.test.Path;
import com.aimprosoft.korotkov.test.db.dao.DaoDepartmentImpl;
import com.aimprosoft.korotkov.test.db.dao.DaoEmployeeImpl;
import com.aimprosoft.korotkov.test.db.entity.Department;
import com.aimprosoft.korotkov.test.exception.AppException;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Андрей on 04.02.2017.
 */
public class AddEmployeeCommand extends Command {

    private static final long serialVersionUID = 92741626638876441L;

    private static final Logger LOG = Logger.getLogger(AddEmployeeCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, AppException {

        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String phone = request.getParameter("phone");
        String email = request.getParameter("email");
        String birthdayString = request.getParameter("birthday");
        String nameDep = request.getParameter("departmentNameAdd");
        LOG.debug("nameDep --> "+nameDep);
        LOG.debug("birthdayString --> "+birthdayString);
        java.sql.Date d = null;

        boolean flagErrorAddEmployee = false;
        StringBuilder errorAddEmployee = new StringBuilder("");
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        try {
            java.util.Date birthday= format.parse(birthdayString);
            Calendar cal = Calendar.getInstance();
            cal.setTime(birthday);

            d = new java.sql.Date(cal.getTimeInMillis());
            LOG.debug("d --> "+d);

            GregorianCalendar birthDay = new GregorianCalendar(cal.get(cal.YEAR),cal.get(cal.MONTH),cal.get(cal.DAY_OF_MONTH));
            GregorianCalendar now = new GregorianCalendar();
            int years = now.get(GregorianCalendar.YEAR) - birthDay.get(GregorianCalendar.YEAR);

            if(years<14 || years>60){
                flagErrorAddEmployee = true;
                errorAddEmployee.append(" ").append("Age must be more than 14 and less than 60 years,");
            }

            if(nameDep==null){
                flagErrorAddEmployee = true;
                errorAddEmployee.append(" ").append("You need to choose a department,");
            }

        } catch (ParseException e) {
            flagErrorAddEmployee = true;
            errorAddEmployee.append(" ").append("Error parse date,");
        }

        if(email==null || email.isEmpty()){
            flagErrorAddEmployee = true;
            errorAddEmployee.append(" ").append("The value of email can not be empty,");
        }

        if(phone==null || phone.isEmpty()){
            flagErrorAddEmployee = true;
            errorAddEmployee.append(" ").append("The value of phone can not be empty,");
        }


        if(firstName.length()<=2||firstName.length()>45){
            flagErrorAddEmployee = true;
            errorAddEmployee.append(" ").append("Employee first name can not be shorter than two or longer than 45 characters,");
        }

        if(lastName.length()<=2||lastName.length()>45){
            flagErrorAddEmployee = true;
            errorAddEmployee.append(" ").append("Employee last name can not be shorter than two or longer than 45 characters,");
        }

        String domen1 = "[a-zA-Z][a-zA-Z[0-9]\u005F\u002E\u002D]*[a-z||0-9]";
        String domen2 = "([a-z]){2,4}";
        Pattern p = Pattern.compile(domen1 + "@" + domen1 + "\u002E" + domen2);
        Matcher m = p.matcher(email);
        if(!m.matches()){
            flagErrorAddEmployee = true;
            errorAddEmployee.append(" ").append("New email does not meet the requirements,");
        }

        String number = "(\\+)(380)([0-9]){2}(-)([0-9]){2}-[0-9]{2}-[0-9]{3}";
        Pattern p1 = Pattern.compile(number);
        Matcher m1 = p1.matcher(phone);
        if(!m1.matches()){
            flagErrorAddEmployee = true;
            errorAddEmployee.append(" ").append("New phone does not meet the requirements,");
        }

        if(flagErrorAddEmployee){
            request.setAttribute("firstNameNewEmployee",request.getParameter("firstName"));
            request.setAttribute("lastNameNewEmployee",request.getParameter("lastName"));
            request.setAttribute("phoneNewEmployee",request.getParameter("phone"));
            request.setAttribute("emailNewEmployee",request.getParameter("email"));
            request.setAttribute("birthdayNewEmployee",request.getParameter("birthday"));
            request.setAttribute("departmentNameNewEmployee",nameDep);
            errorAddEmployee.trimToSize();
            errorAddEmployee.setLength(errorAddEmployee.length()-1);
            request.setAttribute("errorAddEmployee", errorAddEmployee);
        }else {
            Department department = DaoDepartmentImpl.getInstance().getDepartmentByName(request.getParameter("departmentNameAdd"));
            LOG.debug("department - " + department);
            DaoEmployeeImpl.getInstance().addEmployee(firstName, lastName, d, phone, email, department.getId());
        }
        request.setAttribute("departmentName", request.getParameter("departmentName"));
        request.setAttribute("departments", request.getParameter("departments"));
        List<Department> dep = DaoDepartmentImpl.getInstance().findDepartments();
        List<String> namesDep = new ArrayList<String>();
        for(Department d1 : dep){
            namesDep.add(d1.getName());
        }
        LOG.debug("namesDep --------------- " + request.getParameter("namesDep"));
        request.setAttribute("namesDep", request.getParameter("namesDep"));
        LOG.debug("departments - " + request.getParameter("departments"));
        request.setAttribute("departmentId", request.getParameter("departmentId"));
        if (Integer.parseInt(request.getParameter("departmentId")) != 0) {
            request.setAttribute("employees", DaoEmployeeImpl.getInstance().findEmployeesThisDepartment(Integer.parseInt(request.getParameter("departmentId"))));
        } else {
            request.setAttribute("employees", DaoEmployeeImpl.getInstance().findAllEmployees());
        }
        LOG.debug("Command finished");
        return Path.PAGE_EMPLOYEE;
    }
}
