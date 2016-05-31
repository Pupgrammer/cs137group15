/*
CS137 Spring 2016 | Group 15
Main Author: Thomas Tai Nguyen
Filename: src/SubmitOrder.java
*/

import pkg.DatabaseResultSet;

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
import javax.servlet.http.HttpSession;
import java.sql.*;

public class SubmitOrder extends HttpServlet
{
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession();
        HashMap<String, String> order = organizeOrderInfo(request, request.getParameterNames());
        HashMap<Integer, Integer> cart = (HashMap<Integer, Integer>) request.getSession().getAttribute("cart");


        printDebugInformation(out, order, cart);
        executeOrderSQLStatement(session.getId(), order, cart);
    }

    HashMap<String, String> organizeOrderInfo(HttpServletRequest request, Enumeration<String> parameterNames) {
        HashMap<String, String> result = new HashMap<String, String>();
        while (parameterNames.hasMoreElements())
        {
            String parameter = parameterNames.nextElement();
            result.put(parameter, request.getParameterValues(parameter)[0]);
        }
        return result;
    }

    private void executeOrderSQLStatement(String session_id, HashMap<String, String> order, HashMap<Integer, Integer> cart)
    { // Assumes that cart has at least one element and order is not empty.
        HashMap<Integer, Double> product_costs = getProductCosts(cart);
        DatabaseOrderHandler connection = new DatabaseOrderHandler();
        Iterator iterate = cart.entrySet().iterator();
        while (iterate.hasNext())
        {
            Map.Entry pair = (Map.Entry) iterate.next(); // Key = Product ID, Value = Quantity of Product ID
            connection.executeOrderInfoStatement(session_id, (int) pair.getKey(), (int) pair.getValue(), product_costs.get(pair.getKey()) * (double) pair.getValue());
        }

    }

    private HashMap<Integer, Double> getProductCosts(HashMap<Integer, Integer> cart)
    {
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

    String createProductSQLStatement(HashMap<Integer, Integer> cart) {
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
    void printDebugInformation(PrintWriter out,  HashMap<String, String> order, HashMap<Integer, Integer> cart)
    {
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
