package com.aimprosoft.korotkov.test.db;

import com.aimprosoft.korotkov.test.exception.DBException;
import com.aimprosoft.korotkov.test.exception.Messages;
import org.apache.log4j.Logger;

import java.sql.*;

/**
 * Created by Андрей on 25.10.2017.
 */
public class DBManager1 {

    private static final Logger LOG = Logger.getLogger(DBManager1.class);

    private static DBManager1 instance;

    private static String USERNAME = "root";
    private static String PASSWORD = "root";
    private static String URL = "jdbc:mysql://localhost:3307/departments";

    public static synchronized DBManager1 getInstance() throws DBException {
        if (instance == null) {
            instance = new DBManager1();
        }
        return instance;
    }

    public DBManager1() throws DBException {
        try {
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
        } catch (SQLException ex) {
            LOG.error(Messages.ERR_CANNOT_OBTAIN_DATA_SOURCE, ex);
            throw new DBException(Messages.ERR_CANNOT_OBTAIN_DATA_SOURCE, ex);
        }
    }

    public static Connection getConnection() throws DBException {
        Connection con = null;
        try {
            getInstance();
            con = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            con.setAutoCommit(false);
        } catch (SQLException ex) {
            LOG.error(Messages.ERR_CANNOT_OBTAIN_CONNECTION, ex);
            throw new DBException(Messages.ERR_CANNOT_OBTAIN_CONNECTION, ex);
        }
        return con;
    }


    private static void close(Connection con) {
        if (con != null) {
            try {
                con.close();
            } catch (SQLException ex) {
                LOG.error(Messages.ERR_CANNOT_CLOSE_CONNECTION, ex);
            }
        }
    }
    private static void close(Statement stmt) {
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException ex) {
                LOG.error(Messages.ERR_CANNOT_CLOSE_STATEMENT, ex);
            }
        }
    }
    private static void close(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException ex) {
                LOG.error(Messages.ERR_CANNOT_CLOSE_RESULTSET, ex);
            }
        }
    }
    public static void close(Connection con, Statement stmt, ResultSet rs) {
        close(rs);
        close(stmt);
        close(con);
    }

    public static void rollback(Connection con) {
        if (con != null) {
            try {
                con.rollback();
            } catch (SQLException ex) {
                LOG.error("Cannot rollback transaction", ex);
            }
        }
    }


}
