<?php

require_once 'connection_info.php';
$pdo = new PDO("mysql:host=$servername;dbname=$dbname", $username, $password);
$pdo->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);

$price = "SELECT price FROM products WHERE products.product_number = " . $_POST['product_number'] . ";";
$coststatement = $pdo->query($price);
$costarray = $coststatement->fetch(PDO::FETCH_ASSOC);
$cost = $costarray['price'];

$cost *= $_POST['quantity'];

if ($_POST['shipping'] == "One Day") {
    $cost += 10;
}
elseif ($_POST['shipping'] == "Two Day") {
    $cost += 5;
}

$order = "INSERT INTO order_info (order_id, product_number, product_name, quantity, first_name, last_name, email, phone_number, address, zipcode, city, state, shipping_method, credit_card, cost)  
    VALUES (:order_id, :product_number, :product_name, :quantity, :first_name, :last_name, :email, :phone_number, :address, :zipcode, :city, :state, :shipping_method, :credit_card, :cost)";
$order_stmt = $pdo->prepare($order);
$order_stmt->execute(array(
  ':order_id' => "null",
  ':product_number' => $_POST['product_number'],
  ':product_name' => $_POST['friendly_name'],
  ':quantity' => $_POST['quantity'],
  ':first_name' => $_POST['firstName'],
  ':last_name' => $_POST['lastName'],
  ':email' => $_POST['email'],
  ':phone_number' => $_POST['phoneNumber'],
  ':address' => $_POST['streetAddress'],
  ':zipcode' => $_POST['zipcode'],
  ':city' => $_POST['city'],
  ':state' => $_POST['state'],
  ':shipping_method' => $_POST['shipping'],
  ':credit_card' => $_POST['creditCard'],
  ':cost' => $cost,
));

header('Location: confirmation.php');
exit;

?>