<?php

require_once 'connection_info.php';
$pdo = new PDO("mysql:host=$servername;dbname=$dbname", $username, $password);
$pdo->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);

/*   Prints out the POST array values in order.

foreach ($_POST as $data2)
{
    echo "$data2 <br>";
}

*/

$order = "INSERT INTO order_info (order_id, product_number, product_name, quantity, first_name, last_name, email, phone_number, address, zipcode, city, state, shipping_method, credit_card)  
    VALUES (:order_id, :product_number, :product_name, :quantity, :first_name, :last_name, :email, :phone_number, :address, :zipcode, :city, :state, :shipping_method, :credit_card)";
$order_stmt = $pdo->prepare($order);
$order_stmt->execute(array(
    ':order_id' => "null",
    ':product_number' => $_POST['product_number'],
    ':product_name' => $_POST['friendly_name'],
    ':quantity' => $_POST['quantity'],
    ':first_name' => $_POST['first_name'],
    ':last_name' => $_POST['last_name'],
    ':email' => $_POST['email'],
    ':phone_number' => $_POST['phone_number'],
    ':address' => $_POST['address'],
    ':zipcode' => $_POST['zipcode'],
    ':city' => $_POST['city'],
    ':state' => $_POST['state'],
    ':shipping_method' => $_POST['shipping_method'],
    ':credit_card' => $_POST['credit_card'],
));

?>

<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <link type="text/css" rel="stylesheet" href="styles/style.css">
  <title>Confirmation Page</title>
</head>

<body>
  <img class="logo" src="images/logo.png" alt="Logo" />
  <div class="container">
  <ul>
    <li><a class="active" href="index.html">Home</a></li>
    <li><a href="shop.php">Shop</a></li>
    <li><a href="aboutus.html">About Us</a></li>
    <li><a href="contactus.html">Contact</a></li>
  </ul>
  </div>
  <h1> Thank you for your purchase! </h1>
  <div class="confirmation">
  <p> Here is your order information:
  <br>
  <?php
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
  }

  
  ?>
  </div>
  </p>
</body>
</html>

