<?php

/* 
 * CS137 Spring 2016 | Group 15
 * Main Author: Thomas Tai Nguyen
 * Filename: submitQuestion.php
 */


/* Note: Most of the code is reused; original author is Bryan Nham/Alex Lin. */

require_once '../connection_info.php';
$pdo = new PDO("mysql:host=$servername;dbname=$dbname", $username, $password);
$pdo->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);

$question = "INSERT INTO questions (question_id, firstName, lastName, email, phoneNumber, department, question)  
    VALUES (:question_id, :firstName, :lastName, :email, :phoneNumber, :department, :question)";

$order_stmt = $pdo->prepare($question);
$order_stmt->execute(array(
    ':question_id' => "null",
    ':firstName' => $_POST['firstName'],
    ':lastName' => $_POST['lastName'],
    ':email' => $_POST['email'],
    ':phoneNumber' => $_POST['phoneNumber'],
    ':department' => $_POST['department'],
    ':question' => $_POST['question'],
));


?>

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
    <li><a class="active" href="../index.html">Home</a></li>
    <li><a href="../shop.php">Shop</a></li>
    <li><a href="../aboutus.html">About Us</a></li>
    <li><a href="../contactus.html">Contact</a></li>
  </ul>
  </div>
  <h1> Thank you for contacting us! </h1>
  <div class="confirmation">
  <p> Your question has been submitted.
  <br>
  <?php
  $sql = "SELECT * FROM questions ORDER BY question_id DESC LIMIT 1";
  $statement = $pdo->query($sql);
  
  while ($question = $statement->fetch(PDO::FETCH_ASSOC)) {
    echo "<br><b>Question ID: </b>" . $question['question_id']; 
    echo "<br><b>First Name: </b>" . $question['firstName']; 
    echo "<br><b>Last Name: </b>" . $question['lastName'];
    echo "<br><b>Your E-mail: </b>" . $question['email'];
    echo "<br><b>Your Phone Number: </b>" . $question['phoneNumber'];
    echo "<br><b>Related Department: </b>" . $question['department'];
    echo "<br><b>Question (first 25 characters): </b>".substr($question['question'], 0, 25);
  }
  
  ?>
  </div>
  </p>
</body>
</html>
