<!DOCTYPE html>

<!--
CS137 Spring 2016 | Group 15
Main Author: Brian Chipman
Filename: product.php
-->

<?php

require_once "connection_info.php";


// Getting *ALL* data from database and displaying it
$sql = 'SELECT * FROM products';
$query = $pdo->query($sql);
//while ($row = $query->fetch(PDO::FETCH_ASSOC)) {
//  echo '<br>';
//  echo 'product_number: ' . $row['product_number'] . '<br>';
//  echo 'model_name: ' . $row['model_name'] . '<br>';
//}

// Closing database connection
$pdo = null;
//echo "Closed connection.";

$product_number = $_GET['product_number'];

while ($row = $query->fetch(PDO::FETCH_ASSOC)) {
  if ($row['product_number'] == $product_number) {
    $data_row = $row;
    break;
  }
}
?>


<html lang="en">
<head>
  <meta charset="UTF-8">
  <link type="text/css" rel="stylesheet" href="styles/style.css">
  <script type="text/javascript" src="scripts/validate_orderForm.js"></script>
  <title>Product <?php echo $_GET['product_number']; ?></title>
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

    <?php
    $friendly_name =
        $data_row['manufacturer'] . ' ' .
        $data_row['model_name'] . ' ' .
        $data_row['screen_size'] . '&quot;' . ' ' .
        'Laptop - ' .
        $data_row['processor'] . ' - ' .
        $data_row['ram_size_gb'] . 'GB - ' .
        ($data_row['hdd_size_gb'] < 1000 ? $data_row['hdd_size_gb'] . 'GB ' : $data_row['hdd_size_gb'] / 1000 . 'TB ') .
        $data_row['hdd_type'];
    ?>
    <tr class="info">
      <th class="info" colspan="2"><?php echo $friendly_name; ?></th>
    </tr>

    <tr>
      <td class="img" colspan="2"><img src="<?php echo $data_row['image_path']; ?>" class="thumbnail"
                                       alt="<?php echo $friendly_name; ?>"
                                       title="<?php echo $friendly_name; ?>"
                                       width=200/></td>
    </tr>

    <tr class="info">
      <td class="info">Model No.</td>
      <td class="desc"><?php echo $data_row['model_number']; ?></td>
    </tr>
    <tr class="info">
      <td class="info">Manufacturer</td>
      <td class="desc"><?php echo $data_row['manufacturer']; ?></td>
    </tr>
    <tr class="info">
      <td class="info">Price</td>
      <td class="desc"><?php echo '$' . number_format($data_row['price'], 2); ?></td>
    </tr>
    <tr class="info">
      <td class="info">Processor</td>
      <td class="desc"><?php echo $data_row['processor']; ?></td>
    </tr>
    <tr class="info">
      <td class="info">Screen Size</td>
      <td class="desc"><?php echo $data_row['screen_size'] . '&quot;'; ?></td>
    </tr>
    <tr class="info">
      <td class="info">Graphics</td>
      <td class="desc"><?php echo $data_row['graphics']; ?></td>
    </tr>
    <tr class="info">
      <td class="info">RAM</td>
      <td class="desc"><?php echo $data_row['ram_size_gb'] . 'GB'; ?></td>
    </tr>
    <tr class="info">
      <td class="info">HDD</td>
      <td class="desc"><?php echo ($data_row['hdd_size_gb'] < 1000 ? $data_row['hdd_size_gb'] . 'GB ' : $data_row['hdd_size_gb'] / 1000 . 'TB ') . $data_row['hdd_type']; ?>
      </td>
    </tr>
    <tr class="info">
      <td class="info">OS</td>
      <td class="desc"><?php echo $data_row['operating_system']; ?></td>
    </tr>
  </table>

  <form class="orderForm" name="orderForm" onSubmit="return (validate())"
        action="mailto:malekware@malekware.com?subject=Order" ENCTYPE="text/plain" method="POST">
    <p>
      <br>Product Number:<br>
      <input type="number" name="productNumber" value="<?php echo $data_row['product_number']?>"/>
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
