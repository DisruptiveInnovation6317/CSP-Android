package com.frc63175985.csp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import com.frc63175985.csp.auth.Match;
import com.frc63175985.csp.auth.ScoutAuthState;
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

        autofill(view);

        return view;
    }

    private void autofill(View view) {
        boolean defence = ScoutAuthState.shared.currentMatch.bool(Match.DEFENSE).equals("TRUE");
        ((CheckBox)view.findViewById(R.id.teleop_defence_checkBox)).setChecked(defence);
    }

    @Override
    public BaseScoutType getScoutType() {
        return TELEOP;
    }
}
