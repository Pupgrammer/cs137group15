<%@ page import="pkg.DataRow"%>
<%@ page import="pkg.DatabaseResultSet"%>
<%@ page import="java.sql.SQLException"%>
<%@ page import="java.util.regex.Matcher" %>
<%@ page import="java.util.regex.Pattern" %>
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
        <li><a href='shop'>Home/Shop</a></li>
        <li><a href='checkout'>Cart/Checkout</a></li>
        <li><a href='contactus.html'>Contact</a></li>
        <li><a href='aboutus.html'>About Us</a></li>
    </ul>
</div>
<h1>Order Details</h1>
<p>Thank you for your purchase! Your order has been submitted.</p>
<table>
    <tr>
        <th>Product ID</th>
        <th>Name</th>
        <th>Image</th>
        <th>Quantity</th>
        <th>Subtotal</th>
    </tr>

<%
    String order_id = request.getParameter("order_id");

    String customer_info_sql = "SELECT * FROM customer_info WHERE order_id=\"" + order_id + "\";";
    String order_info_sql = "SELECT * FROM order_info WHERE order_id=\"" + order_id + "\";";

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
                out.println("<td>" + dataRow.get("friendly_name_short") + "<br>(Price: " + dataRow.get("price") + ")" +"</td>");

                out.println("<td class=\"img\">" );
                out.println("<a href=\"product?product_number=" + dataRow.get("product_number") + "\">");
                out.println("<img src=\"" + dataRow.get("image_path") + "\"");
                out.println("alt=\"" + dataRow.get("friendly_name") + "\"");
                out.println("title=\"" + dataRow.get("friendly_name") + "\"/>");
                out.println("</a>");
                out.println("</td>");

                //out.println("<td>" + dataRow.get("price") + "</td>");
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
            //out.println("<td></td>");
            out.println("<td>" + "$" + String.format("%.2f", Double.parseDouble(shipping_cost)) + "</td>");
        }
        out.println("</tr>");

        // Total
        out.println("<tr class=\"info\">");
        out.println("<td></td>");
        out.println("<td>Total</td>");
        out.println("<td></td>");
        out.println("<td></td>");
        //out.println("<td></td>");
        out.println("<td>" + "$" + String.format("%.2f", totalCost) + "</td>");
        out.println("</tr>");


%>
</table>
<%
        dbrs = new DatabaseResultSet(customer_info_sql);
        while (dbrs.getResultSet().next()) {
            
            out.println("<h3 style='margin-left:200px; text-decoration: underline'>Order Information</h3>");
            out.println("<table class='cost' style='margin-left:200px'>");
            out.println("<tr>");
            out.println("<td class='cost'>Order ID: </td>");
            out.println("<td class='cost'>" + dbrs.getResultSet().getString("order_id") + "</td>");
            out.println("</tr>");
            out.println("<tr>");
            out.println("<td class='cost'>Order Time: </td>");
            out.println("<td class='cost'>" + dbrs.getResultSet().getString("order_time") + "</td>");
            out.println("</tr>");
            out.println("</table>");
            
            out.println("<h3 style='margin-left:200px; text-decoration: underline'>Contact Information</h3>");
            out.println("<table class='cost' style='margin-left:200px'>");
            out.println("<tr>");
            out.println("<td class='cost'>First Name: </td>");
            out.println("<td class='cost'>" + dbrs.getResultSet().getString("first_name") + "</td>");
            out.println("</tr>");
            out.println("<tr>");
            out.println("<td class='cost'>Last Name: </td>");
            out.println("<td class='cost'>" + dbrs.getResultSet().getString("last_name") + "</td>");
            out.println("</tr>");
            out.println("<tr>");
            out.println("<td class='cost'>E-mail: </td>");
            out.println("<td class='cost'>" + dbrs.getResultSet().getString("email") + "</td>");
            out.println("</tr>");         
            out.println("<tr>");
            out.println("<td class='cost'>Phone Number: </td>");
            out.println("<td class='cost'>" + dbrs.getResultSet().getString("phone_number") + "</td>");
            out.println("</tr>");
            out.println("</table>");          
            out.println("<h3 style='margin-left:200px; text-decoration: underline'>Shipping Information</h3>");
            out.println("<table class='cost' style='margin-left:200px'>");
            out.println("<tr>");
            out.println("<td class='cost'>Address: </td>");
            out.println("<td class='cost'>" + dbrs.getResultSet().getString("address") + "</td>");
            out.println("</tr>");         
            out.println("<tr>");
            out.println("<td class='cost'>City: </td>");
            out.println("<td class='cost'>" + dbrs.getResultSet().getString("city") + "</td>");
            out.println("</tr>");         
            out.println("<tr>");
            out.println("<td class='cost'>State: </td>");
            out.println("<td class='cost'>" + dbrs.getResultSet().getString("state") + "</td>");
            out.println("</tr>");           
            out.println("<tr>");
            out.println("<td class='cost'>Zipcode: </td>");
            out.println("<td class='cost'>" + dbrs.getResultSet().getString("zipcode") + "</td>");
            out.println("</tr>");
            out.println("</table>");
                  
            out.println("<h3 style='margin-left:200px; text-decoration: underline'>Payment Information</h3>");
            out.println("<table class='cost' style='margin-left:200px'>");
            out.println("<tr>");
            out.println("<td class='cost'>Credit Card Number: </td>");
            // Begin CC Formatting
            String regex = "^\\d{12}";
            String cc = dbrs.getResultSet().getString("credit_card");
            Pattern p =  Pattern.compile(regex);
            Matcher m = p.matcher(cc);
            cc = m.replaceAll("XXXXXXXXXXXX");
            // End CC Formatting
            out.println("<td class='cost'>" + cc + "</td>");
            out.println("</tr>");
            out.println("</table>");
            
            out.println("<br>");
            out.println("<h3 style='margin-left:200px'>Have any questions about your order? Contact us <a href=\"./contactus.html\">here.</a></h3>");
        }
    }
    catch (SQLException e) {
        e.printStackTrace();
    }
%>



</body>
</html>
