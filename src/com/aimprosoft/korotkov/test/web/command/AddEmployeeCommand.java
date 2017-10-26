package com.aimprosoft.korotkov.test.web.command;

import com.aimprosoft.korotkov.test.Path;
import com.aimprosoft.korotkov.test.db.dao.DaoDepartmentImpl;
import com.aimprosoft.korotkov.test.db.dao.DaoEmployeeImpl;
import com.aimprosoft.korotkov.test.db.entity.Department;
import com.aimprosoft.korotkov.test.db.entity.Employee;
import com.aimprosoft.korotkov.test.exception.AppException;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
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
        int idDep = Integer.parseInt(request.getParameter("departmentId"));
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String phone = request.getParameter("phone");
        String email = request.getParameter("email");
        String birthdayString = request.getParameter("birthday");
        LOG.debug("birthdayString --> "+birthdayString);
        java.sql.Date d = null;

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
                throw new AppException("Unsuitable age");
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }

        if(email.isEmpty() || email==null){
            throw new AppException("The value of email can not be empty");
        }

        if(phone.isEmpty() || phone==null){
            throw new AppException("The value of phone can not be empty");
        }


        if(firstName.length()<2||firstName.length()>45){
            throw new AppException("Employee first name can not be shorter than two or longer than 45 characters");
        }

        if(lastName.length()<2||lastName.length()>45){
            throw new AppException("Employee last name can not be shorter than two or longer than 45 characters");
        }

        String domen1 = "[a-zA-Z][a-zA-Z[0-9]\u005F\u002E\u002D]*[a-z||0-9]";
        String domen2 = "([a-z]){2,4}";
        Pattern p = Pattern.compile(domen1 + "@" + domen1 + "\u002E" + domen2);
        Matcher m = p.matcher(email);
        if(!m.matches()){
            throw new AppException("New email does not meet the requirements");
        }

        String number = "(\\+)(380)([0-9]){2}(-)([0-9]){2}-[0-9]{2}-[0-9]{3}";
        Pattern p1 = Pattern.compile(number);
        Matcher m1 = p1.matcher(phone);
        if(!m1.matches()){
            throw new AppException("New phone does not meet the requirements");
        }

        LOG.debug("firstName - " + firstName);
        LOG.debug("lastName - "+ lastName);
        LOG.debug("d - "+ d);
        LOG.debug("phone - "+ phone);
        LOG.debug("email - "+ email);
        LOG.debug("idDep - "+ idDep);

        Department department = DaoDepartmentImpl.getInstance().getDepartmentByName(request.getParameter("departmentName"));
        Employee employee = new Employee(firstName, lastName, d, phone, email, department.getId());
        LOG.debug("employee ===== "+employee);
        DaoEmployeeImpl.getInstance().addEmployee(employee);
        request.setAttribute("itemId", idDep);
        request.setAttribute("itemName", request.getParameter("departmentName"));
        LOG.debug("Command finished");
        return Path.EMPLOYEE_THIS_DEPARTMENT_COMMAND;
    }
}
