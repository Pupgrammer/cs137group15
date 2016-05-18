<?php

/* 
 * CS137 Spring 2016 | Group 15
 * Main Author: Thomas Tai Nguyen
 * Filename: submitQuestion.php
 */

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

header('Location: confirmQuestion.php');
exit;

?>