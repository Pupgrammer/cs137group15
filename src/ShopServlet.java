import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.crypto.Data;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.*;
import javax.servlet.http.HttpSession;

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
        out.println("<li><a class=\"active\" href=\"index.html\">Home</a></li>");
        out.println("<li><a href=\"shop\">Shop</a></li>");
        out.println("<li><a href=\"aboutus.html\">About Us</a></li>");
        out.println("<li><a href=\"contactus.html\">Contact</a></li>");
        out.println("<li><a href=\"checkout\">Cart/Checkout</a></li>");
        out.println("<li><a href=\'checkoutdebug\'>Cart/Checkout DEBUG</a></li>");
        out.println("</ul>");
        out.println("</div>");
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
        createProductView(session);
        printProductView(out, session);
       
        
        
        
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
            out.println("</body>");
            out.println("</html>");
            
           
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    void createProductView(HttpSession session) {
        if (session.getAttribute("products") == null) {
            String[] a = {"0","0","0","0","0"};
            session.setAttribute("products", a);
        }
    }
    
    void createProductViewed(HttpSession session) {
        if (session.getAttribute("products") == null) {
            String[] a = {"0","0","0","0","0"};
            session.setAttribute("products", a);
        }
    }
    
    void printProductView(PrintWriter out, HttpSession session) {
        out.println("<p> Items previously checked: ");
        String[] viewed = (String[]) session.getAttribute("products");
        if (!(viewed[0].equals("0"))) {
            out.println("<a href=\"product?product_number=" + viewed[0] + "\">" + viewed[0] + "</a>");
            for (int i = 1; i < viewed.length; i++) {
                if ((viewed[i].equals("0"))) {
                    break;
                }
                else {
                    out.println(" , " + "<a href=\"product?product_number=" + "\">" + viewed[i] + "</a>");
                    out.println("<img src=\"product?product_number=" + viewed[0] + "\"");
                }
            }
        }
        else {
            out.println("None");
        }
        out.println("</p>");
    }
    
    
}
