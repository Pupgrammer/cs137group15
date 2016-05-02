/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


function getSubtotal(){
    var price = document.getElementById("getPriceFromJS").innerHTML;
    var quantity = document.getElementByID("quantity").innerHTML;
    alert("getting subtotal"); //not working?
    var sub_price = (price * quantity);
    $.getSubtotal('shop.php',{postprice:sub_price},function(data)
    {
       $('#sub_price').html(data); 
        
    });
}

/*function calculateTotalPrice(){
    var totalPrice = 
    
}*/
