package com.aimprosoft.korotkov.test.db.dao;

import com.aimprosoft.korotkov.test.db.entity.Department;
import com.aimprosoft.korotkov.test.exception.DBException;

import java.util.List;

/**
 * Created by Андрей on 25.10.2017.
 */
public interface DaoDepartment {

    public List<Department> findDepartments() throws DBException;
    public void updateDepartment(Department department) throws DBException;
    public void deleteDepartmentAndAllItsEmployees(int idDepartment) throws DBException;
    public void addDepartment(Department department) throws DBException;
    public Department getDepartmentByName(String name) throws DBException;
    public Department getDepartmentById(int id) throws DBException;
    public List<String> nameDepartments() throws DBException;

}
