package com.aimprosoft.korotkov.test.web.command;

import com.aimprosoft.korotkov.test.exception.AppException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;

public abstract class Command implements Serializable{

    private static final long serialVersionUID = 2675933039646911049L;

    public abstract String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, AppException;

    @Override
    public final String toString() {
        return getClass().getSimpleName();
    }

}
