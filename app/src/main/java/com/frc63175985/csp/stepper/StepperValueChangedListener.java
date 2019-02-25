package com.frc63175985.csp.stepper;

public interface StepperValueChangedListener {
    /**
     * If this method returns true, a user-requested change in the
     * value of this {@link Stepper} will go forward and change. If this method
     * returns false, this {@link Stepper} will act as if the button wasn't pressed
     * @return Whether the value of this {@link Stepper} should change
     */
    boolean shouldChange();

    /**
     * Fires when the value of this {@link Stepper} changes
     * @param stepper The {@link Stepper} that changed values
     * @param newValue The new value of the {@link Stepper}
     */
    void valueChanged(Stepper stepper, int newValue);
}
