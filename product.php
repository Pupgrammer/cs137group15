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
$data = array();
while ($row = $statement->fetch(PDO::FETCH_ASSOC)) {
  if ($row['product_number'] == $_GET['product_number']) {
    $data = $row;
    break;
  }
}
// Add or modify entries within associated array, $data
require_once 'php/prettifyDatabaseOutput.php';
$data = prettifyData($data);
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
  <script type="text/javascript" src="scripts/calculatePrices.js"></script>
  <title>Product <?php echo $data['product_number']; ?></title>
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
      <th class="info" colspan="2"><?php echo $data['friendly_name']; ?></th>
    </tr>

    <tr>
      <td class="img" colspan="2"><img src="<?php echo $data['image_path']; ?>" class="thumbnail"
                                       alt="<?php echo $data['friendly_name']; ?>"
                                       title="<?php echo $data['friendly_name']; ?>"
                                       width=200/></td>
    </tr>

    <tr class="info">
      <td class="info">Model No.</td>
      <td class="desc"><?php echo $data['model_number']; ?></td>
    </tr>
    <tr class="info">
      <td class="info">Manufacturer</td>
      <td class="desc"><?php echo $data['manufacturer']; ?></td>
    </tr>
    <tr class="info">
      <td class="info">Price</td>
      <td class="desc" id="getPriceFromJS"><?php echo $data['price']; ?></td>
    </tr>
    <tr class="info">
      <td class="info">Processor</td>
      <td class="desc"><?php echo $data['processor']; ?></td>
    </tr>
    <tr class="info">
      <td class="info">Screen Size</td>
      <td class="desc"><?php echo $data['screen_size']; ?></td>
    </tr>
    <tr class="info">
      <td class="info">Graphics</td>
      <td class="desc"><?php echo $data['graphics']; ?></td>
    </tr>
    <tr class="info">
      <td class="info">RAM</td>
      <td class="desc"><?php echo $data['ram_size']; ?></td>
    </tr>
    <tr class="info">
      <td class="info">HDD</td>
      <td class="desc"><?php echo $data['hdd']; ?></td>
      </td>
    </tr>
    <tr class="info">
      <td class="info">OS</td>
      <td class="desc"><?php echo $data['operating_system']; ?></td>
    </tr>
  </table>

  <form action="confirmation.php" class="orderForm" name="orderForm" onSubmit="return (validate())" method="POST">
    <br>Product Number:<br>
    <input type="number" name="product_number" value="<?php echo $data['product_number'] ?>" readonly/>     
    <br>Product Name:<br>
    <input type="text" name="friendly_name" value="<?php echo $data['friendly_name'] ?>" readonly/>
    <br>Quantity:<br>    
    <input type="number" name="quantity" onchange="updateCosts();"/>

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
   <!--<option value="default" selected="selected" disabled="disabled">Please select an option...</option>-->
   <option value="One Day">($10.00) One-Day Overnight Shipping</option>
   <option value="Two Day">($5.00) Two-Day Expedited Shipping</option>
   <option value="Ground" selected="selected">FREE Standard Ground Shipping (5-7 days)</option>
   </select>
   <br><br>
    <br>Credit Card Number (16 digits):<br>
    <input name="creditCard"/>
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
    <br>
    <input type="submit" value="Submit Order"/>
    <br>
    
    
    
  </form>
  
</body>
</html>
