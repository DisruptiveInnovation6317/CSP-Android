package com.frc63175985.csp;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.frc63175985.csp.auth.ScoutAuthState;
import com.frc63175985.csp.enums.BaseScoutType;
import com.frc63175985.csp.enums.ScoutingSubview;

import static com.frc63175985.csp.enums.BaseScoutType.AUTONOMOUS;
import static com.frc63175985.csp.enums.BaseScoutType.TELEOP;

/**
 * This is a parent fragment to Autonomous and TeleOp scouting,
 * as they share a lot of logic
 */
public abstract class BaseScoutFragment extends Fragment {
    private ThumbnailFragment thumbnailFragment;
    private RocketCloseupFragment rocketCloseup;
    private CargoShipCloseupFragment cargoShipCloseup;
    private int contentViewId;

    public void initializeSubviews() {
        thumbnailFragment = new ThumbnailFragment();
        rocketCloseup = new RocketCloseupFragment();
        cargoShipCloseup = new CargoShipCloseupFragment();

        if (getScoutType() == AUTONOMOUS) {
            contentViewId = R.id.autonomous_content_view;
        } else if (getScoutType() == TELEOP) {
            contentViewId = R.id.teleop_content_view;
        }
    }

    public abstract BaseScoutType getScoutType();

    /**
     * Change the subview of this {@link Fragment} to a different view.
     *
     * @param scoutingSubview The new subview to display
     */
    public void switchView(ScoutingSubview scoutingSubview) {
        FragmentTransaction fm = getChildFragmentManager().beginTransaction();

        switch (scoutingSubview) {
            case THUMBNAIL:
                fm.replace(contentViewId, thumbnailFragment);
                break;
            case ROCKET:
                fm.replace(contentViewId, rocketCloseup);
                break;
            case CARGO_SHIP:
                fm.replace(contentViewId, cargoShipCloseup);
                break;
        }

        fm.commit();
    }

    protected void addCheckBoxListener(View parentView, int id, final String key) {
        CheckBox checkBox = parentView.findViewById(id);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                ScoutAuthState.shared.currentMatch.set(key, isChecked);
            }
        });
    }
}