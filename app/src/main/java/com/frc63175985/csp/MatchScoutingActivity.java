package com.frc63175985.csp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;

enum ScoutingView {
    START, AUTONOMOUS, TELEOP, RATING
}

public class MatchScoutingActivity extends FragmentActivity {
    // Scouting Views
    private AutonomousFragment autonomousFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_scouting);

        autonomousFragment = new AutonomousFragment();

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.match_scouting_content_view, autonomousFragment)
                .commit();
    }
}
