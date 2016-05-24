<%--
  Created by IntelliJ IDEA.
  User: brian
  Date: 5/17/2016
  Time: 12:01 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>$Title$</title>
  </head>
  <body>
  <h1><a href="shop">ShopServlet</a></h1>
  <h1><a href="checkout">(Checkout) No GET parameters test</a></h1>
  <br>
    <form method="get" action="checkout">
        <p>(Checkout) GET parameter test:</p>
        <span>Arbitrary Parameter - </span><input type="number" name="test" value="1" />
        <input type='submit' />
    </form>
  <br>
    <form method="post" action="checkout">
        <p>(Checkout) POST parameter test:</p>
        <span>Arbitrary Parameter - </span><input type="number" name="test" value="1" />
        <input type='submit' />
    </form>
  </body>
</html>

  <%--$END$--%>
