package com.health.module;

import jakarta.xml.soap.SOAPFactory;
import jakarta.xml.soap.SOAPFault;
import jakarta.xml.ws.soap.SOAPFaultException;

/**
 * Standalone Basal Metabolic Rate calculation module.
 */
public class BMR {

    public String calculateBMR(double weight, double height, String gender, int age) {

        if (weight <= 0 || height <= 0 || age <= 0) {
            throwSoapFault("Invalid Input Error: Weight, height and age must be greater than zero.");
        }

        if (gender == null || (!gender.equalsIgnoreCase("male") && !gender.equalsIgnoreCase("female"))) {
            throwSoapFault("Invalid Input Error: Gender must be 'male' or 'female'.");
        }

        double bmr;
        if (gender.equalsIgnoreCase("male")) {
            bmr = (10 * weight) + (6.25 * height) - (5 * age) + 5;
        } else {
            bmr = (10 * weight) + (6.25 * height) - (5 * age) - 161;
        }

        return String.format("Your BMR is: %.2f calories/day.", bmr);
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