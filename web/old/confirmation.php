<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <link type="text/css" rel="stylesheet" href="../styles/style.css">
  <title>Confirmation Page</title>
</head>

<body>
  <img class="logo" src="../images/logo.png" alt="Logo" />
  <div class="container">
  <ul>
    <li><a class="active" href="index.html">Home</a></li>
    <li><a href="shop.php">Shop</a></li>
    <li><a href="../aboutus.html">About Us</a></li>
    <li><a href="../contactus.html">Contact</a></li>
  </ul>
  </div>
  <h1> Thank you for your purchase! </h1>
  <div class="confirmation">
  <p> Here is your order information:
  <br>
  <?php
  require_once 'connection_info.php';
  $pdo = new PDO("mysql:host=$servername;dbname=$dbname", $username, $password);
  $pdo->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
  $sql = "SELECT * FROM order_info ORDER BY order_id DESC LIMIT 1";
  $statement = $pdo->query($sql);

  while ($order = $statement->fetch(PDO::FETCH_ASSOC)) {
    echo "<br><b>Order ID: </b>" . $order['order_id'];
    echo "<br><b>Product Number: </b>" . $order['product_number'];
    echo "<br><b>Product Name: </b>" . $order['product_name'];
    echo "<br><b>Quantity: </b>" . $order['quantity'];
    echo "<br><b>First Name: </b>" . $order['first_name'];
    echo "<br><b>Last Name: </b>" . $order['last_name'];
    echo "<br><b>Email: </b>" . $order['email'];
    echo "<br><b>Phone Number: </b>" . $order['phone_number'];
    echo "<br><b>Address: </b>" . $order['address'];
    echo "<br><b>Zip Code: </b>" . $order['zipcode'];
    echo "<br><b>City: </b>" . $order['city'];
    echo "<br><b>State: </b>" . $order['state'];
    echo "<br><b>Shipping Method: </b>" . $order['shipping_method'];
    $card = "**** **** **** " . substr($order['credit_card'],-4);
    echo "<br><b>Credit Card: </b>" . $card;
    echo "<br><b>Total Cost: </b>$" . $order['cost'];
  }
  ?>
  </div>
</body>
</html>


