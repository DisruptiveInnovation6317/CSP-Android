package com.frc63175985.csp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Different views within the MatchScoutingFragment that
 * can be displayed
 */
enum ScoutingSubview {
    THUMBNAIL, ROCKET, CARGO_SHIP
}

public class MatchScoutingFragment extends Fragment {
    private ThumbnailFragment thumbnailFragment;
    private RocketCloseupFragment rocketCloseup;
    private CargoShipCloseupFragment cargoShipCloseup;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_match_scouting, container, false);

        thumbnailFragment = new ThumbnailFragment();
        rocketCloseup = new RocketCloseupFragment();
        cargoShipCloseup = new CargoShipCloseupFragment();

        switchView(ScoutingSubview.THUMBNAIL);

        return view;
    }

    /**
     * Change the subview of this {@link Fragment} to a different view.
     * @param scoutingSubview The new subview to display
     */
    public void switchView(ScoutingSubview scoutingSubview) {
        FragmentTransaction fm = getChildFragmentManager().beginTransaction();

        switch (scoutingSubview) {
            case THUMBNAIL:
                fm.replace(R.id.match_scouting_content_view, thumbnailFragment);
                break;
            case ROCKET:
                fm.replace(R.id.match_scouting_content_view, rocketCloseup);
                break;
            case CARGO_SHIP:
                fm.replace(R.id.match_scouting_content_view, cargoShipCloseup);
                break;
        }

        fm.commit();
    }
}
