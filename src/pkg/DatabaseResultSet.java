/*
CS137 Spring 2016 | Group 15
Main Author: Brian Chipman
Filename: src/pkg/DatabaseResultSet.java
*/

package pkg;

import pkg.ConnectionInfo;
import java.sql.*;

public class DatabaseResultSet {

    ResultSet rs;

    public DatabaseResultSet(String sql) {

        // JDBC driver name and database URL
        final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
        final String SERVER_NAME = ConnectionInfo.SERVER_NAME;
        final String DB_NAME = ConnectionInfo.DATABASE_NAME;

        //  Database credentials
        final String USER = ConnectionInfo.USER_NAME;
        final String PASS = ConnectionInfo.PASSWORD;

        Statement statement = null;
        Connection connection = null;

        try {
            // Register JDBC driver
            Class.forName(JDBC_DRIVER);

            // Open a connection
            connection = DriverManager.getConnection(SERVER_NAME + DB_NAME, USER, PASS);

            // Execute SQL query
            statement = connection.createStatement();
            //String sql = "SELECT * FROM products WHERE product_number=" + request.getParameter("product_number") + ";";
            rs = statement.executeQuery(sql);
            
        }
        catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    //public boolean next() {
    //    try {
    //        return rs.next();
    //    }
    //    catch (SQLException e) {
    //        e.printStackTrace();
    //    }
    //    return false;
    //}

    public ResultSet getResultSet() {
        return rs;
    }
}
