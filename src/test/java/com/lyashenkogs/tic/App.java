package com.lyashenkogs.tic;

import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class App {

    @Test
    public void test() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        Connection conn = null;
        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/tic_tac", "root", "root");
        Statement stmt = null;
        stmt = conn.createStatement();

        String sql = "INSERT INTO users " +
                "VALUES ('bbb')";
        stmt.executeUpdate(sql);
        conn.close();
    }
}
