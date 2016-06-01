import pkg.DataRow;
import pkg.DatabaseResultSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import javax.servlet.http.HttpSession;
import javax.servlet.RequestDispatcher;


public class ShopServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        out.println("<!DOCTYPE html>");
        out.println("<html lang=\"en\">");
        out.println("<head>");
        out.println("<meta charset=\"UTF-8\">");
        out.println("<link type=\"text/css\" rel=\"stylesheet\" href=\"styles/style.css\">");
        out.println("<title>Shop</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<img class=\"logo\" src=\"images/logo.png\" alt=\"Logo\"/>");
        out.println("<div class=\"container\">");
        out.println("<ul>");
        out.println("<li><a href=\"shop\">Home/Shop</a></li>");
        out.println("<li><a href=\"checkout\">Cart/Checkout</a></li>");
        out.println("<li><a href=\"contactus.html\">Contact</a></li>");
        out.println("<li><a href=\"aboutus.html\">About Us</a></li>");
        out.println("</ul>");
        out.println("</div>");
        out.println("<h1> Welcome to Malekware Laptops! </h1>");
        out.println("<p>Now open for sale! Please feel free to contact us regarding available laptops or bulk orders. Orders ship with FREE SHIPPING the next business day through USPS ground. One/two day priority shipping is available at an additional cost.</p>");
        out.println("<p>Interested in a product? Click on the product image for more details and to order it!</p>");
        out.println("<table>");
        out.println("<tr>");
        out.println("<th>#</th>");
        out.println("<th>Name</th>");
        out.println("<th>Image</th>");
        out.println("<th>Model No.</th>");
        out.println("<th>Manufacturer</th>");
        out.println("<th>Price</th>");
        out.println("</tr>");

        HttpSession session = request.getSession(true);

        try {
            String sql = "SELECT * from products";
            DatabaseResultSet dbrs = new DatabaseResultSet(sql);

            while (dbrs.getResultSet().next()) {
                DataRow dataRow = new DataRow(dbrs.getResultSet());
                out.println("<tr class=\"info\">");
                out.println("<td>" + dataRow.get("product_number") + "</td>");
                out.println("<td>" + dataRow.get("model_name") + "</td>");

                out.println("<td class=\"img\">" );
                out.println("<a href=\"product?product_number=" + dataRow.get("product_number") + "\">");
                out.println("<img src=\"" + dataRow.get("image_path") + "\"");
                out.println("alt=\"" + dataRow.get("friendly_name") + "\"");
                out.println("title=\"" + dataRow.get("friendly_name") + "\"/>");
                out.println("</a>");
                out.println("</td>");
                out.println("<td>" + dataRow.get("model_number") + "</td>");
                out.println("<td>" + dataRow.get("manufacturer") + "</td>");
                out.println("<td>" + dataRow.get("price") + "</td>");
                out.println("</tr>");
            }

            out.println("</table>");
            showPreviouslyViewed(request,response);
            out.println("</body>");
            out.println("</html>");


        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void showPreviouslyViewed (HttpServletRequest request, HttpServletResponse response) {
        try {
            RequestDispatcher rd = request.getRequestDispatcher("PreviouslyViewed");
            rd.include(request, response);
        }
        catch (ServletException | IOException i){}
    }
}
