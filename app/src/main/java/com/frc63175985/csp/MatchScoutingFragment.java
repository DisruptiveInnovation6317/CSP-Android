package com.frc63175985.csp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

enum ScoutingSubview {
    THUMBNAIL, ROCKET, CARGO_SHIP
}

public class MatchScoutingFragment extends Fragment {
    private ThumbnailFragment thumbnailFragment;
    private FragmentRocketCloseup rocketCloseup;
    private FragmentCargoShipCloseup cargoShipCloseup;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_match_scouting, container, false);

        thumbnailFragment = new ThumbnailFragment();
        rocketCloseup = new FragmentRocketCloseup();
        cargoShipCloseup = new FragmentCargoShipCloseup();

        switchView(ScoutingSubview.THUMBNAIL);

        return view;
    }

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
