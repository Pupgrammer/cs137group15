/*
CS137 Spring 2016 | Group 15
Main Author: Thomas Tai Nguyen
Filename: src/SubmitOrder.java
*/

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Iterator;
import java.util.Enumeration;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;
import java.sql.*;

public class SubmitOrder extends HttpServlet 
{
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException { 
        PrintWriter out = response.getWriter(); // Note: When JSP page implemented, this is no longer needed.
        
        String order_id = generateOrderId();
        DatabaseOrderHandler connection = new DatabaseOrderHandler();
        
        HashMap<String, String> order = organizeOrderInfo(request, request.getParameterNames());
        HashMap<Integer, Integer> cart = (HashMap<Integer, Integer>) request.getSession().getAttribute("cart");


        printDebugInformation(out, order_id, order, cart); // Note: When JSP page implemented, this is no longer needed.
        
        executeOrderSQLStatement(connection, order_id, cart);
        executeCustomerSQLStatement(connection, order_id, order);
        
        connection.close();
    }
    
    /* Begin SQL Functions */
    private void executeCustomerSQLStatement(DatabaseOrderHandler connection, String session_id, HashMap<String, String> order)  {
        connection.executeCustomerInfoStatement(session_id, order.get("firstName"), order.get("lastName"), order.get("email"), order.get("phoneNumber"), order.get("streetAddress"), order.get("zipcode"), order.get("city"), order.get("state"), order.get("shipping"), order.get("creditCard"));
    }
        
    private void executeOrderSQLStatement(DatabaseOrderHandler connection, String session_id, HashMap<Integer, Integer> cart) {
        HashMap<Integer, Double> product_costs = getProductCosts(cart); 
        Iterator iterate = cart.entrySet().iterator(); // Begin iteration of items in the cart for order submission.
        while (iterate.hasNext())
        {
            Map.Entry pair = (Map.Entry) iterate.next(); // Key = Product ID, Value = Quantity of Product ID
            int product_id = (int) pair.getKey();
            int quantity = (int) pair.getValue();
            connection.executeOrderInfoStatement(session_id, product_id, quantity, product_costs.get(product_id) * (double) quantity);
        }
    }
    
    /* Begin Helper Functions */
    HashMap<String, String> organizeOrderInfo(HttpServletRequest request, Enumeration<String> parameterNames) { // Organizes request parameters for ease of use.
        HashMap<String, String> result = new HashMap<String, String>();
        while (parameterNames.hasMoreElements())
        {
            String parameter = parameterNames.nextElement();
            result.put(parameter, request.getParameterValues(parameter)[0]);
        }
        return result;
    }

    private HashMap<Integer, Double> getProductCosts(HashMap<Integer, Integer> cart) { // Gets the costs of the products present in the cart from the database.
        HashMap<Integer, Double> product_costs = new HashMap<Integer, Double>();
        DatabaseResultSet database_costs = new DatabaseResultSet(createProductSQLStatement(cart));
        ResultSet result = database_costs.getResultSet();
        try
        {
            while (result.next())
            {
                product_costs.put(result.getInt("product_id"), result.getDouble("price"));
            }
        }
        catch (SQLException e ) 
        {
            e.printStackTrace();
        }
        return product_costs;
    }
    
    String createProductSQLStatement(HashMap<Integer, Integer> cart) { // Creates product price retrieveal SQL statement.
        int counter = 0;
        String sql = "SELECT product_number AS product_id, price from products WHERE product_number = ";
        for (Integer product_id : cart.keySet())
        {
            if (counter == 0)
            {
                sql = sql + product_id;
                counter = counter + 1;
            }
            else
            {
                sql = sql + " OR product_number = " + product_id;
            }
        }
        return sql;
    }
    
    String generateOrderId()
    {
        String uuid = UUID.randomUUID().toString();
        return uuid;
    }
        
    /* Begin Debug Functions */
    void printDebugInformation(PrintWriter out, String order_id, HashMap<String, String> order, HashMap<Integer, Integer> cart)
    {
        out.println("Randomly Generated Order-ID: " + order_id);
        out.println("");
        for (Map.Entry<String, String> entry : order.entrySet())
        {
            out.println(entry.getKey() + "/" + entry.getValue());
        }
        
        for (Map.Entry<Integer, Integer> entry : cart.entrySet())
        {
            out.println(entry.getKey() + "/" + entry.getValue());
        } 
    }
    
}
