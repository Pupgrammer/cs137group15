/*
CS137 Spring 2016 | Group 15
Main Author: Brian Chipman / (Alex Lin & Bryan Nham)
Filename: src/ProductServlet.java
*/

import pkg.DataRow;
import pkg.DatabaseResultSet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.HashMap;

public class ProductServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("doGet");
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession(true);
        ServletContext context = this.getServletContext();

        // Creates counter for incrementing number of sessions that viewed pid
        HashMap<Integer, Integer> counter = (HashMap<Integer, Integer>) context.getAttribute("counter");
        // Creates binary counter to check if pid has been added for current session
        HashMap<Integer, Integer> check_add = (HashMap<Integer, Integer>) session.getAttribute("check_add");

        int pid = Integer.parseInt(request.getParameter("product_number")); //Grab product number
        if (request.getRequestedSessionId() != null && !request.isRequestedSessionIdValid()) {
             // Session is expired
            for(int i = 1; i <10; i++){
                if(check_add.get(pid) == 1){ //Checks which product has been incremented and deducts
                int count = counter.containsKey(pid) ? counter.get(pid) : 0;
                counter.put(pid, (count-1));
                }
            }
        }
        if (counter == null) //Creates new context and sets to 1 for pid on hashmap if context contains no values
        {
            counter = createNewCounter(context);
        }
        if(check_add == null) // Checks if current session has been created and increments count on counter
        {
            check_add = createNewCounter(session);
            int count = counter.containsKey(pid) ? counter.get(pid) : 0; // Gets value of views for current product number
            int count_check = check_add.get(pid);
            if (count_check == 0)
            { // Checks if current product has been incremented on hashmap; 0 will allow to increment
                ++count;
                counter.put(pid, count);
                check_add.put(pid, 1); // Sets to 1 so product number will not be incremented in current session
                context.setAttribute("counter", counter);
                session.setAttribute("check_add", check_add);
            }
        }
        else
        {
            int count = counter.containsKey(pid) ? counter.get(pid) : 0; // Gets value of views for current product number
            int count_check = check_add.get(pid);
            if (count_check == 0) // Checks if product number has been incremented on hashmap; 0 will allow to increment
            {
                ++count;
                counter.put(pid, count);
                check_add.put(pid, 1); // Sets to 1 so product number will not be incremented in current session
                context.setAttribute("counter", counter);
                session.setAttribute("check_add", check_add);
            }
        }

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
                out.println("<td class='img' colspan='2'><img src='./" + dataRow.get("image_path") + "' class='thumbnail'");
                out.println("alt='" + dataRow.get("friendly_name") + "'");
                out.println("title='" + dataRow.get("friendly_name") + "'");
                out.println("width=200/></td>");
                out.println("</tr>");
                out.println("<tr class='info'>");
                out.println("<td class='info'>Number of Viewers</td>");
                int count = counter.containsKey(pid) ? counter.get(pid) : 0;
                out.println("<td class='desc'>" + count + "</td>");
                out.println("<tr class='info'>");
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
        String[] viewed = (String[]) session.getAttribute("products");
        String product_id = request.getParameter("product_number");
        if (checkDuplicates(viewed,product_id).equals("1")) {
            for (int i = viewed.length-2; i >= 0; i--) {
                viewed[i+1] = viewed[i];
            }
        viewed[0] = product_id;
        session.setAttribute("products",viewed);
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

    private HashMap<Integer, Integer> createNewCounter(ServletContext context) { // Creates a new counter map for the session, and returns it.
        HashMap<Integer, Integer> map = new HashMap<>(20);
        for (int i = 1; i < 11; i++) {
            map.put(i, 0);
        }
        context.setAttribute("counter", map);
        return map;
    }

    private HashMap<Integer, Integer> createNewCounter(HttpSession session) { // Creates a new counter map for the session, and returns it.
        HashMap<Integer, Integer> map = new HashMap<>(20);
        for (int i = 1; i < 11; i++) {
            map.put(i, 0);
        }
        session.setAttribute("check_add", map);
        return map;
    }

}

