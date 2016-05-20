<!--
CS137 Spring 2016 | Group 15
Main Author: Brian Chipman
Filename: shop.php
-->


<?php

// Connect to database and get PDO object
require_once 'connection_info.php';
$pdo = new PDO("mysql:host=$servername;dbname=$dbname", $username, $password);
$pdo->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);

// Query database and get PDOStatement object
$sql = 'SELECT * FROM products';
$statement = $pdo->query($sql);

// Close database connection
$pdo = null;

?>


<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <link type="text/css" rel="stylesheet" href="../styles/style.css">
  <title>Shop</title>
</head>


<body>
  <img class="logo" src="../images/logo.png" alt="Logo"/>
  <div class="container">
    <ul>
      <li><a class="active" href="index.html">Home</a></li>
      <li><a href="shop.php">Shop</a></li>
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
      require_once 'php/prettifyDatabaseOutput.php';
      $data = prettifyData($data);

      // Create html table row with data from database?>
      <tr class="info">
        <td><?php echo $data['product_number'] ?></td>
        <td><?php echo $data['model_name'] ?></td>
        <td class="img">
          <a href="product.php?product_number=<?php echo $data['product_number'] ?>">
            <img src="../<?php echo $data['image_path'] ?>"
                 alt="<?php echo $data['friendly_name'] ?>"
                 title="<?php echo $data['friendly_name'] ?>"/>
          </a>
        </td>
        <td><?php echo $data['model_number'] ?></td>
        <td><?php echo $data['manufacturer'] ?></td>
        <td><?php echo $data['price'] ?></td>
      </tr>
    <?php } ?>
  </table>

</body>

</html>
