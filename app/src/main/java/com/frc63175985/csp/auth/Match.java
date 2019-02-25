package com.frc63175985.csp.auth;

import android.support.annotation.Nullable;

import com.frc63175985.csp.enums.LevelSelection;
import com.frc63175985.csp.MainActivity;
import com.frc63175985.csp.enums.ScoreObject;

import java.util.HashMap;

import static com.frc63175985.csp.enums.LevelSelection.TOP;
import static com.frc63175985.csp.enums.ScoreObject.CARGO;
import static com.frc63175985.csp.enums.ScoreObject.HATCH;

/**
 * The object representation of a Match.
 * This will be responsible for storing match information, conforming to the database
 * and being able to export in CSV format.
 */
public class Match {
    private HashMap<String, Object> metaData;
    private HashMap<String, Object> autonomous;
    private HashMap<String, Object> teleop;

    public Match() {
        metaData = new HashMap<>();
        autonomous = new HashMap<>();
        teleop = new HashMap<>();
    }

    /**
     * Update a value in our autonomous dictionary based on the information provided
     * @param selection
     * @param scoreObject
     * @param newValue
     * @throws IllegalArgumentException thrown if input is not of type {@link Integer} or {@link Boolean}
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
                        autonomous.put("auto_numRocketHighAttempt", newValue);
                        break;
                    case MIDDLE:
                        autonomous.put("auto_numRocketMidHatchAttempt", newValue);
                        break;
                    case LOW:
                        autonomous.put("auto_numRocketLowHatchAttempt", newValue);
                        break;
                }
            } else if (scoreObject == CARGO) {
                switch (selection) {
                    case TOP:
                        autonomous.put("auto_numRocketHighCargoAttempt", newValue);
                        break;
                    case MIDDLE:
                        autonomous.put("auto_numRocketMidCargoAttempt", newValue);
                        break;
                    case LOW:
                        autonomous.put("auto_numRocketLowCargoAttempt", newValue);
                        break;
                }
            }
        } else if (newValue instanceof Boolean) {
            // Success

            if (scoreObject == HATCH) {
                switch (selection) {
                    case TOP:
                        autonomous.put("auto_numRocketHighSuccess", newValue);
                        break;
                    case MIDDLE:
                        autonomous.put("auto_numRocketMidHatchSuccess", newValue);
                        break;
                    case LOW:
                        autonomous.put("auto_numRocketLowHatchSuccess", newValue);
                        break;
                }
            } else if (scoreObject == CARGO) {
                switch (selection) {
                    case TOP:
                        autonomous.put("auto_numRocketHighCargoSuccess", newValue);
                        break;
                    case MIDDLE:
                        autonomous.put("auto_numRocketMidCargoSuccess", newValue);
                        break;
                    case LOW:
                        autonomous.put("auto_numRocketLowCargoSuccess", newValue);
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
        sb.append(bool("auto_flState")).append(","); // auto_flState
        sb.append(num("auto_idStartPosition")).append(","); // auto_idStartPosition
        sb.append(num("auto_idStartLevel")).append(","); // auto_idStartLevel
        sb.append(bool("auto_flBaseLine")).append(","); // auto_flBaseLine
        sb.append(num("auto_idStartObject")).append(","); // auto_idStartObject

        sb.append(bool("auto_flLoseStartObject")).append(","); // auto_flLoseStartObject
        sb.append(bool("auto_flRobotContact")).append(","); // auto_flRobotContact
        sb.append(bool("auto_flFoul")).append(","); // auto_flFoul
        sb.append(bool("auto_flCrossOver")).append(","); // auto_flCrossOver

        // TeleOp

        return sb.toString();
    }

    /**
     * Search for and return a value in any of our {@link HashMap}'s
     * @param key The key to searched for
     * @return The value, if it exists, otherwise {@code null}
     */
    @Nullable
    private Object findObject(String key) {
        Object potential = metaData.get(key);
        if (potential == null) {
            potential = autonomous.get(key);
            if (potential == null) {
                potential = teleop.get(key);
                if (potential == null) {
                    return null;
                }
            }
        }

        return potential;
    }

    /**
     * Search for and return a number in any of our {@link HashMap}'s
     * @param key The key to be searched for
     * @return The value, if it exists, otherwise {@code 0}
     */
    public int num(String key) {
        Object num = findObject(key);
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
        Object bool = findObject(key);
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