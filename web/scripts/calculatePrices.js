/*
 * CS137 Spring 2016 | Group 15
 * Main Author: Alex Lin/Thomas Tai Nguyen
 * Filename: calculate_prices.js
 */

function ensureValidQuantity()
{
    var quantity = document.orderForm.quantity.value;
    if (quantity !== "")
    {
        if (quantity.indexOf('.') !== -1)
        {
            document.orderForm.quantity.style.backgroundColor = "yellow";
            alert("Please only enter integers for quantity.");
            document.orderForm.quantity.value = 0;
            updateCosts();
        }
        else if (quantity[0] === "-")
        {
            document.orderForm.quantity.style.backgroundColor = "yellow";
            alert("Please only enter POSITIVE numbers for quantity.");
            document.orderForm.quantity.value = 0;
            updateCosts();
        }
        else
        {
            document.orderForm.quantity.style.backgroundColor = "";
        }
    }
}

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

function getQuantity()
{
    var result = document.orderForm.quantity.value;
    if (result === "")
    {
        return 0;
    }
    else
    {
        if (result[0] === "-")
        {
            alert("Please enter a valid positive integer for quantity.");
            document.orderForm.quantity.value = 0;
            return 0;
        }
        else
        {
            result.replace(/./g, ''); // gets rid of any periods i.e. quantity = 45. or 53. or 12. and so on
            return result;
        }
    }

}

function updateSubtotal()
{
    var price = 0; // IMPLEMENT GET TOTAL PRICE!!
    var quantity = 0; // IMPLEMENT GET TOTAL PRICE!!
    var sub_price = (parseFloat(price) * parseFloat(quantity));
    document.getElementById("subtotalCost").innerHTML = sub_price.toFixed(2);
    return sub_price;
}

function updateCosts()
{
    document.getElementById("totalCost").innerHTML = (updateShippingCost() + updateSubtotal()).toFixed(2);
}

