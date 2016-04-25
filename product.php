<!--
CS137 Spring 2016 | Group 15
Main Author: Brian Chipman
Filename: product.php
-->


<?php

// Connect to database and get PDOStatement object
require_once "connection_info.php";
$sql = 'SELECT * FROM products';
$statement = $pdo->query($sql);

// Close database connection
$pdo = null;

// Get entire row from table
$data = [];
while ($row = $statement->fetch(PDO::FETCH_ASSOC)) {
  if ($row['product_number'] == $_GET['product_number']) {
    $data = $row;
    break;
  }
}

// Add or modify entries within associated array, $data
$data['price'] = '$' . number_format($data['price'], 2);
$data['hdd'] = ($data['hdd_size_gb'] < 1000 ? $data['hdd_size_gb'] . 'GB ' : $data['hdd_size_gb'] / 1000 . 'TB ') . $data['hdd_type'];
$data['ram_size'] = $data['ram_size_gb'] . 'GB';
$data['screen_size'] = $data['screen_size'] . '&quot;';
$data['friendly_name'] =
    $data['manufacturer'] . ' ' .
    $data['model_name'] . ' ' .
    $data['screen_size'] . ' ' .
    'Laptop - ' .
    $data['processor'] . ' - ' .
    $data['ram_size'] . ' ' .
    $data['hdd'];
?>


<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <link type="text/css" rel="stylesheet" href="styles/style.css">
  <script type="text/javascript" src="scripts/validate_orderForm.js"></script>
  <title>Product <?= $data['product_number']; ?></title>
</head>


<body>
  <img class="logo" src="images/logo.png" alt="Logo"/>

  <div class="container">
    <ul>
      <li><a class="active" href="index.html">Home</a></li>
      <li><a href="shop.html">Shop</a></li>
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
      <td class="desc"><?= $data['hdd'];?></td>
      </td>
    </tr>
    <tr class="info">
      <td class="info">OS</td>
      <td class="desc"><?= $data['operating_system']; ?></td>
    </tr>
  </table>

  <form class="orderForm" name="orderForm" onSubmit="return (validate())"
        action="mailto:malekware@malekware.com?subject=Order" ENCTYPE="text/plain" method="POST">
    <p>
      <br>Product Number:<br>
      <input type="number" name="productNumber" value="<?= $data['product_number']?>"/>
      <br>Quantity:<br>
      <input type="number" name="quantity"/>
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

      <br>Shipping Address:<br>
      <textarea name="shippingAddress" rows="5" cols="40"></textarea>
      <br>Shipping Method:<br>
      <select name="shipping">
        <option value="oneday">One-Day Overnight Shipping</option>
        <option value="twoday">Two-Day Expedited Shipping</option>
        <option value="ground" selected="selected">Standard Ground Shipping (5-7 days)</option>
      </select>

      <br><br>

      <br>Credit Card Number (16 digits):<br>
      <input type="number" name="creditCard"/>
      <br>
      <br>
      <button class="button1" type="submit">Submit Order</button>
      <br>
    </p>
  </form>

</body>

</html>
