/* 
 * CS137 Spring 2016 | Group 15
 * Main Author: Thomas Tai Nguyen
 *                                  NOTE: Not Thomas's job to implement. Someone else must implement this via database.
 * Filename: ajax_shippingCost.js
 */

/* Functions */

// Currently not AJAX, but JavaScript instead. Change to database later if we have time (this would be the 3rd AJAX feature though. Not required.)
function updateShippingCost()
{
    var shippingMethod = document.orderForm.shipping.value;
    if (shippingMethod == "Ground")
    {
        document.getElementById("shippingCost").innerHTML = "0.00";
    }
    else if (shippingMethod == "One Day")
    {
        document.getElementById("shippingCost").innerHTML = "10.00";
    }
    else if (shippingMethod == "Two Day")
    {
        document.getElementById("shippingCost").innerHTML = "5.00";
    }
    else if (shippingMethod == "default") // Just in case.
    {
        alert("Please select a valid shipping method.");
        document.getElementById("shippingCost").innerHTML = "-";
    }
}
