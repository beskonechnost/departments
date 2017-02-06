package com.aimprosoft.korotkov.test.db.entity;

/**
 * Created by Андрей on 30.01.2017.
 */
public class Department extends Entity{

    private static final long serialVersionUID = 5359981708905511482L;

    private String name;

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Department: name = " + name;
    }
}