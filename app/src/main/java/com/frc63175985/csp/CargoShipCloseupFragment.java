package com.frc63175985.csp;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.frc63175985.csp.auth.ScoutAuthState;
import com.frc63175985.csp.enums.CargoShipSelection;
import com.frc63175985.csp.stepper.Stepper;
import com.frc63175985.csp.stepper.StepperValueChangedListener;

import static com.frc63175985.csp.enums.ScoreObject.CARGO;
import static com.frc63175985.csp.enums.ScoreObject.HATCH;
import static com.frc63175985.csp.enums.ScoutingSubview.THUMBNAIL;

public class CargoShipCloseupFragment extends Fragment implements StepperValueChangedListener {
    private Stepper hatchAttemptStepper;
    private Stepper cargoAttemptStepper;
    private RadioGroup cargoSelectionGroup;
    private CheckBox hatchSuccessCheckBox;
    private CheckBox cargoSuccessCheckBox;
    private CargoShipSelection cargoSelection;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cargoship_closeup, container, false);

        view.findViewById(R.id.cargoship_closeup_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((BaseScoutFragment)getParentFragment()).switchView(THUMBNAIL);
            }
        });

        cargoSelectionGroup = view.findViewById(R.id.cargoship_closeup_level_selection);
        cargoSelectionGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                View radioButtonView = group.findViewById(group.getCheckedRadioButtonId());
                int radioIndex = group.indexOfChild(radioButtonView);
                RadioButton radioButton = (RadioButton)group.getChildAt(radioIndex);
                cargoSelection = CargoShipSelection.valueOf(radioButton.getText().toString().toUpperCase());
                autofill();
            }
        });

        hatchSuccessCheckBox = view.findViewById(R.id.cargoship_closeup_hatch_success_checkbox);
        hatchSuccessCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (shouldChange()) {
                    ScoutAuthState.shared.currentMatch.updateAutonomousCargoShipValue(cargoSelection, HATCH, isChecked);
                } else {
                    buttonView.setChecked(!isChecked);
                }
            }
        });

        hatchAttemptStepper = new Stepper(view.findViewById(R.id.cargoship_closeup_hatches_attempts));
        hatchAttemptStepper.setOnValueChangedListener(this);

        cargoSuccessCheckBox = view.findViewById(R.id.cargoship_closeup_cargo_success_checkbox);
        cargoSuccessCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (shouldChange()) {
                    ScoutAuthState.shared.currentMatch.updateAutonomousCargoShipValue(cargoSelection, CARGO, isChecked);
                } else {
                    buttonView.setChecked(!isChecked);
                }
            }
        });

        cargoAttemptStepper = new Stepper(view.findViewById(R.id.cargoship_closeup_cargo_attempts));
        cargoAttemptStepper.setOnValueChangedListener(this);

        return view;
    }

    /**
     * Auto-fill the hatch and cargo information
     * based on the selection
     */
    private void autofill() {
        String hatchAttemptKey = null, hatchSuccessKey = null;
        String cargoAttemptKey = null, cargoSuccessKey = null;

        switch (cargoSelection) {
            case FRONT:
                hatchAttemptKey = "auto_numShipFrontHatchAttempt";
                hatchSuccessKey = "auto_numShipFrontHatchSuccess";
                cargoAttemptKey = "auto_numShipFrontCargoAttempt";
                cargoSuccessKey = "auto_numShipFrontCargoSuccess";
                break;
            case SIDE:
                hatchAttemptKey = "auto_numShipSideHatchAttempt";
                hatchSuccessKey = "auto_numShipSideHatchSuccess";
                cargoAttemptKey = "auto_numShipSideCargoAttempt";
                cargoSuccessKey = "auto_numShipSideCargoSuccess";
                break;
        }

        int hatchAttempts = ScoutAuthState.shared.currentMatch.num(hatchAttemptKey);
        boolean hatchSuccess = ScoutAuthState.shared.currentMatch.bool(hatchSuccessKey)
                .equals("TRUE");
        int cargoAttempts = ScoutAuthState.shared.currentMatch.num(cargoAttemptKey);
        boolean cargoSuccess = ScoutAuthState.shared.currentMatch.bool(cargoSuccessKey)
                .equals("TRUE");

        hatchAttemptStepper.setValue(hatchAttempts);
        hatchSuccessCheckBox.setChecked(hatchSuccess);

        cargoAttemptStepper.setValue(cargoAttempts);
        cargoSuccessCheckBox.setChecked(cargoSuccess);
    }

    @Override
    public boolean shouldChange() {
        // Check if a level is selected
        if (cargoSelectionGroup.getCheckedRadioButtonId() == -1) {
            Toast.makeText(getContext(), "Must have side selected", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void valueChanged(Stepper stepper, int newValue) {
        // Change value in match database
        if (stepper == hatchAttemptStepper) {
            ScoutAuthState.shared.currentMatch.updateAutonomousCargoShipValue(cargoSelection, HATCH, newValue);
        } else if (stepper == cargoAttemptStepper) {
            ScoutAuthState.shared.currentMatch.updateAutonomousCargoShipValue(cargoSelection, CARGO, newValue);
        }
    }
}
