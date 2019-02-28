package com.frc63175985.csp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.Spinner;

import com.frc63175985.csp.auth.Match;
import com.frc63175985.csp.auth.ScoutAuthState;
import com.frc63175985.csp.enums.BaseScoutType;

import static com.frc63175985.csp.enums.BaseScoutType.AUTONOMOUS;
import static com.frc63175985.csp.enums.ScoutingSubview.THUMBNAIL;

public class AutonomousFragment extends BaseScoutFragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_autonomous, container, false);

        initializeSubviews();
        switchView(THUMBNAIL);

        String[] objects = {"Start Object", "Nothing", "Hatch", "Cargo"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), R.layout.spinner_drop_down_item, objects);

        addCheckBoxListener(view, R.id.autonomous_foul_checkbox, Match.FOUL);
        addCheckBoxListener(view, R.id.autonomous_crosses_midline_checkbox, Match.CROSS_OVER);
        addCheckBoxListener(view, R.id.autonomous_loses_start_obj_checkbox, Match.LOSES_START_OBJECT);
        addCheckBoxListener(view, R.id.autonomous_leaves_hab, Match.LEAVES_HAB);

        Spinner startObjectSpinner = view.findViewById(R.id.autonomous_start_object_spinner);
        startObjectSpinner.setAdapter(adapter);
        startObjectSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                position--; // to move "Start Object" to -1 and "Nothing" to 0.
                ScoutAuthState.shared.currentMatch.set(Match.START_OBJECT, position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        autofill(view);

        return view;
    }

    /**
     * Auto fill autonomous meta bar
     */
    private void autofill(View view) {
        boolean leavesHab = ScoutAuthState.shared.currentMatch.bool(Match.LEAVES_HAB).equals("TRUE");
        ((CheckBox)view.findViewById(R.id.autonomous_leaves_hab)).setChecked(leavesHab);

        boolean foul = ScoutAuthState.shared.currentMatch.bool(Match.FOUL).equals("TRUE");
        ((CheckBox)view.findViewById(R.id.autonomous_foul_checkbox)).setChecked(foul);

        boolean crossesMidline = ScoutAuthState.shared.currentMatch.bool(Match.CROSS_OVER).equals("TRUE");
        ((CheckBox)view.findViewById(R.id.autonomous_crosses_midline_checkbox)).setChecked(crossesMidline);

        int startObjectId = ScoutAuthState.shared.currentMatch.num(Match.START_OBJECT) + 1;
        ((Spinner)view.findViewById(R.id.autonomous_start_object_spinner)).setSelection(startObjectId);

        boolean losesStartObj = ScoutAuthState.shared.currentMatch.bool(Match.LOSES_START_OBJECT).equals("TRUE");
        ((CheckBox)view.findViewById(R.id.autonomous_loses_start_obj_checkbox)).setChecked(losesStartObj);
    }

    @Override
    public BaseScoutType getScoutType() {
        return AUTONOMOUS;
    }
}