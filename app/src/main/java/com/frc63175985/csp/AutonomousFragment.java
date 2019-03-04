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
import static com.frc63175985.csp.enums.ScoutingSubview.THUMBNAIL;

public class AutonomousFragment extends BaseScoutFragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_autonomous, container, false);

        initializeSubviews();
        switchView(THUMBNAIL);

        Match.GUI.bindCheckbox(view, R.id.autonomous_foul_checkbox, Match.FOUL);
        Match.GUI.bindCheckbox(view, R.id.autonomous_crosses_midline_checkbox, Match.CROSS_OVER);
        Match.GUI.bindCheckbox(view, R.id.autonomous_loses_start_obj_checkbox, Match.LOSES_START_OBJECT);
        Match.GUI.bindCheckbox(view, R.id.autonomous_leaves_hab, Match.LEAVES_HAB);

        String[] options = {"Start Object", "Nothing", "Hatch", "Cargo"};
        Match.GUI.bindSpinner(getContext(), view, R.id.autonomous_start_object_spinner,
                Match.START_OBJECT, options);

        return view;
    }

    @Override
    public BaseScoutType getScoutType() {
        return AUTONOMOUS;
    }
}