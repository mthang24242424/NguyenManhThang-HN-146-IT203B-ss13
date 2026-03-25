package Bai5;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseHelper {
    private static final String URL = "jdbc:mysql://localhost:3306/bai5";
    private static final String USER = "root";
    private static final String PASS = "123456";

    public static Connection getConnection() throws Exception {
        return DriverManager.getConnection(URL, USER, PASS);
    }
}