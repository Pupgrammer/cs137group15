/* 
 * CS137 Spring 2016 | Group 15
 * Main Author: Thomas Tai Nguyen
 *                                  NOTE: Not Thomas's job to implement. Someone else must implement this via database.
 * Filename: ajax_shippingCost.js
 */

/* Functions */

// Currently not AJAX, but JavaScript instead. Change to database later.
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
