package com.frc63175985.csp.auth;

import android.support.annotation.Nullable;

import com.frc63175985.csp.MainActivity;
import com.frc63175985.csp.enums.CargoShipSelection;
import com.frc63175985.csp.enums.LevelSelection;
import com.frc63175985.csp.enums.ScoreObject;

import java.util.HashMap;

import static com.frc63175985.csp.enums.ScoreObject.CARGO;
import static com.frc63175985.csp.enums.ScoreObject.HATCH;

/**
 * The object representation of a Match.
 * This will be responsible for storing match information, conforming to the database
 * and being able to export in CSV format.
 */
public class Match {
    /* Keys */
    // Autonomous
    public static final String ACTIVE = "auto_flState";
    public static final String LEAVES_HAB = "auto_flBaseLine";
    public static final String START_OBJECT = "auto_idStartObject";

    // Autonomous - Rocket
    public static final String AUTO_ROCKET_LOW_HATCH_ATTEMPT = "auto_numRocketLowHatchAttempt";
    public static final String AUTO_ROCKET_LOW_HATCH_SUCCESS = "auto_numRocketLowHatchSuccess";
    public static final String AUTO_ROCKET_LOW_CARGO_ATTEMPT = "auto_numRocketLowCargoAttempt";
    public static final String AUTO_ROCKET_LOW_CARGO_SUCCESS = "auto_numRocketLowCargoSuccess";
    public static final String AUTO_ROCKET_MIDDLE_HATCH_ATTEMPT = "auto_numRocketMidHatchAttempt";
    public static final String AUTO_ROCKET_MIDDLE_HATCH_SUCCESS = "auto_numRocketMidHatchSuccess";
    public static final String AUTO_ROCKET_MIDDLE_CARGO_ATTEMPT = "auto_numRocketMidCargoAttempt";
    public static final String AUTO_ROCKET_MIDDLE_CARGO_SUCCESS = "auto_numRocketMidCargoSuccess";
    public static final String AUTO_ROCKET_HIGH_HATCH_ATTEMPT = "auto_numRocketHighAttempt";
    public static final String AUTO_ROCKET_HIGH_HATCH_SUCCESS = "auto_numRocketHighSuccess";
    public static final String AUTO_ROCKET_HIGH_CARGO_ATTEMPT = "auto_numRocketHighCargoAttempt";
    public static final String AUTO_ROCKET_HIGH_CARGO_SUCCESS = "auto_numRocketHighCargoSuccess";

    // Autonomous - Cargo
    public static final String AUTO_CARGO_FRONT_HATCH_ATTEMPT = "auto_numShipFrontHatchAttempt";
    public static final String AUTO_CARGO_FRONT_HATCH_SUCCESS = "auto_numShipFrontHatchSuccess";
    public static final String AUTO_CARGO_FRONT_CARGO_ATTEMPT = "auto_numShipFrontCargoAttempt";
    public static final String AUTO_CARGO_FRONT_CARGO_SUCCESS = "auto_numShipFrontCargoSuccess";
    public static final String AUTO_CARGO_SIDE_HATCH_ATTEMPT = "auto_numShipSideHatchAttempt";
    public static final String AUTO_CARGO_SIDE_HATCH_SUCCESS = "auto_numShipSideHatchSuccess";
    public static final String AUTO_CARGO_SIDE_CARGO_ATTEMPT = "auto_numShipSideCargoAttempt";
    public static final String AUTO_CARGO_SIDE_CARGO_SUCCESS = "auto_numShipSideCargoSuccess";

    public static final String LOSES_START_OBJECT = "auto_flLoseStartObject";
    public static final String ROBOT_CONTACT = "auto_flRobotContact";
    public static final String FOUL = "auto_flFoul";
    public static final String CROSS_OVER = "auto_flCrossOver";

    private HashMap<String, Object> data;

    public Match() {
        data = new HashMap<>();
    }

    public void set(String key, Object value) {
        data.put(key, value);
    }

    public void updateAutonomousCargoShipValue(
            CargoShipSelection selection,
            ScoreObject scoreObject,
            Object newValue) throws IllegalArgumentException {
        if (newValue instanceof Integer) {
            if (scoreObject == HATCH) {
                switch (selection) {
                    case FRONT:
                        // front hatch attempt
                        data.put(AUTO_CARGO_FRONT_HATCH_ATTEMPT, newValue);
                        break;
                    case SIDE:
                        // side hatch attempt
                        data.put(AUTO_CARGO_SIDE_HATCH_ATTEMPT, newValue);
                        break;
                }
            } else if (scoreObject == CARGO) {
                switch (selection) {
                    case FRONT:
                        // front cargo attempt
                        data.put(AUTO_CARGO_FRONT_CARGO_ATTEMPT, newValue);
                        break;
                    case SIDE:
                        // side cargo attempt
                        data.put(AUTO_CARGO_SIDE_CARGO_ATTEMPT, newValue);
                        break;
                }
            }
        } else if (newValue instanceof Boolean) {
            if (scoreObject == HATCH) {
                switch (selection) {
                    case FRONT:
                        // front hatch success
                        data.put(AUTO_CARGO_FRONT_HATCH_SUCCESS, newValue);
                        break;
                    case SIDE:
                        // side hatch success
                        data.put(AUTO_CARGO_SIDE_HATCH_SUCCESS, newValue);
                        break;
                }
            } else if (scoreObject == CARGO) {
                switch (selection) {
                    case FRONT:
                        // front cargo success
                        data.put(AUTO_CARGO_FRONT_CARGO_SUCCESS, newValue);
                        break;
                    case SIDE:
                        // side cargo success
                        data.put(AUTO_CARGO_SIDE_CARGO_SUCCESS, newValue);
                        break;
                }
            }
        } else {
            throw new IllegalArgumentException("newValue must be Integer or Boolean");
        }
    }

    /**
     * Update a value in our autonomous dictionary based on the information provided
     * @param selection
     * @param scoreObject
     * @param newValue
     * @throws IllegalArgumentException thrown if {@code newValue} is not of type {@link Integer} or {@link Boolean}
     */
    public void updateAutonomousRocketValue(
            LevelSelection selection,
            ScoreObject scoreObject,
            Object newValue) throws IllegalArgumentException {
        if (newValue instanceof Integer) {
            // Attempt

            if (scoreObject == HATCH) {
                switch (selection) {
                    case TOP:
                        data.put(AUTO_ROCKET_HIGH_HATCH_ATTEMPT, newValue);
                        break;
                    case MIDDLE:
                        data.put(AUTO_ROCKET_MIDDLE_HATCH_ATTEMPT, newValue);
                        break;
                    case LOW:
                        data.put(AUTO_ROCKET_LOW_HATCH_ATTEMPT, newValue);
                        break;
                }
            } else if (scoreObject == CARGO) {
                switch (selection) {
                    case TOP:
                        data.put(AUTO_ROCKET_HIGH_CARGO_ATTEMPT, newValue);
                        break;
                    case MIDDLE:
                        data.put(AUTO_ROCKET_MIDDLE_CARGO_ATTEMPT, newValue);
                        break;
                    case LOW:
                        data.put(AUTO_ROCKET_LOW_CARGO_ATTEMPT, newValue);
                        break;
                }
            }
        } else if (newValue instanceof Boolean) {
            // Success

            if (scoreObject == HATCH) {
                switch (selection) {
                    case TOP:
                        data.put(AUTO_ROCKET_HIGH_HATCH_SUCCESS, newValue);
                        break;
                    case MIDDLE:
                        data.put(AUTO_ROCKET_MIDDLE_HATCH_SUCCESS, newValue);
                        break;
                    case LOW:
                        data.put(AUTO_ROCKET_LOW_HATCH_SUCCESS, newValue);
                        break;
                }
            } else if (scoreObject == CARGO) {
                switch (selection) {
                    case TOP:
                        data.put(AUTO_ROCKET_HIGH_CARGO_SUCCESS, newValue);
                        break;
                    case MIDDLE:
                        data.put(AUTO_ROCKET_MIDDLE_CARGO_SUCCESS, newValue);
                        break;
                    case LOW:
                        data.put(AUTO_ROCKET_LOW_CARGO_SUCCESS, newValue);
                        break;
                }
            }
        } else {
            throw new IllegalArgumentException("newValue must be Integer or Boolean");
        }
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

        return sb.toString();
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