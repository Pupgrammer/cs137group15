/*
CS137 Spring 2016 | Group 15
Main Author: Bryan Nham/Alex Lin
Filename: src/PreviouslyViewed.java
*/

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import pkg.DatabaseResultSet;

public class PreviouslyViewed extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession session = request.getSession();
        createProductView(session);
        try (PrintWriter out = response.getWriter()) {
            printProductView(out,session);
        }
    }

    private void createProductView(HttpSession session) {
        if (session.getAttribute("products") == null) {
            String[] a = {"0","0","0","0","0"};
            session.setAttribute("products", a);
        }
    }

    void printProductView(PrintWriter out, HttpSession session) {
        out.println("<p> Items previously viewed: ");
        out.println("<ul class=\"viewed\">");
        String[] viewed = (String[]) session.getAttribute("products");
        if (!(viewed[0].equals("0"))) {
            for (int i = 0; i < viewed.length; i++) {
                if ((viewed[i].equals("0"))) {
                    break;
                }
                else {
                    String pid = "SELECT image_path FROM products WHERE product_number=" + viewed[i]  + ";";
                    try {
                        DatabaseResultSet rspid = new DatabaseResultSet(pid);
                        while (rspid.getResultSet().next()) {
                            String id = rspid.getResultSet().getString(1);
                            out.println("<li class=\"viewed\">");
                            out.print("<a href=\"product?product_number=" + viewed[i] + "\">");
                            out.print("<img class=\"small\" src=\"" + id + "\"");
                            out.print("alt=\"" + id + "\"");
                            out.print("title=\"" + id + "\"/>");
                            out.print("</a>");
                            out.println("</li>");
                        }
                    }
                    catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
           }
        }
        else {
            out.println("None");
        }
        out.println("</p>");
        out.println("</ul");
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
}