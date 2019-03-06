package com.frc63175985.csp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.frc63175985.csp.auth.Match;
import com.frc63175985.csp.auth.PitScoutRecord;
import com.frc63175985.csp.enums.BaseScoutType;

import static com.frc63175985.csp.enums.BaseScoutType.TELEOP;

public class TeleOpFragment extends BaseScoutFragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_teleop, container, false);

        initializeRocketAndCargoship(view);

        // Issues
        Match.GUI.bindCheckbox(view, R.id.teleop_total_robot_failure_checkBox, Match.ROBOT_CRASHED);
        Match.GUI.bindCheckbox(view, R.id.teleop_yellow_card_checkBox, Match.YELLOW_CARD);
        Match.GUI.bindCheckbox(view, R.id.teleop_red_card_checkBox, Match.RED_CARD);

        // Strategy
        Match.GUI.bindCheckbox(view, R.id.teleop_played_defense_checkBox, Match.DEFENSE);

        // Intake
        Match.GUI.bindCheckbox(view, R.id.teleop_cargo_ground_intake, Match.TAKE_CARGO_GROUND);
        Match.GUI.bindCheckbox(view, R.id.teleop_cargo_station_intake, Match.TAKE_CARGO_STATION);
        Match.GUI.bindCheckbox(view, R.id.teleop_hatch_ground_intake, Match.TAKE_HATCH_GROUND);
        Match.GUI.bindCheckbox(view, R.id.teleop_hatch_station_intake, Match.TAKE_HATCH_STATION);

        // End Game
        String[] climbOptions = new String[]{"Climb Option", "N/A", "Failed", "Success"};
        Match.GUI.bindSpinner(getContext(), view, R.id.teleop_climb_spinner, Match.CLIMB, climbOptions);
        Match.GUI.bindSpinner(getContext(), view, R.id.teleop_secure_hold_spinner, Match.CLIMB_GRAB, Match.SPEED_OPTIONS);
        Match.GUI.bindSpinner(getContext(), view, R.id.teleop_climb_speed_spinner, Match.CLIMB_SPEED, Match.SPEED_OPTIONS);
        String[] outcomeOptions = {"Outcome Option", "Self Only", "Self Plus Others", "Others Only", "Assisted"};
        Match.GUI.bindSpinner(getContext(), view, R.id.teleop_climb_outcome_spinner, Match.CLIMB_OUTCOME, outcomeOptions);
        Match.GUI.bindSpinner(getContext(), view, R.id.teleop_climb_height_spinner, Match.CLIMB_LEVEL, PitScoutRecord.HEIGHT_OPTIONS);
        Match.GUI.bindStepper(view, R.id.teleop_climb_assists_stepper, Match.NUMBER_CLIMB_ASSISTS);
        Match.GUI.bindCheckbox(view, R.id.teleop_robot_fell_checkBox, Match.CLIMB_FALL);

        return view;
    }

    @Override
    public BaseScoutType getScoutType() {
        return TELEOP;
    }
}
