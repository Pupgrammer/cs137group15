/*
 * CS137 Spring 2016 | Group 15
 * Main Author: Thomas Tai Nguyen
 * Filename: ajax_cityState.js
 */

/* Helper Functions */

function getLengthOfNumber(number) {
    return number.toString().length;
}

function closeSuggestions() {
    document.getElementById("suggestions").style.backgroundColor = "";
    document.getElementById("suggestions").style.border = "0px"; // Color taken from W3 schools
    document.getElementById("suggestions").innerHTML = "";
}

/* Active Functions */

function changeCityState(city, state) {
    if (city != "") {  // && state == ""? Works without it, and doesn't work with it.
        document.getElementById("city").value = city;
    }
    if (state != "") {
        document.getElementById("state").value = state;
    }
}

// function getCityState(zipcode) {
//     if (zipcode != "") {
//         if (getLengthOfNumber(zipcode) == 5) {
//             setTimeout(closeSuggestions, 200);
//             var xhr = new XMLHttpRequest();
//             xhr.onreadystatechange = function () {
//                 if (xhr.readyState == 4 && xhr.status == 200) {
//                     var cityState = xhr.responseText;
//                     var split = cityState.split(',');
//                     changeCityState(split[0], split[1]);
//                 }
//             };
//             xhr.open("POST", "./php/getCityState.php", true);
//             xhr.setRequestHeader("content-type", "application/x-www-form-urlencoded");
//             xhr.send("zipcode=" + zipcode);
//         }
//         else if (getLengthOfNumber(zipcode) > 5) {
//             alert("Zipcode should only contain 5 digits maximum.");
//             document.getElementById("zipcode").value = "";
//             closeSuggestions();
//         }
//         else {
//             setTimeout(closeSuggestions, 200);
//         }
//     }
// }

document.getElementById("zipcode").onblur = function () {
    var request = new XMLHttpRequest();
    request.open('GET', '/checkout?event=onblur&zipcode=' + getZipFromInputBox(), true);

    request.onload = function () {
        var alertText = "request.onload()\n";
        if (request.status >= 200 && request.status < 400) {
            alertText += "request.status: " + request.status + "\n";
            alertText += "request.responseText: " + request.responseText + "\n";
            var cityState = request.responseText;
            var split = cityState.split(',');
            changeCityState(split[0], split[1]);
        } else {
            alertText += "We reached our target server, but it returned an error\n";
        }
        alert(alertText);
    };

    request.onerror = function () {
        alert("There was a connection error of some sort");
    };

    request.send();
};

function getZipFromInputBox() {
    return document.getElementById("zipcode").value;
}

document.getElementById("zipcode").onkeyup = function () {
    var request = new XMLHttpRequest();
    request.open('GET', '/checkout?event=onkeyup&zipcode=' + getZipFromInputBox(), true);

    request.onload = function () {
        var alertText = "request.onload()\n";
        if (request.status >= 200 && request.status < 400) {
            alertText += "request.status: " + request.status + "\n";
            alertText += "request.responseText: " + request.responseText + "\n";
            var cityState = request.responseText;
            var split = cityState.split(',');
            changeCityState(split[0], split[1]);
        } else {
            alertText += "We reached our target server, but it returned an error\n";
        }
        alert(alertText);
    };

    request.onerror = function () {
        alert("There was a connection error of some sort");
    };

    request.send();
};
