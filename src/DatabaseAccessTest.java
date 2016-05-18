import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DatabaseAccessTest extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // JDBC driver name and database URL
        final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
        final String SERVER_NAME = ConnectionInfo.SERVER_NAME;
        final String DB_NAME = ConnectionInfo.DATABASE_NAME;

        //  Database credentials
        final String USER = ConnectionInfo.USER_NAME;
        final String PASS = ConnectionInfo.PASSWORD;

        Statement statement = null;
        Connection connection = null;

        // Set response content type
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        String title = "DatabaseAccessTest";

        out.println("<!doctype html public \"-//w3c//dtd html 4.0 " + "transitional//en\">\n");
        out.println("<html>\n" + "<head><title>" + title + "</title></head>\n" + "<body bgcolor=\"#f0f0f0\">\n");
        out.println("<h2><a href=\"../\"><-- Back</a></h2>");
        out.println("<h1>" + title + "</h1>\n");
        out.println("<h3>Timestamp: " + new SimpleDateFormat("yyyy-MM-dd--HH-mm-ss").format(Calendar.getInstance().getTime()) + "</h3>");

        try {
            // Register JDBC driver
            Class.forName(JDBC_DRIVER);

            // Open a connection
            connection = DriverManager.getConnection(SERVER_NAME + DB_NAME, USER, PASS);

            // Execute SQL query
            statement = connection.createStatement();
            String sql = "SELECT * from products";
            ResultSet rs = statement.executeQuery(sql);

            // Extract data from result set
            while (rs.next()) {
                //Retrieve by column name
                int product_number = rs.getInt("product_number");
                String model_name = rs.getString("model_name");

                //Display values
                out.print(String.format("product_number: %02d, &nbsp&nbsp model_name: %s!!<br>", product_number, model_name));
            }
            out.println("</body></html>");

            // Clean-up environment
            rs.close();
            statement.close();
            connection.close();
        }
        catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        }
        catch (Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        }
        finally {
            //finally block used to close resources
            try {
                if (statement != null) statement.close();
            }
            catch (SQLException se2) {
            }// nothing we can do
            try {
                if (connection != null) connection.close();
            }
            catch (SQLException se) {
                se.printStackTrace();
            }//end finally try
        } //end try
    }
}
