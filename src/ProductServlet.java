import pkg.DatabaseResultSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.HashMap;
import javax.servlet.http.HttpSession;

public class ProductServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession(true);

        HashMap<Integer, Integer> counter = (HashMap<Integer, Integer>) session.getAttribute("counter");

            if (counter == null)
            {
                counter = createNewCounter(session);
                out.println("Session counter for" + session.getId() + "was empty so created with empty values");
                out.println("adding count to " + request.getParameter("product_number"));
                int p = Integer.parseInt(request.getParameter("product_number"));
                int count = counter.containsKey(p) ? counter.get(p) : 0;
                counter.put(p, count + 1);
                out.println("counter for this is :" + count);
            }
            else
            {
                out.println(" continue adding count to " + request.getParameter("product_number"));
                int p = Integer.parseInt(request.getParameter("product_number"));
                int count = counter.containsKey(p) ? counter.get(p) : 0;
                counter.put(p, count + 1);
                out.println("counter for this is :" + count);
            }

        printProductView(out, session);

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
        out.println("<li><a href=\"shop\">Shop</a></li>");
        out.println("<li><a href=\"aboutus.html\">About Us</a></li>");
        out.println("<li><a href=\"contactus.html\">Contact</a></li>");
        out.println("<li><a href=\'checkout\'>Cart/Checkout</a></li>");
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
                out.println("<td class=\"img\" colspan=\"2\"><img src=\"./" + dataRow.get("image_path") + "\" class=\"thumbnail\"");
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

                updateViewedProducts(request,session);


                out.println("<form action=\"checkoutdebug\" method=\"post\">");
                out.println("<input name=\"addProductToCart\" type=\"hidden\" value=\""+request.getParameter("product_number")+"\">");
                out.println("<input type=\"submit\" value=\"addProductToCart\"/>");
                out.println("</form>");


            }
        }

        catch (SQLException e) {
            e.printStackTrace();
        }

        out.println("</body>");
        out.println("</html>");

    }

    private void updateViewedProducts(HttpServletRequest request, HttpSession session) {
        String[] viewed = (String[]) session.getAttribute("products");
        for (int i = viewed.length-2; i >= 0; i--) {
            viewed[i+1] = viewed[i];
        }
        viewed[0] = request.getParameter("product_number");
        session.setAttribute("products",viewed);
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
                  //  out.println(" , " + "<a href=\"product?product_number=" + "\">" + viewed[i] + "</a>");
                 //  out.println("<img src=\"product?product_number=" + viewed[i] + "\"");
                }
           }
        }
        else {
            out.println("None");
        }
        out.println("</p>");


    }

        HashMap<Integer, Integer> createNewCounter(HttpSession session) { // Creates a new counter map for the session, and returns it.
        HashMap<Integer, Integer> map = new HashMap<Integer, Integer>(20);
        for (int i = 1; i < 10; i++){
            map.put(i, 0);
        }
        session.setAttribute("counter", map);
        return map;
    }

}
