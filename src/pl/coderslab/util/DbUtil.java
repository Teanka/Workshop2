package pl.coderslab.util;

import java.sql.*;

public class DbUtil {

    public static Connection getConnection() throws SQLException{
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/workshop2?useSSL=false&characterEncoding=" +
                        "utf8&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "coderslab");
            return connection;
    }
}
