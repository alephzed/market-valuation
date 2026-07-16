package com.marketvaluation.calibration;

import org.springframework.stereotype.Component;

/**
 * Placeholder marking the Model Calibration bounded context (ADR-0001).
 * Slice 1 exists only to prove the module boundary and the Gradle wiring;
 * fitting Coefficients by ordinary least squares over Shiller Data is
 * filled in by later slices.
 */
@Component
public class CalibrationModule {

    public String describe() {
        return "Model Calibration module placeholder";
    }
}
