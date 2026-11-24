package com.health.module;

import jakarta.xml.soap.SOAPFactory;
import jakarta.xml.soap.SOAPFault;
import jakarta.xml.ws.soap.SOAPFaultException;

/**
 * Body Mass Index (BMI) calculation module.
 */
public class BMI {

    public String calculateBMI(double weight, double height) {
        
        if (height <= 0 || weight <= 0) {
            throwSoapFault("Invalid Input Error: Weight and height must be greater than zero.");
        }
        
        // Safety Unit Check: Convert height to meters if user typed centimeters (e.g., 175 instead of 1.75)
        double heightInMeters = height;
        if (height > 3.0) {
            heightInMeters = height / 100.0;
        }
        
        double bmi = weight / (heightInMeters * heightInMeters);
        
        String status;
        if (bmi < 18.5) {
            status = "Underweight - You may need to increase your nutritional intake.";
        } else if (bmi >= 18.5 && bmi < 25.0) {
            status = "Normal weight - Excellent! Keep maintaining a healthy lifestyle.";
        } else if (bmi >= 25.0 && bmi < 30.0) {
            status = "Overweight - Consider watching your diet and exercising regularly.";
        } else {
            status = "Obesity - Highly recommended to consult a doctor or health expert.";
        }
        
        return String.format("Your BMI is: %.2f. Status: %s", bmi, status);
    }

    private void throwSoapFault(String message) {
        try {
            SOAPFault fault = SOAPFactory.newInstance().createFault();
            fault.setFaultString(message);
            throw new SOAPFaultException(fault);
        } catch (SOAPFaultException sfe) {
            throw sfe;
        } catch (Exception e) {
            throw new RuntimeException("SOAP Fault Engine Failed: " + e.getMessage());
        }
    }
}