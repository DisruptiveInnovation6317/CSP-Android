package com.frc63175985.csp.auth;

import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;

import com.frc63175985.csp.Debug;
import com.frc63175985.csp.R;
import com.frc63175985.csp.stepper.Stepper;
import com.frc63175985.csp.stepper.StepperValueChangedListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import javax.annotation.Nullable;

public class PitScoutRecord {
    public static final String[] SPEED_OPTIONS = Match.SPEED_OPTIONS;
    public static final String[] HEIGHT_OPTIONS = {"--------", "Low", "Mid", "High"};

    public static final String TEAM_NUMBER = "idTeam";

    public static final String SANDSTORM_ACTIVE = "flAuto";
    public static final String AUTO_TYPE = "auto_idAutoType";
    public static final String START_LEVEL = "auto_idStartLevel";
    public static final String CAN_DELIVER_HATCH = "auto_flHatch";
    public static final String NUM_HATCHES_DELIVERED = "auto_intHatchDeliver";
    public static final String MAX_HATCH_HEIGHT = "auto_idHatchDeliverHeight";
    public static final String CAN_DELIVER_CARGO = "auto_flCargo";
    public static final String NUM_CARGO_DELIVERED = "auto_intCargoDelivered";
    public static final String MAX_CARGO_HEIGHT = "auto_idCargoDeliveryHeight";

    public static final String CAN_MANIPULATE_HATCH = "flHatch";
    public static final String HATCH_FROM_EXCHANGE = "flHatchInWall";
    public static final String HATCH_FROM_FLOOR = "flHatchInFloor";
    public static final String HATCH_LOW = "flHatchOutLow";
    public static final String HATCH_MID = "flHatchOutMed";
    public static final String HATCH_HIGH = "flHatchOutHigh";
    public static final String HATCH_INTAKE_NOTES = "txHatchNotes";
    public static final String CAN_MANIPULATE_CARGO = "flCargo";
    public static final String CARGO_FROM_EXCHANGE = "flCargoInWall";
    public static final String CARGO_FROM_FLOOR = "flCargoInFloor";
    public static final String CARGO_LOW = "flCargoOutLow";
    public static final String CARGO_MID = "flCargoOutMed";
    public static final String CARGO_HIGH = "flCargoOutHigh";
    public static final String CARGO_INTAKE_NOTES = "txCargoNotes";

    public static final String CAN_CLIMB = "tele_flClimb";
    public static final String CLIMB_TYPE = "tele_idClimbType";
    public static final String CLIMB_GRAB_SPEED = "tele_idClimbGrab";
    public static final String CLIMB_SPEED = "tele_idClimbSpeed";
    public static final String NUM_CLIMB_ASSISTS = "tele_numClimbAssists";
    public static final String CLIMB_HEIGHT = "tele_idClimbLevel";

    public static final String ROBOT_FRONT_FILENAME = "imgRobotFront";
    public static final String ROBOT_SIDE_FILENAME = "imgRobotSide";

    public static final String COMMENTS = "txComments";

    private HashMap<String, Object> data;

    public PitScoutRecord() {
        data = new HashMap<>();
    }

    public String export() {
        StringBuilder sb = new StringBuilder();

        sb.append("DEFAULT").append(","); // ID
        sb.append(ScoutAuthState.shared.tournament).append(","); // idEvent
        sb.append(str(TEAM_NUMBER)).append(","); // idTeam
        sb.append(ScoutAuthState.shared.scout).append(","); // txScoutName

        sb.append(bool(SANDSTORM_ACTIVE)).append(","); // flAuto
        sb.append(num(AUTO_TYPE)).append(","); // auto_idAutoType
        sb.append(num(START_LEVEL)).append(","); // auto_idStartLevel
        sb.append(bool(CAN_DELIVER_HATCH)).append(","); // auto_flHatch
        sb.append(num(NUM_HATCHES_DELIVERED)).append(","); // auto_intHatchDeliver
        sb.append(num(MAX_HATCH_HEIGHT)).append(","); // auto_idHatchDeliverHeight
        sb.append(bool(CAN_DELIVER_CARGO)).append(","); // auto_flCargo
        sb.append(num(NUM_CARGO_DELIVERED)).append(","); // auto_intCargoDelivered
        sb.append(num(MAX_CARGO_HEIGHT)).append(","); // auto_idCargoDeliveryHeight

        sb.append(bool(CAN_MANIPULATE_HATCH)).append(","); // flHatch
        sb.append(bool(HATCH_FROM_EXCHANGE)).append(","); // flHatchInWall
        sb.append(bool(HATCH_FROM_FLOOR)).append(","); // flHatchInFloor
        sb.append(bool(HATCH_LOW)).append(","); // flHatchOutLow
        sb.append(bool(HATCH_MID)).append(","); // flHatchOutMed
        sb.append(bool(HATCH_HIGH)).append(","); // flHatchOutHigh
        sb.append(str(HATCH_INTAKE_NOTES)).append(","); // txHatchNotes
        sb.append(bool(CAN_MANIPULATE_CARGO)).append(","); // flCargo
        sb.append(bool(CARGO_FROM_EXCHANGE)).append(","); // flCargoInWall
        sb.append(bool(CARGO_FROM_FLOOR)).append(","); // flCargoInFloor
        sb.append(bool(CARGO_LOW)).append(","); // flCargoOutLow
        sb.append(bool(CARGO_MID)).append(","); // flCargoOutMed
        sb.append(bool(CARGO_HIGH)).append(","); // flCargoOutHigh
        sb.append(str(CARGO_INTAKE_NOTES)).append(","); // txCargoNotes

        sb.append(bool(CAN_CLIMB)).append(","); // tele_flClimb
        sb.append(num(CLIMB_TYPE)).append(","); // tele_idClimbType
        sb.append(num(CLIMB_GRAB_SPEED)).append(","); // tele_idClimbGrab
        sb.append(num(CLIMB_SPEED)).append(","); // tele_idClimbSpeed
        sb.append(num(NUM_CLIMB_ASSISTS)).append(","); // tele_numClimbAssists
        sb.append(num(CLIMB_HEIGHT)).append(","); // tele_idClimbLevel

        sb.append(str(ROBOT_FRONT_FILENAME)).append(","); // imgRobotFront
        sb.append(str(ROBOT_SIDE_FILENAME)).append(","); // imgRobotSide

        sb.append(str(COMMENTS)).append(","); // txComments
        sb.append(new SimpleDateFormat("y/M/d h:m:s", Locale.US).format(new Date())).append(","); // dtCreation
        sb.append(new SimpleDateFormat("y/M/d h:m:s", Locale.US).format(new Date())).append(","); // dtModified
        sb.append(Build.MODEL == null || Build.MODEL.isEmpty() ? "Android-Device" : Build.MODEL); // txComputerName

        return sb.toString();
    }

    public void clear() {
        data.clear();
    }

    public String str(String key) {
        Object value = data.get(key);
        if (value == null) {
            return "";
        } else {
            return (String)value;
        }
    }

    /**
     * Search for and return a number in any of our {@link HashMap}'s
     * @param key The key to be searched for
     * @return The value, if it exists, otherwise {@code 0}
     */
    private int num(String key) {
        Object num = data.get(key);
        return num == null ? 0 : (int)num;
    }

    public void set(String key, Object value) {
        if (Debug.LOG_DATABASE_SET) Debug.log("PitScoutDB: Setting key " + key + " to value " + value);
        data.put(key, value);
    }

    /**
     * Search for and return a boolean in any of our {@link HashMap}'s
     * This method returns the String representation of this boolean,
     * e.g. {@code "TRUE"} or {@code "FALSE"}
     * @param key The key to be searched for
     * @return The value, if it exists, otherwise {@code "FALSE"}
     */
    private String bool(@NonNull String key) {
        Object bool = data.get(key);
        if (bool == null) return "FALSE";
        else return (boolean)bool ? "TRUE" : "FALSE";
    }

    /**
     * A class GUI helpers revolving around displaying Pit Scouting information
     */
    public static class GUI {
        /**
         * Creates a listener that keeps the value of a {@link CheckBox} in sync
         * with the value of the current {@link Match}.
         * Also preloads the checkbox checked value to what the current value in the
         * {@link Match} database is.
         * @param view The view hosting the {@link CheckBox}
         * @param key The key that should be updated in the current {@link Match} object
         * @see Match
         */
        public static void bindCheckbox(@NonNull View view, @NonNull final String key, @Nullable final View[] children) {
            CheckBox checkBox = (CheckBox)view;

            // Add listener
            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    ScoutAuthState.shared.pitScoutRecord.set(key, isChecked);

                    if (children != null) {
                        for (View view : children) {
                            view.setEnabled(isChecked);

                            if (isChecked) {
                                continue;
                            }

                            if (view instanceof CheckBox) {
                                ((CheckBox) view).setChecked(false);
                            } else if (view instanceof Spinner) {
                                ((Spinner) view).setSelection(0);
                            }
                        }
                    }
                }
            });

            // Preload data
            boolean checked = ScoutAuthState.shared.pitScoutRecord.bool(key).equals("TRUE");
            checkBox.setChecked(checked);

            // if I'm not checked, disable my children
            if (!checked && children != null) {
                for (View child : children) {
                    child.setEnabled(false);
                }
            }
        }

        public static void bindEditText(@NonNull View view, @NonNull final String key) {
            EditText editText = (EditText)view;

            // Add listener
            editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {}

                @Override
                public void afterTextChanged(Editable s) {
                    ScoutAuthState.shared.pitScoutRecord.set(key, s.toString());
                }
            });

            // Preload data
            String comments = ScoutAuthState.shared.pitScoutRecord.str(key);
            if (comments != null) {
                editText.setText(comments);
            }

            // Disallow commas
            editText.setFilters(new InputFilter[]{new InputFilter() {
                @Override
                public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                    if (source != null && source.toString().contains(",")) {
                        return "";
                    }

                    return null;
                }
            }});
        }

        public static void bindSpinner(Context context, View view, final String key,
                                       String[] options) {
            // Create listener
            ArrayAdapter<String> adapter = new ArrayAdapter<>(
                    context, R.layout.spinner_drop_down_item, options);
            Spinner spinner = (Spinner)view;
            spinner.setAdapter(adapter);
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    ScoutAuthState.shared.pitScoutRecord.set(key, position);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {}
            });

            // Prefill
            int savedIndex = ScoutAuthState.shared.pitScoutRecord.num(key);
            if (savedIndex == -1) savedIndex = 0;
            spinner.setSelection(savedIndex);
        }

        public static void bindStepper(View view, final String key) {
            Stepper stepper = new Stepper(view);
            StepperValueChangedListener listener = new StepperValueChangedListener() {
                @Override
                public boolean shouldChange() {
                    return true;
                }

                @Override
                public void valueChanged(Stepper stepper, int newValue) {
                    ScoutAuthState.shared.pitScoutRecord.set(key, newValue);
                }
            };
            stepper.setOnValueChangedListener(listener);

            // Preload
            stepper.setValue(ScoutAuthState.shared.pitScoutRecord.num(key));
        }
    }
}
