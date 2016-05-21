<%-- 
    Document   : /WEB-INF/checkout_test.jsp
    Created on : May 20, 2016, 1:38:41 AM
    Author     : Thomas Tai Nguyen
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/style.css">
  <script type="text/javascript" src="${pageContext.request.contextPath}/scripts/validate_orderForm.js"></script>
  <script type="text/javascript" src="${pageContext.request.contextPath}/scripts/ajax_cityState.js"></script>
  <script type="text/javascript" src="${pageContext.request.contextPath}/scripts/ajax_zipSuggestions.js"></script>
  <script type="text/javascript" src="${pageContext.request.contextPath}/scripts/calculatePrices.js"></script>
  <title>Checkout</title>
</head>


<body>
    <p>Request Parameter Value: ${test}</p>
  <img class="logo" src="${pageContext.request.contextPath}/images/logo.png" alt="Logo"/>

  <div class="container">
    <ul>
      <li><a href="./index.html">Home</a></li>
      <li><a href="./shop">Shop</a></li>
      <li><a href="./aboutus.html">About Us</a></li>
      <li><a href="./contactus.html">Contact</a></li>
      <li><a href="checkout" class="active">Checkout</a></li>
    </ul>
  </div>  
      <br><br>
      <table class="cost">
    <tr>
      <td class="cost">Subtotal</td>
      <td class="cost">$<span id="subtotalCost">0.00</span></td> 
    </tr>
    <tr style="border-bottom: 2px solid black">
      <td class="cost">Shipping Cost</td>
      <td class="cost">$<span id="shippingCost">0.00</span></td> 
    <tr>                                                            
    <tr>
      <td class="cost">Total Cost</td>
      <td class="cost">$<span id="totalCost">0.00</span></td>
    </tr>
    </table>
  
  <form action="" class="orderForm" name="orderForm" onSubmit="return (validate())" method="POST">
    <br><br>
    <br>First Name:<br>
    <input type="text" name="firstName"/>
    <br>Last Name:<br>
    <input type="text" name="lastName"/>
    <br>E-mail (x@y.z):<br>
    <input type="email" name="email"/>
    <br>Phone Number (xxx-xxx-xxxx):<br>
    <input type="tel" name="phoneNumber"/>

    <br><br>
      
     
    <br>Street Address:<br>
    <input type="text" name="streetAddress"/>

      
    <br>Zipcode (5 digits):<br>
    <input type="text" onblur="getCityState(this.value)" onkeyup="getZipSuggestions(this.value)" id="zipcode" name="zipcode"/><br>
    <span id="suggestions" style="border:0px"></span>

    <br>City:<br>
    <input type="text" name="city" id="city"/>
    <br>State:<br>
    <input type="text" name="state" id="state"/>

   <br>Shipping Method:<br>
   <select name="shipping" onChange="updateCosts()">
   <option value="One Day">($10.00) One-Day Overnight Shipping</option>
   <option value="Two Day">($5.00) Two-Day Expedited Shipping</option>
   <option value="Ground" selected="selected">FREE Standard Ground Shipping (5-7 days)</option>
   </select>
   <br><br>
    <br>Credit Card Number (16 digits):<br>
    <input name="creditCard"/>
    <br><br>
    <br>
    <input type="submit" value="Submit Order"/>
    <br>
  </form>
  
</body>
</html>
