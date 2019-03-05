package com.frc63175985.csp.auth;

import android.content.Context;
import android.os.Build;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;

import com.frc63175985.csp.MainActivity;
import com.frc63175985.csp.R;
import com.frc63175985.csp.enums.BaseScoutType;
import com.frc63175985.csp.enums.CargoShipSelection;
import com.frc63175985.csp.enums.LevelSelection;
import com.frc63175985.csp.enums.ScoreObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import static com.frc63175985.csp.enums.BaseScoutType.AUTONOMOUS;
import static com.frc63175985.csp.enums.CargoShipSelection.FRONT;
import static com.frc63175985.csp.enums.CargoShipSelection.SIDE;
import static com.frc63175985.csp.enums.LevelSelection.HIGH;
import static com.frc63175985.csp.enums.LevelSelection.LOW;
import static com.frc63175985.csp.enums.LevelSelection.MIDDLE;
import static com.frc63175985.csp.enums.ScoreObject.CARGO;
import static com.frc63175985.csp.enums.ScoreObject.HATCH;

/**
 * The object representation of a Match.
 * This will be responsible for storing match information, conforming to the database
 * schema and being able to export in CSV format.
 */
public class Match {
    /* Keys */
    public static final String AUTO_PREFIX = "auto_";
    public static final String TELEOP_PREFIX = "tele_";

    public static final String MATCH_NUMBER = "numMatch";
    public static final String TEAM_NUMBER = "idTeam";
    public static final String ALLIANCE = "idAlliance";
    public static final String DRIVE_STATION = "idDriveStation";

    public static final String ROBOT_CRASHED = "flCrashed";
    public static final String YELLOW_CARD = "flYellow";
    public static final String RED_CARD = "flRed";

    // Autonomous
    public static final String ACTIVE = "auto_flState";
    public static final String START_POSITION = "auto_idStartPosition";
    public static final String START_LEVEL = "auto_idStartLevel";
    public static final String LEAVES_HAB = "auto_flBaseLine";
    public static final String START_OBJECT = "auto_idStartObject";
    // AUTONOMOUS CARGOSHIP
    // AUTONOMOUS ROCKET
    public static final String LOSES_START_OBJECT = "auto_flLoseStartObject";
    public static final String ROBOT_CONTACT = "auto_flRobotContact";
    public static final String FOUL = "auto_flFoul";
    public static final String CROSS_OVER = "auto_flCrossOver";

    // Cargo Ship
    public static final String CARGO_FRONT_HATCH_ATTEMPT = "numShipFrontHatchAttempt";
    public static final String CARGO_FRONT_HATCH_SUCCESS = "numShipFrontHatchSuccess";
    public static final String CARGO_SIDE_HATCH_ATTEMPT = "numShipSideHatchAttempt";
    public static final String CARGO_SIDE_HATCH_SUCCESS = "numShipSideHatchSuccess";
    public static final String CARGO_FRONT_CARGO_ATTEMPT = "numShipFrontCargoAttempt";
    public static final String CARGO_FRONT_CARGO_SUCCESS = "numShipFrontCargoSuccess";
    public static final String CARGO_SIDE_CARGO_ATTEMPT = "numShipSideCargoAttempt";
    public static final String CARGO_SIDE_CARGO_SUCCESS = "numShipSideCargoSuccess";

    // Rocket
    public static final String ROCKET_LOW_HATCH_ATTEMPT = "numRocketLowHatchAttempt";
    public static final String ROCKET_LOW_HATCH_SUCCESS = "numRocketLowHatchSuccess";
    public static final String ROCKET_MIDDLE_HATCH_ATTEMPT = "numRocketMidHatchAttempt";
    public static final String ROCKET_MIDDLE_HATCH_SUCCESS = "numRocketMidHatchSuccess";
    public static final String ROCKET_HIGH_HATCH_ATTEMPT = "numRocketHighAttempt";
    public static final String ROCKET_HIGH_HATCH_SUCCESS = "numRocketHighSuccess";
    public static final String ROCKET_LOW_CARGO_ATTEMPT = "numRocketLowCargoAttempt";
    public static final String ROCKET_LOW_CARGO_SUCCESS = "numRocketLowCargoSuccess";
    public static final String ROCKET_MIDDLE_CARGO_ATTEMPT = "numRocketMidCargoAttempt";
    public static final String ROCKET_MIDDLE_CARGO_SUCCESS = "numRocketMidCargoSuccess";
    public static final String ROCKET_HIGH_CARGO_ATTEMPT = "numRocketHighCargoAttempt";
    public static final String ROCKET_HIGH_CARGO_SUCCESS = "numRocketHighCargoSuccess";

    // TeleOp
    public static final String DEFENSE = "tele_flDefence";
    public static final String TAKE_HATCH_GROUND = "flIntakeHatchGround";
    public static final String TAKE_HATCH_STATION = "flIntakeHatchStation";
    public static final String TAKE_CARGO_GROUND = "flIntakeCargoGround";
    public static final String TAKE_CARGO_STATION = "flIntakeCargoStation";

    // End Game
    public static final String CLIMB = "tele_idClimb";
    public static final String CLIMB_OUTCOME = "tele_idClimbOutcome";
    public static final String CLIMB_GRAB = "tele_idClimbGrab";
    public static final String CLIMB_SPEED = "tele_idClimbSpeed";
    public static final String NUMBER_CLIMB_ASSISTS = "tele_numClimbAssists";
    public static final String CLIMB_LEVEL = "tele_idClimbLevel";
    public static final String CLIMB_FALL = "tele_flClimbFall";

    // Comments
    public static final String COMMENTS = "comm_txNotes";
    public static final String HIGHLIGHT = "comm_flHighlight";
    public static final String WARNING = "comm_flWarning";
    public static final String DRIVE_RATING = "comm_idDriveRating";
    public static final String WORK_WITH_ALLIANCE = "comm_flAlliance";
    public static final String RECOVER = "comm_flRecovery";
    public static final String WORKED_STRATEGY = "comm_flStrategy";
    public static final String WORKED_SOLO = "comm_flOwnThing";
    public static final String EFFECTIVE_DEFENCE = "comm_flGoodDefence";

    public static final String RANKING_1 = "flRanking1";
    public static final String RANKING_2 = "flRanking2";

    private HashMap<String, Object> data;

    public Match() {
        data = new HashMap<>();
    }

    public void set(String key, Object value) {
        data.put(key, value);
    }

    public void updateRocketValue(BaseScoutType type, LevelSelection selection, ScoreObject object, Object value) {
        if (!(value instanceof Integer) && !(value instanceof Boolean)) {
            // Abort if the new value is not a number or boolean
            throw new IllegalArgumentException("newValue must be Integer or Boolean");
        }

        // Add prefix to key
        String key = type == AUTONOMOUS ? AUTO_PREFIX : TELEOP_PREFIX;

        if (value instanceof Integer) {
            // Attempt
            if (object == HATCH) {
                // Attempt Hatch
                if (selection == HIGH) {
                    // Attempt Hatch High
                    key += ROCKET_HIGH_HATCH_ATTEMPT;
                } else if (selection == MIDDLE) {
                    // Attempt Hatch Middle
                    key += ROCKET_MIDDLE_HATCH_ATTEMPT;
                } else if (selection == LOW) {
                    // Attempt Hatch Low
                    key += ROCKET_LOW_HATCH_ATTEMPT;
                }
            } else if (object == CARGO) {
                // Attempt Cargo
                if (selection == HIGH) {
                    // Attempt Cargo High
                    key += ROCKET_HIGH_CARGO_ATTEMPT;
                } else if (selection == MIDDLE) {
                    // Attempt Cargo Middle
                    key += ROCKET_MIDDLE_CARGO_ATTEMPT;
                } else if (selection == LOW) {
                    // Attempt Cargo Low
                    key += ROCKET_LOW_CARGO_ATTEMPT;
                }
            }
        } else {
            // Success
            if (object == HATCH) {
                // Success Hatch
                if (selection == HIGH) {
                    // Success Hatch High
                    key += ROCKET_HIGH_HATCH_SUCCESS;
                } else if (selection == MIDDLE) {
                    // Success Hatch Middle
                    key += ROCKET_MIDDLE_HATCH_SUCCESS;
                } else if (selection == LOW) {
                    // Success Hatch Low
                    key += ROCKET_LOW_HATCH_SUCCESS;
                }
            } else if (object == CARGO) {
                // Success Cargo
                if (selection == HIGH) {
                    // Success Cargo High
                    key += ROCKET_HIGH_CARGO_SUCCESS;
                } else if (selection == MIDDLE) {
                    // Success Cargo Middle
                    key += ROCKET_MIDDLE_CARGO_SUCCESS;
                } else if (selection == LOW) {
                    // Success Cargo Low
                    key += ROCKET_LOW_CARGO_SUCCESS;
                }
            }
        }

        data.put(key, value);
    }

    public void updateCargoShipValue(BaseScoutType type, CargoShipSelection selection, ScoreObject object, Object value) {
        if (!(value instanceof Integer) && !(value instanceof Boolean)) {
            // Abort if the new value is not a number or boolean
            throw new IllegalArgumentException("newValue must be Integer or Boolean");
        }

        // Add prefix to key
        String key = type == AUTONOMOUS ? AUTO_PREFIX : TELEOP_PREFIX;

        if (value instanceof Integer) {
            // Attempt
            if (object == HATCH) {
                // Attempt Hatch
                if (selection == FRONT) {
                    // Attempt Hatch Front
                    key += CARGO_FRONT_HATCH_ATTEMPT;
                } else if (selection == SIDE) {
                    // Attempt Hatch Side
                    key += CARGO_SIDE_HATCH_ATTEMPT;
                }
            } else if (object == CARGO) {
                // Attempt Cargo
                if (selection == FRONT) {
                    // Attempt Cargo Front
                    key += CARGO_FRONT_CARGO_ATTEMPT;
                } else if (selection == SIDE) {
                    // Attempt Cargo Side
                    key += CARGO_SIDE_CARGO_ATTEMPT;
                }
            }
        } else {
            // Success
            if (object == HATCH) {
                // Success Hatch
                if (selection == FRONT) {
                    // Success Hatch Front
                    key += CARGO_FRONT_HATCH_SUCCESS;
                } else if (selection == SIDE) {
                    // Success Hatch Side
                    key += CARGO_SIDE_HATCH_SUCCESS;
                }
            } else if (object == CARGO) {
                // Success Cargo
                if (selection == FRONT) {
                    // Success Cargo Front
                    key += CARGO_FRONT_CARGO_SUCCESS;
                } else if (selection == SIDE) {
                    // Success Cargo Side
                    key += CARGO_SIDE_CARGO_SUCCESS;
                }
            }
        }

        data.put(key, value);
    }

    /**
     * Get this class expressed in CSV format in accordance to ProjectB's Microsoft
     * Access database tables.
     * @return this match in CSV format
     */
    @Nullable
    public String export() {
        if (!ScoutAuthState.shared.isLoggedIn()) {
            // Something is very wrong...
            MainActivity.logE("Trying to export match without a scout being logged in.");
            return null;
        }

        StringBuilder sb = new StringBuilder();

        // Meta
        sb.append("DEFAULT").append(","); // ID
        sb.append(ScoutAuthState.shared.tournament).append(","); // idEvent
        sb.append(str(MATCH_NUMBER)).append(","); // numMatch
        sb.append(str(TEAM_NUMBER)).append(","); // idTeam
        sb.append(num(ALLIANCE)).append(","); // idAlliance
        sb.append(num(DRIVE_STATION)).append(","); // idDriveStation
        sb.append(ScoutAuthState.shared.scout).append(","); // txScoutName
        sb.append(bool(ROBOT_CRASHED)).append(","); // flCrashed
        sb.append(bool(YELLOW_CARD)).append(","); // flYellow
        sb.append(bool(RED_CARD)).append(","); // flRed

        // Autonomous
        sb.append(bool(ACTIVE)).append(","); // auto_flState
        sb.append(num(START_POSITION)).append(","); // auto_idStartPosition
        sb.append(num(START_LEVEL)).append(","); // auto_idStartLevel
        sb.append(bool(LEAVES_HAB)).append(","); // auto_flBaseLine
        sb.append(num(START_OBJECT)).append(","); // auto_idStartObject

        // Autonomous - Cargo Ship
        sb.append(num(AUTO_PREFIX + CARGO_FRONT_HATCH_ATTEMPT)).append(",");
        sb.append(bool(AUTO_PREFIX + CARGO_FRONT_HATCH_SUCCESS)).append(",");
        sb.append(num(AUTO_PREFIX + CARGO_SIDE_HATCH_ATTEMPT)).append(",");
        sb.append(bool(AUTO_PREFIX + CARGO_SIDE_HATCH_SUCCESS)).append(",");
        sb.append(num(AUTO_PREFIX + CARGO_FRONT_CARGO_ATTEMPT)).append(",");
        sb.append(bool(AUTO_PREFIX + CARGO_FRONT_CARGO_SUCCESS)).append(",");
        sb.append(num(AUTO_PREFIX + CARGO_SIDE_CARGO_ATTEMPT)).append(",");
        sb.append(bool(AUTO_PREFIX + CARGO_SIDE_CARGO_SUCCESS)).append(",");

        // Autonomous - Rocket
        sb.append(num(AUTO_PREFIX + ROCKET_LOW_HATCH_ATTEMPT)).append(",");
        sb.append(bool(AUTO_PREFIX + ROCKET_LOW_HATCH_SUCCESS)).append(",");
        sb.append(num(AUTO_PREFIX + ROCKET_MIDDLE_HATCH_ATTEMPT)).append(",");
        sb.append(bool(AUTO_PREFIX + ROCKET_MIDDLE_HATCH_SUCCESS)).append(",");
        sb.append(num(AUTO_PREFIX + ROCKET_HIGH_HATCH_ATTEMPT)).append(",");
        sb.append(bool(AUTO_PREFIX + ROCKET_HIGH_HATCH_SUCCESS)).append(",");
        sb.append(num(AUTO_PREFIX + ROCKET_LOW_CARGO_ATTEMPT)).append(",");
        sb.append(bool(AUTO_PREFIX + ROCKET_LOW_CARGO_SUCCESS)).append(",");
        sb.append(num(AUTO_PREFIX + ROCKET_MIDDLE_CARGO_ATTEMPT)).append(",");
        sb.append(bool(AUTO_PREFIX + ROCKET_MIDDLE_CARGO_SUCCESS)).append(",");
        sb.append(num(AUTO_PREFIX + ROCKET_HIGH_CARGO_ATTEMPT)).append(",");
        sb.append(bool(AUTO_PREFIX + ROCKET_HIGH_CARGO_SUCCESS)).append(",");

        sb.append(bool(LOSES_START_OBJECT)).append(","); // auto_flLoseStartObject
        sb.append(bool(ROBOT_CONTACT)).append(","); // auto_flRobotContact
        sb.append(bool(FOUL)).append(","); // auto_flFoul
        sb.append(bool(CROSS_OVER)).append(","); // auto_flCrossOver

        // TeleOp - Cargo Ship
        sb.append(num(TELEOP_PREFIX + CARGO_FRONT_HATCH_ATTEMPT)).append(",");
        sb.append(bool(TELEOP_PREFIX + CARGO_FRONT_HATCH_SUCCESS)).append(",");
        sb.append(num(TELEOP_PREFIX + CARGO_SIDE_HATCH_ATTEMPT)).append(",");
        sb.append(bool(TELEOP_PREFIX + CARGO_SIDE_HATCH_SUCCESS)).append(",");
        sb.append(num(TELEOP_PREFIX + CARGO_FRONT_CARGO_ATTEMPT)).append(",");
        sb.append(bool(TELEOP_PREFIX + CARGO_FRONT_CARGO_SUCCESS)).append(",");
        sb.append(num(TELEOP_PREFIX + CARGO_SIDE_CARGO_ATTEMPT)).append(",");
        sb.append(bool(TELEOP_PREFIX + CARGO_SIDE_CARGO_SUCCESS)).append(",");

        // TeleOp - Rocket
        sb.append(num(TELEOP_PREFIX + ROCKET_LOW_HATCH_ATTEMPT)).append(",");
        sb.append(bool(TELEOP_PREFIX + ROCKET_LOW_HATCH_SUCCESS)).append(",");
        sb.append(num(TELEOP_PREFIX + ROCKET_MIDDLE_HATCH_ATTEMPT)).append(",");
        sb.append(bool(TELEOP_PREFIX + ROCKET_MIDDLE_HATCH_SUCCESS)).append(",");
        sb.append(num(TELEOP_PREFIX + ROCKET_HIGH_HATCH_ATTEMPT)).append(",");
        sb.append(bool(TELEOP_PREFIX + ROCKET_HIGH_HATCH_SUCCESS)).append(",");
        sb.append(num(TELEOP_PREFIX + ROCKET_LOW_CARGO_ATTEMPT)).append(",");
        sb.append(bool(TELEOP_PREFIX + ROCKET_LOW_CARGO_SUCCESS)).append(",");
        sb.append(num(TELEOP_PREFIX + ROCKET_MIDDLE_CARGO_ATTEMPT)).append(",");
        sb.append(bool(TELEOP_PREFIX + ROCKET_MIDDLE_CARGO_SUCCESS)).append(",");
        sb.append(num(TELEOP_PREFIX + ROCKET_HIGH_CARGO_ATTEMPT)).append(",");
        sb.append(bool(TELEOP_PREFIX + ROCKET_HIGH_CARGO_SUCCESS)).append(",");

        // End Game
        sb.append(num(CLIMB)).append(","); // tele_idClimb
        sb.append(num(CLIMB_OUTCOME)).append(","); // tele_idClimbOutcome
        sb.append(num(CLIMB_GRAB)).append(","); // tele_idClimbGrab
        sb.append(num(CLIMB_SPEED)).append(","); // tele_idClimbSpeed
        sb.append(str(NUMBER_CLIMB_ASSISTS)).append(","); // tele_numClimbAssists
        sb.append(num(CLIMB_LEVEL)).append(","); // tele_idClimbLevel
        sb.append(bool(CLIMB_FALL)).append(","); // tele_flClimbFall

        sb.append(bool(DEFENSE)).append(","); // tele_flDefence
        sb.append(bool(TAKE_HATCH_GROUND)).append(","); // flIntakeHatchGround
        sb.append(bool(TAKE_HATCH_STATION)).append(","); // flIntakeHatchStation
        sb.append(bool(TAKE_CARGO_GROUND)).append(","); // flIntakeCargoGround
        sb.append(bool(TAKE_CARGO_STATION)).append(","); // flIntakeCargoStation

        // Comments
        sb.append(str(COMMENTS)).append(","); // comm_txNotes
        sb.append(bool(HIGHLIGHT)).append(","); // comm_flHighlight
        sb.append(bool(WARNING)).append(","); // comm_flWarning
        sb.append(num(DRIVE_RATING)).append(","); // comm_idDriveRating
        sb.append(bool(WORK_WITH_ALLIANCE)).append(","); // comm_flAlliance
        sb.append(bool(RECOVER)).append(","); // comm_flRecovery
        sb.append(bool(WORKED_STRATEGY)).append(","); // comm_flStrategy
        sb.append(bool(WORKED_SOLO)).append(","); // comm_flOwnThing
        sb.append(bool(EFFECTIVE_DEFENCE)).append(","); // comm_flGoodDefence

        // Creation
        sb.append(new SimpleDateFormat("y-M-d-k-h-m-s", Locale.US).format(new Date())).append(","); // dtCreation
        sb.append(new SimpleDateFormat("y-M-d-k-h-m-s", Locale.US).format(new Date())).append(","); // dtModified
        sb.append(Build.MODEL == null || Build.MODEL.isEmpty() ? "Android-Device" : Build.MODEL).append(","); // txComputerName
        sb.append(bool(RANKING_1)).append(","); // flRanking1
        sb.append(bool(RANKING_2)); // flRanking2

        return sb.toString();
    }

    private String str(String key) {
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
    public int num(String key) {
        Object num = data.get(key);
        return num == null ? 0 : (int)num;
    }

    /**
     * Search for and return a boolean in any of our {@link HashMap}'s
     * This method returns the String representation of this boolean,
     * e.g. {@code "TRUE"} or {@code "FALSE"}
     * @param key The key to be searched for
     * @return The value, if it exists, otherwise {@code "FALSE"}
     */
    public String bool(String key) {
        Object bool = data.get(key);
        if (bool == null) return "FALSE";
        else return (boolean)bool ? "TRUE" : "FALSE";
    }

    /**
     * A class GUI helpers revolving around displaying Match information
     */
    public static class GUI {
        /**
         * Creates a listener that keeps the value of a {@link CheckBox} in sync
         * with the value of the current {@link Match}.
         * Also preloads the checkbox checked value to what the current value in the
         * {@link Match} database is.
         * @param parentView The parent view hosting the {@link CheckBox}
         * @param id The identifier of the {@link CheckBox}
         * @param key The key that should be updated in the current {@link Match} object
         * @see Match
         */
        public static void bindCheckbox(View parentView, int id, final String key) {
            CheckBox checkBox = parentView.findViewById(id);

            // Add listener
            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                ScoutAuthState.shared.currentMatch.set(key, isChecked);
                }
            });

            // Preload data
            boolean checked = ScoutAuthState.shared.currentMatch.bool(key).equals("TRUE");
            checkBox.setChecked(checked);
        }

        public static void bindEditText(View parentView, int id, final String key) {
            EditText editText = parentView.findViewById(id);

            // Add listener
            editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {}

                @Override
                public void afterTextChanged(Editable s) {
                    ScoutAuthState.shared.currentMatch.set(key, s.toString());
                }
            });

            // Preload data
            String comments = ScoutAuthState.shared.currentMatch.str(key);
            if (comments != null) {
                editText.setText(comments);
            }
        }

        public static void bindSpinner(Context context, View parentView, int id,
                                       final String key, String[] options) {
            // Create listener
            ArrayAdapter<String> adapter = new ArrayAdapter<>(
                    context, R.layout.spinner_drop_down_item, options);
            Spinner spinner = parentView.findViewById(id);
            spinner.setAdapter(adapter);
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    ScoutAuthState.shared.currentMatch.set(key, position);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {}
            });

            // Prefill
            int savedIndex = ScoutAuthState.shared.currentMatch.num(key);
            if (savedIndex == -1) savedIndex = 0;
            spinner.setSelection(savedIndex);
        }
    }
}