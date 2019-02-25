package com.frc63175985.csp.stepper;

import android.content.Context;
import android.util.Range;
import android.view.View;
import android.widget.TextView;

import com.frc63175985.csp.R;

public class Stepper {
    private TextView stepperTextView;
    private StepperValueChangedListener listener;
    private int min = 0, max = Integer.MAX_VALUE;

    public Stepper(View view) {
        stepperTextView = view.findViewById(R.id.stepper_number);
        view.findViewById(R.id.stepper_increment).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                increment();
            }
        });

        view.findViewById(R.id.stepper_decrement).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                decrement();
            }
        });
    }

    /**
     * Set the value of the stepper
     * @param newValue the new stepper value
     * @throws IllegalArgumentException if the number supplied is less than this object's
     * {@code min} or larger than it's {@code} max then this exception will be thrown.
     */
    public void setValue(int newValue) throws IllegalArgumentException {
        if (newValue < min || newValue > max) {
            throw new IllegalArgumentException("newValue is less than min or greater than max.");
        }

        stepperTextView.setText(String.valueOf(newValue));
    }

    /**
     * Set a listener to allow them to monitor changes in this value
     * @param newListener The listener that wants to monitor value changes
     */
    public void setOnValueChangedListener(StepperValueChangedListener newListener) {
        this.listener = newListener;
    }

    /**
     * Add 1 to the counter
     */
    private void increment() {
        if (listener != null && !listener.shouldChange()) {
            return;
        }

        try {
            int old = Integer.parseInt(stepperTextView.getText().toString());
            old++;
            old = Math.min(old, max);
            stepperTextView.setText(String.valueOf(old));
            if (listener != null) listener.valueChanged(this, old);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }

    /**
     * Remove one from the counter
     */
    private void decrement() {
        if (listener != null && !listener.shouldChange()) {
            return;
        }

        try {
            int old = Integer.parseInt(stepperTextView.getText().toString());
            old--;
            old = Math.max(old, min);
            stepperTextView.setText(String.valueOf(old));
            if (listener != null) listener.valueChanged(this, old);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }
}
