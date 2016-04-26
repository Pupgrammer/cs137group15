/* 
 * CS137 Spring 2016 | Group 15
 * Main Author: Thomas Tai Nguyen
 * Filename: ajax_zipSuggestions.js
 */

// Currently, suggestions are based on distance from each other numerically (i.e. if you type in 01, closest is 02 and 03, etc, not 011, 012)
// I don't know how to do the other one, so I'm going to leave it at this for now. I might change it for closest ZIP proximity based on Latitude/Longitude.


function setZip(zipcode)
{
    document.getElementById("suggestions").style.backgroundColor="";
    document.getElementById("suggestions").style.border="0px";
    document.getElementById("suggestions").innerHTML="";
    document.orderForm.zipcode.value = zipcode;
    
    // This is to force the server to recalculate the new city/state pair. It will call getCityState, probably for the 2nd time.
    document.orderForm.zipcode.focus();
    document.orderForm.zipcode.blur();
}

function showZipSuggestions(split)
{
    /* Current Bug: This won't go away unless someone selects an item from the menu. */
    var result = "";
    for (i = 0; i < split.length; i++)
    {
        result = result + "<label id=\""+split[i]+"\" onclick=\"setZip(this.id)\">" + split[i] + "</label><br>";
    }
    document.getElementById("suggestions").style.backgroundColor="white";
    document.getElementById("suggestions").style.border="1px solid #A5ACB2"; // Color taken from W3 schools
    document.getElementById("suggestions").innerHTML=result;
}

function getZipSuggestions(zipcode)
{
    if (zipcode !== "")
    {
        var xhr = new XMLHttpRequest();
        xhr.onreadystatechange = function() {
            if (xhr.readyState == 4 && xhr.status == 200)
            {
                var suggestions = xhr.responseText;
                suggestions = suggestions.slice(0, -1);
                var split = suggestions.split('|');
                showZipSuggestions(split);
            }
        };
        xhr.open("POST", "./php/getZipSuggestions.php", true);
        xhr.setRequestHeader("content-type", "application/x-www-form-urlencoded");
        xhr.send("zipcode=" + zipcode);
    }
}

