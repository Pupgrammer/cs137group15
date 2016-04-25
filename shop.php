<!--
CS137 Spring 2016 | Group 15
Main Author: Brian Chipman
Filename: shop.php
-->


<?php

// Connect to database and get PDOStatement object
require_once "connection_info.php";
$sql = 'SELECT * FROM products';
$statement = $pdo->query($sql);

// Close database connection
$pdo = null;

?>


<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <link type="text/css" rel="stylesheet" href="styles/style.css">
  <title>Shop</title>
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
  <p>Interested in a product? Click on the product image for more details and to order it!</p>
  <table>
    <tr>
      <th>#</th>
      <th>Name</th>
      <th>Image</th>
      <th>Model No.</th>
      <th>Manufacturer</th>
      <th>Price</th>
    </tr>

    <?php
    while ($data = $statement->fetch(PDO::FETCH_ASSOC)) {
      echo "\n";

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

      // Create html table row with data from database?>
      <tr class="info">
        <td><?= $data['product_number'] ?></td>
        <td><?= $data['model_name'] ?></td>
        <td class="img">
          <a href="product.php?product_number=<?= $data['product_number'] ?>">
            <img src="<?= $data['image_path'] ?>"
                 alt="<?= $data['friendly_name'] ?>"
                 title="<?= $data['friendly_name'] ?>"/>
          </a>
        </td>
        <td><?= $data['model_number'] ?></td>
        <td><?= $data['manufacturer'] ?></td>
        <td><?= $data['price'] ?></td>
      </tr>
    <?php } ?>
  </table>

</body>

</html>
