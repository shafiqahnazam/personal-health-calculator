package com.health.service;

import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;

// Import all calculation files
import com.health.module.BMI;
import com.health.module.BodyFat;
import com.health.module.Calories;
import com.health.module.BMR;
import com.health.module.LBM;

@WebService(serviceName = "HealthServerService")
public class HealthServer {

    @WebMethod(operationName = "calculateBMI")
    public String calculateBMI(
            @WebParam(name = "weight") double weight, 
            @WebParam(name = "height") double height) {
        BMI module = new BMI();
        return module.calculateBMI(weight, height);
    }

    @WebMethod(operationName = "calculateBodyFat")
    public String calculateBodyFat(
            @WebParam(name = "waist") double waist, 
            @WebParam(name = "neck") double neck, 
            @WebParam(name = "height") double height, 
            @WebParam(name = "hip") double hip, 
            @WebParam(name = "gender") String gender) {
        BodyFat module = new BodyFat();
        return module.calculateBodyFat(waist, neck, height, hip, gender);
    }

    @WebMethod(operationName = "calculateCalories")
    public String calculateCalories(
            @WebParam(name = "weight") double weight, 
            @WebParam(name = "height") double height, 
            @WebParam(name = "age") int age, 
            @WebParam(name = "gender") String gender,
            @WebParam(name = "weeklyMinutes") int weeklyMinutes) {
        Calories module = new Calories();
        return module.calculateTDEE(weight, height, age, gender, weeklyMinutes);
    }

    @WebMethod(operationName = "calculateBMR")
    public String calculateBMR(
            @WebParam(name = "weight") double weight, 
            @WebParam(name = "height") double height, 
            @WebParam(name = "gender") String gender,
            @WebParam(name = "age") int age) {
        BMR module = new BMR();
        return module.calculateBMR(weight, height, gender, age);
    }

    @WebMethod(operationName = "calculateLBM")
    public String calculateLBM(
            @WebParam(name = "weight") double weight, 
            @WebParam(name = "bodyFatPercentage") double bodyFatPercentage,
            @WebParam(name = "gender") String gender) {
        LBM module = new LBM();
        return module.calculateLBM(weight, bodyFatPercentage, gender);
    }
}