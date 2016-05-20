/*
CS137 Spring 2016 | Group 15
Main Author: Thomas Tai Nguyen
Filename: src/Checkout_Test.java
*/

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

public class Checkout extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // doGet is called in a standard <ahref="..> as well as method GET. Will be most likely used to retrieve current cart.
        req.setAttribute("test", req.getParameter("test")); // This will be available as ${message}
        req.getRequestDispatcher("/WEB-INF/checkout_test.jsp").forward(req, resp); // maybe temporary.
    }
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    // doPost only called explicitly in action. Will be most likely used to process a new product.
        req.setAttribute("test", req.getParameter("test")); // This will be available as ${message}
        req.getRequestDispatcher("/WEB-INF/checkout_test.jsp").forward(req, resp); // maybe temporary.
    }
}
