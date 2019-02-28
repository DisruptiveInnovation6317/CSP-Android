package com.frc63175985.csp.auth;

import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.frc63175985.csp.MainActivity;
import com.frc63175985.csp.enums.BaseScoutType;
import com.frc63175985.csp.enums.CargoShipSelection;
import com.frc63175985.csp.enums.LevelSelection;
import com.frc63175985.csp.enums.ScoreObject;

import java.util.HashMap;

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
    public static final String LEAVES_HAB = "auto_flBaseLine";
    public static final String START_OBJECT = "auto_idStartObject";
    // AUTONOMOUS CARGOSHIP
    // AUTONOMOUS ROCKET
    public static final String LOSES_START_OBJECT = "auto_flLoseStartObject";
    public static final String ROBOT_CONTACT = "auto_flRobotContact";
    public static final String FOUL = "auto_flFoul";
    public static final String CROSS_OVER = "auto_flCrossOver";

    // Rocket
    public static final String ROCKET_LOW_HATCH_ATTEMPT = "numRocketLowHatchAttempt";
    public static final String ROCKET_LOW_HATCH_SUCCESS = "numRocketLowHatchSuccess";
    public static final String ROCKET_LOW_CARGO_ATTEMPT = "numRocketLowCargoAttempt";
    public static final String ROCKET_LOW_CARGO_SUCCESS = "numRocketLowCargoSuccess";
    public static final String ROCKET_MIDDLE_HATCH_ATTEMPT = "numRocketMidHatchAttempt";
    public static final String ROCKET_MIDDLE_HATCH_SUCCESS = "numRocketMidHatchSuccess";
    public static final String ROCKET_MIDDLE_CARGO_ATTEMPT = "numRocketMidCargoAttempt";
    public static final String ROCKET_MIDDLE_CARGO_SUCCESS = "numRocketMidCargoSuccess";
    public static final String ROCKET_HIGH_HATCH_ATTEMPT = "numRocketHighAttempt";
    public static final String ROCKET_HIGH_HATCH_SUCCESS = "numRocketHighSuccess";
    public static final String ROCKET_HIGH_CARGO_ATTEMPT = "numRocketHighCargoAttempt";
    public static final String ROCKET_HIGH_CARGO_SUCCESS = "numRocketHighCargoSuccess";

    // Cargo Ship
    public static final String CARGO_FRONT_HATCH_ATTEMPT = "numShipFrontHatchAttempt";
    public static final String CARGO_FRONT_HATCH_SUCCESS = "numShipFrontHatchSuccess";
    public static final String CARGO_FRONT_CARGO_ATTEMPT = "numShipFrontCargoAttempt";
    public static final String CARGO_FRONT_CARGO_SUCCESS = "numShipFrontCargoSuccess";
    public static final String CARGO_SIDE_HATCH_ATTEMPT = "numShipSideHatchAttempt";
    public static final String CARGO_SIDE_HATCH_SUCCESS = "numShipSideHatchSuccess";
    public static final String CARGO_SIDE_CARGO_ATTEMPT = "numShipSideCargoAttempt";
    public static final String CARGO_SIDE_CARGO_SUCCESS = "numShipSideCargoSuccess";

    public static final String DEFENSE = "tele_flDefence";

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
        sb.append("NULL").append(","); // ID
        sb.append(ScoutAuthState.shared.tournament).append(","); // idEvent

        // Autonomous
        sb.append(bool(ACTIVE)).append(","); // auto_flState
        sb.append(num("auto_idStartPosition")).append(","); // auto_idStartPosition
        sb.append(num("auto_idStartLevel")).append(","); // auto_idStartLevel
        sb.append(bool(LEAVES_HAB)).append(","); // auto_flBaseLine
        sb.append(num(START_OBJECT)).append(","); // auto_idStartObject

        // TODO Autonomous - Rocket

        // TODO Autonomous - Cargo Ship

        sb.append(bool(LOSES_START_OBJECT)).append(","); // auto_flLoseStartObject
        sb.append(bool(ROBOT_CONTACT)).append(","); // auto_flRobotContact
        sb.append(bool(FOUL)).append(","); // auto_flFoul
        sb.append(bool(CROSS_OVER)).append(","); // auto_flCrossOver

        // TeleOp

        // Other

        return sb.toString();
    }

    public String str(String key) {
        Object value = data.get(key);
        if (value == null) {
            return null;
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
        return num == null ? -1 : (int)num;
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

        /**
         * TODO
         * @param parentView
         * @param id
         * @param key
         * @param options
         * @param enumClass
         */
        public static void bindSpinner(View parentView, int id, final String key, String[] options, Class enumClass) {

        }
    }
}


/*
numMatch
idTeam
idAlliance
idDriveStation
txScoutName
flCrashed
flYellow
flRed

X auto_flState
X auto_idStartPosition
X auto_idStartLevel
X auto_flBaseLine
X auto_idStartObject
auto_numShipFrontHatchAttempt
auto_numShipFrontHatchSuccess
auto_numShipSideHatchAttempt
auto_numShipSideHatchSuccess
auto_numShipFrontCargoAttempt
auto_numShipFrontCargoSuccess
auto_numShipSideCargoAttempt
auto_numShipSideCargoSuccess
auto_numRocketLowHatchAttempt
auto_numRocketLowHatchSuccess
auto_numRocketMidHatchAttempt
auto_numRocketMidHatchSuccess
auto_numRocketHighAttempt
auto_numRocketHighSuccess
auto_numRocketLowCargoAttempt
auto_numRocketLowCargoSuccess
auto_numRocketMidCargoAttempt
auto_numRocketMidCargoSuccess
auto_numRocketHighCargoAttempt
auto_numRocketHighCargoSuccess
X auto_flLoseStartObject
X auto_flRobotContact
X auto_flFoul
X auto_flCrossOver

tele_numShipFrontHatchAttempt
tele_numShipFrontHatchSuccess
tele_numShipSideHatchAttempt
tele_numShipSideHatchSuccess
tele_numShipFrontCargoAttempt
tele_numShipFrontCargoSuccess
tele_numShipSideCargoAttempt
tele_numShipSideCargoSuccess
tele_numRocketLowHatchAttempt
tele_numRocketLowHatchSuccess
tele_numRocketMidHatchAttempt
tele_numRocketMidHatchSuccess
tele_numRocketHighAttempt
tele_numRocketHighSuccess
tele_numRocketLowCargoAttempt
tele_numRocketLowCargoSuccess
tele_numRocketMidCargoAttempt
tele_numRocketMidCargoSuccess
tele_numRocketHighCargoAttempt
tele_numRocketHighCargoSuccess

tele_idClimb
tele_idClimbOutcome
tele_idClimbGrab
tele_idClimbSpeed
tele_numClimbAssists
tele_idClimbLevel
tele_flClimbFall
tele_flDefence

flIntakeHatchGround
flIntakeHatchStation
flIntakeCargoGround
flIntakeCargoStation

comm_txNotes
comm_flHighlight
comm_flWarning
comm_idDriveRating
comm_flAlliance
comm_flRecovery
comm_flStrategy
comm_flOwnThing
comm_flGoodDefence

dtCreation
dtModified
txComputerName
flRanking1
flRanking2
 */