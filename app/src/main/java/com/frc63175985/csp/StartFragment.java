package com.frc63175985.csp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.frc63175985.csp.auth.Match;
import com.frc63175985.csp.auth.ScoutAuthState;
import com.frc63175985.csp.enums.AllianceColor;
import com.frc63175985.csp.enums.LevelSelection;

import static com.frc63175985.csp.enums.AllianceColor.RED;

public class StartFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_start, container, false);

        // Match # listener
        ((EditText)view.findViewById(R.id.start_match_num_editText))
                .addTextChangedListener(new DatabaseTextWatcher(Match.MATCH_NUMBER));

        // Team # listener
        ((EditText)view.findViewById(R.id.start_team_num_editText))
                .addTextChangedListener(new DatabaseTextWatcher(Match.TEAM_NUMBER));

        // Alliance listener
        ((RadioGroup)view.findViewById(R.id.start_alliance_radioGroup)).setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                View radioButtonView = group.findViewById(group.getCheckedRadioButtonId());
                int radioIndex = group.indexOfChild(radioButtonView);
                RadioButton radioButton = (RadioButton)group.getChildAt(radioIndex);
                AllianceColor color = AllianceColor.valueOf(radioButton.getText().toString().toUpperCase());
                ScoutAuthState.shared.currentMatch.set(Match.ALLIANCE, color == RED ? 0 : 1);
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
        // Match #
        String matchNumber = ScoutAuthState.shared.currentMatch.str(Match.MATCH_NUMBER);
        ((EditText)view.findViewById(R.id.start_match_num_editText)).setText(matchNumber);

        // Team #
        String teamNumber = ScoutAuthState.shared.currentMatch.str(Match.TEAM_NUMBER);
        ((EditText)view.findViewById(R.id.start_team_num_editText)).setText(teamNumber);

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

class DatabaseTextWatcher implements TextWatcher {
    private String key;

    public DatabaseTextWatcher(String key) {
        this.key = key;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {}

    @Override
    public void afterTextChanged(Editable s) {
        ScoutAuthState.shared.currentMatch.set(key, s.toString());
    }
}