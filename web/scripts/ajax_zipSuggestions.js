/* 
 * CS137 Spring 2016 | Group 15
 * Main Author: Thomas Tai Nguyen
 * Filename: ajax_zipSuggestions.js
 */

/* Helper Functions */

function getLengthOfNumber(number)
{
    return number.toString().length;
}

function closeSuggestions()
{
    document.getElementById("suggestions").style.backgroundColor="";
    document.getElementById("suggestions").style.border="0px"; // Color taken from W3 schools
    document.getElementById("suggestions").innerHTML="";
}

function fixDigits(zipcode)
{
    length = getLengthOfNumber(zipcode);
    required = 5 - length;
    while (required != 0)
    {
        zipcode = zipcode + "0";
        required = required - 1;
    }
    return zipcode;
}

/* Action Functions */

function setZip(zipcode)
{
    closeSuggestions();
    document.orderForm.zipcode.value = zipcode;
    document.orderForm.zipcode.focus(); // This is to force the server to recalculate the new city/state pair. It will call getCityState.
    document.orderForm.zipcode.blur();
}

function showZipSuggestions(split)
{
    if (document.activeElement == document.getElementById("zipcode"))
    {
        document.getElementById("suggestions").style.backgroundColor="white";
        document.getElementById("suggestions").style.border="1px solid #A5ACB2"; // Color taken from W3 schools
        var result = "";
        for (i = 0; i < split.length; i++)
        {
            result = result + "<label id=\""+split[i].trim()+"\"class=\"zipSuggestion\" onclick=\"setZip(this.id)\">" + split[i].trim() + "</label><br>";
        }
        document.getElementById("suggestions").innerHTML=result;
    }
}

function getZipSuggestions(zipcode)
{
    if (zipcode != "" && getLengthOfNumber(zipcode) <= 5)
    {
        var checkDigits = /^\d+$/.test(zipcode);
        if (checkDigits != true)
        {
            alert("Zipcode should only contain digits.");
            document.getElementById("zipcode").value = "";
            closeSuggestions();
        }
        else
        {
            zipcode = fixDigits(zipcode); // Makes this 5 digits all the time :)
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
}

