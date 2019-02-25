package com.frc63175985.csp.stepper;

public interface StepperValueChangedListener {
    boolean shouldChange();
    void valueChanged(Stepper stepper, int newValue);
}
