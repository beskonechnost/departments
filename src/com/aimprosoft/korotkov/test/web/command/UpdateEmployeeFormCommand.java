package com.aimprosoft.korotkov.test.web.command;

import com.aimprosoft.korotkov.test.Path;
import com.aimprosoft.korotkov.test.db.DBManager;
import com.aimprosoft.korotkov.test.db.entity.Department;
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

public class UpdateEmployeeFormCommand extends Command {

    private static final long serialVersionUID = 62872658717891445L;

    private static final Logger LOG = Logger.getLogger(UpdateEmployeeFormCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, AppException {

        int id = Integer.parseInt(request.getParameter("employeeId"));
        LOG.debug("id --> "+ id);

        String oldFirstName = request.getParameter("employeeFirstName");
        LOG.debug("oldFirstName --> "+ oldFirstName);
        String oldLastName = request.getParameter("employeeLastName");
        LOG.debug("oldLastName --> "+ oldLastName);
        String oldBirthday = request.getParameter("employeeBirthday");
        LOG.debug("oldBirthday --> "+ oldBirthday);
        String oldPhone = request.getParameter("employeePhone");
        LOG.debug("oldPhone --> "+ oldPhone);
        String oldEmail = request.getParameter("employeeEmail");
        LOG.debug("oldEmail --> "+ oldEmail);
        String oldDepartmentName = request.getParameter("departmentName");
        LOG.debug("oldDepartmentName --> "+ oldDepartmentName);

        String newFirstName = request.getParameter("newFirstName");
        LOG.debug("newFirstName --> "+ newFirstName);
        String newLastName = request.getParameter("newLastName");
        LOG.debug("newLastName --> "+ newLastName);
        String newBirthday = request.getParameter("newBirthday");
        LOG.debug("newBirthday --> "+ newBirthday);
        String newPhone = request.getParameter("newPhone");
        LOG.debug("newPhone --> "+ newPhone);
        String newEmail = request.getParameter("newEmail");
        LOG.debug("newEmail --> "+ newEmail);
        String newDepartmentName = request.getParameter("newDepartmentName");
        LOG.debug("newDepartmentName --> "+ newDepartmentName);

        boolean flag = false;
        if(oldFirstName!=newFirstName || oldLastName!=newLastName || oldBirthday!=newBirthday || oldPhone!=newPhone || oldEmail!=newEmail || oldDepartmentName!=newDepartmentName){
            flag=true;
        }

        java.sql.Date d = null;

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        try {
            java.util.Date birthday= format.parse(newBirthday);
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

        if(newEmail.isEmpty() || newEmail==null){
            throw new AppException("The value of email can not be empty");
        }

        if(newPhone.isEmpty() || newPhone==null){
            throw new AppException("The value of phone can not be empty");
        }


        if(newFirstName.length()<2||newFirstName.length()>45){
            throw new AppException("Employee first name can not be shorter than two or longer than 45 characters");
        }

        if(newLastName.length()<2||newLastName.length()>45){
            throw new AppException("Employee last name can not be shorter than two or longer than 45 characters");
        }

        String domen1 = "[a-zA-Z][a-zA-Z[0-9]\u005F\u002E\u002D]*[a-z||0-9]";
        String domen2 = "([a-z]){2,4}";
        Pattern p = Pattern.compile(domen1 + "@" + domen1 + "\u002E" + domen2);
        Matcher m = p.matcher(newEmail);
        if(!m.matches()){
            throw new AppException("New email does not meet the requirements");
        }

        String number = "(\\+)(380)([0-9]){2}(-)([0-9]){2}-[0-9]{2}-[0-9]{3}";
        Pattern p1 = Pattern.compile(number);
        Matcher m1 = p1.matcher(newPhone);
        if(!m1.matches()){
            throw new AppException("New phone does not meet the requirements");
        }

        LOG.debug("flag --> "+ flag);
        if(flag){
            Department Dep = DBManager.getInstance().getDepartmentByName(newDepartmentName);
            LOG.debug("Dep --> "+ Dep);
            int idDep = Dep.getId();

            DBManager.getInstance().updateEmployee(newFirstName, newLastName, d, newPhone, newEmail, idDep, id);
        }


        LOG.debug("Command finished");
        return Path.ALL_DEPARTMENTS_COMMAND;
    }
}
