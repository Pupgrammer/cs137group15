/* 
 * CS137 Spring 2016 | Group 15
 * Main Author: Alex Lin/Thomas Tai Nguyen
 * Filename: calculate_prices.js
 */

function updateShippingCost()
{
    var shippingMethod = document.orderForm.shipping.value;
    if (shippingMethod == "Ground")
    {
        document.getElementById("shippingCost").innerHTML = "0.00";
        return 0;
    }
    else if (shippingMethod == "One Day")
    {
        document.getElementById("shippingCost").innerHTML = "10.00";
        return 10.00;
    }
    else if (shippingMethod == "Two Day")
    {
        document.getElementById("shippingCost").innerHTML = "5.00";
        return 5.00;
    }
}

function updateSubtotal()
{
    var price = document.getElementById("getPriceFromJS").innerHTML;
    price = price.substr(1, price.length); // gets rid of $ sign
    price = price.replace(/,/g, ''); // gets rid of commas
    var quantity = document.orderForm.quantity.value;    
    var sub_price = (parseFloat(price) * parseFloat(quantity));
    document.getElementById("subtotalCost").innerHTML = sub_price.toFixed(2);
    return sub_price;
}

function updateCosts()
{
    document.getElementById("totalCost").innerHTML = (updateShippingCost() + updateSubtotal()).toFixed(2);
}

