import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.*;

public class ShopServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // JDBC driver name and database URL
        final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
        final String SERVER_NAME = ConnectionInfo.SERVER_NAME;
        final String DB_NAME = ConnectionInfo.DATABASE_NAME;

        //  Database credentials
        final String USER = ConnectionInfo.USER_NAME;
        final String PASS = ConnectionInfo.PASSWORD;

        Statement statement = null;
        Connection connection = null;

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
        out.println("<li><a href=\"shop.php\">Shop</a></li>");
        out.println("<li><a href=\"aboutus.html\">About Us</a></li>");
        out.println("<li><a href=\"contactus.html\">Contact</a></li>");
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

        try {
            // Register JDBC driver
            Class.forName(JDBC_DRIVER);

            // Open a connection
            connection = DriverManager.getConnection(SERVER_NAME + DB_NAME, USER, PASS);

            // Execute SQL query
            statement = connection.createStatement();
            String sql = "SELECT * from products";
            ResultSet rs = statement.executeQuery(sql);

            while (rs.next()) {
                Map<String, String> map = prettifyData(rs);
                out.println("<tr class=\"info\">");
                out.println("<td>" + map.get("product_number") + "</td>");
                out.println("<td>" + map.get("model_name") + "</td>");
                out.println("<td>" + map.get("model_number") + "</td>");
                out.println("<td>" + map.get("manufacturer") + "</td>");
                out.println("<td>" + map.get("price") + "</td>");
                out.println("</tr>");
            }

            out.println("</table>");
            out.println("</body>");
            out.println("</html>");
        }
        catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    private Map<String, String> prettifyData(ResultSet rs) throws SQLException {
        List<String> strings = Arrays.asList("model_name", "image_path", "model_number", "manufacturer", "processor", "graphics", "hdd_type", "operating_system");
        List<String> doubles = Arrays.asList("price", "screen_size", "ram_size_gb", "hdd_size_gb");
        List<String> ints = Arrays.asList("product_number");

        Map<String, String> map = new HashMap<>();
        for (String str : strings) {
            map.put(str, rs.getString(str));
        }
        for (String str : doubles) {
            Double d = rs.getDouble(str);
            map.put(str, d.toString());
        }
        for (String str : ints) {
            Integer i = rs.getInt(str);
            map.put(str, i.toString());
        }
        return map;
    }
}
