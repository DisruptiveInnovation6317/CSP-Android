package com.frc63175985.csp;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import com.frc63175985.csp.enums.BaseScoutType;
import com.frc63175985.csp.enums.ScoutingSubview;

import static com.frc63175985.csp.enums.BaseScoutType.AUTONOMOUS;
import static com.frc63175985.csp.enums.BaseScoutType.TELEOP;

/**
 * This is a parent fragment to Autonomous and TeleOp scouting,
 * as they share a lot of logic
 */
public abstract class BaseScoutFragment extends Fragment {
    /**
     * Get whether this {@link Fragment} is hosting a scouting view
     * for Autonomous or TeleOp
     * @return What category of content is being displayed
     */
    public abstract BaseScoutType getScoutType();
}
