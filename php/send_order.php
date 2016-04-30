<?php

if ( isset($_POST['product_name']) && isset($_POST['product_name']) && isset($_POST['quantity']) && isset($_POST['first_name']) && isset($_POST['last_name']) && isset($_POST['email']) && isset($_POST['phone_number']) && isset($_POST['address']) && isset($_POST['zipcode']) && isset($_POST['city']) && isset($_POST['state']) && isset($_POST['shipping_method']) && isset($_POST['credit_card'])) {
    $order = "INSERT INTO inf124grp15.order_info (order_id, product_number, product_name, quantity, first_name, last_name, email, phone_number, address, zipcode, city, state, shipping_method, credit_card)  
        VALUES (:order_id, :product_number, :product_name, :quantity, :first_name, :last_name, :email, :phone_number, :address, :zipcode, :city, :state, :shipping_method, :credit_card)";
    $order_stmt = $connection_info->prepare($order);
    $order_stmt = $execute(array(
        ':order_id' => "null",
        ':product_number' => $data['product_number'],
        ':product_name' => $data['friendly_name'],
        ':quanity' => $_POST['quantity'],
        ':first_name' => $_POST['first_name'],
        ':last_name' => $_POST['last_name'],
        ':email' => $_POST['email'],
        ':phone_number' => $_POST['phone_number'],
        ':address' => $_POST['address'],
        ':zipcode' => $_POST['zipcode'],
        ':city' => $_POST['city'],
        ':state' => $_POST['state'],
        ':shipping_method' => $_POST['shipping_method'],
        ':credit_card' => $_POST['credit_card'],
    ));   
}

?>
