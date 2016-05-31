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
        /* OrderInfo Structure:  CREATE TABLE `order_info`
            (
            `order_id` varchar(40) NOT NULL,
            `product_id` int(5) NOT NULL,
            `product_name` varchar(40) NOT NULL,
            `quantity` int(3) NOT NULL,
            `total_cost` float(20) NOT NULL,
            PRIMARY KEY (order_id, product_id)
            ) ENGINE=InnoDB;
        */
        
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
        }            
        catch (SQLException e) 
        {
            e.printStackTrace();
        }
    }
}
