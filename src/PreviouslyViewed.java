/*
CS137 Spring 2016 | Group 15
Main Author: Bryan Nham/Alex Lin
Filename: src/PreviouslyViewed.java
*/

import pkg.DatabaseResultSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;


public class PreviouslyViewed extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession session = request.getSession();
        createProductView(session);
        PrintWriter out = response.getWriter();
        printProductView(out,session);
    }

    private void createProductView(HttpSession session) {
        if (session.getAttribute("previously_viewed_products") == null) {
            String[] a = {"0","0","0","0","0"};
            session.setAttribute("previously_viewed_products", a);
        }
    }

    void printProductView(PrintWriter out, HttpSession session) {
        String[] viewed = (String[]) session.getAttribute("previously_viewed_products");
        if (!(viewed[0].equals("0"))) {
            out.println("<p id='prev_viewed'> Items previously viewed: ");
            out.println("<ul class=\"viewed\">");
            for (String aViewed : viewed) {
                if ((aViewed.equals("0"))) {
                    break;
                }
                else {
                    String pid = "SELECT image_path FROM products WHERE product_number=" + aViewed + ";";
                    try {
                        DatabaseResultSet rspid = new DatabaseResultSet(pid);
                        while (rspid.getResultSet().next()) {
                            String id = rspid.getResultSet().getString(1);
                            out.println("<li class=\"viewed\">");
                            out.print("<a href=\"product?product_number=" + aViewed + "\">");
                            out.print("<img class=\"small\" src=\"" + id + "\"");
                            out.print("alt=\"" + id + "\"");
                            out.print("title=\"" + id + "\"/>");
                            out.print("</a>");
                            out.println("</li>");
                        }
                        rspid.closeDatabaseConnection();
                    }
                    catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
            out.println("</p>");
            out.println("</ul>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }
}
