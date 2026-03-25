package Bai4;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseManager {

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/ss13",
                    "root",
                    "123456"
            );
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}