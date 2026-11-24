<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Personal Health & Fitness Calculator</title>

    <link rel="stylesheet" href="css/style.css">
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Montserrat:wght@600;700&family=Roboto:wght@400;500&display=swap" rel="stylesheet">
</head>
<body>

<div class="container">

    <div class="header">
        <h1>Personal Health & Fitness Calculator</h1>
        <p>SOAP-Based Automated Health Assessment System</p>
    </div>

    <form action="Client" method="POST">

        <div class="card personal-info">
            <h2>Personal Information</h2>
            <div class="form-grid">
                <div>
                    <label for="name">Full Name</label>
                    <input type="text" id="name" name="name" required placeholder="Enter your full name" 
                           value="${not empty sessionScope.savedName ? sessionScope.savedName : ''}">
                </div>

                <div>
                    <label for="ic">IC Number</label>
                    <input type="text" id="icNumber" name="icNumber" placeholder="YYMMDD-XX-XXXX" maxlength="14"
                           value="${not empty sessionScope.savedIC ? sessionScope.savedIC : ''}">
                </div>

                <div class="form-group" id="gender-container">
                    <label for="gender">Gender:</label>
                    <select id="gender" name="gender">
                        <option value="">-- Auto Detected --</option>
                        <option value="male" ${sessionScope.savedGender eq 'male' ? 'selected' : ''}>Male</option>
                        <option value="female" ${sessionScope.savedGender eq 'female' ? 'selected' : ''}>Female</option>
                    </select>
                </div>

                <div>
                    <label for="weight">Weight (kg)</label>
                    <input type="number" step="0.1" id="weight" name="weight" required placeholder="Enter weight"
                           value="${not empty sessionScope.savedWeight ? sessionScope.savedWeight : ''}">
                </div>

                <div>
                    <label for="height">Height (cm)</label>
                    <input type="number" step="0.1" id="height" name="height" required placeholder="Enter height (e.g. 175)"
                           value="${not empty sessionScope.savedHeight ? sessionScope.savedHeight : ''}">
                </div>
            </div>

            <div style="display: flex; gap: 15px; margin-top: 20px; align-items: center;">
                <button type="submit" name="action" value="profile" class="btn-submit" style="flex: 2; height: 44px; padding: 0; box-sizing: border-box;">
                    Save Profile
                </button>
                <a href="Client?action=clear" class="btn-clear-gray" style="flex: 1; height: 44px; line-height: 40px; text-align: center; box-sizing: border-box; padding: 0;">
                    Clear Form Fields
                </a>
            </div>
        </div>

        <div class="calculator-grid">

            <div class="card">
                <h2>Body Mass Index (BMI)</h2>
                <p style="font-size: 13px; margin-bottom: 20px; opacity: 0.8;">
                    Calculates whether your weight is healthy for your height.
                </p>
                <button type="submit" name="action" value="bmi" class="btn-submit">
                    Calculate BMI
                </button>
            </div>

            <div class="card">
                <h2>Basal Metabolic Rate (BMR)</h2>
                <p style="font-size: 13px; margin-bottom: 20px; opacity: 0.8;">
                    Calculates the minimum calories your body needs to function at rest.
                </p>
                <button type="submit" name="action" value="bmr" class="btn-submit">
                    Calculate BMR
                </button>
            </div>

            <div class="card">
                <h2>Body Fat Percentage</h2>
                <p style="font-size: 13px; margin-bottom: 20px; opacity: 0.8;">
                    Estimates your total body fat percentage using body measurements.
                </p>
                <div style="margin-bottom: 15px;">
                    <label for="waist">Waist Circumference (cm)</label>
                    <input type="number" step="0.1" id="waist" name="waist" placeholder="e.g. 80"
                           value="${not empty sessionScope.savedWaist ? sessionScope.savedWaist : ''}">
                </div>
                <div style="margin-bottom: 15px;">
                    <label for="neck">Neck Circumference (cm)</label>
                    <input type="number" step="0.1" id="neck" name="neck" placeholder="e.g. 38"
                           value="${not empty sessionScope.savedNeck ? sessionScope.savedNeck : ''}">
                </div>
                <div id="hip-container" style="transition: opacity 0.25s ease, transform 0.25s ease; margin-bottom: 20px;" >
                    <label for="hip">Hip Measurement (cm):</label>
                    <input type="number" id="hip" name="hip" placeholder="e.g. 95.5">
                </div>
                
                <button type="submit" name="action" value="bodyfat" class="btn-submit">
                    Calculate Body Fat
                </button>
            </div>

            <div class="card">
                <h2>Lean Body Mass (LBM)</h2>
                <p style="font-size: 13px; margin-bottom: 20px; opacity: 0.8;">
                    Calculates your total weight minus your body fat (muscles, bones, and organs).
                </p>
                <div style="margin-bottom: 25px;">
                    <label for="bodyFatPercentage">Body Fat Percentage (%)</label>
                    <input type="number" step="0.1" id="bodyFatPercentage" name="bodyFatPercentage" placeholder="e.g. 15"
                           value="${not empty sessionScope.savedBfp ? sessionScope.savedBfp : ''}">
                </div>
                <button type="submit" name="action" value="lbm" class="btn-submit">
                    Calculate LBM
                </button>
            </div>

        </div>

        <div class="card" style="margin-top: 25px;">
            <h2>Calories Burn Rate (TDEE)</h2>
                <p style="font-size: 13px; margin-bottom: 20px; opacity: 0.8;">
                    Estimates the total calories you burn daily based on your activity level.
                </p>
            <div style="margin-bottom: 15px;">
                <label for="exerciseMinutes">Exercise Minutes per Week</label>
                <input type="number" id="exerciseMinutes" name="weeklyMinutes" placeholder="e.g. 150"
                       value="${not empty sessionScope.savedMinutes ? sessionScope.savedMinutes : ''}">
            </div>
            <button type="submit" name="action" value="calories" class="btn-submit">
                Calculate TDEE
            </button>
        </div>

    </form>

    <div class="footer">
        <p>BIW 20404 WEB SERVICE TECHNOLOGY © GROUP 7 (2026)</p>
    </div>

</div>
<script src="script.js"></script>
</body>
</html>