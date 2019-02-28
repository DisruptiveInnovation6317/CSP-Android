package com.frc63175985.csp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.frc63175985.csp.auth.Match;

public class CommentsFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_comments, container, false);

        String[] driveRatings = {"Driver Rating", "Terrible", "Average", "Good", "Amazing"};
        ArrayAdapter<String> driveRatingsAdapter = new ArrayAdapter<>(getContext(), R.layout.spinner_drop_down_item, driveRatings);
        ((Spinner)view.findViewById(R.id.comments_drive_rating_spinner)).setAdapter(driveRatingsAdapter);

        Match.GUI.bindEditText(view, R.id.comments_comments_editText, Match.COMMENTS);
        Match.GUI.bindCheckbox(view, R.id.comments_highlight_checkBox, Match.HIGHLIGHT);
        Match.GUI.bindCheckbox(view, R.id.comments_warning_checkBox, Match.WARNING);
        Match.GUI.bindCheckbox(view, R.id.comments_worked_with_alliance_checkBox, Match.WORK_WITH_ALLIANCE);
        Match.GUI.bindCheckbox(view, R.id.comments_recovered_checkBox, Match.RECOVER);
        Match.GUI.bindCheckbox(view, R.id.comments_strategy_checkBox, Match.WORKED_STRATEGY);
        Match.GUI.bindCheckbox(view, R.id.comments_solo_checkBox, Match.WORKED_SOLO);
        Match.GUI.bindCheckbox(view, R.id.comments_good_defence_checkBox, Match.EFFECTIVE_DEFENCE);

        return view;
    }
}
