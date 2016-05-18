import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ServletTest extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();
        String title = "ServletTest";
        out.println("<!doctype html public \"-//w3c//dtd html 4.0 " + "transitional//en\">\n");
        out.println("<html>\n" + "<head><title>" + title + "</title></head>\n" + "<body bgcolor=\"#f0f0f0\">\n");
        out.println("<h2><a href=\"../\"><-- Back</a></h2>");
        out.println("<h1>" + title + "</h1>\n");
        out.println("<h3>Timestamp: " + new SimpleDateFormat("yyyy-MM-dd--HH-mm-ss").format(Calendar.getInstance().getTime()) + "</h3>");
        out.println("<h4> Testing 1 2 3...</h4>");
        out.println("</body></html>");
    }
}
