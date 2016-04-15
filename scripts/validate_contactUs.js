/* 
 * CS137 Spring 2016 | Group 15
 * Main Author: Thomas Tai Nguyen
 * Filename: validate_contactUs.js
 */

/* Fork of validate_orderForm.js */

/* Helper Functions */ 
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
function validate_phoneNumber()
{
    /* Regex Source: http://stackoverflow.com/questions/13679310/validation-for-xxx-xxx-xxxx-or-xxxxxx-xxxx */
    var regex = /^(?:\(\d{3}\)|\d{3}-)\d{3}-\d{4}$/;
    if (!(document.contactUs.phoneNumber.value.match(regex)))
    {
        alert("Please enter a 10-digit phone number in xxx-xxx-xxxx format.");
        document.contactUs.phoneNumber.style.backgroundColor = "yellow";
        return 1;
    }
    document.contactUs.phoneNumber.style.backgroundColor = "";
    return 0;
}

function validate_email()
/* For Legacy browsers that don't support HTML5 e-mail tag. */
{
    var regex = /[^\s@]+@[^\s@]+\.[^\s@]+/;
    if (!(document.contactUs.email.value.match(regex)))
    {
        alert("Please enter a valid email address in x@y.z format.");
        document.contactUs.email.style.backgroundColor = "yellow";
        return 1;
    }
    document.contactUs.email.style.backgroundColor = "";
    return 0;
}

function validate_checkEmptyText()
{
    var firstName = document.contactUs.firstName;
    var lastName = document.contactUs.lastName;
    var email = document.contactUs.email;
    var question = document.contactUs.question;
    
    if (checkEmptyText(firstName, lastName, email, question))
    {
        alert("Please do not leave any text fields blank.");
        return 1;
    } 
    else return 0;
}

/* Main Function */
function validate()
{
    var error = 0 + validate_checkEmptyText() + validate_email() + validate_phoneNumber();
    if (error === 0)
    {
        return true;
    }
    else return false;
}