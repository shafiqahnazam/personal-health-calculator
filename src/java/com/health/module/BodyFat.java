package com.health.module;

import jakarta.xml.soap.SOAPFactory;
import jakarta.xml.soap.SOAPFault;
import jakarta.xml.ws.soap.SOAPFaultException;

/**
 * Body Fat Percentage computation via the U.S. Navy Circumference Method.
 */
public class BodyFat {

    public String calculateBodyFat(double waist, double neck, double height, double hip, String gender) {
        
        // 1. Core structural parameter verification
        if (gender == null || (!gender.equalsIgnoreCase("male") && !gender.equalsIgnoreCase("female"))) {
            throwSoapFault("Invalid Input Error: Gender must be specified as 'male' or 'female'.");
        }
       
        if (waist <= 0 || neck <= 0 || height <= 0) {
            throwSoapFault("Waist, neck, and hip must be a positive number or greater than zero.");
        }

        boolean isMale = gender.equalsIgnoreCase("male");

        // 2. Separate validation rules cleanly by gender to avoid cross-contamination errors
        if (isMale) {
            if (waist <= neck) {
                throwSoapFault("Waist circumference must be a positive number or greater than neck circumference.");
            }
        } else {
            // Female specific validation rules
            if (hip <= 0) {
                throwSoapFault("Hip circumference must be a positive number or greater than zero for females.");
            }
            if ((waist + hip) <= neck) {
                throwSoapFault("Combined waist and hip metrics must be a positive number or greater than neck size.");
            }
        }



        double bfp = 0.0;
        
        // 4. Run official Navy equations using base-10 log calculations
        if (isMale) {
            bfp = 495 / (1.0324 - 0.19077 * Math.log10(waist - neck) + 0.15456 * Math.log10(height)) - 450;
        } else {
            bfp = 495 / (1.29579 - 0.35004 * Math.log10(waist + hip - neck) + 0.22100 * Math.log10(height)) - 450;
        }

        // Catch edge case calculation anomalies safely
        if (bfp < 0 || Double.isNaN(bfp) || Double.isInfinite(bfp)) {
            bfp = 0.0;
        }

        // 5. Determine body composition status category bounds
        String status;
        if (isMale) {
            if (bfp < 6.0) status = "Essential Fat / Too Low - Consider reviewing nutritional intake.";
            else if (bfp >= 6.0 && bfp < 14.0) status = "Athletic - Excellent body composition with lean muscle focus.";
            else if (bfp >= 14.0 && bfp < 18.0) status = "Fitness - Healthy and well-proportioned body fat level.";
            else if (bfp >= 18.0 && bfp < 25.0) status = "Average - Acceptable ranges, maintaining an active routine is advised.";
            else status = "High Body Fat - Watching your caloric intake and active cardio routines is recommended.";
        } else {
            if (bfp < 14.0) status = "Essential Fat / Too Low - Consider reviewing nutritional intake.";
            else if (bfp >= 14.0 && bfp < 21.0) status = "Athletic - Excellent body composition with lean muscle focus.";
            else if (bfp >= 21.0 && bfp < 25.0) status = "Fitness - Healthy and well-proportioned body fat level.";
            else if (bfp >= 25.0 && bfp < 32.0) status = "Average - Acceptable ranges, maintaining an active routine is advised.";
            else status = "High Body Fat - Watching your caloric intake and active cardio routines is recommended.";
        }

        return String.format("Your Body Fat Percentage is: %.2f%% Status: %s", bfp, status);
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