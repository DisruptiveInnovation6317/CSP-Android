package com.frc63175985.csp;

import android.support.v4.app.Fragment;
import android.view.View;

import com.frc63175985.csp.auth.Match;
import com.frc63175985.csp.enums.BaseScoutType;

/**
 * This is a parent fragment to Autonomous and TeleOp scouting,
 * as they share a lot of logic
 */
public abstract class BaseScoutFragment extends Fragment {
    public void initializeRocketAndCargoship(View view) {
        String prefix = getScoutType() == BaseScoutType.AUTONOMOUS ? Match.AUTO_PREFIX : Match.TELEOP_PREFIX;

        // Cargoship Cargo
        Match.GUI.bindStepper(view, R.id.base_scout_cargoship_cargo_top_attempts, prefix + Match.CARGO_FRONT_CARGO_ATTEMPT);
        Match.GUI.bindStepper(view, R.id.base_scout_cargoship_cargo_top_successes, prefix + Match.CARGO_FRONT_CARGO_SUCCESS);
        Match.GUI.bindStepper(view, R.id.base_scout_cargoship_cargo_side_attempts, prefix + Match.CARGO_SIDE_CARGO_ATTEMPT);
        Match.GUI.bindStepper(view, R.id.base_scout_cargoship_cargo_side_successes, prefix + Match.CARGO_SIDE_CARGO_SUCCESS);

        // Cargoship Hatches
        Match.GUI.bindStepper(view, R.id.base_scout_cargoship_hatch_front_attempts, prefix + Match.CARGO_FRONT_HATCH_ATTEMPT);
        Match.GUI.bindStepper(view, R.id.base_scout_cargoship_hatch_front_successes, prefix + Match.CARGO_FRONT_HATCH_SUCCESS);
        Match.GUI.bindStepper(view, R.id.base_scout_cargoship_hatch_side_attempts, prefix + Match.CARGO_SIDE_HATCH_ATTEMPT);
        Match.GUI.bindStepper(view, R.id.base_scout_cargoship_hatch_side_successes, prefix + Match.CARGO_SIDE_HATCH_SUCCESS);

        // Rocket Cargo
        Match.GUI.bindStepper(view, R.id.base_scout_rocket_cargo_top_attempt, prefix + Match.ROCKET_HIGH_CARGO_ATTEMPT);
        Match.GUI.bindStepper(view, R.id.base_scout_rocket_cargo_top_success, prefix + Match.ROCKET_HIGH_CARGO_SUCCESS);
        Match.GUI.bindStepper(view, R.id.base_scout_rocket_cargo_middle_attempt, prefix + Match.ROCKET_MIDDLE_CARGO_ATTEMPT);
        Match.GUI.bindStepper(view, R.id.base_scout_rocket_cargo_middle_success, prefix + Match.ROCKET_MIDDLE_CARGO_SUCCESS);
        Match.GUI.bindStepper(view, R.id.base_scout_rocket_cargo_low_attempt, prefix + Match.ROCKET_LOW_CARGO_ATTEMPT);
        Match.GUI.bindStepper(view, R.id.base_scout_rocket_cargo_low_success, prefix + Match.ROCKET_LOW_CARGO_SUCCESS);

        // Rocket Hatches
        Match.GUI.bindStepper(view, R.id.base_scout_rocket_hatch_top_attempt, prefix + Match.ROCKET_HIGH_HATCH_ATTEMPT);
        Match.GUI.bindStepper(view, R.id.base_scout_rocket_hatch_top_success, prefix + Match.ROCKET_HIGH_HATCH_SUCCESS);
        Match.GUI.bindStepper(view, R.id.base_scout_rocket_hatch_middle_attempt, prefix + Match.ROCKET_MIDDLE_HATCH_ATTEMPT);
        Match.GUI.bindStepper(view, R.id.base_scout_rocket_hatch_middle_success, prefix + Match.ROCKET_MIDDLE_HATCH_SUCCESS);
        Match.GUI.bindStepper(view, R.id.base_scout_rocket_hatch_low_attempt, prefix + Match.ROCKET_LOW_HATCH_ATTEMPT);
        Match.GUI.bindStepper(view, R.id.base_scout_rocket_hatch_low_success, prefix + Match.ROCKET_LOW_HATCH_SUCCESS);
    }

    /**
     * Get whether this {@link Fragment} is hosting a scouting view
     * for Autonomous or TeleOp
     * @return What category of content is being displayed
     */
    public abstract BaseScoutType getScoutType();
}
