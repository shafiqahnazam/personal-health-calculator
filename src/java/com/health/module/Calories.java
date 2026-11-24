package com.health.module;

import jakarta.xml.soap.SOAPFactory;
import jakarta.xml.soap.SOAPFault;
import jakarta.xml.ws.soap.SOAPFaultException;

/**
 * Total Daily Energy Expenditure (TDEE) and Calorie Tracking module.
 */
public class Calories {

    public String calculateTDEE(double weightKg, double heightCm, int age, String gender, int weeklyMinutes) {

        if (gender == null || (!gender.equalsIgnoreCase("male") && !gender.equalsIgnoreCase("female"))) {
            throwSoapFault("Gender must be specified as 'male' or 'female'.");
        }

        if (weightKg <= 0 || heightCm <= 0 || age <= 0 || weeklyMinutes < 0) {
            throwSoapFault("Activity minutes must be a positive number or greater than zero.");
        }

        // Mifflin-St Jeor Calculation
        double bmr;
        if (gender.equalsIgnoreCase("male")) {
            bmr = (10 * weightKg) + (6.25 * heightCm) - (5 * age) + 5;
        } else {
            bmr = (10 * weightKg) + (6.25 * heightCm) - (5 * age) - 161;
        }

        // TDEE Activity Multipliers
        double multiplier;
        if (weeklyMinutes <= 60) multiplier = 1.2;
        else if (weeklyMinutes <= 150) multiplier = 1.375;
        else if (weeklyMinutes <= 300) multiplier = 1.55;
        else if (weeklyMinutes <= 420) multiplier = 1.725;
        else multiplier = 1.9;

        double tdee = Math.ceil(bmr * multiplier);
        double mildLoss = tdee - 250;
        double moderateLoss = tdee - 500;
        double mildGain = tdee + 250;
        double moderateGain = tdee + 500;

        return String.format(
            "===== Calories Burn Rate Report =====\n" +
            "Basal Metabolic Rate (BMR): %.2f kcal/day\n" +
            "Total Daily Energy Expenditure (TDEE): %.0f kcal/day\n\n" +
            "Calorie Management Goals:\n" +
            "  - Weight Maintenance: %.0f kcal/day\n" +
            "  - Mild Weight Loss (-0.25kg/wk): %.0f kcal/day\n" +
            "  - Moderate Weight Loss (-0.5kg/wk): %.0f kcal/day\n" +
            "  - Mild Weight Gain (+0.25kg/wk): %.0f kcal/day\n" +
            "  - Moderate Weight Gain (+0.5kg/wk): %.0f kcal/day",
            bmr, tdee, tdee, mildLoss, moderateLoss, mildGain, moderateGain
        );
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