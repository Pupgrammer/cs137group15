/*
CS137 Spring 2016 | Group 15
Main Author: Brian Chipman / (Alex Lin & Bryan Nham)
Filename: src/ProductServlet.java
*/

import pkg.DataRow;
import pkg.DatabaseResultSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;


public class ProductServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("doGet");
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession(true);

        out.println("<!DOCTYPE html>");
        out.println("<html lang='en'>");
        out.println("<head>");
        out.println("<meta charset='UTF-8'>");
        out.println("<link type='text/css' rel='stylesheet' href='styles/style.css'>");
        out.println("<title>Product " + request.getParameter("product_number") + "</title>");
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
        out.println("<table class='info'>");

        try {
            String sql = "SELECT * FROM products WHERE product_number=" + request.getParameter("product_number")  + ";";
            DatabaseResultSet dbrs = new DatabaseResultSet(sql);

            while (dbrs.getResultSet().next()) {
                DataRow dataRow = new DataRow(dbrs.getResultSet());
                out.println("<tr class='info'>");
                out.println("<th class='info' colspan='2'>" + dataRow.get("friendly_name") + "</th>");
                out.println("</tr>");
                out.println("<tr>");
                out.println("<td class='img' colspan='2'><img src='" + dataRow.get("image_path") + "' class='thumbnail'");
                out.println("alt='" + dataRow.get("friendly_name") + "'");
                out.println("title='" + dataRow.get("friendly_name") + "'");
                out.println("</td>");
                out.println("</tr>");

                int count = 0;
                out.println("<tr class='numviewersinfo'>");
                if (count == 0) {
                    out.println("<td class='numviewersinfo' colspan='2'>Number of people viewing product feature is currently disabled.</td>");
                } else if (count == 1) {
                    out.println("<td class='numviewersinfo' colspan='2'>There is currently " + count + " person viewing this item</td>");
                } else {
                    out.println("<td class='numviewersinfo' colspan='2'>There are currently " + count + " people viewing this item</td>");
                }
                out.println("</tr>");

                out.println("<td class='info'>Model No.</td>");
                out.println("<td class='desc'>" + dataRow.get("model_number") + "</td>");
                out.println("</tr>");
                out.println("<tr class='info'>");
                out.println("<td class='info'>Manufacturer</td>");
                out.println("<td class='desc'>" + dataRow.get("manufacturer") + "</td>");
                out.println("</tr>");
                out.println("<tr class='info'>");
                out.println("<td class='info'>Price</td>");
                out.println("<td class='desc' id='getPriceFromJS'>" + dataRow.get("price") + "</td>");
                out.println("</tr>");
                out.println("<tr class='info'>");
                out.println("<td class='info'>Processor</td>");
                out.println("<td class='desc'>" + dataRow.get("processor") + "</td>");
                out.println("</tr>");
                out.println("<tr class='info'>");
                out.println("<td class='info'>Screen Size</td>");
                out.println("<td class='desc'>" + dataRow.get("screen_size") + "</td>");
                out.println("</tr>");
                out.println("<tr class='info'>");
                out.println("<td class='info'>Graphics</td>");
                out.println("<td class='desc'>" + dataRow.get("graphics") + "</td>");
                out.println("</tr>");
                out.println("<tr class='info'>");
                out.println("<td class='info'>RAM</td>");
                out.println("<td class='desc'>" + dataRow.get("ram_size") + "</td>");
                out.println("</tr>");
                out.println("<tr class='info'>");
                out.println("<td class='info'>HDD</td>");
                out.println("<td class='desc'>" + dataRow.get("hdd") + "</td>");
                out.println("</td>");
                out.println("</tr>");
                out.println("<tr class='info'>");
                out.println("<td class='info'>OS</td>");
                out.println("<td class='desc'>" + dataRow.get("operating_system") + "</td>");
                out.println("</tr>");
                out.println("</table>");
            }
            dbrs.closeDatabaseConnection();
        }

        catch (SQLException e) {
            e.printStackTrace();
        }
        out.println("<form action='checkout' method='post' style='margin-left:200px'>");
        out.println("<input name='addProductToCart" + request.getParameter("product_number") + "' type='hidden' value='" + request.getParameter("product_number") + "'>");
        out.println("<input type='submit' value='Add Product to Cart'/>");
        out.println("</form>");
        updateViewedProducts(request,session);
        out.println("</body>");
        out.println("</html>");
    }

    private void updateViewedProducts(HttpServletRequest request, HttpSession session) {
        String[] viewed = (String[]) session.getAttribute("previously_viewed_products");
        String product_id = request.getParameter("product_number");
        if (checkDuplicates(viewed,product_id).equals("1")) {
            for (int i = viewed.length-2; i >= 0; i--) {
                viewed[i+1] = viewed[i];
            }
        viewed[0] = product_id;
        session.setAttribute("previously_viewed_products",viewed);
        }
    }

    private String checkDuplicates(String[] view, String s) {
        for (String aView : view) {
            if (s.equals(aView)) {
                return "0";
            }
        }
        return "1";
    }

}

