package com.frc63175985.csp;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
        final View view = inflater.inflate(R.layout.fragment_start, container, false);

        // Match # listener
        Match.GUI.bindEditText(view, R.id.start_match_num_editText, Match.MATCH_NUMBER);

        if (TbaCoordinator.shared.teams == null) {
            Match.GUI.bindEditText(view, R.id.start_team_num_editText, Match.TEAM_NUMBER);
            view.findViewById(R.id.start_team_num_editText).setVisibility(View.VISIBLE);
            view.findViewById(R.id.start_team_num_spinner).setVisibility(View.GONE);
        } else {
            Match.GUI.bindTeamNumberSpinner(getContext(), view.findViewById(R.id.start_team_num_spinner));
            view.findViewById(R.id.start_team_num_editText).setVisibility(View.GONE);
            view.findViewById(R.id.start_team_num_spinner).setVisibility(View.VISIBLE);
        }

        // Alliance listener
        ((RadioGroup)view.findViewById(R.id.start_alliance_radioGroup)).setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                View radioButtonView = group.findViewById(group.getCheckedRadioButtonId());
                int radioIndex = group.indexOfChild(radioButtonView);
                RadioButton radioButton = (RadioButton)group.getChildAt(radioIndex);
                AllianceColor color = AllianceColor.valueOf(radioButton.getText().toString().toUpperCase());
                ScoutAuthState.shared.currentMatch.set(Match.ALLIANCE, color == RED ? 1 : 2);
                tryDriveStationImage(view);
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
                tryDriveStationImage(view);
            }
        });

        autofill(view);

        return view;
    }

    private void tryDriveStationImage(View view) {
        if (ScoutAuthState.shared.currentMatch.num(Match.ALLIANCE) == 0 ||
            ScoutAuthState.shared.currentMatch.num(Match.DRIVE_STATION) == 0) {
            return;
        }

        // Set image
        Drawable drawable = null;
        if (ScoutAuthState.shared.currentMatch.num(Match.ALLIANCE) == 1) {
            // Red
            switch (ScoutAuthState.shared.currentMatch.num(Match.DRIVE_STATION)) {
                case 1:
                    drawable = ContextCompat.getDrawable(getActivity(), R.drawable.red_1);
                    break;
                case 2:
                    drawable = ContextCompat.getDrawable(getActivity(), R.drawable.red_2);
                    break;
                case 3:
                    drawable = ContextCompat.getDrawable(getActivity(), R.drawable.red_3);
                    break;
            }
        } else {
            // Blue
            switch (ScoutAuthState.shared.currentMatch.num(Match.DRIVE_STATION)) {
                case 1:
                    drawable = ContextCompat.getDrawable(getActivity(), R.drawable.blue_1);
                    break;
                case 2:
                    drawable = ContextCompat.getDrawable(getActivity(), R.drawable.blue_2);
                    break;
                case 3:
                    drawable = ContextCompat.getDrawable(getActivity(), R.drawable.blue_3);
                    break;
            }
        }

        ((ImageView)view.findViewById(R.id.drive_station_imageView)).setImageDrawable(drawable);
    }

    /**
     * Auto fill information from the start
     */
    private void autofill(View view) {
        // Alliance
        int alliance = ScoutAuthState.shared.currentMatch.num(Match.ALLIANCE);
        if (alliance == 1) {
            ((RadioGroup) view.findViewById(R.id.start_alliance_radioGroup))
                    .check(R.id.start_alliance_red_radio);
        } else if (alliance == 2) {
            ((RadioGroup) view.findViewById(R.id.start_alliance_radioGroup))
                    .check(R.id.start_alliance_blue_radio);
        }

        // Drive Station
        int driveStation = ScoutAuthState.shared.currentMatch.num(Match.DRIVE_STATION);
        if (driveStation == 1) {
            ((RadioGroup)view.findViewById(R.id.start_drive_station_radioGroup))
                    .check(R.id.start_drive_station_1_radio);
        } else if (driveStation == 2) {
            ((RadioGroup)view.findViewById(R.id.start_drive_station_radioGroup))
                    .check(R.id.start_drive_station_2_radio);
        } else if (driveStation == 3) {
            ((RadioGroup)view.findViewById(R.id.start_drive_station_radioGroup))
                    .check(R.id.start_drive_station_3_radio);
        }

        tryDriveStationImage(view);
    }
}