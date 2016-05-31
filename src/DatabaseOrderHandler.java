import java.sql.*;
import java.io.PrintWriter;

public class DatabaseOrderHandler {

    Connection current;
    
    public DatabaseOrderHandler() 
    {
        current = null;
    }
    
    public void close()
    {
        try
        {
            current.close();   
        }
        catch (SQLException e)
        {
            e.printStackTrace();  
        }
        current = null;
    }
    
    private Connection handleGetConnection()
    {
        if (current != null)
        {
            return current;
        }
        else
        {
            // JDBC driver name and database URL
            final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
            final String SERVER_NAME = ConnectionInfo.SERVER_NAME;
            final String DB_NAME = ConnectionInfo.DATABASE_NAME;

            //  Database credentials
            final String USER = ConnectionInfo.USER_NAME;
            final String PASS = ConnectionInfo.PASSWORD;

            try {
                // Register JDBC driver
                Class.forName(JDBC_DRIVER);

                current = DriverManager.getConnection(SERVER_NAME + DB_NAME, USER, PASS);
            }
            catch (SQLException | ClassNotFoundException e)
            {
                e.printStackTrace();  
            }
            return current;
        }
    }

    public void executeOrderInfoStatement(String order_id, int product_id, int quantity, double total_cost)
    {
        String orderSQL = "INSERT INTO order_info (order_id, product_id, quantity, total_cost) VALUES (?, ?, ?, ?)";
        
        try
        {
            Connection connection = handleGetConnection();

            // Execute SQL query
            PreparedStatement statement = connection.prepareStatement(orderSQL);
            statement.setString(1, order_id);
            statement.setInt(2, product_id);
            statement.setInt(3, quantity);
            statement.setDouble(4, total_cost);
            statement.executeUpdate();
            
            connection = null;
        }            
        catch (SQLException e) 
        {
            e.printStackTrace();
        }
    }
    
    public void executeCustomerInfoStatement(String order_id, String first_name, String last_name, String email, String phone_number, String address, String zipcode, String city, String state, String shipping_method, String credit_card)
    {
        String customerSQL = "INSERT INTO customer_info (order_id, first_name, last_name, email, phone_number, address, zipcode, city, state, shipping_method, credit_card) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        try
        {
            Connection connection = handleGetConnection();

            // Execute SQL query
            PreparedStatement statement = connection.prepareStatement(customerSQL);
            statement.setString(1, order_id);
            statement.setString(2, first_name);
            statement.setString(3, last_name);
            statement.setString(4, email);
            statement.setString(5, phone_number);
            statement.setString(6, address);
            statement.setString(7, zipcode);
            statement.setString(8, city);
            statement.setString(9, state);
            statement.setString(10, shipping_method);
            statement.setString(11, credit_card);
            statement.executeUpdate();
            
            connection = null;
        }            
        catch (SQLException e) 
        {
            e.printStackTrace();
        }
    }
}
