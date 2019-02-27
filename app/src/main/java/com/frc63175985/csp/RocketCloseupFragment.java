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

import com.frc63175985.csp.auth.Match;
import com.frc63175985.csp.auth.ScoutAuthState;
import com.frc63175985.csp.enums.BaseScoutType;
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

        final BaseScoutType type = ((BaseScoutFragment)getParentFragment()).getScoutType();

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
                    ScoutAuthState.shared.currentMatch.updateRocketValue(type, levelSelection, HATCH, isChecked);
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
                    ScoutAuthState.shared.currentMatch.updateRocketValue(type, levelSelection, CARGO, isChecked);
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

        BaseScoutType type = ((BaseScoutFragment)getParentFragment()).getScoutType();
        String prefix = type == BaseScoutType.AUTONOMOUS ? Match.AUTO_PREFIX : Match.TELEOP_PREFIX;

        switch (selection) {
            case HIGH:
                hatchAttemptKey = prefix + Match.ROCKET_HIGH_HATCH_ATTEMPT;
                hatchSuccessKey = prefix + Match.ROCKET_HIGH_HATCH_SUCCESS;
                cargoAttemptKey = prefix + Match.ROCKET_HIGH_CARGO_ATTEMPT;
                cargoSuccessKey = prefix + Match.ROCKET_HIGH_CARGO_SUCCESS;
                break;
            case MIDDLE:
                hatchAttemptKey = prefix + Match.ROCKET_MIDDLE_HATCH_ATTEMPT;
                hatchSuccessKey = prefix + Match.ROCKET_MIDDLE_HATCH_SUCCESS;
                cargoAttemptKey = prefix + Match.ROCKET_MIDDLE_CARGO_ATTEMPT;
                cargoSuccessKey = prefix + Match.ROCKET_MIDDLE_CARGO_SUCCESS;
                break;
            case LOW:
                hatchAttemptKey = prefix + Match.ROCKET_LOW_HATCH_ATTEMPT;
                hatchSuccessKey = prefix + Match.ROCKET_LOW_HATCH_SUCCESS;
                cargoAttemptKey = prefix + Match.ROCKET_LOW_CARGO_ATTEMPT;
                cargoSuccessKey = prefix + Match.ROCKET_LOW_CARGO_SUCCESS;
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
        BaseScoutType type = ((BaseScoutFragment)getParentFragment()).getScoutType();

        // Change value in match database
        if (stepper == hatchAttemptStepper) {
            ScoutAuthState.shared.currentMatch.updateRocketValue(type, levelSelection, HATCH, newValue);
        } else if (stepper == cargoAttemptStepper) {
            ScoutAuthState.shared.currentMatch.updateRocketValue(type, levelSelection, CARGO, newValue);
        }
    }
}
