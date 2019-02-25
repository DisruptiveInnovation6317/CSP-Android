package com.frc63175985.csp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.HashMap;

enum LevelSelection {
    TOP(1),
    MIDDLE(2),
    LOW(3);

    private final int value;

    LevelSelection(final int newValue) {
        value = newValue;
    }
}

public class FragmentRocketCloseup extends Fragment {
    private TextView hatchAttemptStepper;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rocket_closeup, container, false);

        view.findViewById(R.id.rocket_closeup_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MatchScoutingFragment)getParentFragment()).switchView(ScoutingSubview.THUMBNAIL);
            }
        });

        ((RadioGroup)view.findViewById(R.id.rocket_closeup_level_selection)).setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton button = (RadioButton)group.getChildAt(checkedId-1);
                LevelSelection level = LevelSelection.valueOf(button.getText().toString().toUpperCase());
                autofill(level);
            }
        });

        return view;
    }

    /**
     * TODO
     * Autofill the information in this view
     */
    private void autofill(LevelSelection selection) {

    }
}
