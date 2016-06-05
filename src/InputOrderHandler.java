/*
CS137 Spring 2016 | Group 15
Main Author: Thomas Tai Nguyen
Filename: src/InputOrderHandler.java
*/


/* Example Inputs (Format: parameterName/input)
firstName/asdasd
lastName/asdasd
email/test@test.com
phoneNumber/123-456-7890
streetAddress/1234 ASD Street
zipcode/45351
city/OSGOOD
state/OH
shipping/One Day
creditCard/1231231231231231
*/

import java.util.Map;
import java.util.HashMap;

public class InputOrderHandler {
    
    HashMap<String, String> order;
    boolean error = false;
    String debug = "";
    
    public InputOrderHandler(HashMap<String, String> new_order)
    {
        order = new_order;
    }
    
    private void addErrorMessage(String message)
    {
        debug = debug + message;
        error = true;
    }
    
    public String getErrorMessage()
    {
        return debug + "<br>Your order has not been processed. Please try again.";
    }
    
    private void checkEmptyText()
    {
        boolean counter = false;
        for (Map.Entry<String, String> element : order.entrySet())
        {
            String key = element.getKey();
            String value = element.getValue();
            if (value.equals(""))
            {
                addErrorMessage("An empty input field was found for " + key + ".<br>");
                counter = true;
            }
        }
        if (counter)
        {
            addErrorMessage("<br>Please note that all your other information will not be screened for correctness until all fields are non-empty.<br>");
        }
    }
    
    private void checkValidNumber(String zipcode, String credit_card) // Hacky fix, but only 2 arguments allowed for now.
    {
        String regex_pattern = "[0-9]+";
        if (!zipcode.matches(regex_pattern))
        {
            addErrorMessage("Your zipcode, " + zipcode + ", is not a valid, positive number.<br>");
        }
        if (!credit_card.matches(regex_pattern))
        {
            addErrorMessage("Your credit card number, " + credit_card + ", is not a valid, positive number.<br>");
        }
    }
   
    
    private void validate_names(String firstName, String lastName)
    {
        String regex_pattern = "[a-zA-Z]+";
        if (!firstName.matches(regex_pattern) || !lastName.matches(regex_pattern))
        {
            addErrorMessage("Your first or last name, " + firstName + " " + lastName + ", can only contain alphabet characters.<br>");
        }
    }
    
    private void validate_email(String email)
    {
        // Note: Regex was found at http://www.java2novice.com/java-collections-and-util/regex/valid-email-address/
        if (!email.matches("^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{1,})$"))
        {
            addErrorMessage("You've provided an invalid e-mail: " + email + ".<br>");
        }
    }
    
     private void validate_phoneNumber(String phone_number)
    {
        if (!phone_number.matches("\\d{3}[-]\\d{3}[-]\\d{4}"))
        {
            addErrorMessage("Your phone number, " + phone_number + ", must be in a xxx-xxx-xxxx format.<br>");
        }
    }
    
    private void validate_zipcode(String zipcode)
    {
        Integer length = zipcode.length();
        if (!length.equals(5))
        {
            addErrorMessage("Your zipcode of " + zipcode + " must be exactly 5 digits long.<br>");
        }
    }
    
    private void validate_cityState(String city, String state)
    {
        String regex_pattern = "[a-zA-Z]+";
        if (!city.matches(regex_pattern) || !state.matches(regex_pattern))
        {
            addErrorMessage("Your city or state, (" + city + ", " + state + "), can only contain alphabet characters.<br>");
        }
    }
    
    private void validate_creditCard(String card_number)
    {
        Integer length = card_number.length();
        if (!length.equals(16))
        {
            addErrorMessage("Your credit card number " + card_number + " must be exactly 16 digits long.<br>");
        }
    }
    
    public boolean validate()
    {
        checkEmptyText(); // All inputs were non-empty, so proceed to individual validations.
        if (error == false)
        {
            checkValidNumber(order.get("zipcode"), order.get("creditCard"));
            validate_names(order.get("firstName"), order.get("lastName"));
            validate_email(order.get("email"));
            validate_phoneNumber(order.get("phoneNumber"));
            validate_zipcode(order.get("zipcode"));
            validate_cityState(order.get("city"), order.get("state"));
            validate_creditCard(order.get("creditCard"));
        }
        
        if (error == false)
        {
            return true;
        }
        else return false;
    }
}
