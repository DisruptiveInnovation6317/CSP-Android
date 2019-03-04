package com.frc63175985.csp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.frc63175985.csp.auth.Match;
import com.frc63175985.csp.auth.ScoutAuthState;
import com.frc63175985.csp.enums.AllianceColor;

import static com.frc63175985.csp.enums.AllianceColor.RED;

public class StartFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_start, container, false);

        // Match # listener
        Match.GUI.bindEditText(view, R.id.start_match_num_editText, Match.MATCH_NUMBER);
        Match.GUI.bindEditText(view, R.id.start_team_num_editText, Match.TEAM_NUMBER);

        // Alliance listener
        ((RadioGroup)view.findViewById(R.id.start_alliance_radioGroup)).setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                View radioButtonView = group.findViewById(group.getCheckedRadioButtonId());
                int radioIndex = group.indexOfChild(radioButtonView);
                RadioButton radioButton = (RadioButton)group.getChildAt(radioIndex);
                AllianceColor color = AllianceColor.valueOf(radioButton.getText().toString().toUpperCase());
                ScoutAuthState.shared.currentMatch.set(Match.ALLIANCE, color == RED ? 1 : 2);
            }
        });

        // Drive Station listener
        ((RadioGroup)view.findViewById(R.id.start_drive_station_radioGroup)).setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                View radioButtonView = group.findViewById(group.getCheckedRadioButtonId());
                int radioIndex = group.indexOfChild(radioButtonView);
                RadioButton radioButton = (RadioButton)group.getChildAt(radioIndex);
                int driveStation = Integer.valueOf(radioButton.getText().toString());
                ScoutAuthState.shared.currentMatch.set(Match.DRIVE_STATION, driveStation);
            }
        });

        autofill(view);

        return view;
    }

    /**
     * Auto fill information from the start
     */
    private void autofill(View view) {
        // Alliance
        int alliance = ScoutAuthState.shared.currentMatch.num(Match.ALLIANCE);
        if (alliance == 0) {
            ((RadioGroup) view.findViewById(R.id.start_alliance_radioGroup))
                    .check(R.id.start_alliance_red_radio);
        } else if (alliance == 1) {
            ((RadioGroup) view.findViewById(R.id.start_alliance_radioGroup))
                    .check(R.id.start_alliance_blue_radio);
        }

        // Drive Station
        int driveStation = ScoutAuthState.shared.currentMatch.num(Match.DRIVE_STATION);
        if (driveStation == 0) {
            ((RadioGroup)view.findViewById(R.id.start_drive_station_radioGroup))
                    .check(R.id.start_drive_station_1_radio);
        } else if (driveStation == 1) {
            ((RadioGroup)view.findViewById(R.id.start_drive_station_radioGroup))
                    .check(R.id.start_drive_station_2_radio);
        } else if (driveStation == 2) {
            ((RadioGroup)view.findViewById(R.id.start_drive_station_radioGroup))
                    .check(R.id.start_drive_station_3_radio);
        }
    }
}