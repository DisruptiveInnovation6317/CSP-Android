package com.frc63175985.csp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.frc63175985.csp.auth.Match;
import com.frc63175985.csp.enums.BaseScoutType;

import static com.frc63175985.csp.enums.BaseScoutType.AUTONOMOUS;

public class SandstormFragment extends BaseScoutFragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sandstorm, container, false);

        initializeRocketAndCargoship(view);

        // Start
        Match.GUI.bindCheckbox(view, R.id.sandstorm_sandstorm_active, Match.SANDSTORM_ACTIVE);
        Match.GUI.bindCheckbox(view, R.id.sandstorm_leaves_hab_checkBox, Match.LEAVES_HAB);
        Match.GUI.bindSpinner(getContext(), view, R.id.sandstorm_hab_level_spinner, Match.START_LEVEL, new String[]{"--------", "Low", "Middle"});
        Match.GUI.bindSpinner(getContext(), view, R.id.sandstorm_start_position_spinner, Match.START_POSITION, new String[]{"--------", "Left", "Middle", "Right"});
        Match.GUI.bindSpinner(getContext(), view, R.id.sandstorm_start_object_spinner, Match.START_OBJECT, new String[]{"--------", "N/A", "Hatch", "Cargo"});
        Match.GUI.bindCheckbox(view, R.id.sandstorm_crosses_midline_checkBox, Match.CROSS_OVER);

        // Errors
        Match.GUI.bindCheckbox(view, R.id.sandstorm_drop_start_object_checkBox, Match.LOSES_START_OBJECT);
        Match.GUI.bindCheckbox(view, R.id.sandstorm_contact_robot_checkBox, Match.ROBOT_CONTACT);
        Match.GUI.bindCheckbox(view, R.id.sandstorm_field_collision_checkBox, Match.FOUL);

        return view;
    }

    @Override
    public BaseScoutType getScoutType() {
        return AUTONOMOUS;
    }
}