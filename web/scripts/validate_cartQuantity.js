/* 
 * CS137 Spring 2016 | Group 15
 * Main Author: Thomas Tai Nguyen
 * Filename: scripts/validate_cartQuantity.js
 */

function validate_cartQuantity(element)
{
    var id = element.id;
    var quantityInputBox = document.getElementById(id);
    var quantity = document.getElementById(id).value;
    if (parseInt(quantity) === 0 || parseInt(quantity) < 0)
    {
        quantityInputBox.style.backgroundColor = "yellow";
        alert("For quantity, please enter a number that is greater than 0.");
        quantityInputBox.value = 1;
    }
    else
    {
        quantityInputBox.style.backgroundColor = "";
    }
}


