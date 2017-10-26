package com.aimprosoft.korotkov.test.db.dao;

import com.aimprosoft.korotkov.test.db.entity.Employee;
import com.aimprosoft.korotkov.test.exception.DBException;

import java.sql.Date;
import java.util.List;

/**
 * Created by Андрей on 26.10.2017.
 */
public interface DaoEmployee {

    public List<Employee> findAllEmployees() throws DBException;
    public List<Employee> findEmployeesThisDepartment(int idDepartment) throws DBException;
    public Employee findEmployeeById(int idEmployee);
    public void addEmployee(String firstName, String lastName, Date date, String phone, String mail, int idDep) throws DBException;
    public void updateEmployee(Employee employee) throws DBException;
    public void deleteEmployee(int id) throws DBException;


}
