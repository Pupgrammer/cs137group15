/*
 * CS137 Spring 2016 | Group 15
 * Main Author: Thomas Tai Nguyen
 * Filename: ajax_cityState.js
 */

/* Active Functions */

document.getElementById("zipcode").onblur = function () {
    var zipcode = getZipFromInputBox();
    if (zipcode != "") {
        if (getLengthOfNumber(zipcode) == 5) {
            setTimeout(closeSuggestions, 200);
            var xhr = new XMLHttpRequest();
            xhr.onreadystatechange = function () {
                if (xhr.readyState == 4 && xhr.status == 200) {
                    var cityState = xhr.responseText;
                    var split = cityState.split(',');
                    changeCityState(split[0], split[1]);
                }
            };
            xhr.open("GET", "/checkout?event=onblur&zipcode=" + getZipFromInputBox(), true);
            xhr.send();
        }
        else if (getLengthOfNumber(zipcode) > 5) {
            alert("Zipcode should only contain 5 digits maximum.");
            document.getElementById("zipcode").value = "";
            closeSuggestions();
        }
        else {
            setTimeout(closeSuggestions, 200);
        }
    }
};

document.getElementById("zipcode").onkeyup = function () {
    var zipcode = getZipFromInputBox();
    if (zipcode != "" && getLengthOfNumber(zipcode) <= 5) {
        var checkDigits = /^\d+$/.test(zipcode);
        if (checkDigits != true) {
            alert("Zipcode should only contain digits.");
            document.getElementById("zipcode").value = "";
            closeSuggestions();
        }
        else {
            zipcode = fixDigits(zipcode);
            var xhr = new XMLHttpRequest();
            xhr.onreadystatechange = function () {
                if (xhr.readyState == 4 && xhr.status == 200) {
                    var suggestions = xhr.responseText;
                    var split = suggestions.split('|');
                    showZipSuggestions(split);
                }
            };
            xhr.open("GET", "/checkout?event=onkeyup&zipcode=" + zipcode, true);
            xhr.send();
        }
    }
};

/* Helper Functions */
function getZipFromInputBox() {
    return document.getElementById("zipcode").value;
}

function changeCityState(city, state) {
    if (city != "") {  // && state == ""? Works without it, and doesn't work with it.
        document.getElementById("city").value = city;
    }
    if (state != "") {
        document.getElementById("state").value = state;
    }
}

function getLengthOfNumber(number) {
    return number.toString().length;
}

function fixDigits(zipcode) {
    return String(zipcode + "00000").slice(0, 5);
}

function showZipSuggestions(split) {
    if (document.activeElement.valueOf() == document.getElementById("zipcode")) {
        document.getElementById("suggestions").style.backgroundColor = "white";
        document.getElementById("suggestions").style.border = "1px solid #A5ACB2"; // Color taken from W3 schools
        var result = "";
        for (var i = 0; i < split.length; i++) {
            result = result + "<label id=\"" + split[i].trim() + "\" class=\"zipSuggestion\" onclick=\"setZip(this.id)\">" + split[i].trim() + "</label><br>";
        }
        document.getElementById("suggestions").innerHTML = result;
    }
}

function closeSuggestions() {
    document.getElementById("suggestions").style.backgroundColor = "";
    document.getElementById("suggestions").style.border = "0px"; // Color taken from W3 schools
    document.getElementById("suggestions").innerHTML = "";
}

function setZip(zipcode) {
    closeSuggestions();
    document.getElementById("zipcode").value = zipcode;
    document.getElementById("zipcode").focus(); // This is to force the server to recalculate the new city/state pair. It will call getCityState.
    document.getElementById("zipcode").blur();
}
