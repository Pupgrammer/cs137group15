<!--
CS137 Spring 2016 | Group 15
Main Author: Brian Chipman
Filename: product.php
-->


<?php

// Connect to database and get PDO object
require_once 'connection_info.php';
$pdo = new PDO("mysql:host=$servername;dbname=$dbname", $username, $password);
$pdo->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);

// Query database and get PDOStatement object
$sql = 'SELECT * FROM products';
$statement = $pdo->query($sql);

// Get entire row from table
$data = [];
while ($row = $statement->fetch(PDO::FETCH_ASSOC)) {
  if ($row['product_number'] == $_GET['product_number']) {
    $data = $row;
    break;
  }
}

// Add or modify entries within associated array, $data
require_once 'php/prettifyDatabaseOutput.php';
$data = prettifyData($data);

if (isset($_POST['quantity'])) {
$order = "INSERT INTO order_info (order_id, product_number, product_name, quantity, first_name, last_name, email, phone_number, address, zipcode, city, state, shipping_method, credit_card)  
    VALUES (:order_id, :product_number, :product_name, :quantity, :first_name, :last_name, :email, :phone_number, :address, :zipcode, :city, :state, :shipping_method, :credit_card)";
$order_stmt = $pdo->prepare($order);
$order_stmt->execute(array(
    ':order_id' => "null",
    ':product_number' => $data['product_number'],
    ':product_name' => $data['friendly_name'],
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
}

// Close database connection
$pdo = null;

?>


<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <link type="text/css" rel="stylesheet" href="styles/style.css">
  <script type="text/javascript" src="scripts/validate_orderForm.js"></script>
  <script type="text/javascript" src="scripts/ajax_cityState.js"></script>
  <script type="text/javascript" src="scripts/ajax_zipSuggestions.js"></script>
  <script type="text/javascript" src="scripts/ajax_shippingCost.js"></script>
  <title>Product <?= $data['product_number']; ?></title>
</head>


<body>
  <img class="logo" src="images/logo.png" alt="Logo"/>

  <div class="container">
    <ul>
      <li><a class="active" href="index.html">Home</a></li>
      <li><a href="shop.php">Shop</a></li>
      <li><a href="aboutus.html">About Us</a></li>
      <li><a href="contactus.html">Contact</a></li>
    </ul>
  </div>

  <table class="info">

    <tr class="info">
      <th class="info" colspan="2"><?= $data['friendly_name']; ?></th>
    </tr>

    <tr>
      <td class="img" colspan="2"><img src="<?= $data['image_path']; ?>" class="thumbnail"
                                       alt="<?= $data['friendly_name']; ?>"
                                       title="<?= $data['friendly_name']; ?>"
                                       width=200/></td>
    </tr>

    <tr class="info">
      <td class="info">Model No.</td>
      <td class="desc"><?= $data['model_number']; ?></td>
    </tr>
    <tr class="info">
      <td class="info">Manufacturer</td>
      <td class="desc"><?= $data['manufacturer']; ?></td>
    </tr>
    <tr class="info">
      <td class="info">Price</td>
      <td class="desc"><?= $data['price']; ?></td>
    </tr>
    <tr class="info">
      <td class="info">Processor</td>
      <td class="desc"><?= $data['processor']; ?></td>
    </tr>
    <tr class="info">
      <td class="info">Screen Size</td>
      <td class="desc"><?= $data['screen_size']; ?></td>
    </tr>
    <tr class="info">
      <td class="info">Graphics</td>
      <td class="desc"><?= $data['graphics']; ?></td>
    </tr>
    <tr class="info">
      <td class="info">RAM</td>
      <td class="desc"><?= $data['ram_size']; ?></td>
    </tr>
    <tr class="info">
      <td class="info">HDD</td>
      <td class="desc"><?= $data['hdd']; ?></td>
      </td>
    </tr>
    <tr class="info">
      <td class="info">OS</td>
      <td class="desc"><?= $data['operating_system']; ?></td>
    </tr>
  </table>

  <form action="confirmation.php" class="orderForm" name="orderForm" onSubmit="return (validate())" method="POST">
    <br>Product Number:<br>
    <input type="number" name="product_number" disabled="disabled" value="<?= $data['product_number'] ?>"/>     <br>Quantity:<br>     <input type="number" name="quantity"/>     <br><br>
    <br>First Name:<br>
    <input type="text" name="first_name"/>
    <br>Last Name:<br>
    <input type="text" name="last_name"/>
    <br>E-mail (x@y.z):<br>
    <input type="email" name="email"/>
    <br>Phone Number (xxx-xxx-xxxx):<br>
    <input type="tel" name="phone_number"/>

    <br><br>
      
     
    <br>Street Address:<br>
    <input type="text" name="address"/>

      
    <br>Zipcode (5 digits):<br>
    <input type="text" onblur="getCityState(this.value)" onkeyup="getZipSuggestions(this.value)" id="zipcode" name="zipcode"/><br>
    <span id="suggestions" style="border:0px"></span>

    <br>City:<br>
    <input type="text" name="city" id="city"/>
    <br>State:<br>
    <input type="text" name="state" id="state"/>

   <br>Shipping Method:<br>
   <select name="shipping_method" onChange="updateShippingCost()">
   <option value="default" selected="selected" disabled="disabled">Please select an option...</option>
   <option value="One Day">($10.00) One-Day Overnight Shipping</option>
   <option value="Two Day">($5.00) Two-Day Expedited Shipping</option>
   <option value="Ground">FREE Standard Ground Shipping (5-7 days)</option>
   </select>
`
   <br><br>

    <br>Credit Card Number (16 digits):<br>
    <input name="credit_card"/>
    <br><br>
    <table class="cost">
    <tr>
      <td class="cost">Subtotal</td>
      <td class="cost">$<span id="subtotalCost">0.00</span></td> <!--- It's just the product price times quantity. Someone else's job to implement, not mine. (Thomas) --->
    </tr>
    <tr style="border-bottom: 2px solid black">
      <td class="cost">Shipping Cost</td>
      <td class="cost">$<span id="shippingCost">0.00</span></td> <!--- Shipping cost based off of database or JS. Someone else's job to implement, not mine. (Thomas) --->
    <tr>                                                             <!--- Note: A rudimentary JS implementation has been provided in scripts/ajax_shippingCost.js (Thomas) --->
    <tr>
      <td class="cost">Total Cost</td>
      <td class="cost">$<span id="totalCost">0.00</span></td> <!--- Total cost = Subtotal + Shipping Cost. Someone else's job to implement, not mine. (Thomas) --->
    </tr>
    </table>
    <br>
    <input type="submit" value="Submit Order"/>
    <br>
    
    
    
  </form>
  
</body>

</html>
