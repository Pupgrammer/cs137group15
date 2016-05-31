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
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
        //response.setContentType("text/html");
        HashMap<String, String> order = new HashMap<String, String>();
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession();
        Enumeration<String> parameterNames = request.getParameterNames();
        
        HashMap<Integer, Integer> cart = (HashMap<Integer, Integer>) session.getAttribute("cart");
        while (parameterNames.hasMoreElements())
        {
            String parameter = parameterNames.nextElement();
            order.put(parameter, request.getParameterValues(parameter)[0]);
        }
        
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
