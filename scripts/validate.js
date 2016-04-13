/* 
 * CS137 Spring 2016 | Group 15
 * Main Author: Thomas Tai Nguyen
 * Filename: validate.js
 */

// I'm following the example Slide 79 on Lecture 1. Even though it's programmatically sloppy. - Thomas
// If anyone else knows how to check for negative integer, please let me know (right now, I'm doing document.orderForm.input.value[0] === "-" which is a bit crude.)

var maxProductNumber = 10; /* The maximum number of products available. */

/* Helper Functions */ 
function getLengthOfNumber(number)
{
    return number.toString().length;
}

function checkEmptyText()
{
    var error = 0;
    for (var i = 0; i < arguments.length; i++)
    {
        if (arguments[i].value.length === 0)
        {
            error = 1; /* A field was empty. You want to continue looking though. */
            arguments[i].style.backgroundColor = "yellow";
        }
        else
        {
            arguments[i].style.backgroundColor = "";
        }
    }
    
    if (error > 0)
    {
        return true;
    }
    else return false;
}

/* Validation Functions */
function validate_productNumber()
{
    if (!parseInt(document.orderForm.productNumber.value) > 0 || document.orderForm.productNumber.value[0] === "-" || parseInt(document.orderForm.productNumber.value) > maxProductNumber)
    {
        alert("Please enter a valid product number that exists (greater than 0).");
        document.orderForm.productNumber.style.backgroundColor = "yellow";
        return 1;
    }
    document.orderForm.productNumber.style.backgroundColor = "";
    return 0;
}

function validate_creditCard()
{
    if (!parseInt(document.orderForm.creditCard.value) > 0 || document.orderForm.productNumber.value[0] === "-" || getLengthOfNumber(document.orderForm.creditCard.value) !== 16)
    {
        alert("Please enter a valid, 16-digit credit card number.");
        document.orderForm.creditCard.style.backgroundColor = "yellow";
        return 1;
    }
    document.orderForm.creditCard.style.backgroundColor = "";
    return 0;
}
    
function validate_quantity()
{
    if (!parseInt(document.orderForm.quantity.value) > 0 || document.orderForm.quantity.value[0] === "-")
    {
        alert("Please enter a valid quantity (greater than 0).");
        document.orderForm.quantity.style.backgroundColor = "yellow";
        return 1;
    }
    document.orderForm.quantity.style.backgroundColor = "";
    return 0;
}

function validate_phoneNumber()
{
    /* Regex Source: http://stackoverflow.com/questions/13679310/validation-for-xxx-xxx-xxxx-or-xxxxxx-xxxx */
    var regex = /^(?:\(\d{3}\)|\d{3}-)\d{3}-\d{4}$/;
    if (!(document.orderForm.phoneNumber.value.match(regex)))
    {
        alert("Please enter a 10-digit phone number in xxx-xxx-xxxx format.");
        document.orderForm.phoneNumber.style.backgroundColor = "yellow";
        return 1;
    }
    document.orderForm.phoneNumber.style.backgroundColor = "";
    return 0;
}

function validate_email()
/* For Legacy browsers that don't support HTML5 e-mail tag. */
{
    var regex = /[^\s@]+@[^\s@]+\.[^\s@]+/;
    if (!(document.orderForm.email.value.match(regex)))
    {
        alert("Please enter a valid email address in x@y.z format.");
        document.orderForm.email.style.backgroundColor = "yellow";
        return 1;
    }
    document.orderForm.email.style.backgroundColor = "";
    return 0;
}

function validate_checkEmptyText()
{
    var firstName = document.orderForm.firstName;
    var lastName = document.orderForm.lastName;
    var email = document.orderForm.email;
    var shippingAddress = document.orderForm.shippingAddress;
    
    if (checkEmptyText(firstName, lastName, email, shippingAddress))
    {
        alert("Please do not leave any text fields blank.");
        return 1;
    } 
    else return 0;
}

/* Main Function */
function validate()
{
    var error = 0 + validate_productNumber() + validate_quantity() + validate_checkEmptyText() + validate_email() + validate_phoneNumber() + validate_creditCard();
    if (error === 0)
    {
        return true;
    }
    else return false;
}