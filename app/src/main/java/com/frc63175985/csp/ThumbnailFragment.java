package com.frc63175985.csp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import static com.frc63175985.csp.enums.ScoutingSubview.CARGO_SHIP;
import static com.frc63175985.csp.enums.ScoutingSubview.ROCKET;

public class ThumbnailFragment extends Fragment implements View.OnClickListener {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_thumbnails, container, false);

        view.findViewById(R.id.match_scouting_thumbnail_rocket).setOnClickListener(this);
        view.findViewById(R.id.match_scouting_thumbnail_cargo_ship).setOnClickListener(this);

        return view;
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.match_scouting_thumbnail_rocket) {
            ((AutonomousFragment)getParentFragment()).switchView(ROCKET);
        } else if (v.getId() == R.id.match_scouting_thumbnail_cargo_ship) {
            ((AutonomousFragment)getParentFragment()).switchView(CARGO_SHIP);
        }
    }
}
