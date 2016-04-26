<?php
/* 
 * CS137 Spring 2016 | Group 15
 * Main Author: Thomas Tai Nguyen
 * Filename: getCityState.php
 */

// Most of the following code for DB connection was created by Brian Chipman and reused here

try
{
    // Connect to database and get PDO object
    require_once "../connection_info.php";
    $pdo = new PDO("mysql:host=$servername;dbname=$dbname", $username, $password);
    $pdo->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);

    // Query database and get PDOStatement object
    $zipcode = $_POST['zipcode'];
    $sql = 'SELECT City, State FROM zipcode WHERE Zipcode = '.$zipcode;
    $statement = $pdo->query($sql);

    // Close database connection
    $pdo = null;
    
    // Send the city and state
    $data = $statement->fetch(PDO::FETCH_ASSOC);
    print $data['City'].",".$data['State'];
}
catch (PDOException $e)
{
    echo "Database connection failed: " . $e->getMessage();
}

?>

