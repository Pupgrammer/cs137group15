/*
 * CS137 Spring 2016 | Group 15
 * Main Author: Thomas Tai Nguyen
 * Filename: validate_orderForm.js
 */

// I'm following the example Slide 79 on Lecture 1. Even though it's programmatically sloppy. - Thomas
// If anyone else knows how to check for negative integer, please let me know (right now, I'm doing document.orderForm.input.value[0] === "-" which is a bit crude.)

var alertText = [];

/* Helper Functions */

function checkSymbolInText()
{

    var error = 0;
    for (var i = 0; i < arguments.length; i++)
    {
        if (/^[a-zA-Z]+$/.test(arguments[i].value) === false)
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
function validate_creditCard()
{
    if (!parseInt(document.orderForm.creditCard.value) > 0 || document.orderForm.creditCard.value[0] === "-" || getLengthOfNumber(document.orderForm.creditCard.value) !== 16)
    {
        document.orderForm.creditCard.style.backgroundColor = "yellow";
        alertText.push("Please enter a valid, 16-digit credit card number.");
        return 1;
    }
    document.orderForm.creditCard.style.backgroundColor = "";
    return 0;
}

function validate_quantity()
{
    if (!parseInt(document.orderForm.quantity.value) > 0 || document.orderForm.quantity.value[0] === "-")
    {
        document.orderForm.quantity.style.backgroundColor = "yellow";
        alertText.push("Please enter a valid quantity (greater than 0).");
        return 1;
    }
    else if (parseInt(document.orderForm.quantity.value) > 1000)
    {
        document.orderForm.quantity.style.backgroundColor = "yellow";
        alertText.push("For orders greater than 1000 quantity, please message us directly (Sales/Order department) via Contact Us for bulk pricing.");
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
        document.orderForm.phoneNumber.style.backgroundColor = "yellow";
        alertText.push("Please enter a 10-digit phone number in xxx-xxx-xxxx format.");
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
        document.orderForm.email.style.backgroundColor = "yellow";
        alertText.push("Please enter a valid email address in x@y.z format.");
        return 1;
    }
    document.orderForm.email.style.backgroundColor = "";
    return 0;
}
function validate_shippingAddress()
{
    var error = 0; // Found an error.
    var streetAddress = document.orderForm.streetAddress;
    var zipcode = document.orderForm.zipcode;
    var city = document.orderForm.city;
    var state = document.orderForm.state;

    if (checkEmptyText(streetAddress, city, state, zipcode))
    {
        alertText.push("Please do not leave your street address, zipcode, city, or state blank.");
        error = 1;
        console.log("Error found.");
    }
    else
    {
        document.orderForm.zipcode.style.backgroundColor = "";
    }

    if (checkSymbolInText(city, state))
    {
        alertText.push("Please only enter alphabetical characters for your city and state.");
        error = 1;
    }

    if (error === 1)
    {
        return 1;
    }
    else return 0;
}


function validate_checkEmptyText()
{
    var firstName = document.orderForm.firstName;
    var lastName = document.orderForm.lastName;
    //var email = document.orderForm.email;

    if (checkEmptyText(firstName, lastName))
    {
        alertText.push("Please do not leave your personal information fields blank.");
        return 1;
    }
    else if (checkSymbolInText(firstName, lastName))
    {
        alertText.push("Please only use A-Z alphabet in your name.");
        return 1;
    }
    else return 0;
}

function validate_shippingMethod()
{
    var shippingMethod = document.orderForm.shipping.value;

    if (shippingMethod === "default")
    {
        document.orderForm.shipping.style.backgroundColor = "yellow";
        alertText.push("Please select a valid shipping method.");
        return 1;
    }
    else
    {
        document.orderForm.shipping.style.backgroundColor = "";
        return 0;
    }
}

/* Main Function */
function validate()
{
    alertText = [];
    var error = 0 + validate_checkEmptyText() + validate_email() + validate_phoneNumber() + validate_shippingAddress() + validate_shippingMethod() + validate_creditCard();
    if (error === 0)
    {
        return true;
    }
    else {
        alert(alertText.join("\n"));
        return false;
    }
}
