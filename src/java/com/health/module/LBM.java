package com.health.module;

import jakarta.xml.soap.SOAPFactory;
import jakarta.xml.soap.SOAPFault;
import jakarta.xml.ws.soap.SOAPFaultException;

/**
 * Lean Body Mass (LBM) evaluation module.
 */
public class LBM {

    public String calculateLBM(double weight, double bodyFatPercentage, String gender) {

        if (weight <= 0) {
            throwSoapFault("Weight must be a positive number or greater than zero.");
        }

        if (bodyFatPercentage < 0 || bodyFatPercentage > 100) {
            throwSoapFault("Body Fat Percentage must be between 0 and 100.");
        }

        if (gender == null || (!gender.equalsIgnoreCase("male") && !gender.equalsIgnoreCase("female"))) {
            throwSoapFault("Gender must be 'male' or 'female'.");
        }

        double fatMass = weight * (bodyFatPercentage / 100.0);
        double lbm = weight - fatMass;

        String category;
        boolean isMale = gender.equalsIgnoreCase("male");

        if (isMale) {
            if (bodyFatPercentage < 6.0) category = "Essential Fat / Too Lean";
            else if (bodyFatPercentage < 14.0) category = "Athletic";
            else if (bodyFatPercentage < 18.0) category = "Fitness / Healthy";
            else if (bodyFatPercentage < 25.0) category = "Average";
            else category = "High Body Fat";
        } else {
            if (bodyFatPercentage < 14.0) category = "Essential Fat / Too Lean";
            else if (bodyFatPercentage < 21.0) category = "Athletic";
            else if (bodyFatPercentage < 25.0) category = "Fitness / Healthy";
            else if (bodyFatPercentage < 32.0) category = "Average";
            else category = "High Body Fat";
        }

        return String.format("Lean Body Mass = %.2f kg. \n Category = %s", lbm, category);
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