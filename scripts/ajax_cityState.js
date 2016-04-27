/* 
 * CS137 Spring 2016 | Group 15
 * Main Author: Thomas Tai Nguyen
 * Filename: ajax_cityState.js
 */

// in progress

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

/* Active Functions */

function changeCityState(city, state)
{
    if (city != "") // && state == ""? Works without it, and doesn't work with it.
    {
        document.getElementById("city").value = city;
    }
    if (state != "")
    {
        document.getElementById("state").value = state;
    }
}

function getCityState(zipcode)
{ 
    if (zipcode != "")
    {
        if (getLengthOfNumber(zipcode) == 5)
        {
            setTimeout(closeSuggestions, 200);
            var xhr = new XMLHttpRequest();
            xhr.onreadystatechange = function() {
                if (xhr.readyState == 4 && xhr.status == 200)
                {
                    var cityState = xhr.responseText;
                    var split = cityState.split(',');
                    changeCityState(split[0], split[1]);
                }
            };
            xhr.open("POST", "./php/getCityState.php", true);
            xhr.setRequestHeader("content-type", "application/x-www-form-urlencoded");
            xhr.send("zipcode=" + zipcode);
        }
        else if (getLengthOfNumber(zipcode) > 5)
        {
            alert("Zipcode should only contain 5 digits maximum.");
            document.getElementById("zipcode").value = "";
            closeSuggestions();
        }
        else
        {
            setTimeout(closeSuggestions, 200);
        }
    }
}