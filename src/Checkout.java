/*
CS137 Spring 2016 | Group 15
Main Author: Thomas Tai Nguyen
Filename: src/Checkout.java
*/

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Enumeration;

// Note: DB code created by Brian Chipman and reused here.

// I'm getting a lot of OutOfMemoryErrors after running this for a while. Do I need to explicitly empty the heap? Hopefully the session API will help me out here,
// because I'm creating a lot of new ArrayLists since I don't have a static one stored with a session... - Thomas

public class Checkout extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
        // doGet is called in a standard <ahref='..> as well as method GET. Will be most likely used to retrieve current cart.
        
        /* The following code will be replaced with session code once implemented. */
        ArrayList productList = new ArrayList<DataRow>();
        productList.add(1);
        productList.add(2);
        
        // Get the information for the products.
        ArrayList<DataRow> prettyProductList = getProductInfo(productList);
        PrintWriter out = response.getWriter();
        printPage(out, prettyProductList, "");
        prettyProductList = null;
        // Send the products to JSP for printing.
        //request.setAttribute("products", prettyProductList); // This will be available as ${message}
        //request.getRequestDispatcher("/WEB-INF/checkout_test.jsp").forward(request, response); // maybe temporary.
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
        // doPost only called explicitly in action. Will be most likely used to process a new product.
        
        PrintWriter out = response.getWriter();
        Enumeration parameters = (Enumeration) request.getParameterNames();
        ArrayList<String> parameterList = new ArrayList<String>();
        
        // Note to self: Might change this entire program control flow to having just a hidden value for submission to treat it like others. Currently temporary.
        while(parameters.hasMoreElements()) 
        {
            parameterList.add((String) parameters.nextElement());
        }
        if (parameterList.size() == 1)
        {
            String current = parameterList.get(0);
            String action = parameterList.get(0).substring(0, current.length()-1);
            if (action.equals("updateProductQuantity"))
            {
                // update the new quantity in the session. Will be moved to a helper function later
                // edge case: might somehow be called when product doesn't exist.
                
                /* The following code will be replaced with session code once implemented. */
                ArrayList productList = new ArrayList<DataRow>();
                productList.add(1);
                productList.add(2);
                ArrayList<DataRow> prettyProductList = getProductInfo(productList);
                
                /* Print the page. */
                printPage(out, prettyProductList, "Notice: Quantity update initiated for product ID " + current.substring(current.length() - 1) + ". New quantity is " + request.getParameter(current) + ". Note that this isn't implemented yet. ");
                prettyProductList = null;
            }
            else if (action.equals("addNewProduct"))
            {
                // add a new product to the cart. question: quantity allowed or no?
                // edge case: what happens if something is already in the cart? add to current quantity, or cause error message?
                
                /* The following code will be replaced with session code once implemented. */
                ArrayList productList = new ArrayList<DataRow>();
                productList.add(1);
                productList.add(2);
                ArrayList<DataRow> prettyProductList = getProductInfo(productList);
                
                /* Print the page. */
                printPage(out, prettyProductList, "Notice: Add product initiated for product ID " + current.substring(current.length() - 1));
                prettyProductList = null;
            }
            else if (action.equals("removeCurrentProduct"))
            {
                // remove a current product from the cart. Will be moved to a helper function later
                // edge case: might somehow be called when product doesn't exist.
                
                /* The following code will be replaced with session code once implemented. */
                ArrayList productList = new ArrayList<DataRow>();
                productList.add(1);
                productList.add(2);
                ArrayList<DataRow> prettyProductList = getProductInfo(productList);
                
                /* Print the page. */
                printPage(out, prettyProductList, "Notice: Remove product initiated for product ID " + current.substring(current.length() - 1));
                prettyProductList = null;
            }
        }
        else
        {
            // order submission handling. Will be moved to a helper function later
        }
        parameterList = null;
    }
    
    /* Begin Helper Functions */ 
    ArrayList<DataRow> getProductInfo(ArrayList productList)
    { // Main function that is called to retrieve product information.
        if (productList.isEmpty())
        {
            return null;
        }
        else
        {
            ArrayList<DataRow> result = retrieveProductsFromDB(createProductSQLStatement(productList));
            return result;
        }
    }
    
    String createProductSQLStatement(ArrayList<DataRow> productList)
    { // Assumes that productList has at least one element.
        String sql = "SELECT * from products WHERE product_number = " + productList.get(0);
        for (int i = 1; i != productList.size(); i++)
        {
            sql = sql + " OR product_number = " + productList.get(i);
        }
        return sql;
    }
    
    ArrayList<DataRow> retrieveProductsFromDB(String sql)
    { // Retrieves product information from DB and stores it into a ArrayList<DataRow>
       ArrayList<DataRow> result = new ArrayList<DataRow>();
       try {
            final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
            final String SERVER_NAME = ConnectionInfo.SERVER_NAME;
            final String DB_NAME = ConnectionInfo.DATABASE_NAME;

            //  Database credentials
            final String USER = ConnectionInfo.USER_NAME;
            final String PASS = ConnectionInfo.PASSWORD;

            Statement statement = null;
            Connection connection = null;
            
            // Register JDBC driver
            Class.forName(JDBC_DRIVER);

            // Open a connection
            connection = DriverManager.getConnection(SERVER_NAME + DB_NAME, USER, PASS);
            statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);

            while (rs.next()) 
            {
                DataRow dataRow = new DataRow(rs);
                result.add(dataRow);
            }
        }
        catch (SQLException | ClassNotFoundException e) 
        {
            e.printStackTrace();
        }
        return result;
    }
    
    double roundDecimalPlaces(double number)
    {
        double result = Math.round(number * 100);
        result = result/100;
        return result;
    }
    
    void printPage(PrintWriter out, ArrayList<DataRow> prettyProductList, String notice)
    {
        double subtotal = 0.00;
        out.println("<!DOCTYPE html>");
        out.println("<html lang=\'en\'>");
        out.println("<head>");
        out.println("<meta charset=\'UTF-8\'>");
        out.println("<link type=\'text/css\' rel=\'stylesheet\' href=\'styles/style.css\'>");
        out.println("<script type=\"text/javascript\" src=\"./scripts/validate_orderForm.js\"></script>");
        out.println("<script type=\"text/javascript\" src=\"./scripts/ajax_cityState.js\"></script>");
        out.println("<script type=\"text/javascript\" src=\"./scripts/ajax_zipSuggestions.js\"></script>");
        out.println("<script type=\"text/javascript\" src=\"./scripts/calculatePrices.js\"></script>");
        out.println("<title>Cart/Checkout</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<img class=\'logo\' src=\'images/logo.png\' alt=\'Logo\'/>");
        out.println("<div class=\'container\'>");
        out.println("<ul>");
        out.println("<li><a class=\'active\' href=\'index.html\'>Home</a></li>");
        out.println("<li><a href=\'shop\'>Shop</a></li>");
        out.println("<li><a href=\'aboutus.html\'>About Us</a></li>");
        out.println("<li><a href=\'contactus.html\'>Contact</a></li>");
        out.println("<li><a href=\'checkout\'>Cart/Checkout</a></li>");
        out.println("</ul>");
        out.println("</div>");
        
        if (prettyProductList == null || prettyProductList.isEmpty())
        {
            out.println("<p id=\"notice\">The cart is currently empty.</p>");
        }
        else
        {
            out.println("<p id=\"notice\">" + notice + "</p>");
            out.println("<table>");
            out.println("<tr>");
            out.println("<th>Product ID</th>");
            out.println("<th>Name</th>");
            out.println("<th>Image</th>");
            out.println("<th>Price</th>");
            out.println("<th>Quantity</th>");
            out.println("</tr>");

            /* Print Product Information Here */
            for (int i = 0; i != prettyProductList.size(); i++)
            {
                DataRow current = prettyProductList.get(i);
                out.println("<tr>");
                out.println("<td>" + current.get("product_number") + "</td>");
                out.println("<td>" + current.get("friendly_name") + "</td>");
                out.println("<td>" + "<img src=\"" + current.get("image_path") + "\"" + "/>" + "</td>");
                out.println("<td>" + current.get("price") + "</td>");
                // Handle Quantity.
                out.println("<td>");
                out.println("<form action=\"checkout\" method=\"post\">");
                out.println("<input name=\"updateProductQuantity" + current.get("product_number") + "\" type=\"number\" value=\"1\"/>");
                out.println("<input class=\"updateProductQuantity\" type=\"submit\" value=\"Update Quantity\"/>");
                out.println("</form><br>");
                out.println("<form action=\"checkout\" method=\"post\">");
                out.println("<input name=\"removeCurrentProduct" + current.get("product_number") + "\" type=\"hidden\" value=\"" + current.get("product_number") + "\"/>");
                out.println("<input class=\"removeCurrentProduct" + current.get("product_number") + "\" type=\"submit\" value=\"Remove Product\"/>");
                out.println("</form>");
                out.println("</td>");
                // End Handle Quantity
                // While we're here, we might as well get the subtotal for use later.
                subtotal = subtotal + (Double.parseDouble("1") * Double.parseDouble(current.getRaw("price")));
                out.println("</tr>");
            }
            out.println("</table>");
        
            out.println("<br><br>");

            out.println("<table class='cost'>");
            out.println("<tr>");
            // Handle Subtotal.
            out.println("<td class='cost'>Subtotal</td>");
            out.println("<td class='cost'>$<span id='subtotalCost'>" + roundDecimalPlaces(subtotal) + "</span></td>"); 
            // End Handle Subtotal.
            out.println("</tr>");
            out.println("<tr style='border-bottom: 2px solid black'>");
            out.println("<td class='cost'>Shipping Cost</td>");
            out.println("<td class='cost'>$<span id='shippingCost'>0.00</span></td>"); 
            out.println("<tr>");                                                            
            out.println("<tr>");
            // Handle Current Total Cost
            out.println("<td class='cost'>Total Cost</td>");
            out.println("<td class='cost'>$<span id='totalCost'>" + roundDecimalPlaces(subtotal) + "</span></td>"); // Reason for this: Might have a session-stored shipping method
            // End Handle Current Total Cost
            out.println("</tr>");
            out.println("</table>");

            out.println("<form action='' class='orderForm' name='orderForm' onSubmit='return (validate())' method='POST'>");
            out.println("<br><br>");
            out.println("<br>First Name:<br>");
            out.println("<input type='text' name='firstName'/>");
            out.println("<br>Last Name:<br>");
            out.println("<input type='text' name='lastName'/>");
            out.println("<br>E-mail (x@y.z):<br>");
            out.println("<input type='email' name='email'/>");
            out.println("<br>Phone Number (xxx-xxx-xxxx):<br>");
            out.println("<input type='tel' name='phoneNumber'/>");

            out.println("<br><br>");


            out.println("<br>Street Address:<br>");
            out.println("<input type='text' name='streetAddress'/>");


            out.println("<br>Zipcode (5 digits):<br>");
            out.println("<input type='text' onblur='getCityState(this.value)' onkeyup='getZipSuggestions(this.value)' id='zipcode' name='zipcode'/><br>");
            out.println("<span id='suggestions' style='border:0px'></span>");

            out.println("<br>City:<br>");
            out.println("<input type='text' name='city' id='city'/>");
            out.println("<br>State:<br>");
            out.println("<input type='text' name='state' id='state'/>");

            out.println("<br>Shipping Method:<br>");
            out.println("<select name='shipping' onChange='updateCosts()'>");
            out.println("<option value='One Day'>($10.00) One-Day Overnight Shipping</option>");
            out.println("<option value='Two Day'>($5.00) Two-Day Expedited Shipping</option>");
            out.println("<option value='Ground' selected='selected'>FREE Standard Ground Shipping (5-7 days)</option>");
            out.println("</select>");
            out.println("<br><br>");
            out.println("<br>Credit Card Number (16 digits):<br>");
            out.println("<input name='creditCard'/>");
            out.println("<br><br>");
            out.println("<br>");
            out.println("<input type='submit' value='Submit Order'/>");
            out.println("<br>");
            out.println("</form>");
        }
        out.println("</body>");
        out.println("</html>");
    }
}
