package com.health.client;

import com.health.client.util.InputValidator;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "Client", urlPatterns = {"/Client"})
public class Client extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String action = request.getParameter("action");
        if ("clear".equals(action)) {
            HttpSession session = request.getSession();
            session.invalidate(); 
            response.sendRedirect("index.jsp"); 
            return;
        }
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession();
        
        try {
            String action = request.getParameter("action");
            if (action == null) action = "profile";

            if ("clear".equals(action)) {
                session.invalidate();
                response.sendRedirect("index.jsp");
                return;
            }

            // 1. Gather Core Profile Data Raw Inputs
            String name = request.getParameter("name");
            String icNumber = request.getParameter("icNumber");
            String gender = request.getParameter("gender");
            
            String rawWeight = request.getParameter("weight");
            String rawHeight = request.getParameter("height");
            
            // 2. Gather Optional Module Data
            String waistStr = request.getParameter("waist");
            String neckStr = request.getParameter("neck");
            String hipStr = request.getParameter("hip");
            String bfpStr = request.getParameter("bodyFatPercentage");
            String minutesStr = request.getParameter("weeklyMinutes");

            // =================================================================
            // UNIFIED SERVER VALIDATION RUNTIME LAYER (DIRECT LINK TO VALIDATOR)
            // =================================================================
            // First, validate text structures to avoid missing fields
            InputValidator.validateProfile(name, icNumber);

            // Parse numbers safely. If fields are empty or garbage strings, 
            // trigger custom explicit errors instead of letting Java crash generically.
            double weight = 0.0;
            double height = 0.0;
            try {
                if (rawWeight == null || rawWeight.trim().isEmpty()) {
                    throw new IllegalArgumentException("Weight parameter cannot be left blank.");
                }
                weight = Double.parseDouble(rawWeight);
            } catch (NumberFormatException nfe) {
                throw new IllegalArgumentException("Weight metric configuration breakdown. The input '" + rawWeight + "' is not a valid numeric expression.");
            }

            try {
                if (rawHeight == null || rawHeight.trim().isEmpty()) {
                    throw new IllegalArgumentException("Height parameter cannot be left blank.");
                }
                height = Double.parseDouble(rawHeight);
            } catch (NumberFormatException nfe) {
                throw new IllegalArgumentException("Height metric configuration breakdown. The input '" + rawHeight + "' is not a valid numeric expression.");
            }

            // Run numerical boundary check from InputValidator.java
            InputValidator.validateCalculations(weight, height);

            // =================================================================
            // CACHE VALUES TO SESSION FOR DASHBOARD PERSISTENCE
            // =================================================================
            session.setAttribute("savedName", name);
            session.setAttribute("savedIC", icNumber);
            session.setAttribute("savedGender", gender);
            session.setAttribute("savedWeight", rawWeight);
            session.setAttribute("savedHeight", rawHeight);
            session.setAttribute("savedWaist", waistStr);
            session.setAttribute("savedNeck", neckStr);
            session.setAttribute("savedHip", hipStr);
            session.setAttribute("savedBfp", bfpStr);
            session.setAttribute("savedMinutes", minutesStr);

            // =================================================================
            // EXACT CALENDAR DAY AGE ENGINE (Anchored to June 15, 2026)
            // =================================================================
            int derivedAge = 0;
            if (icNumber != null) {
                String cleanIC = icNumber.replaceAll("[^0-9]", "");
                if (cleanIC.length() >= 6) {
                    int birthYearDigits = Integer.parseInt(cleanIC.substring(0, 2));
                    int birthMonth = Integer.parseInt(cleanIC.substring(2, 4));
                    int birthDay = Integer.parseInt(cleanIC.substring(4, 6));
                    
                    int birthYear = (birthYearDigits >= 0 && birthYearDigits <= 26) ? (2000 + birthYearDigits) : (1900 + birthYearDigits);
                    
                    int currentYear = 2026;
                    int currentMonth = 6;
                    int currentDay = 15;
                    
                    derivedAge = currentYear - birthYear;
                    
                    if (birthMonth > currentMonth) {
                        derivedAge--;
                    } else if (birthMonth == currentMonth && birthDay > currentDay) {
                        derivedAge--;
                    }
                }
            }

            // SOAP Client Connection Setup
            com.health.client.HealthServerService service = new com.health.client.HealthServerService();
            com.health.client.HealthServer port = service.getHealthServerPort();

            String moduleHeading = "";
            String rawServiceOutput = "";
            
            String bigNumericDisplay = "";
            String statusBadgeHtml = "";
            String recommendationsHtml = "";
            String profileCardsHtml = "";

            switch (action) {
                case "profile":
                    moduleHeading = "Personal Information Summary";
                    rawServiceOutput = "Name: " + name + ", IC number: " + icNumber + ", Gender: " + gender + ", Height: " + height + "cm, Weight: " + weight + "kg, Age: " + derivedAge;
                    bigNumericDisplay = "<div class='profile-status-message'>Profile Saved Successfully!</div>";
                    
                    profileCardsHtml = "<div class='flex-metrics-container'>" +
                        "<div class='metric-box'><div class='m-title'>Name</div><div class='m-val'>" + name + "</div></div>" +
                        "<div class='metric-box'><div class='m-title'>Age</div><div class='m-val'>" + derivedAge + " years</div></div>" +
                        "<div class='metric-box'><div class='m-title'>Gender</div><div class='m-val' style='text-transform:capitalize;'>" + gender + "</div></div>" +
                        "<div class='metric-box'><div class='m-title'>Height</div><div class='m-val'>" + height + " cm</div></div>" +
                        "<div class='metric-box'><div class='m-title'>Weight</div><div class='m-val'>" + weight + " kg</div></div>" +
                        "</div>";
                    break;
                    
                case "bmi":
                    moduleHeading = "BMI Calculation Result";
                    rawServiceOutput = port.calculateBMI(weight, height);
                    
                    try {
                        String cleanBmi = rawServiceOutput.split("is:")[1].split("Status:")[0].trim();
                        if(cleanBmi.endsWith(".")) cleanBmi = cleanBmi.substring(0, cleanBmi.length()-1);
                        String cleanStatus = rawServiceOutput.split("Status:")[1].trim();
                        
                        bigNumericDisplay = "<div class='big-number'>" + cleanBmi + "</div>";
                        statusBadgeHtml = "<div class='status-badge badge-green'>" + cleanStatus + "</div>";
                    } catch (Exception ex) {
                        bigNumericDisplay = "<div class='big-number'>" + String.format("%.2f", weight / Math.pow(height/100, 2)) + "</div>";
                        statusBadgeHtml = "<div class='status-badge badge-green'>" + rawServiceOutput + "</div>";
                    }
                    
                    break;
                    
                case "bmr":
                    moduleHeading = "BMR Calculation Result";
                    rawServiceOutput = port.calculateBMR(weight, height, gender, derivedAge);
                    
                    try {
                        String cleanBmr = rawServiceOutput.split("is:")[1].trim();
                        bigNumericDisplay = "<div class='big-number'>" + cleanBmr + "</div>";
                    } catch(Exception e) {
                        bigNumericDisplay = "<div class='big-number'>" + rawServiceOutput + "</div>";
                    }
                    break;
                    
                case "bodyfat":
                    moduleHeading = "Body Fat Percentage Result";
                    double waist = Double.parseDouble(waistStr);
                    double neck = Double.parseDouble(neckStr);
                    double hip = (hipStr != null && !hipStr.isEmpty()) ? Double.parseDouble(hipStr) : 0.0;
                    
                    rawServiceOutput = port.calculateBodyFat(waist, neck, height, hip, gender);
                    
                    try {
                        String cleanBfp = rawServiceOutput.split("is:")[1].split("Status:")[0].trim();
                        String cleanStatus = rawServiceOutput.split("Status:")[1].trim();
                        
                        bigNumericDisplay = "<div class='big-number'>" + cleanBfp + "</div>";
                        statusBadgeHtml = "<div class='status-badge badge-green'>" + cleanStatus + "</div>";
                    } catch(Exception e) {
                        bigNumericDisplay = "<div class='big-number'>" + bfpStr + "%</div>";
                        statusBadgeHtml = "<div class='status-badge badge-green'>" + rawServiceOutput + "</div>";
                    }
                    
                    break;
                    
                case "lbm":
                    moduleHeading = "Lean Body Mass Result";
                    double bfpVal = Double.parseDouble(bfpStr);
                    rawServiceOutput = port.calculateLBM(weight, bfpVal, gender); 
                    
                    try {
                        String cleanLbmNum = rawServiceOutput.split("Lean Body Mass =")[1].split("Category")[0].replaceAll("[\\.\\s]*$", "").trim();
                        String cleanCategoryOnly = rawServiceOutput.split("Category\\s*=\\s*")[1].trim();
                        
                        bigNumericDisplay = "<div class='big-number'>" + cleanLbmNum + "</div>";
                        statusBadgeHtml = "<div class='status-badge badge-green'>" + cleanCategoryOnly + "</div>";
                    } catch(Exception e) {
                        bigNumericDisplay = "<div class='big-number'>" + String.format("%.2f", weight * (1 - (bfpVal / 100))) + " kg</div>";
                        statusBadgeHtml = "<div class='status-badge badge-green'>Active Fitness Track</div>";
                    }
                    break;
                    
                case "calories":
                    moduleHeading = "Daily Calorie Requirements (TDEE)";
                    int weeklyMinutes = Integer.parseInt(minutesStr);
                    rawServiceOutput = port.calculateCalories(weight, height, derivedAge, gender, weeklyMinutes);
                    
                    try {
                        String tdeeValStr = "";
                        if (rawServiceOutput.contains("Total Daily Energy Expenditure (TDEE):")) {
                            tdeeValStr = rawServiceOutput.split("Total Daily Energy Expenditure \\(TDEE\\):")[1].split("kcal")[0].trim();
                        } else {
                            tdeeValStr = rawServiceOutput.replaceAll("[^0-9.]", "");
                        }
                        double extractedTdee = Double.parseDouble(tdeeValStr);
                        
                        bigNumericDisplay = "<div class='big-number' style='font-size:32px; margin: 25px 0;'> " 
                                          + String.format("%.0f kcal/day", extractedTdee) 
                                          + "</div>";
                        
                        double maintenance = extractedTdee;
                        double mildLoss = maintenance - 250; 
                        double modLoss = maintenance - 500;   
                        double mildGain = maintenance + 250;  
                        double modGain = maintenance + 500;   
                        
                        if (rawServiceOutput.contains("Weight Maintenance:")) {
                            try { maintenance = Double.parseDouble(rawServiceOutput.split("Weight Maintenance:")[1].split("kcal")[0].trim()); } catch(Exception ex){}
                        }
                        if (rawServiceOutput.contains("Mild Weight Loss")) {
                            try { mildLoss = Double.parseDouble(rawServiceOutput.split("Mild Weight Loss[^:]*:")[1].split("kcal")[0].trim()); } catch(Exception ex){}
                        }
                        if (rawServiceOutput.contains("Moderate Weight Loss")) {
                            try { modLoss = Double.parseDouble(rawServiceOutput.split("Moderate Weight Loss[^:]*:")[1].split("kcal")[0].trim()); } catch(Exception ex){}
                        }
                        if (rawServiceOutput.contains("Mild Weight Gain")) {
                            try { mildGain = Double.parseDouble(rawServiceOutput.split("Mild Weight Gain[^:]*:")[1].split("kcal")[0].trim()); } catch(Exception ex){}
                        }
                        if (rawServiceOutput.contains("Moderate Weight Gain")) {
                            try { modGain = Double.parseDouble(rawServiceOutput.split("Moderate Weight Gain[^:]*:")[1].split("kcal")[0].trim()); } catch(Exception ex){}
                        }

                        recommendationsHtml = "<div class='recommendation-title'>Calorie Management Goals:</div>" +
                            "<div class='rec-item'><strong>Weight Maintenance:</strong> " + String.format("%.0f kcal/day", maintenance) + "</div>" +
                            "<div class='rec-item'><strong>Mild Weight Loss (-0.25kg/wk):</strong> " + String.format("%.0f kcal/day", mildLoss) + "</div>" +
                            "<div class='rec-item'><strong>Moderate Weight Loss (-0.5kg/wk):</strong> " + String.format("%.0f kcal/day", modLoss) + "</div>" +
                            "<div class='rec-item'><strong>Mild Weight Gain (+0.25kg/wk):</strong> " + String.format("%.0f kcal/day", mildGain) + "</div>" +
                            "<div class='rec-item'><strong>Moderate Weight Gain (+0.5kg/wk):</strong> " + String.format("%.0f kcal/day", modGain) + "</div>";
                            
                    } catch(Exception e) {
                        bigNumericDisplay = "<div class='big-number' style='font-size:26px;'>" + rawServiceOutput + "</div>";
                    }
                    break;
            }

            // USER PRESENTATION RENDERING SHEET (NORMAL SUCCESS VIEWS)
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Health Service Results</title>");
            out.println("<link href='https://fonts.googleapis.com/css2?family=Montserrat:wght@600;700&family=Roboto:wght@400;500&display=swap' rel='stylesheet'>");
            out.println("<style>");
            out.println("  body { font-family: 'Roboto', sans-serif; background-color: #f1e2d1; color: #541212; margin: 0; padding: 25px; }");
            out.println("  .top-navbar { position: relative; display: flex; align-items: center; max-width: 1050px; margin: 0 auto 30px auto; border-bottom: 3px solid #810b38; padding-bottom: 15px; min-height: 50px; }");
            out.println("  .btn-back-nav { position: absolute; left: 0; background-color: #810b38; color: #ffffff; font-family: 'Montserrat', sans-serif; font-weight: 700; font-size: 13px; text-decoration: none; padding: 12px 22px; border-radius: 4px; text-transform: uppercase; letter-spacing: 0.5px; transition: all 0.2s ease; z-index: 10; }");
            out.println("  .btn-back-nav:hover { background-color: #541212; }");
            out.println("  .nav-title { font-family: 'Montserrat', sans-serif; font-size: 32px; font-weight: 700; color: #810b38; margin: 0 auto; text-align: center; width: 100%; }");
            out.println("  .main-dashboard-card { max-width: 1050px; margin: 0 auto; background: #ffffff; border-radius: 12px; padding: 45px; box-shadow: 0 4px 20px rgba(84,18,18,0.12); border-left: 6px solid #810b38; box-sizing: border-box; }");
            out.println("  .card-heading { font-family: 'Montserrat', sans-serif; font-size: 24px; font-weight: 700; color: #541212; margin-top: 0; margin-bottom: 25px; text-align: left; }");
            out.println("  .big-number { font-family: 'Montserrat', sans-serif; font-size: 54px; font-weight: 700; color: #2c3e50; text-align: center; margin: 20px 0 10px 0; letter-spacing: -1px; }");
            out.println("  .profile-status-message { font-size: 16px; font-weight: 400; color: #810b38; margin-bottom: 25px; line-height: 1.5; }");
            out.println("  .status-badge { max-width: 100%; padding: 14px 20px; text-align: center; font-family: 'Montserrat', sans-serif; font-weight: 700; font-size: 15px; border-radius: 6px; margin: 0 auto 35px auto; box-sizing: border-box; }");
            out.println("  .badge-green { background-color: #e8f5e9; color: #2e7d32; border: 1px solid #c8e6c9; }");
            out.println("  .recommendation-title { font-family: 'Montserrat', sans-serif; font-size: 16px; font-weight: 700; color: #2c3e50; margin-bottom: 15px; text-align: left; }");
            out.println("  .rec-item { background: #f8f9fa; border-left: 4px solid #7f8c8d; padding: 15px 20px; margin-bottom: 12px; font-size: 14px; color: #555555; border-radius: 0 4px 4px 0; font-weight: 500; text-align: left; box-sizing: border-box; }");
            out.println("  .flex-metrics-container { display: flex; flex-wrap: wrap; gap: 15px; margin-bottom: 30px; width: 100%; }");
            out.println("  .metric-box { background: #f2f2f2; border-radius: 8px; padding: 18px; flex: 1; min-width: 140px; text-align: center; }");
            out.println("  .m-title { font-family: 'Montserrat', sans-serif; font-size: 12px; font-weight: 700; color: #7f8c8d; text-transform: uppercase; margin-bottom: 8px; }");
            out.println("  .m-val { font-size: 15px; font-weight: 500; color: #2c3e50; }");
            out.println("  .raw-service-footer { background: #f8f9fa; border-left: 3px solid #7f8c8d; padding: 14px 20px; font-family: monospace; font-size: 13px; color: #555555; border-radius: 4px; box-sizing: border-box; width: 100%; display: block; margin-top: 30px; clear: both; text-align: left; }");
            out.println("  .raw-label { font-weight: bold; color: #2c3e50; font-family: 'Roboto', sans-serif; margin-right: 8px; text-transform: uppercase; font-size: 11px; letter-spacing: 0.5px; }");
            out.println("</style>");
            out.println("</head>");
            out.println("<body>");
            out.println("<div class='top-navbar'>");
            out.println("  <a href='index.jsp' class='btn-back-nav'>← Back</a>");
            out.println("  <h1 class='nav-title'>Health Service Results</h1>");
            out.println("</div>");
            out.println("<div class='main-dashboard-card'>");
            out.println("  <div class='card-heading'>" + moduleHeading + "</div>");
            out.println(bigNumericDisplay);
            if (!statusBadgeHtml.isEmpty()) out.println(statusBadgeHtml);
            if (!recommendationsHtml.isEmpty()) out.println(recommendationsHtml);
            if (!profileCardsHtml.isEmpty()) out.println(profileCardsHtml);
            out.println("  <div class='raw-service-footer'>");
            out.println("    <span class='raw-label'>Service Response:</span>" + rawServiceOutput);
            out.println("  </div>");
            out.println("</div>");
            out.println("</body>");
            out.println("</html>");

        } catch (jakarta.xml.ws.soap.SOAPFaultException soapEx) {
            request.setAttribute("title", "Web Service Error");
            request.setAttribute("heading", "Server Communication Fault");
            request.setAttribute("desc", "The application encountered an explicit processing exception generated from the remote Web Service core computation engine.");
            request.setAttribute("label", "SOAP Fault Code: " + soapEx.getFault().getFaultCode());
            request.setAttribute("message", soapEx.getFault().getFaultString());
            
            request.getRequestDispatcher("error.jsp").forward(request, response);

        } catch (IllegalArgumentException e) {
            // This block catches the exact shout from InputValidator.java!
            request.setAttribute("title", "Validation Input Error");
            request.setAttribute("heading", "Data Interception Warning");
            request.setAttribute("desc", "The system rejected your submission because an input field breaks application rules.");
            request.setAttribute("label", "Validation Message:");
            
            // CRITICAL LINE: This grabs the exact text inside throw new IllegalArgumentException(...)
            request.setAttribute("message", e.getMessage()); 
            
            request.getRequestDispatcher("error.jsp").forward(request, response);
            
            

        } catch (Exception e) {
            // General catch-all block
            request.setAttribute("title", "Unexpected App Error");
            request.setAttribute("heading", "System Core Failure");
            request.setAttribute("desc", "An unmapped runtime error was captured by the controller lifecycle filters.");
            request.setAttribute("label", "Exception Trace Class:");
            request.setAttribute("message", e.toString());
            
            request.getRequestDispatcher("error.jsp").forward(request, response);
        } finally {
            out.close();
        }
    }
}