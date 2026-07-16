package com.marketvaluation.calibration;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;

class CalibrationModuleTest {

    @Test
    void describesItself() {
        assertFalse(new CalibrationModule().describe().isBlank());
    }
}
