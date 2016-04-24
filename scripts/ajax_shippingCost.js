/* 
 * CS137 Spring 2016 | Group 15
 * Main Author: Thomas Tai Nguyen
 * Filename: ajax_shippingCost.js
 */

/* Functions */

// Currently not AJAX, but JavaScript instead. If I have time, I will change it later.
function updateShippingCost()
{
    var shippingMethod = document.orderForm.shipping.value;
    if (shippingMethod == "ground")
    {
        document.getElementById("shippingCost").innerHTML = 0.00;
    }
    else if (shippingMethod == "oneday")
    {
        document.getElementById("shippingCost").innerHTML = 9.99;
    }
    else if (shippingMethod == "twoday")
    {
        document.getElementById("shippingCost").innerHTML = 4.99;
    }
    else if (shippingMethod == "default") // Just in case.
    {
        alert("Please select a valid shipping method.");
        document.getElementById("shippingCost").innerHTML = "-";
    }
}
