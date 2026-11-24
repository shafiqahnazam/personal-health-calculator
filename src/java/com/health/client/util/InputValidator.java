package com.health.client.util;

public class InputValidator {

    public static void validateProfile(String name, String icNumber) {
        // Check Name
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be left blank!");
        } else {
            String testName = name.replaceAll("\\s+", "");
            if (!testName.matches("^[a-zA-Z]+$")) {
                // THIS IS THE SHOUTING MESSAGE!
                throw new IllegalArgumentException("The name '" + name + "' is invalid! Names must contain alphabetic letters only (No numbers or symbols allowed).");
            }
        }

        // Check IC
        if (icNumber == null || icNumber.trim().isEmpty()) {
            throw new IllegalArgumentException("IC Number cannot be left blank!");
        } else {
            String numbersOnly = icNumber.replaceAll("[^0-9]", "");
            if (numbersOnly.length() != 12) {
                throw new IllegalArgumentException("The IC Number must be exactly 12 digits! You entered " + numbersOnly.length() + " digits.");
            }
        }
    }

    public static void validateCalculations(double weight, double height) {
        if (weight <= 0.0) {
            throw new IllegalArgumentException("Weight metrics breakdown! Weight must be a positive number or greater than zero.");
        }
        if (height <= 0.0) {
            throw new IllegalArgumentException("Height metrics breakdown! Height must be a positive number or greater than zero.");
        }
    }
}