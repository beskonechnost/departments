package com.aimprosoft.korotkov.test.db.entity;

import java.io.Serializable;

public abstract class Entity implements Serializable{
    
    private static final long serialVersionUID = 1357654080126749205L;

    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "id=" + id;
    }
}
