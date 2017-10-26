package com.aimprosoft.korotkov.test.db.dao;

import com.aimprosoft.korotkov.test.db.DBManager1;
import com.aimprosoft.korotkov.test.db.Fields;
import com.aimprosoft.korotkov.test.db.entity.Employee;
import com.aimprosoft.korotkov.test.exception.DBException;
import com.aimprosoft.korotkov.test.exception.Messages;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Андрей on 26.10.2017.
 */
public class DaoEmployeeImpl implements DaoEmployee{

    private static final Logger LOG = Logger.getLogger(DaoEmployeeImpl.class);

    private static final String SQL_UPDATE_EMPLOYEE = "UPDATE employees SET employees.firstName=?, employees.lastName=?,employees.birthday=?,employees.phone=?,employees.email=?,employees.id_departments=?  WHERE employees.id=?";
    private static final String SQL_DELETE_EMPLOYEE = "DELETE FROM employees WHERE employees.id=?";
    private static final String SQL_SELECT_EMPLOYEES_THIS_DEPARTMENT = "SELECT * FROM employees WHERE id_department=?";
    private static final String SQL_SELECT_ALL_EMPLOYEES = "SELECT * FROM employees";
    private static final String SQL_ADD_EMPLOYEE = "INSERT INTO employees(firstName, lastName, birthday, phone, email, id_department) VALUES (?,?,?,?,?,?)";

    private static DaoEmployeeImpl instance;

    private DaoEmployeeImpl(){
    }

    public static synchronized DaoEmployeeImpl getInstance() {
        if (instance == null) {
            instance = new DaoEmployeeImpl();
        }
        return instance;
    }

    private Employee extractEmployee(ResultSet rs) throws SQLException {
        Employee employee = new Employee();
        employee.setId(rs.getInt(Fields.ENTITY_ID));
        employee.setFirstName(rs.getString(Fields.EMPLOYEE_FIRST_NAME));
        employee.setLastName(rs.getString(Fields.EMPLOYEE_LAST_NAME));
        employee.setBirthday(rs.getDate(Fields.EMPLOYEE_BIRTHDAY));
        employee.setPhone(rs.getString(Fields.EMPLOYEE_PHONE));
        employee.setEmail(rs.getString(Fields.EMPLOYEE_EMAIL));
        employee.setIdDepartment(rs.getInt(Fields.EMPLOYEE_ID_DEPARTMENT));
        return employee;
    }

    @Override
    public List<Employee> findAllEmployees() throws DBException{
        List<Employee> employees = new ArrayList<Employee>();
        Statement stmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = DBManager1.getConnection();
            stmt = con.createStatement();
            rs = stmt.executeQuery(SQL_SELECT_ALL_EMPLOYEES);
            while (rs.next()) {
                employees.add(extractEmployee(rs));
            }
            con.commit();
        } catch (SQLException ex) {
            DBManager1.rollback(con);
            LOG.error(Messages.ERR_CANNOT_OBTAIN_EMPLOYEES, ex);
            throw new DBException(Messages.ERR_CANNOT_OBTAIN_EMPLOYEES, ex);
        } finally {
            DBManager1.close(con, stmt, rs);
        }
        return employees;
    }

    @Override
    public List<Employee> findEmployeesThisDepartment(int idDepartment) throws DBException{
        List<Employee> employees = new ArrayList<Employee>();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = DBManager1.getConnection();
            pstmt = con.prepareStatement(SQL_SELECT_EMPLOYEES_THIS_DEPARTMENT);
            pstmt.setInt(1, idDepartment);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                employees.add(extractEmployee(rs));
            }
            con.commit();
        } catch (SQLException ex) {
            DBManager1.rollback(con);
            LOG.error(Messages.ERR_CANNOT_OBTAIN_EMPLOYEES, ex);
            throw new DBException(Messages.ERR_CANNOT_OBTAIN_EMPLOYEES, ex);
        } finally {
            DBManager1.close(con, pstmt, rs);
        }
        return employees;
    }

    @Override
    public Employee findEmployeeById(int idEmployee) {
        return null;
    }

    @Override
    public void addEmployee(Employee employee) throws DBException{
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = DBManager1.getConnection();
            pstmt = con.prepareStatement(SQL_ADD_EMPLOYEE);
            pstmt.setString(1, employee.getFirstName());
            pstmt.setString(2, employee.getLastName());
            pstmt.setDate(3, (Date) employee.getBirthday());
            pstmt.setString(4, employee.getPhone());
            pstmt.setString(5, employee.getEmail());
            pstmt.setInt(6, employee.getId());
            pstmt.executeUpdate();
            con.commit();
        } catch (SQLException ex) {
            DBManager1.rollback(con);
            throw new DBException(Messages.ERR_ADD_EMPLOYEE, ex);
        } finally {
            DBManager1.close(con, pstmt, rs);
        }
    }

    @Override
    public void updateEmployee(Employee employee) {

    }

    @Override
    public void deleteEmployee(int id) {

    }
}
