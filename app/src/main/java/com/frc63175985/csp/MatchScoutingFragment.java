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

public class MatchScoutingFragment extends Fragment {
    private ThumbnailFragment thumbnailFragment;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_match_scouting, container, false);

        thumbnailFragment = new ThumbnailFragment();

        FragmentTransaction fm = getChildFragmentManager().beginTransaction();
        fm.replace(R.id.match_scouting_content_view, thumbnailFragment).commit();

        return view;
    }
}
