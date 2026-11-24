document.addEventListener("DOMContentLoaded", function () {
    const genderSelect = document.getElementById("gender");
    const genderContainer = document.getElementById("gender-container");
    const hipContainer = document.getElementById("hip-container");
    const hipInput = document.getElementById("hip");
    const icInput = document.getElementById("icNumber");

    // =================================================================
    // 1. GENDER-BASED HIP LOCK ENGINE
    // =================================================================
    function handleGenderHipLogic(isInitialLoad = false) {
        if (!genderSelect || !hipInput) return;
        
        if (genderSelect.value === "male") {
            if (!isInitialLoad) hipInput.value = ""; 
            hipInput.disabled = true; 
            if (hipContainer) {
                hipContainer.style.opacity = "0.4";
                hipContainer.style.pointerEvents = "none";
            }
        } else if (genderSelect.value === "female") {
            hipInput.disabled = false; 
            if (hipContainer) {
                hipContainer.style.opacity = "1";
                hipContainer.style.pointerEvents = "auto";
            }
        }
    }

    // =================================================================
    // 2. TYPING GUARDRAILS, AUTO-DETECTION & AUTO-LOCK (IC Input)
    // =================================================================
    if (icInput) {
        formatAndDetectIC(icInput, true); // Initialize on load

        icInput.addEventListener("input", function (e) {
            formatAndDetectIC(e.target, false);
        });
    }

    function formatAndDetectIC(inputElement, isInitialLoad) {
        let value = inputElement.value.replace(/\D/g, ""); // Strip non-digits
        if (value.length > 12) value = value.slice(0, 12);

        // A. Format with Hyphens (XXXXXX-XX-XXXX)
        let formattedValue = "";
        if (value.length > 8) {
            formattedValue = value.slice(0, 6) + "-" + value.slice(6, 8) + "-" + value.slice(8);
        } else if (value.length > 6) {
            formattedValue = value.slice(0, 6) + "-" + value.slice(6);
        } else {
            formattedValue = value;
        }
        inputElement.value = formattedValue;

        // B. Auto-Detect Gender and Lock/Unlock Field
        if (genderSelect) {
            if (value.length === 12) {
                const lastDigit = parseInt(value.charAt(11), 10);
                if (lastDigit % 2 === 0) {
                    genderSelect.value = "female";
                } else {
                    genderSelect.value = "male";
                }

                // Apply CSS styles to visually lock the dropdown while keeping data submittable
                genderSelect.style.backgroundColor = "#e9ecef";
                genderSelect.style.cursor = "not-allowed";
                if (genderContainer) {
                    genderContainer.style.pointerEvents = "none"; // Disables clicking
                }
                
                handleGenderHipLogic(isInitialLoad);
            } else {
                // If IC is incomplete or cleared, unlock the field and reset it
                if (!isInitialLoad) {
                    genderSelect.value = "";
                    genderSelect.style.backgroundColor = "";
                    genderSelect.style.cursor = "";
                    if (genderContainer) {
                        genderContainer.style.pointerEvents = "auto";
                    }
                    handleGenderHipLogic(isInitialLoad);
                }
            }
        }
    }

    // Run lock check initially if data exists in the session scope
    if (genderSelect && genderSelect.value !== "") {
        genderSelect.style.backgroundColor = "#e9ecef";
        genderSelect.style.cursor = "not-allowed";
        if (genderContainer) {
            genderContainer.style.pointerEvents = "none";
        }
        handleGenderHipLogic(true);
    }
});