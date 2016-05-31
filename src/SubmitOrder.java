/*
CS137 Spring 2016 | Group 15
Main Author: Thomas Tai Nguyen
Filename: src/SubmitOrder.java
*/

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Enumeration;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SubmitOrder extends HttpServlet 
{
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException { 
        PrintWriter out = response.getWriter();
        HashMap<String, String> order = organizeOrderInfo(request, request.getParameterNames());
        HashMap<Integer, Integer> cart = (HashMap<Integer, Integer>) request.getSession().getAttribute("cart");

        printDebugInformation(out, order, cart);
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
    
    String createOrderSQLStatement(HashMap<Integer, Integer> cart) { // Assumes that cart has at least one element and order is not empty.
        String sql = "INSERT INTO tables VALUES from products WHERE product_number = ";
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
