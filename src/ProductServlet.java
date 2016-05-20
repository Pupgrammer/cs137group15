import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Statement;

public class ProductServlet extends HttpServlet {

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
        out.println("<script type=\"text/javascript\" src=\"../scripts/validate_orderForm.js\"></script>");
        out.println("<script type=\"text/javascript\" src=\"../scripts/ajax_cityState.js\"></script>");
        out.println("<script type=\"text/javascript\" src=\"../scripts/ajax_zipSuggestions.js\"></script>");
        out.println("<script type=\"text/javascript\" src=\"../scripts/calculatePrices.js\"></script>");
        out.println("<title>?????</title>");
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
        //out.println("<table class=\"info\">");
        //out.println("<tr class=\"info\">");

        out.println("</body>");
        out.println("</html>");






    }


}
