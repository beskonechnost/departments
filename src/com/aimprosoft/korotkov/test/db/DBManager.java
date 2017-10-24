package com.aimprosoft.korotkov.test.db;

import com.aimprosoft.korotkov.test.db.entity.Department;
import com.aimprosoft.korotkov.test.db.entity.Employee;
import com.aimprosoft.korotkov.test.db.entity.EmployeeDep;
import com.aimprosoft.korotkov.test.exception.DBException;
import com.aimprosoft.korotkov.test.exception.Messages;
import org.apache.log4j.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBManager {

    private static final Logger LOG = Logger.getLogger(DBManager.class);

    private static DBManager instance;

    public static synchronized DBManager getInstance() throws DBException {
        if (instance == null) {
            instance = new DBManager();
        }
        return instance;
    }

    private DBManager() throws DBException {
        try {
            Context initContext = new InitialContext();
            Context envContext = (Context) initContext.lookup("java:/comp/env");
            ds = (DataSource) envContext.lookup("jdbc/departments");
            LOG.trace("Data source ==> " + ds);
        } catch (NamingException ex) {
            LOG.error(Messages.ERR_CANNOT_OBTAIN_DATA_SOURCE, ex);
            throw new DBException(Messages.ERR_CANNOT_OBTAIN_DATA_SOURCE, ex);
        }
    }

    private DataSource ds;

    private static final String SQL_FIND_ALL_DEPARTMENTS = "SELECT * FROM alldepartments";
    private static final String SQL_UPDATE_DEPARTMENT = "UPDATE departments SET departments.name=? WHERE departments.id=?";
    private static final String SQL_UPDATE_EMPLOYEE = "UPDATE employees SET employees.firstName=?, employees.lastName=?,employees.birthday=?,employees.phone=?,employees.email=?,employees.id_departments=?  WHERE employees.id=?";

    private static final String SQL_DELETE_ALL_EMPLOYEES_THIS_DEPARTMENT = "DELETE FROM employees WHERE employees.id_departments=?";
    private static final String SQL_DELETE_DEPARTMENT = "DELETE FROM departments WHERE departments.id=?";
    private static final String SQL_DELETE_EMPLOYEE = "DELETE FROM employees WHERE employees.id=?";


    private static final String SQL_SELECT_EMPLOYEES_THIS_DEPARTMENT = "SELECT * FROM allemployees WHERE name=?";
    private static final String SQL_SELECT_ALL_EMPLOYEES = "SELECT * FROM allemployees";

    private static final String SQL_ADD_EMPLOYEE = "INSERT INTO employees(firstName, lastName, birthday, phone, email, id_departments) VALUES (?,?,?,?,?,?)";
    private static final String SQL_SELECT_DEPARTMENT_ID = "SELECT * FROM alldepartments WHERE name=?";

    private static final String SQL_ADD_DEPARTMENT = "INSERT INTO departments(name) VALUES (?)";

    public Connection getConnection() throws DBException {
        Connection con = null;
        try {
            con = ds.getConnection();
        } catch (SQLException ex) {
            LOG.error(Messages.ERR_CANNOT_OBTAIN_CONNECTION, ex);
            throw new DBException(Messages.ERR_CANNOT_OBTAIN_CONNECTION, ex);
        }
        return con;
    }

    public List<Department> findDepartments() throws DBException {
        List<Department> departments = new ArrayList<Department>();
        Statement stmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = getConnection();
            stmt = con.createStatement();
            rs = stmt.executeQuery(SQL_FIND_ALL_DEPARTMENTS);
            while (rs.next()) {
                departments.add(extractDepartment(rs));
            }
            con.commit();
        } catch (SQLException ex) {
            rollback(con);
            LOG.error(Messages.ERR_CANNOT_OBTAIN_DEPARTMENTS, ex);
            throw new DBException(Messages.ERR_CANNOT_OBTAIN_DEPARTMENTS, ex);
        } finally {
            close(con, stmt, rs);
        }
        return departments;
    }

    public void updateDepartment(int id, String name) throws DBException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = getConnection();
            pstmt = con.prepareStatement(SQL_UPDATE_DEPARTMENT);
            pstmt.setString(1, name);
            pstmt.setInt(2, id);
            pstmt.executeUpdate();
            con.commit();
        } catch (SQLException ex) {
            rollback(con);
            throw new DBException(Messages.ERR_UPDATE_DEPARTMENT, ex);
        } finally {
            close(con, pstmt, rs);
        }
    }

    public void deleteDepartmentAndAllItsEmployees(int id) throws DBException {
        PreparedStatement pstmt = null;
        PreparedStatement pstmt1 = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = getConnection();
            con.setAutoCommit(false);
            pstmt = con.prepareStatement(SQL_DELETE_ALL_EMPLOYEES_THIS_DEPARTMENT);
            pstmt.setInt(1, id);
            pstmt.executeUpdate();

            pstmt1 = con.prepareStatement(SQL_DELETE_DEPARTMENT);
            pstmt1.setInt(1, id);
            pstmt1.executeUpdate();
            con.commit();
        } catch (SQLException ex) {
            rollback(con);
            throw new DBException(Messages.ERR_DELETE_DEPARTMENT, ex);
        } finally {
            close(con, pstmt, rs);
        }
    }

    public List<EmployeeDep> findAllEmployees() throws DBException {
        List<EmployeeDep> employees = new ArrayList<EmployeeDep>();
        Statement stmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = getConnection();
            stmt = con.createStatement();
            rs = stmt.executeQuery(SQL_SELECT_ALL_EMPLOYEES);
            while (rs.next()) {
                employees.add(extractEmployeeDep(rs));
            }
            con.commit();
        } catch (SQLException ex) {
            rollback(con);
            LOG.error(Messages.ERR_CANNOT_OBTAIN_EMPLOYEES, ex);
            throw new DBException(Messages.ERR_CANNOT_OBTAIN_EMPLOYEES, ex);
        } finally {
            close(con, stmt, rs);
        }
        return employees;
    }

    public List<EmployeeDep> findEmployeesThisDepartment(String name) throws DBException {
        List<EmployeeDep> employees = new ArrayList<EmployeeDep>();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = getConnection();
            pstmt = con.prepareStatement(SQL_SELECT_EMPLOYEES_THIS_DEPARTMENT);
            pstmt.setString(1, name);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                employees.add(extractEmployeeDep(rs));
            }
            con.commit();
        } catch (SQLException ex) {
            rollback(con);
            LOG.error(Messages.ERR_CANNOT_OBTAIN_EMPLOYEES, ex);
            throw new DBException(Messages.ERR_CANNOT_OBTAIN_EMPLOYEES, ex);
        } finally {
            close(con, pstmt, rs);
        }
        return employees;
    }

    public void addEmployee(String firstName, String lastName, Date date, String phone, String mail, int idDep) throws DBException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            LOG.debug("date - "+ date);
            con = getConnection();
            pstmt = con.prepareStatement(SQL_ADD_EMPLOYEE);
            pstmt.setString(1, firstName);
            pstmt.setString(2, lastName);
            pstmt.setDate(3, date);
            pstmt.setString(4, phone);
            pstmt.setString(5, mail);
            pstmt.setInt(6, idDep);
            pstmt.executeUpdate();
            con.commit();
        } catch (SQLException ex) {
            rollback(con);
            throw new DBException(Messages.ERR_ADD_EMPLOYEE, ex);
        } finally {
            close(con, pstmt, rs);
        }
    }

    public void addDepartment(String name) throws DBException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = getConnection();
            pstmt = con.prepareStatement(SQL_ADD_DEPARTMENT);
            pstmt.setString(1, name);
            pstmt.executeUpdate();
            con.commit();
        } catch (SQLException ex) {
            rollback(con);
            throw new DBException(Messages.ERR_ADD_DEPARTMENT, ex);
        } finally {
            close(con, pstmt, rs);
        }
    }

    public void deleteEmployee(int id) throws DBException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = getConnection();
            pstmt = con.prepareStatement(SQL_DELETE_EMPLOYEE);
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            con.commit();
        } catch (SQLException ex) {
            rollback(con);
            throw new DBException(Messages.ERR_DELETE_EMPLOYEE, ex);
        } finally {
            close(con, pstmt, rs);
        }
    }

    public Department getDepartmentByName(String name) throws DBException {
        Department dep = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            LOG.debug("name --> "+ name);
            con = getConnection();
            pstmt = con.prepareStatement(SQL_SELECT_DEPARTMENT_ID);
            pstmt.setString(1,name);
            pstmt.execute();
            rs = pstmt.executeQuery();
            if (rs.next()) {
                dep = extractDepartment(rs);
                LOG.debug("dep --> "+ dep);
            }
            con.commit();
        } catch (SQLException ex) {
            rollback(con);
            ex.printStackTrace();
            throw new DBException(Messages.ERR_SELECT_DEPARTMENT, ex);
        } finally {
            close(con, pstmt, rs);
        }
        return dep;
    }

    public void updateEmployee(String firstName, String lastName, Date date, String phone, String mail, int idDep, int id) throws DBException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            LOG.debug("date - "+ date);
            con = getConnection();
            pstmt = con.prepareStatement(SQL_UPDATE_EMPLOYEE);
            pstmt.setString(1, firstName);
            pstmt.setString(2, lastName);
            pstmt.setDate(3, date);
            pstmt.setString(4, phone);
            pstmt.setString(5, mail);
            pstmt.setInt(6, idDep);
            pstmt.setInt(7, id);
            pstmt.executeUpdate();
            con.commit();
        } catch (SQLException ex) {
            rollback(con);
            throw new DBException(Messages.ERR_UPDATE_EMPLOYEE, ex);
        } finally {
            close(con, pstmt, rs);
        }
    }








    private void close(Connection con) {
        if (con != null) {
            try {
                con.close();
            } catch (SQLException ex) {
                LOG.error(Messages.ERR_CANNOT_CLOSE_CONNECTION, ex);
            }
        }
    }

    private void close(Statement stmt) {
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException ex) {
                LOG.error(Messages.ERR_CANNOT_CLOSE_STATEMENT, ex);
            }
        }
    }

    private void close(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException ex) {
                LOG.error(Messages.ERR_CANNOT_CLOSE_RESULTSET, ex);
            }
        }
    }

    private void close(Connection con, Statement stmt, ResultSet rs) {
        close(rs);
        close(stmt);
        close(con);
    }

    private void rollback(Connection con) {
        if (con != null) {
            try {
                con.rollback();
            } catch (SQLException ex) {
                LOG.error("Cannot rollback transaction", ex);
            }
        }
    }

    private Department extractDepartment(ResultSet rs) throws SQLException {
        Department department = new Department();
        department.setId(rs.getInt(Fields.ENTITY_ID));
        department.setName(rs.getString(Fields.DEPARTMENT_NAME));
        return department;
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

    private EmployeeDep extractEmployeeDep(ResultSet rs) throws SQLException {
        EmployeeDep employeeDep = new EmployeeDep();
        employeeDep.setId(rs.getInt(Fields.ENTITY_ID));
        employeeDep.setFirstName(rs.getString(Fields.EMPLOYEE_FIRST_NAME));
        employeeDep.setLastName(rs.getString(Fields.EMPLOYEE_LAST_NAME));
        employeeDep.setBirthday(rs.getDate(Fields.EMPLOYEE_BIRTHDAY));
        employeeDep.setPhone(rs.getString(Fields.EMPLOYEE_PHONE));
        employeeDep.setEmail(rs.getString(Fields.EMPLOYEE_EMAIL));
        employeeDep.setNameDepartment(rs.getString(Fields.DEPARTMENT_NAME));
        return employeeDep;
    }


}
