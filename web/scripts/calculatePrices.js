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
        return parseFloat(0);
    }
    else if (shippingMethod == "One Day")
    {
        document.getElementById("shippingCost").innerHTML = "10.00";
        return parseFloat(10.00);
    }
    else if (shippingMethod == "Two Day")
    {
        document.getElementById("shippingCost").innerHTML = "5.00";
        return parseFloat(5.00);
    }
}

function getSubtotal()
{
    return parseFloat(document.getElementById("subtotalCost").innerHTML);
}

function updateCosts()
{
    console.log(getSubtotal());
    console.log(updateShippingCost());
    document.getElementById("totalCost").innerHTML = (updateShippingCost() + getSubtotal()).toFixed(2);
}

