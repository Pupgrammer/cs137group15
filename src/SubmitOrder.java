/*
CS137 Spring 2016 | Group 15
Main Author: Thomas Tai Nguyen
Filename: src/SubmitOrder.java
*/

import pkg.DatabaseResultSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;


public class SubmitOrder extends HttpServlet
{
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String order_id = generateOrderId();
        SimpleDateFormat order_time_format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String order_time = order_time_format.format(new Date());

        DatabaseOrderHandler connection = new DatabaseOrderHandler();

        HashMap<String, String> order = organizeOrderInfo(request, request.getParameterNames());
        HashMap<Integer, Integer> cart = (HashMap<Integer, Integer>) request.getSession().getAttribute("cart");

        executeOrderSQLStatement(connection, order_id, cart);
        executeCustomerSQLStatement(connection, order_id, order_time, order);

        connection.close();
        connection = null; // Dereference - hopefully makes garbage collection faster.
        order = null; // Dereference - hopefully makes garbage collection faster.
        request.getSession().removeAttribute("cart"); // Clears the cart.
        response.sendRedirect("order_details.jsp?order_id=" + order_id);
    }

    /* Begin SQL Functions */

    private void executeOrderSQLStatement(DatabaseOrderHandler connection, String order_id, HashMap<Integer, Integer> cart) {
        HashMap<Integer, Double> product_costs = getProductCosts(cart);
        Iterator iterate = cart.entrySet().iterator(); // Begin iteration of items in the cart for order submission.
        while (iterate.hasNext())
        {
            Map.Entry pair = (Map.Entry) iterate.next(); // Key = Product ID, Value = Quantity of Product ID
            int product_id = (int) pair.getKey();
            int quantity = (int) pair.getValue();
            connection.executeOrderInfoStatement(order_id, product_id, quantity, product_costs.get(product_id) * (double) quantity);
        }
    }

    private void executeCustomerSQLStatement(DatabaseOrderHandler connection, String order_id, String order_time, HashMap<String, String> order)  {
        connection.executeCustomerInfoStatement(order_id, order_time, order.get("firstName"), order.get("lastName"), order.get("email"), order.get("phoneNumber"), order.get("streetAddress"), order.get("zipcode"), order.get("city"), order.get("state"), order.get("shipping"), order.get("creditCard"));
    }

    /* Begin Helper Functions */
    private HashMap<String, String> organizeOrderInfo(HttpServletRequest request, Enumeration<String> parameterNames) { // Organizes request parameters for ease of use.
        HashMap<String, String> result = new HashMap<>();
        while (parameterNames.hasMoreElements())
        {
            String parameter = parameterNames.nextElement();
            result.put(parameter, request.getParameterValues(parameter)[0]);
        }
        return result;
    }

    private HashMap<Integer, Double> getProductCosts(HashMap<Integer, Integer> cart) { // Gets the costs of the products present in the cart from the database.
        HashMap<Integer, Double> product_costs = new HashMap<>();
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

    private String createProductSQLStatement(HashMap<Integer, Integer> cart) { // Creates product price retrieveal SQL statement.
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

    private String generateOrderId()
    {
        return UUID.randomUUID().toString();
    }

}
