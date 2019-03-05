package com.frc63175985.csp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.frc63175985.csp.auth.Match;
import com.frc63175985.csp.enums.BaseScoutType;

import static com.frc63175985.csp.enums.BaseScoutType.TELEOP;
import static com.frc63175985.csp.enums.ScoutingSubview.THUMBNAIL;

public class TeleOpFragment extends BaseScoutFragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_teleop, container, false);

        initializeSubviews();
        switchView(THUMBNAIL);

        Match.GUI.bindCheckbox(view, R.id.teleop_defence_checkBox, Match.DEFENSE);
        Match.GUI.bindCheckbox(view, R.id.teleop_robot_crashed_checkBox, Match.ROBOT_CRASHED);
        Match.GUI.bindCheckbox(view, R.id.teleop_yellow_card, Match.YELLOW_CARD);
        Match.GUI.bindCheckbox(view, R.id.teleop_red_card, Match.RED_CARD);
        Match.GUI.bindCheckbox(view, R.id.teleop_hatch_from_ground, Match.TAKE_HATCH_GROUND);
        Match.GUI.bindCheckbox(view, R.id.teleop_hatch_from_station, Match.TAKE_HATCH_STATION);
        Match.GUI.bindCheckbox(view, R.id.teleop_cargo_from_ground, Match.TAKE_CARGO_GROUND);
        Match.GUI.bindCheckbox(view, R.id.teleop_cargo_from_station, Match.TAKE_CARGO_STATION);

        String[] climbOptions = {"Able to Climb", "N/A", "Success", "Failure"};
        Match.GUI.bindSpinner(getContext(), view, R.id.teleop_climb_spinner, Match.CLIMB,
                climbOptions);

        String[] outcomeOptions = {"Outcome", "Self Only", "Self Plus Others", "Others Only", "Assisted"};
        Match.GUI.bindSpinner(getContext(), view, R.id.teleop_climb_outcome_spinner, Match.CLIMB_OUTCOME, outcomeOptions);

        String[] grabOptions = {"Grab Speed", "Slow", "Medium", "Fast"};
        Match.GUI.bindSpinner(getContext(), view, R.id.teleop_climb_grab_spinner, Match.CLIMB_GRAB, grabOptions);

        String[] climbSpeedOptions = {"Climb Speed", "Slow", "Medium", "Fast"};
        Match.GUI.bindSpinner(getContext(), view, R.id.teleop_climb_speed_spinner, Match.CLIMB_SPEED, climbSpeedOptions);

        Match.GUI.bindEditText(view, R.id.teleop_climb_attempts_editText, Match.NUMBER_CLIMB_ASSISTS);

        String[] climbLevel = {"Climb Level", "1", "2", "3"};
        Match.GUI.bindSpinner(getContext(), view, R.id.teleop_climb_level_spinner, Match.CLIMB_LEVEL, climbLevel);

        Match.GUI.bindCheckbox(view, R.id.teleop_climb_robot_fell, Match.CLIMB_FALL);

        return view;
    }

    @Override
    public BaseScoutType getScoutType() {
        return TELEOP;
    }
}
