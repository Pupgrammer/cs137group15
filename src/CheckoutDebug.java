/*
CS137 Spring 2016 | Group 15
Main Author: Thomas Tai Nguyen
Filename: src/CheckoutDebug.java
*/

// NOTE: This servlet class and its references in web.xml should be removed prior to submission and deployment.

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CheckoutDebug extends HttpServlet
{
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        HttpSession session = request.getSession();
        printPage(response.getWriter(), "Your Session ID is " + session.getId());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession();
        Map<String, String[]> parameters = request.getParameterMap();

        if(request.getParameterMap().isEmpty())
        {
            printPage(response.getWriter(), "Your Session ID is " + session.getId());
        }
        else
        {
            for (String parameter : parameters.keySet())
            {
                if (parameter.startsWith("checkCartMapExists"))
                {
                    if (session.getAttribute("cart") == null) // || product not in getattribute.
                    {
                        printPage(out, "There is no existing cart map for your session ID " + session.getId());
                    }
                    else
                    {
                        printPage(out, "There exists a cart map for your session ID " + session.getId());
                    }
                }
                else if (parameter.startsWith("addProductToCart"))
                {
                    if (session.getAttribute("cart") == null)
                    {
                        Map<Integer, Integer> map = new HashMap<Integer, Integer>(20);
                        map.put(Integer.parseInt(request.getParameter(parameter)), 1);
                        session.setAttribute("cart", map);
                        printPage(out, "No cart map existed for your session, so one was created and the product was added. Session ID: " + session.getId());
                    }
                    else
                    {
                        Integer product_id = Integer.parseInt(request.getParameter(parameter));
                        HashMap <Integer, Integer> cart = (HashMap <Integer, Integer>) session.getAttribute("cart");

                        if (cart.containsKey(product_id) == true)
                        {
                            cart.put(product_id, cart.get(product_id)+1);
                            printPage(out, "A cart map existed for your session. The product already existed in your cart, so the quantity was increased by one. Session ID: " + session.getId());
                        }
                        else
                        {
                            cart.put(product_id, 1);
                            printPage(out, "A cart map existed for your session. The product did not exist in your cart, it was added with a quantity of 1. Session ID: " + session.getId());
                        }
                    }
                }
                else if (parameter.startsWith("removeProductFromCart"))
                {
                    if (session.getAttribute("cart") == null)
                    {
                        printPage(out, "No cart map existed for your session. Failure. Session ID: " + session.getId());
                    }
                    else
                    {
                        Integer product_id = Integer.parseInt(request.getParameter(parameter));
                        HashMap <Integer, Integer> cart = (HashMap <Integer, Integer>) session.getAttribute("cart");

                        if (cart.containsKey(product_id) == true)
                        {
                            cart.remove(product_id);
                            printPage(out, "A cart map existed for your session. The product existed in your cart, so it was removed. Session ID: " + session.getId());
                        }
                        else
                        {
                            printPage(out, "A cart map existed for your session. The product was not found in your shopping cart. Remove product failed. Session ID: " + session.getId());
                        }
                    }
                }
                else if (parameter.startsWith("resetSession"))
                {
                    session.invalidate();
                    printPage(out, "Your Session ID of " + session.getId() + " was invalidated. A new session ID will be assigned the next time you make a page request.");
                }
            }
        }

    }

    void printPage(PrintWriter out, String notice)
    {
        out.println("<!DOCTYPE html>");
        out.println("<html lang=\'en\'>");
        out.println("<head>");
        out.println("<meta charset=\'UTF-8\'>");
        out.println("<link type=\'text/css\' rel=\'stylesheet\' href=\'styles/style.css\'>");
        out.println("<title>Cart/Checkout Debug</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<img class='logo' src='images/logo.png' alt='Logo' title='Logo' />");
        out.println("<div class=\'container\'>");
        out.println("<ul>");
        out.println("<li><a href=\'shop\'>Home/Shop</a></li>");
        out.println("<li><a href=\'checkout\'>Cart/Checkout</a></li>");
        out.println("<li><a href=\'contactus.html\'>Contact</a></li>");
        out.println("<li><a href=\'aboutus.html\'>About Us</a></li>");
        out.println("</ul>");
        out.println("</div>");
        out.println("<div style=\"text-align: center\">");
        out.println("<p id=\"notice\">" + notice + "</p>");
        out.println("<form action=\"checkoutdebug\" method=\"post\">");
        out.println("<input name=\"checkCartMapExists\" type=\"hidden\" value=\"checkCartMapExists\">");
        out.println("<input type=\"submit\" value=\"checkCartMapExists\"/>");
        out.println("</form>");
        out.println("<br>");
        out.println("<form action=\"checkoutdebug\" method=\"post\">");
        out.println("<input name=\"addProductToCart\" type=\"number\" value=\"1\">");
        out.println("<span>NOTE: DANGEROUS. ONLY ADD PRODUCT ID THAT EXISTS.</span>");
        out.println("<input type=\"submit\" value=\"addProductToCart\"/>");
        out.println("</form>");
        out.println("<br>");
        out.println("<form action=\"checkoutdebug\" method=\"post\">");
        out.println("<input name=\"removeProductFromCart\" type=\"number\" value=\"1\">");
        out.println("<input type=\"submit\" value=\"removeProductFromCart\"/>");
        out.println("</form>");
        out.println("<br>");
        out.println("<form action=\"checkoutdebug\" method=\"post\">");
        out.println("<input name=\"resetSession\" type=\"hidden\" value=\"resetSession\">");
        out.println("<input type=\"submit\" value=\"resetSession\"/>");
        out.println("</form>");
        out.println("</div>");
        out.println("</body>");
        out.println("</html>");
    }
}
