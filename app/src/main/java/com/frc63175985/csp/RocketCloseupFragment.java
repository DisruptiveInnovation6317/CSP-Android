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
import com.frc63175985.csp.enums.LevelSelection;
import com.frc63175985.csp.stepper.Stepper;
import com.frc63175985.csp.stepper.StepperValueChangedListener;

import static com.frc63175985.csp.enums.ScoreObject.CARGO;
import static com.frc63175985.csp.enums.ScoreObject.HATCH;
import static com.frc63175985.csp.enums.ScoutingSubview.THUMBNAIL;

public class RocketCloseupFragment extends Fragment implements StepperValueChangedListener {
    private Stepper hatchAttemptStepper;
    private Stepper cargoAttemptStepper;
    private RadioGroup levelSelectionGroup;
    private CheckBox hatchSuccessCheckBox;
    private CheckBox cargoSuccessCheckBox;
    private LevelSelection levelSelection;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = getView() != null ? getView() : inflater.inflate(R.layout.fragment_rocket_closeup, container, false);

        view.findViewById(R.id.rocket_closeup_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            ((BaseScoutFragment)getParentFragment()).switchView(THUMBNAIL);
            }
        });

        levelSelectionGroup = view.findViewById(R.id.rocket_closeup_level_selection);
        levelSelectionGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                View radioButtonView = group.findViewById(group.getCheckedRadioButtonId());
                int radioIndex = group.indexOfChild(radioButtonView);
                RadioButton radioButton = (RadioButton)group.getChildAt(radioIndex);
                LevelSelection level = LevelSelection.valueOf(radioButton.getText().toString().toUpperCase());
                autofill(level);
            }
        });

        hatchAttemptStepper = new Stepper(view.findViewById(R.id.rocket_closeup_hatches_attempts));
        hatchAttemptStepper.setOnValueChangedListener(this);

        hatchSuccessCheckBox = view.findViewById(R.id.rocket_closeup_hatch_success_checkbox);
        hatchSuccessCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (shouldChange()) {
                    ScoutAuthState.shared.currentMatch.updateAutonomousRocketValue(levelSelection, HATCH, isChecked);
                } else {
                    buttonView.setChecked(!isChecked);
                }
            }
        });

        cargoAttemptStepper = new Stepper(view.findViewById(R.id.rocket_closeup_cargo_attempts));
        cargoAttemptStepper.setOnValueChangedListener(this);

        cargoSuccessCheckBox = view.findViewById(R.id.rocket_closeup_cargo_success_checkbox);
        cargoSuccessCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (shouldChange()) {
                    ScoutAuthState.shared.currentMatch.updateAutonomousRocketValue(levelSelection, CARGO, isChecked);
                } else {
                    buttonView.setChecked(!isChecked);
                }
            }
        });

        return view;
    }

    /**
     * Auto-fill the hatch and cargo information
     * based on the selection
     * @param selection What tier of the Rocket we are auto-filling for
     */
    private void autofill(LevelSelection selection) {
        levelSelection = selection;
        String hatchAttemptKey = null, hatchSuccessKey = null;
        String cargoAttemptKey = null, cargoSuccessKey = null;

        switch (selection) {
            case TOP:
                hatchAttemptKey = "auto_numRocketHighAttempt";
                hatchSuccessKey = "auto_numRocketHighSuccess";
                cargoAttemptKey = "auto_numRocketHighCargoAttempt";
                cargoSuccessKey = "auto_numRocketHighCargoSuccess";
                break;
            case MIDDLE:
                hatchAttemptKey = "auto_numRocketMidHatchAttempt";
                hatchSuccessKey = "auto_numRocketMidHatchSuccess";
                cargoAttemptKey = "auto_numRocketMidCargoAttempt";
                cargoSuccessKey = "auto_numRocketMidCargoSuccess";
                break;
            case LOW:
                hatchAttemptKey = "auto_numRocketLowHatchAttempt";
                hatchSuccessKey = "auto_numRocketLowHatchSuccess";
                cargoAttemptKey = "auto_numRocketLowCargoAttempt";
                cargoSuccessKey = "auto_numRocketLowCargoSuccess";
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
        if (levelSelectionGroup.getCheckedRadioButtonId() == -1) {
            Toast.makeText(getContext(), "Must have level selected", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void valueChanged(Stepper stepper, int newValue) {
        // Change value in match database
        if (stepper == hatchAttemptStepper) {
            ScoutAuthState.shared.currentMatch.updateAutonomousRocketValue(levelSelection, HATCH, newValue);
        } else if (stepper == cargoAttemptStepper) {
            ScoutAuthState.shared.currentMatch.updateAutonomousRocketValue(levelSelection, CARGO, newValue);
        }
    }
}
