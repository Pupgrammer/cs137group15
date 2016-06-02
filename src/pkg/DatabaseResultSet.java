/*
CS137 Spring 2016 | Group 15
Main Author: Brian Chipman
Filename: src/pkg/DatabaseResultSet.java
*/

package pkg;

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

        Statement statement;
        Connection connection;

        try {
            // Register JDBC driver
            Class.forName(JDBC_DRIVER);

            // Open a connection
            connection = DriverManager.getConnection(SERVER_NAME + DB_NAME, USER, PASS);

            // Execute SQL query
            statement = connection.createStatement();
            rs = statement.executeQuery(sql);

        }
        catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public ResultSet getResultSet() {
        return rs;
    }
}
