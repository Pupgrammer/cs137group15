import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

public class ProductServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        out.println("<!DOCTYPE html>");
        out.println("<html lang=\"en\">");
        out.println("<head>");
        out.println("<meta charset=\"UTF-8\">");
        out.println("<link type=\"text/css\" rel=\"stylesheet\" href=\"styles/style.css\">");
        out.println("<title>Product " + request.getParameter("product_number") + "</title>");
        out.println("</head>");

        out.println("<body>");
        out.println("<img class=\"logo\" src=\"images/logo.png\" alt=\"Logo\"/>");
        out.println("<div class=\"container\">");
        out.println("<ul>");
        out.println("<li><a class=\"active\" href=\"index.html\">Home</a></li>");
        out.println("<li><a href=\"shop.php\">Shop</a></li>");
        out.println("<li><a href=\"aboutus.html\">About Us</a></li>");
        out.println("<li><a href=\"contactus.html\">Contact</a></li>");
        out.println("<li><a href=\'checkoutdebug\'>Cart/Checkout DEBUG</a></li>");
        out.println("</ul>");
        out.println("</div>");
        out.println("<table class=\"info\">");

        try {
            String sql = "SELECT * FROM products WHERE product_number=" + request.getParameter("product_number")  + ";";
            DatabaseResultSet dbrs = new DatabaseResultSet(sql);

            while (dbrs.getResultSet().next()) {
                DataRow dataRow = new DataRow(dbrs.getResultSet());
                out.println("<tr class=\"info\">");
                out.println("<th class=\"info\" colspan=\"2\">" + dataRow.get("friendly_name") + "</th>");
                out.println("</tr>");
                out.println("<tr>");
                out.println("<td class=\"img\" colspan=\"2\"><img src=\"../" + dataRow.get("image_path") + "\" class=\"thumbnail\"");
                out.println("alt=\"" + dataRow.get("friendly_name") + "\"");
                out.println("title=\"" + dataRow.get("friendly_name") + "\"");
                out.println("width=200/></td>");
                out.println("</tr>");
                out.println("<tr class=\"info\">");
                out.println("<td class=\"info\">Model No.</td>");
                out.println("<td class=\"desc\">" + dataRow.get("model_number") + "</td>");
                out.println("</tr>");
                out.println("<tr class=\"info\">");
                out.println("<td class=\"info\">Manufacturer</td>");
                out.println("<td class=\"desc\">" + dataRow.get("manufacturer") + "</td>");
                out.println("</tr>");
                out.println("<tr class=\"info\">");
                out.println("<td class=\"info\">Price</td>");
                out.println("<td class=\"desc\" id=\"getPriceFromJS\">" + dataRow.get("price") + "</td>");
                out.println("</tr>");
                out.println("<tr class=\"info\">");
                out.println("<td class=\"info\">Processor</td>");
                out.println("<td class=\"desc\">" + dataRow.get("processor") + "</td>");
                out.println("</tr>");
                out.println("<tr class=\"info\">");
                out.println("<td class=\"info\">Screen Size</td>");
                out.println("<td class=\"desc\">" + dataRow.get("screen_size") + "</td>");
                out.println("</tr>");
                out.println("<tr class=\"info\">");
                out.println("<td class=\"info\">Graphics</td>");
                out.println("<td class=\"desc\">" + dataRow.get("graphics") + "</td>");
                out.println("</tr>");
                out.println("<tr class=\"info\">");
                out.println("<td class=\"info\">RAM</td>");
                out.println("<td class=\"desc\">" + dataRow.get("ram_size") + "</td>");
                out.println("</tr>");
                out.println("<tr class=\"info\">");
                out.println("<td class=\"info\">HDD</td>");
                out.println("<td class=\"desc\">" + dataRow.get("hdd") + "</td>");
                out.println("</td>");
                out.println("</tr>");
                out.println("<tr class=\"info\">");
                out.println("<td class=\"info\">OS</td>");
                out.println("<td class=\"desc\">" + dataRow.get("operating_system") + "</td>");
                out.println("</tr>");
            }
        }

        catch (SQLException e) {
            e.printStackTrace();
        }

        out.println("</body>");
        out.println("</html>");

    }

}
