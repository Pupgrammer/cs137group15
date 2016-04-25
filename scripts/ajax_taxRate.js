/* 
 * CS137 Spring 2016 | Group 15
 * Main Author: Thomas Tai Nguyen
 * Filename: ajax_taxRate.js
 */

function changeTaxRate(taxRate)
{
    document.getElementById("taxRate").value == taxRate;
}

function getTaxRate(zipcode)
{
    var xhr = new XMLHttpRequest();
    xhttp.onreadystatechange = function() {
        if (xhr.readyState == 4 && xhr.status == 200)
        {
            var taxRate = xhr.responseText;
            if (taxRate == -1)
            {
                /* do something */
            }
            else
            {
                changeTaxRate(taxRate);
            }
        }
    };
    xhr.open("POST", "getTaxRate.php", true);
    xhr.setRequestHeader("content-type", "application/x-www-form-urlencoded");
    xhr.send("zipcode=" + zipcode);
}




