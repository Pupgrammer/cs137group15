/* 
 * CS137 Spring 2016 | Group 15
 * Main Author: Thomas Tai Nguyen
 * Filename: ajax_cityState.js
 */

// in progress

function closeSuggestions()
{
    document.getElementById("suggestions").style.backgroundColor="";
    document.getElementById("suggestions").style.border="0px"; // Color taken from W3 schools
    document.getElementById("suggestions").innerHTML="";
}

function changeCityState(city, state)
{
    if (city == "") // && state == ""? Works without it, and doesn't work with it. Gotta fix.
    {
        // Temporary Error for debugging purposes. Probably have to change this.
        document.getElementById("city").value = "DOES NOT EXIST";
        document.getElementById("state").value = "DOES NOT EXIST";
    }
    else
    {
        document.getElementById("city").value = city;
        document.getElementById("state").value = state;
    }
}

function getCityState(zipcode)
{   // This is being called twice. Fix this issue, thinking about also the dropdown box bug (where it won't go away unless you select something.)
    if (zipcode !== "") // If ZIP isn't empty and the user selected a ZIP
    {
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
}