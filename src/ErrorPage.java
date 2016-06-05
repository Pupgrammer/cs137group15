/*
CS137 Spring 2016 | Group 15
Main Author: Thomas Tai Nguyen
Filename: src/ErrorPage.java
*/

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ErrorPage extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        printPage((String) request.getAttribute("error_message"), response.getWriter());
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        printPage((String) request.getAttribute("error_message"), response.getWriter());
    }
    
    private void printPage(String error_message, PrintWriter out) {
        out.println("<!DOCTYPE html>");
        out.println("<html lang='en'>");
        out.println("<head>");
        out.println("<meta charset='UTF-8'>");
        out.println("<link type='text/css' rel='stylesheet' href='styles/style.css'>");
        out.println("<title>Error</title>");
        out.println("</head>");

        out.println("<body>");
        out.println("<img class='logo' src='images/logo.png' alt='Logo' title='Logo' />");
        out.println("<div class='container'>");
        out.println("<ul>");
        out.println("<li><a href='shop'>Home/Shop</a></li>");
        out.println("<li><a href=\'checkout\'>Cart/Checkout</a></li>");
        out.println("<li><a href='contactus.html'>Contact</a></li>");
        out.println("<li><a href='aboutus.html'>About Us</a></li>");
        out.println("</ul>");
        out.println("</div>");
        out.println("<h3>Error :(</h3>");
        if ((error_message != null) && (!error_message.equals("")))
        {
            out.println("<p>" + error_message + "</p>");
        }
        out.println("<h3 style='margin-left:200px;'>Click <a href='./'>here</a> to return to the homepage.</h3>");
        out.println("</body>");
        out.println("</html>");
    }
}