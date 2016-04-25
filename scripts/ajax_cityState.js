/* 
 * CS137 Spring 2016 | Group 15
 * Main Author: Thomas Tai Nguyen
 * Filename: ajax_cityState.js
 */

// in progress

function changeCityState(city, state)
{
    /*
    console.log(city);
    console.log(state);
    console.log(typeof(city));
    console.log(typeof(state));
    console.log(city == "");
    console.log(state == "");
    */
    if (city == "") // && state == ""? Works without it, and doesn't work with it. Gotta fix.
    {
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