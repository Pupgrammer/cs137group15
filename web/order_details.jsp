<%@ page import="java.io.*"%>
<%@ page import="java.util.*"%>
<%@ page import="java.sql.*"%>
<%@ page import="javax.servlet.http.*" %>
<%@ page import="javax.servlet.*" %>
<%@ page import="javax.servlet.jsp.*" %>
<%@ page import="pkg.ConnectionInfo" %>
<%@ page import="pkg.DatabaseResultSet" %>
<%@ page import="pkg.DataRow" %>
<%@ page import="java.util.regex.Pattern" %>
<%@ page import="java.util.regex.Matcher" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <meta charset='UTF-8'>
    <link type='text/css' rel='stylesheet' href='styles/style.css'>
    <title>Order Details</title>
</head>


<body>
<img class='logo' src='images/logo.png' alt='Logo'/>
<div class='container'>
    <ul>
        <li><a class='active' href='index.html'>Home</a></li>
        <li><a href='shop'>Shop</a></li>
        <li><a href='aboutus.html'>About Us</a></li>
        <li><a href='contactus.html'>Contact</a></li>
        <li><a href='checkout'>Cart/Checkout</a></li>
        <li><a href='checkoutdebug'>Cart/Checkout DEBUG</a></li>
    </ul>
</div>
<h1>Order Details</h1>
<table>
    <tr>
        <th>Product ID</th>
        <th>Name</th>
        <th>Image</th>
        <th>Price</th>
        <th>Quantity</th>
        <th>Subtotal</th>
    </tr>

<%
    String order_id = request.getParameter("order_id");

    String customer_info_sql = "SELECT * FROM customer_info WHERE order_id=" + order_id + ";";
    String order_info_sql = "SELECT * FROM order_info WHERE order_id=" + order_id + ";";

    DatabaseResultSet dbrs;
    try {
        Double totalCost = 0.00;
        dbrs = new DatabaseResultSet(order_info_sql);
        while (dbrs.getResultSet().next()) {

            int productID = dbrs.getResultSet().getInt("product_id");
            DatabaseResultSet innerdbrs = new DatabaseResultSet("SELECT * FROM products WHERE product_number=" + productID + ";");
            while (innerdbrs.getResultSet().next()) {
                DataRow dataRow = new DataRow(innerdbrs.getResultSet());
                out.println("<tr class=\"info\">");
                out.println("<td>" + dataRow.get("product_number") + "</td>");
                out.println("<td>" + dataRow.get("friendly_name_short") + "</td>");

                out.println("<td class=\"img\">" );
                out.println("<a href=\"product?product_number=" + dataRow.get("product_number") + "\">");
                out.println("<img src=\"" + dataRow.get("image_path") + "\"");
                out.println("alt=\"" + dataRow.get("friendly_name") + "\"");
                out.println("title=\"" + dataRow.get("friendly_name") + "\"/>");
                out.println("</a>");
                out.println("</td>");

                out.println("<td>" + dataRow.get("price") + "</td>");
            }

            out.println("<td>" + dbrs.getResultSet().getInt("quantity")+ "</td>");
            out.println("<td>" + "$" + String.format("%.2f", dbrs.getResultSet().getDouble("subtotal_cost")) + "</td>");
            totalCost += dbrs.getResultSet().getDouble("subtotal_cost");
            out.println("</tr>");
        }


        // Shipping
        out.println("<tr class=\"info\">");
        out.println("<td></td>");
        dbrs = new DatabaseResultSet(customer_info_sql);
        String shippingMethod = "";
        while (dbrs.getResultSet().next()) {
            shippingMethod = dbrs.getResultSet().getString("shipping_method");
            out.println("<td>" + shippingMethod + " Shipping</td>");
        }
        out.println("<td></td>"); // img

        dbrs = new DatabaseResultSet("SELECT * FROM shipping_cost WHERE method=\"" + shippingMethod + "\";");
        while (dbrs.getResultSet().next()) {
            totalCost += dbrs.getResultSet().getDouble("cost");
            String shipping_cost = ( (Double) dbrs.getResultSet().getDouble("cost")).toString();
            out.println("<td></td>");
            out.println("<td></td>");
            out.println("<td>" + "$" + String.format("%.2f", Double.parseDouble(shipping_cost)) + "</td>");
        }
        out.println("</tr>");

        // Total
        out.println("<tr class=\"info\">");
        out.println("<td></td>");
        out.println("<td>Total</td>");
        out.println("<td></td>");
        out.println("<td></td>");
        out.println("<td></td>");
        out.println("<td>" + "$" + String.format("%.2f", totalCost) + "</td>");
        out.println("</tr>");


%>
</table>
<%
        dbrs = new DatabaseResultSet(customer_info_sql);
        while (dbrs.getResultSet().next()) {
            out.println("<h3>Order Details</h3>");
            out.println("Order ID: " + dbrs.getResultSet().getString("order_id"));
            out.println("<br>");
            out.println("Order Time: " + dbrs.getResultSet().getString("order_time"));

            out.println("<h3>Shipping</h3>");
            out.println(dbrs.getResultSet().getString("first_name") + " " + dbrs.getResultSet().getString("last_name"));
            out.println("<br>");
            out.println(dbrs.getResultSet().getString("address"));
            out.println("<br>");
            out.println(dbrs.getResultSet().getString("city") + ", " + dbrs.getResultSet().getString("state") + " " + dbrs.getResultSet().getString("zipcode"));
            out.println("<br>");
            out.println(dbrs.getResultSet().getString("phone_number"));
            out.println("<br>");
            out.println(dbrs.getResultSet().getString("email"));
            out.println("<br>");

            out.println("<h3>Payment</h3>");
            String regex = "^\\d{12}";
            String cc = dbrs.getResultSet().getString("credit_card");
            Pattern p =  Pattern.compile(regex);
            Matcher m = p.matcher(cc);
            cc = m.replaceAll("XXXXXXXXXXXX");
            out.println(cc);
        }
    }
    catch (SQLException e) {
        e.printStackTrace();
    }
%>



</body>
</html>
