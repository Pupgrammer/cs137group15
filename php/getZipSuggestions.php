<?php

/* 
 * CS137 Spring 2016 | Group 15
 * Main Author: Thomas Tai Nguyen
 * Filename: getZipSuggestions.php
 */

// Currently, suggestions are based on distance from each other numerically (i.e. if you type in 01, closest is 02 and 03, etc, not 011, 012)
// I don't know how to do the other one, so I'm going to leave it at this for now. I might change it for closest ZIP proximity based on Latitude/Longitude.

// Most of the following code for DB connection was created by Brian Chipman and reused here

try
{
    // Connect to database and get PDO object
    require_once "../connection_info.php";
    $pdo = new PDO("mysql:host=$servername;dbname=$dbname", $username, $password);
    $pdo->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);

    // Query database and get PDOStatement object
    $zipcode = $_POST['zipcode'];
    
    // Note: Credit of SQL implementation goes to http://www.techfounder.net/2009/02/02/selecting-closest-values-in-mysql/
    // Effectively, this finds the closest 5 ZIP values in about 0.001 seconds
    $sql = "SELECT Zipcode, ABS( Zipcode - ".$zipcode.") AS distance FROM ( (SELECT Zipcode FROM `zipcode` WHERE Zipcode >= ".$zipcode." ORDER BY Zipcode LIMIT 5 ) UNION ALL ( SELECT Zipcode FROM `zipcode` WHERE Zipcode < ".$zipcode." ORDER BY Zipcode DESC LIMIT 5)) AS result ORDER BY distance LIMIT 5";

    $statement = $pdo->query($sql);

    // Close database connection
    $pdo = null;
    
    // Send the city and state
    $counter = 0;
    while ($data = $statement->fetch(PDO::FETCH_ASSOC))
    {
        if ($counter == 0)
        {
            print $data['Zipcode'];
        }
        else
        {
            print "|". $data['Zipcode'];
        }
        $counter = $counter + 1;
    }

}
catch (PDOException $e)
{
    echo "Database connection failed: " . $e->getMessage();
}

?>

