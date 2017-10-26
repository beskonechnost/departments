package com.aimprosoft.korotkov.test.db.dao;

import com.aimprosoft.korotkov.test.db.DBManager1;
import com.aimprosoft.korotkov.test.db.Fields;
import com.aimprosoft.korotkov.test.db.entity.Department;
import com.aimprosoft.korotkov.test.exception.DBException;
import com.aimprosoft.korotkov.test.exception.Messages;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Андрей on 25.10.2017.
 */
public class DaoDepartmentImpl implements DaoDepartment {

    private static final Logger LOG = Logger.getLogger(DaoDepartmentImpl.class);

    private static final String SQL_FIND_ALL_DEPARTMENTS = "SELECT * FROM alldepartments";
    private static final String SQL_UPDATE_DEPARTMENT = "UPDATE departments SET departments.name=? WHERE departments.id=?";
    private static final String SQL_DELETE_ALL_EMPLOYEES_THIS_DEPARTMENT = "DELETE FROM employees WHERE employees.id_department=?";
    private static final String SQL_DELETE_DEPARTMENT = "DELETE FROM departments WHERE departments.id=?";
    private static final String SQL_SELECT_DEPARTMENT_BY_NAME = "SELECT * FROM alldepartments WHERE name=?";
    private static final String SQL_SELECT_DEPARTMENT_BY_ID = "SELECT * FROM alldepartments WHERE id=?";
    private static final String SQL_ADD_DEPARTMENT = "INSERT INTO departments(name) VALUES (?)";
    private static final String SQL_NAME_DEPARTMENTS = "SELECT departments.name FROM departments";

    private static DaoDepartmentImpl instance;

    private DaoDepartmentImpl(){
    }

    public static synchronized DaoDepartmentImpl getInstance() {
        if (instance == null) {
            instance = new DaoDepartmentImpl();
        }
        return instance;
    }

    private Department extractDepartment(ResultSet rs) throws SQLException {
        Department department = new Department();
        department.setId(rs.getInt(Fields.ENTITY_ID));
        department.setName(rs.getString(Fields.DEPARTMENT_NAME));
        return department;
    }

    @Override
    public List<Department> findDepartments() throws DBException{
        List<Department> departments = new ArrayList<Department>();
        Statement stmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = DBManager1.getConnection();
            stmt = con.createStatement();
            rs = stmt.executeQuery(SQL_FIND_ALL_DEPARTMENTS);
            while (rs.next()) {
                departments.add(extractDepartment(rs));
            }
            con.commit();
        } catch (SQLException ex) {
            DBManager1.rollback(con);
            LOG.error(Messages.ERR_CANNOT_OBTAIN_DEPARTMENTS, ex);
            throw new DBException(Messages.ERR_CANNOT_OBTAIN_DEPARTMENTS, ex);
        } finally {
            DBManager1.close(con, stmt, rs);
        }
        return departments;
    }

    @Override
    public void updateDepartment(Department department) throws DBException{
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = DBManager1.getConnection();
            pstmt = con.prepareStatement(SQL_UPDATE_DEPARTMENT);
            pstmt.setString(1, department.getName());
            pstmt.setInt(2, department.getId());
            pstmt.executeUpdate();
            con.commit();
        } catch (SQLException ex) {
            DBManager1.rollback(con);
            throw new DBException(Messages.ERR_UPDATE_DEPARTMENT, ex);
        } finally {
            DBManager1.close(con, pstmt, rs);
        }
    }

    @Override
    public void deleteDepartmentAndAllItsEmployees(int idDepartment) throws DBException{
        PreparedStatement pstmt = null;
        PreparedStatement pstmt1 = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = DBManager1.getConnection();
            con.setAutoCommit(false);
            pstmt = con.prepareStatement(SQL_DELETE_ALL_EMPLOYEES_THIS_DEPARTMENT);
            pstmt.setInt(1, idDepartment);
            pstmt.executeUpdate();

            pstmt1 = con.prepareStatement(SQL_DELETE_DEPARTMENT);
            pstmt1.setInt(1, idDepartment);
            pstmt1.executeUpdate();
            con.commit();
        } catch (SQLException ex) {
            DBManager1.rollback(con);
            throw new DBException(Messages.ERR_DELETE_DEPARTMENT, ex);
        } finally {
            DBManager1.close(con, pstmt, rs);
        }
    }

    @Override
    public void addDepartment(Department department) throws DBException{
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = DBManager1.getConnection();
            pstmt = con.prepareStatement(SQL_ADD_DEPARTMENT);
            pstmt.setString(1, department.getName());
            pstmt.executeUpdate();
            con.commit();
        } catch (SQLException ex) {
            DBManager1.rollback(con);
            throw new DBException(Messages.ERR_ADD_DEPARTMENT, ex);
        } finally {
            DBManager1.close(con, pstmt, rs);
        }
    }

    @Override
    public Department getDepartmentByName(String name) throws DBException{
        Department dep = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            LOG.debug("name --> "+ name);
            con = DBManager1.getConnection();
            pstmt = con.prepareStatement(SQL_SELECT_DEPARTMENT_BY_NAME);
            pstmt.setString(1,name);
            pstmt.execute();
            rs = pstmt.executeQuery();
            if (rs.next()) {
                dep = extractDepartment(rs);
                LOG.debug("dep --> "+ dep);
            }
            con.commit();
        } catch (SQLException ex) {
            DBManager1.rollback(con);
            ex.printStackTrace();
            throw new DBException(Messages.ERR_SELECT_DEPARTMENT, ex);
        } finally {
            DBManager1.close(con, pstmt, rs);
        }
        return dep;
    }

    @Override
    public Department getDepartmentById(int id) throws DBException{
        Department dep = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = DBManager1.getConnection();
            pstmt = con.prepareStatement(SQL_SELECT_DEPARTMENT_BY_ID);
            pstmt.setInt(1, id);
            pstmt.execute();
            rs = pstmt.executeQuery();
            if (rs.next()) {
                dep = extractDepartment(rs);
                LOG.debug("dep --> "+ dep);
            }
            con.commit();
        } catch (SQLException ex) {
            DBManager1.rollback(con);
            ex.printStackTrace();
            throw new DBException(Messages.ERR_SELECT_DEPARTMENT, ex);
        } finally {
            DBManager1.close(con, pstmt, rs);
        }
        return dep;
    }

    @Override
    public List<String> nameDepartments() throws DBException {
        List<String> nameDepartments = new ArrayList<String>();
        Statement stmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = DBManager1.getConnection();
            stmt = con.createStatement();
            rs = stmt.executeQuery(SQL_NAME_DEPARTMENTS);
            while (rs.next()) {
                nameDepartments.add(String.valueOf(rs));
            }
            con.commit();
        } catch (SQLException ex) {
            DBManager1.rollback(con);
            LOG.error(Messages.ERR_CANNOT_OBTAIN_DEPARTMENTS, ex);
            throw new DBException(Messages.ERR_CANNOT_OBTAIN_DEPARTMENTS, ex);
        } finally {
            DBManager1.close(con, stmt, rs);
        }
        return nameDepartments;
    }
}
