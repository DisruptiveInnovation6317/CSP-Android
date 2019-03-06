package com.frc63175985.csp;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Toast;

import com.frc63175985.csp.auth.PitScoutRecord;
import com.frc63175985.csp.auth.ScoutAuthState;

public class PitScoutingActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pit_scouting);

        // HATCHES
        PitScoutRecord.GUI.bindCheckbox(findViewById(R.id.pit_can_manipulate_hatch), PitScoutRecord.CAN_MANIPULATE_HATCH);
        PitScoutRecord.GUI.bindCheckbox(findViewById(R.id.pit_hatch_floor_intake), PitScoutRecord.HATCH_FROM_FLOOR);
        PitScoutRecord.GUI.bindCheckbox(findViewById(R.id.pit_hatch_exchange_intake), PitScoutRecord.HATCH_FROM_EXCHANGE);
        PitScoutRecord.GUI.bindCheckbox(findViewById(R.id.pit_deliver_hatch_low_level), PitScoutRecord.HATCH_LOW);
        PitScoutRecord.GUI.bindCheckbox(findViewById(R.id.pit_deliver_hatch_mid_level), PitScoutRecord.HATCH_MID);
        PitScoutRecord.GUI.bindCheckbox(findViewById(R.id.pit_deliver_hatch_high_level), PitScoutRecord.HATCH_HIGH);
        PitScoutRecord.GUI.bindEditText(findViewById(R.id.pit_hatch_intake_notes_editText), PitScoutRecord.HATCH_INTAKE_NOTES);

        // CARGO
        PitScoutRecord.GUI.bindCheckbox(findViewById(R.id.pit_can_manipulate_cargo), PitScoutRecord.CAN_MANIPULATE_CARGO);
        PitScoutRecord.GUI.bindCheckbox(findViewById(R.id.pit_cargo_floor_intake), PitScoutRecord.CARGO_FROM_FLOOR);
        PitScoutRecord.GUI.bindCheckbox(findViewById(R.id.pit_cargo_exchange_intake), PitScoutRecord.CARGO_FROM_EXCHANGE);
        PitScoutRecord.GUI.bindCheckbox(findViewById(R.id.pit_deliver_cargo_low_level), PitScoutRecord.CARGO_LOW);
        PitScoutRecord.GUI.bindCheckbox(findViewById(R.id.pit_deliver_cargo_mid_level), PitScoutRecord.CARGO_MID);
        PitScoutRecord.GUI.bindCheckbox(findViewById(R.id.pit_deliver_cargo_high_level), PitScoutRecord.CARGO_HIGH);
        PitScoutRecord.GUI.bindEditText(findViewById(R.id.pit_cargo_intake_notes_editText), PitScoutRecord.CARGO_INTAKE_NOTES);

        // SANDSTORM
        PitScoutRecord.GUI.bindCheckbox(findViewById(R.id.pit_sandstorm_active), PitScoutRecord.SANDSTORM_ACTIVE);

        String[] controlOptions = {"Control Option", "N/A", "Autonomous", "Manual", "Hybrid"};
        PitScoutRecord.GUI.bindSpinner(this, findViewById(R.id.pit_sandstorm_control_spinner), PitScoutRecord.AUTO_TYPE, controlOptions);

        PitScoutRecord.GUI.bindCheckbox(findViewById(R.id.pit_sandstorm_deliver_hatch_checkBox), PitScoutRecord.CAN_DELIVER_HATCH);
        PitScoutRecord.GUI.bindStepper(findViewById(R.id.pit_max_hatches_stepper), PitScoutRecord.NUM_HATCHES_DELIVERED);
        PitScoutRecord.GUI.bindSpinner(this, findViewById(R.id.pit_sandstorm_hatch_max_height_spinner), PitScoutRecord.MAX_HATCH_HEIGHT, PitScoutRecord.HEIGHT_OPTIONS);

        PitScoutRecord.GUI.bindCheckbox(findViewById(R.id.pit_sandstorm_deliver_cargo_checkBox), PitScoutRecord.CAN_DELIVER_CARGO);
        PitScoutRecord.GUI.bindStepper(findViewById(R.id.pit_max_cargo_stepper), PitScoutRecord.NUM_CARGO_DELIVERED);
        PitScoutRecord.GUI.bindSpinner(this, findViewById(R.id.pit_sandsstorm_max_height_cargo_spinner), PitScoutRecord.MAX_CARGO_HEIGHT, PitScoutRecord.HEIGHT_OPTIONS);

        // CLIMB
        PitScoutRecord.GUI.bindCheckbox(findViewById(R.id.pit_climb_active_checkBox), PitScoutRecord.CAN_CLIMB);

        String[] climbTypeOptions = {"Climb Type", "Self Only", "Self Plus Others", "Others Only", "Assisted"};
        PitScoutRecord.GUI.bindSpinner(this, findViewById(R.id.pit_climb_climb_type_spinner), PitScoutRecord.CLIMB_TYPE, climbTypeOptions);

        // TODO grab speed
        String[] grabSpeedOptions = {"Grab Speed", "N/A", "Slow", "Moderate", "Fast"};

        // TODO climb speed

        // TODO # robots

        // TODO max height

        // IMAGE

        // NOTES
        PitScoutRecord.GUI.bindEditText(findViewById(R.id.pit_notes_notes_editText), PitScoutRecord.COMMENTS);

        findViewById(R.id.pit_notes_submit_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String contents = ScoutAuthState.shared.pitScoutRecord.export();
                if (contents == null || contents.isEmpty()) {
                    Toast.makeText(PitScoutingActivity.this, "Nothing to export", Toast.LENGTH_SHORT).show();
                    return;
                }

                AlertDialog alert = QrHelper.qrDialogFromString(PitScoutingActivity.this, "Pit Scouting", contents);
                if (alert != null) {
                    alert.show();
                }
            }
        });
    }

    private void takeFrontImage() {

    }

    private void takeSideImage() {

    }
}
