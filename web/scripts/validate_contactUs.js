/* 
 * CS137 Spring 2016 | Group 15
 * Main Author: Thomas Tai Nguyen
 * Filename: validate_contactUs.js
 */

/* Fork of validate_orderForm.js */

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
    else if (checkSymbolInText(firstName, lastName))
    {
       alert("Please use only A-Z alphabet in your name.");
       return 1;
    }
    else return 0;
}

function validate_questionLength()
{
    if (document.contactUs.question.value !== "")
    {
        var question = document.contactUs.question.value.length;
        if (question > 2000)
        {
            document.contactUs.question.style.backgroundColor = "yellow";
            alert("Please keep your message under 2000 characters.");
            return 1;
        }
        else
        {
            document.contactUs.question.style.backgroundColor = "";
            return 0;
        }
    }
    else
    {
        return 1;
    }
}

/* Main Function */
function validate()
{
    var error = 0 + validate_checkEmptyText() + validate_email() + validate_phoneNumber() + validate_questionLength();
    if (error === 0)
    {
        return true;
    }
    else return false;
}