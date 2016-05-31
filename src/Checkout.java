/*
CS137 Spring 2016 | Group 15
Main Author: Thomas Tai Nguyen
Filename: src/Checkout.java
*/

import pkg.DatabaseResultSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

public class Checkout extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        if (request.getParameter("event") != null) {
            if (request.getParameter("event").equals("onblur")) {
                getCityStateFromDB(request, response);
            }
            else if (request.getParameter("event").equals("onkeyup")) {
                getZipSuggestionsFromDB(request, response);
            }
        }
        else
        {
            HttpSession session = request.getSession();
            PrintWriter out = response.getWriter();
            HashMap<Integer, Integer> cart = (HashMap<Integer, Integer>) session.getAttribute("cart");

            if (cart == null)
            {
                cart = createNewCart(session);
                printPage(out, cart, "[DEBUG MESSAGE] Session cart for session " + session.getId() + " was empty, so a cart map was created with empty values."); // Cart is empty.
            }
            else
            {
                printPage(out, cart, "");
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession();
        Map<String, String[]> parameters = request.getParameterMap();
        HashMap<Integer, Integer> cart = (HashMap<Integer, Integer>) session.getAttribute("cart");

        if(request.getParameterMap().isEmpty())
        {
            printPage(out, cart, "");
        }
        else
        { // Do I need to re-setAttribute after modifying cart? Probably not but this might be a bug later.
            for (String parameter : parameters.keySet())
            {
                if (parameter.startsWith("updateProductQuantity"))
                {
                    if (cart == null)
                    {
                        cart = createNewCart(session);
                        printPage(out, cart, "The product was not found in your shopping cart, which was empty. Quantity update failed.");
                    }
                    else
                    {
                        Integer product_id = Integer.parseInt(parameter.substring(parameter.length() - 1));
                        Integer quantity = cart.get(product_id);

                        if (quantity != null)
                        {
                            cart.put(product_id, Integer.parseInt(request.getParameter(parameter)));
                            printPage(out, cart, "The quantity for Product ID " + product_id + " was successfully updated.");
                        }
                        else
                        {
                            printPage(out, cart, "The Product ID " + product_id + " was not found in your shopping cart. Quantity update stopped.");
                        }
                    }
                }
                else if (parameter.startsWith("removeProduct"))
                {
                    Integer product_id = Integer.parseInt(parameter.substring(parameter.length() - 1));
                    if (cart == null)
                    {
                        cart = createNewCart(session);
                        printPage(out, cart, "Product ID " + product_id + " was not found in your shopping cart, which was empty. Removal stopped.");
                    }
                    else
                    {
                        if (cart.containsKey(product_id) == true)
                        {
                            cart.remove(product_id);
                            printPage(out, cart, "Product ID " + product_id + " was successfully removed from your cart.");
                        }
                        else
                        {
                            printPage(out, cart, "Product ID " + product_id + " was not found in your shopping cart. Removal stopped.");
                        }
                    }
                }
            }
        }
    }

    /* Begin Helper Functions */
    HashMap<Integer, Integer> createNewCart(HttpSession session) { // Creates a new cart map for the session, and returns it.
        HashMap<Integer, Integer> map = new HashMap<Integer, Integer>(20);
        session.setAttribute("cart", map);
        return map;
    }

    String createProductSQLStatement(HashMap<Integer, Integer> cart) { // Assumes that productList has at least one element.
        int counter = 0;
        String sql = "SELECT * from products WHERE product_number = ";
        for (Integer product_id : cart.keySet())
        {
            if (counter == 0)
            {
                sql = sql + product_id;
                counter = counter + 1;
            }
            else
            {
                sql = sql + " OR product_number = " + product_id;
            }
        }
        return sql;
    }

    ArrayList<DataRow> retrieveProductsFromDB(String sql) { // Retrieves product information from DB and stores it into a ArrayList<DataRow>
        ArrayList<DataRow> result = new ArrayList<DataRow>();
        try {
            DatabaseResultSet dbrs = new DatabaseResultSet(sql);
            while (dbrs.getResultSet().next()) {
                DataRow dataRow = new DataRow(dbrs.getResultSet());
                result.add(dataRow);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    double roundDecimalPlaces(double number) { // Rounds doubles for presentation.
        double result = Math.round(number * 100);
        result = result / 100;
        return result;
    }

   double printCart(PrintWriter out, HashMap<Integer, Integer> cart) { // Returns subtotal of all products.
        double subtotal = 0.00;
        ArrayList<DataRow> products = retrieveProductsFromDB(createProductSQLStatement(cart));
        for (int i = 0; i != products.size(); i++)
        {
            DataRow current = products.get(i);
            Integer product_id = Integer.parseInt(current.get("product_number"));

            out.println("<tr>");
            out.println("<td>" + product_id + "</td>");
            out.println("<td>" + current.get("friendly_name") + "</td>");
            out.println("<td>" + "<img src=\"" + current.get("image_path") + "\"" + "/>" + "</td>");
            out.println("<td>" + current.get("price") + "</td>");
            out.println("<td>" + cart.get(product_id) + "</td>");
            // Handle Action.
            out.println("<td>");
            out.println("<form action=\"checkout\" method=\"post\">");
            out.println("<input name=\"updateProductQuantity" + product_id + "\" id=\"updateProductQuantity" + product_id + "\" type=\"number\" onblur=\"validate_cartQuantity(this)\" onchange=\"validate_cartQuantity(this)\" value=\""+ cart.get(product_id) +"\"/>");
            out.println("<input class=\"updateProductQuantity\" type=\"submit\" value=\"Update Quantity\"/>");
            out.println("</form><br>");
            out.println("<form action=\"checkout\" method=\"post\">");
            out.println("<input name=\"removeProduct" + product_id + "\" type=\"hidden\" value=\"" + current.get("product_number") + "\"/>");
            out.println("<input class=\"removeProduct" + product_id + "\" type=\"submit\" value=\"Remove Product\"/>");
            out.println("</form>");
            out.println("</td>");
            // End Handle Action
            // While we're here, we might as well get the subtotal for use later.
            subtotal = subtotal + ( ((double) cart.get(product_id)) * Double.parseDouble(current.get("raw_price")));
            out.println("</tr>");
        }
        return subtotal;
    }

    void printPage(PrintWriter out, HashMap<Integer, Integer> cart, String notice) { // Prints HTML page.
        out.println("<!DOCTYPE html>");
        out.println("<html lang=\'en\'>");
        out.println("<head>");
        out.println("<meta charset=\'UTF-8\'>");
        out.println("<link type=\'text/css\' rel=\'stylesheet\' href=\'styles/style.css\'>");
        out.println("<script type=\"text/javascript\" src=\"scripts/validate_orderForm.js\" defer></script>");
        out.println("<script type=\"text/javascript\" src=\"scripts/ajax_zipcode.js\" defer></script>");
        out.println("<script type=\"text/javascript\" src=\"scripts/ajax_zipSuggestions.js\" defer></script>");
        out.println("<script type=\"text/javascript\" src=\"scripts/calculatePrices.js\" defer></script>");
        out.println("<script type=\"text/javascript\" src=\"scripts/validate_cartQuantity.js\" defer></script>");
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
        out.println("<li><a href=\'checkoutdebug\'>Cart/Checkout DEBUG</a></li>");
        out.println("</ul>");
        out.println("</div>");
        out.println("<h1>Cart/Checkout</h1>");
        out.println("<p id=\"notice\">" + notice + "</p>");

        if (cart == null || cart.isEmpty()) {
            out.println("<p id=\"emptyNotice\">Your cart is currently empty.</p>");
        }

        if (cart != null && cart.isEmpty() == false)
        {
            double subtotal = 0.00;
            out.println("<table>");
            out.println("<tr>");
            out.println("<th>Product ID</th>");
            out.println("<th>Name</th>");
            out.println("<th>Image</th>");
            out.println("<th>Price</th>");
            out.println("<th>Quantity</th>");
            out.println("<th>Actions</th>");
            out.println("</tr>");

            subtotal = printCart(out, cart);

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
            out.println("<td class='cost'>$<span id='totalCost'>" + roundDecimalPlaces(subtotal) + "</span></td>");
            // End Handle Current Total Cost
            out.println("</tr>");
            out.println("</table>");

            out.println("<form action='order' class='orderForm' name='orderForm' onSubmit='return (validate())' method='POST'>");
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
            out.println("<input type='text' id='zipcode' name='zipcode'/><br>");
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
            out.println("</body>");
            out.println("</html>");
        }
    }

    private void getCityStateFromDB(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException { // Handles AJAX request, "returning" string of "city,state"
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        String zip = request.getParameter("zipcode");
        System.out.println("\ngetCityStateFromDB zipcode = " + zip);

        try {
            String sql = "SELECT City, State FROM location_data WHERE Zipcode=" + zip;
            DatabaseResultSet dbrs = new DatabaseResultSet(sql);
            String cityState = "";
            while (dbrs.getResultSet().next()) {
                cityState += dbrs.getResultSet().getString("City") + "," + dbrs.getResultSet().getString("State");
            }
            System.out.println("cityState: " + cityState);
            out.print(cityState);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void getZipSuggestionsFromDB(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        String zip = String.format("%-5s", request.getParameter("zipcode")).replace(' ', '0');
        zip = zip.equals("00000") ? null : zip;
        System.out.println("\ngetZipSuggestionsFromDB zipcode = " + zip);
        if (zip == null) {
            return;
        }

        try {
            String sql = "SELECT Zipcode, ABS( Zipcode - " + zip + ") AS distance FROM ( (SELECT Zipcode FROM `location_data` WHERE Zipcode >= " + zip + " ORDER BY Zipcode LIMIT 5 ) UNION ALL ( SELECT Zipcode FROM `location_data` WHERE Zipcode < " + zip + " ORDER BY Zipcode DESC LIMIT 5)) AS result ORDER BY distance LIMIT 5";
            DatabaseResultSet dbrs = new DatabaseResultSet(sql);
            String suggestedZipCodes = "";
            while(dbrs.getResultSet().next()) {
                suggestedZipCodes += dbrs.getResultSet().getString("Zipcode") + "|";
            }
            suggestedZipCodes = suggestedZipCodes.substring(0, suggestedZipCodes.length() - 1);
            System.out.println("suggestedZipCodes: " + suggestedZipCodes);
            out.print(suggestedZipCodes);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
