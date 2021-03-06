package com.frc63175985.csp;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.frc63175985.csp.auth.PitScoutRecord;
import com.frc63175985.csp.auth.ScoutAuthState;

import java.io.File;

public class PitScoutingActivity extends AppCompatActivity {
    public static final int REQUEST_FRONT_ROBOT_IMAGE = 1;
    public static final int REQUEST_SIDE_ROBOT_IMAGE = 2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pit_scouting);

        if (TbaCoordinator.shared.teams == null) {
            PitScoutRecord.GUI.bindEditText(findViewById(R.id.pit_team_number_editText), PitScoutRecord.TEAM_NUMBER);
            findViewById(R.id.pit_team_number_editText).setVisibility(View.VISIBLE);
            findViewById(R.id.pit_team_number_textView).setVisibility(View.INVISIBLE);
            findViewById(R.id.pit_team_number_spinner).setVisibility(View.INVISIBLE);
        } else {
            PitScoutRecord.GUI.bindTeamNumberSpinner(this, findViewById(R.id.pit_team_number_spinner));
            findViewById(R.id.pit_team_number_editText).setVisibility(View.INVISIBLE);
            findViewById(R.id.pit_team_number_textView).setVisibility(View.VISIBLE);
            findViewById(R.id.pit_team_number_spinner).setVisibility(View.VISIBLE);
        }

        // HATCHES
        PitScoutRecord.GUI.bindCheckbox(findViewById(R.id.pit_can_manipulate_hatch), PitScoutRecord.CAN_MANIPULATE_HATCH, new View[]{
                findViewById(R.id.pit_hatch_floor_intake), findViewById(R.id.pit_hatch_exchange_intake),
                findViewById(R.id.pit_deliver_hatch_low_level), findViewById(R.id.pit_deliver_hatch_mid_level),
                findViewById(R.id.pit_deliver_hatch_high_level)
        });
        PitScoutRecord.GUI.bindCheckbox(findViewById(R.id.pit_hatch_floor_intake), PitScoutRecord.HATCH_FROM_FLOOR, null);
        PitScoutRecord.GUI.bindCheckbox(findViewById(R.id.pit_hatch_exchange_intake), PitScoutRecord.HATCH_FROM_EXCHANGE, null);
        PitScoutRecord.GUI.bindCheckbox(findViewById(R.id.pit_deliver_hatch_low_level), PitScoutRecord.HATCH_LOW, null);
        PitScoutRecord.GUI.bindCheckbox(findViewById(R.id.pit_deliver_hatch_mid_level), PitScoutRecord.HATCH_MID, null);
        PitScoutRecord.GUI.bindCheckbox(findViewById(R.id.pit_deliver_hatch_high_level), PitScoutRecord.HATCH_HIGH, null);
        PitScoutRecord.GUI.bindEditText(findViewById(R.id.pit_hatch_intake_notes_editText), PitScoutRecord.HATCH_INTAKE_NOTES);

        // CARGO
        PitScoutRecord.GUI.bindCheckbox(findViewById(R.id.pit_can_manipulate_cargo), PitScoutRecord.CAN_MANIPULATE_CARGO, new View[]{
                findViewById(R.id.pit_cargo_floor_intake), findViewById(R.id.pit_cargo_exchange_intake),
                findViewById(R.id.pit_deliver_cargo_low_level), findViewById(R.id.pit_deliver_cargo_mid_level),
                findViewById(R.id.pit_deliver_cargo_high_level)
        });
        PitScoutRecord.GUI.bindCheckbox(findViewById(R.id.pit_cargo_floor_intake), PitScoutRecord.CARGO_FROM_FLOOR, null);
        PitScoutRecord.GUI.bindCheckbox(findViewById(R.id.pit_cargo_exchange_intake), PitScoutRecord.CARGO_FROM_EXCHANGE, null);
        PitScoutRecord.GUI.bindCheckbox(findViewById(R.id.pit_deliver_cargo_low_level), PitScoutRecord.CARGO_LOW, null);
        PitScoutRecord.GUI.bindCheckbox(findViewById(R.id.pit_deliver_cargo_mid_level), PitScoutRecord.CARGO_MID, null);
        PitScoutRecord.GUI.bindCheckbox(findViewById(R.id.pit_deliver_cargo_high_level), PitScoutRecord.CARGO_HIGH, null);
        PitScoutRecord.GUI.bindEditText(findViewById(R.id.pit_cargo_intake_notes_editText), PitScoutRecord.CARGO_INTAKE_NOTES);

        // SANDSTORM
        PitScoutRecord.GUI.bindCheckbox(findViewById(R.id.pit_sandstorm_active), PitScoutRecord.SANDSTORM_ACTIVE, new View[]{
                findViewById(R.id.pit_sandstorm_control_spinner), findViewById(R.id.pit_sandstorm_start_level_spinner),
                findViewById(R.id.pit_sandstorm_deliver_hatch_checkBox), findViewById(R.id.pit_sandstorm_deliver_cargo_checkBox),
                findViewById(R.id.pit_sandstorm_hatch_max_height_spinner), findViewById(R.id.pit_sandsstorm_cargo_max_height_spinner)
        });

        String[] controlOptions = {"--------", "N/A", "Autonomous", "Manual", "Hybrid"};
        PitScoutRecord.GUI.bindSpinner(this, findViewById(R.id.pit_sandstorm_control_spinner), PitScoutRecord.AUTO_TYPE, controlOptions);

        String[] startLevelOptions = {"--------", "1", "2", "3"};
        PitScoutRecord.GUI.bindSpinner(this, findViewById(R.id.pit_sandstorm_start_level_spinner), PitScoutRecord.START_LEVEL, startLevelOptions);

        PitScoutRecord.GUI.bindCheckbox(findViewById(R.id.pit_sandstorm_deliver_hatch_checkBox), PitScoutRecord.CAN_DELIVER_HATCH, null);
        PitScoutRecord.GUI.bindStepper(findViewById(R.id.pit_max_hatches_stepper), PitScoutRecord.NUM_HATCHES_DELIVERED);
        PitScoutRecord.GUI.bindSpinner(this, findViewById(R.id.pit_sandstorm_hatch_max_height_spinner), PitScoutRecord.MAX_HATCH_HEIGHT, PitScoutRecord.HEIGHT_OPTIONS);

        PitScoutRecord.GUI.bindCheckbox(findViewById(R.id.pit_sandstorm_deliver_cargo_checkBox), PitScoutRecord.CAN_DELIVER_CARGO, null);
        PitScoutRecord.GUI.bindStepper(findViewById(R.id.pit_max_cargo_stepper), PitScoutRecord.NUM_CARGO_DELIVERED);
        PitScoutRecord.GUI.bindSpinner(this, findViewById(R.id.pit_sandsstorm_cargo_max_height_spinner), PitScoutRecord.MAX_CARGO_HEIGHT, PitScoutRecord.HEIGHT_OPTIONS);

        // CLIMB
        PitScoutRecord.GUI.bindCheckbox(findViewById(R.id.pit_climb_active_checkBox), PitScoutRecord.CAN_CLIMB, new View[]{
                findViewById(R.id.pit_climb_climb_type_spinner), findViewById(R.id.pit_climb_grab_speed_spinner),
                findViewById(R.id.pit_climb_climb_speed_spinner), findViewById(R.id.pit_climb_max_height_spinner)
        });

        String[] climbTypeOptions = {"--------", "Self Only", "Self Plus Others", "Others Only", "Assisted"};
        PitScoutRecord.GUI.bindSpinner(this, findViewById(R.id.pit_climb_climb_type_spinner), PitScoutRecord.CLIMB_TYPE, climbTypeOptions);
        PitScoutRecord.GUI.bindSpinner(this, findViewById(R.id.pit_climb_grab_speed_spinner), PitScoutRecord.CLIMB_GRAB_SPEED, PitScoutRecord.SPEED_OPTIONS);
        PitScoutRecord.GUI.bindSpinner(this, findViewById(R.id.pit_climb_climb_speed_spinner), PitScoutRecord.CLIMB_SPEED, PitScoutRecord.SPEED_OPTIONS);
        PitScoutRecord.GUI.bindStepper(findViewById(R.id.pit_climb_num_robots_stepper), PitScoutRecord.NUM_CLIMB_ASSISTS);
        PitScoutRecord.GUI.bindSpinner(this, findViewById(R.id.pit_climb_max_height_spinner), PitScoutRecord.CLIMB_HEIGHT, PitScoutRecord.HEIGHT_OPTIONS);

        // IMAGE
        findViewById(R.id.pit_image_front_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String filename = ScoutAuthState.shared.pitScoutRecord.str(PitScoutRecord.TEAM_NUMBER);
                if (filename.isEmpty()) {
                    Toast.makeText(PitScoutingActivity.this, "Supply Team Number First", Toast.LENGTH_LONG).show();
                    return;
                }

                Intent pictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                if (pictureIntent.resolveActivity(getPackageManager()) != null) {
                    File imageFile = FileManager.shared.getNewImageFile("FRONT-" + filename);
                    if (imageFile == null) {
                        return;
                    }

                    ScoutAuthState.shared.pitScoutRecord.set(PitScoutRecord.ROBOT_FRONT_FILENAME, imageFile.getName());
                    Debug.log("Set " + PitScoutRecord.ROBOT_FRONT_FILENAME + " to " + imageFile.getName());

                    Uri imageUri = FileProvider.getUriForFile(PitScoutingActivity.this, "com.frc63175985.csp.fileprovider", imageFile);
                    pictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);

                    startActivityForResult(pictureIntent, REQUEST_FRONT_ROBOT_IMAGE);
                } else {
                    Toast.makeText(PitScoutingActivity.this, "Could not start camera", Toast.LENGTH_LONG).show();
                }
            }
        });

        findViewById(R.id.pit_image_side_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String filename = ScoutAuthState.shared.pitScoutRecord.str(PitScoutRecord.TEAM_NUMBER);
                if (filename.isEmpty()) {
                    Toast.makeText(PitScoutingActivity.this, "Supply Team Number First", Toast.LENGTH_LONG).show();
                    return;
                }
                Intent pictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                if (pictureIntent.resolveActivity(getPackageManager()) != null) {
                    File imageFile = FileManager.shared.getNewImageFile("SIDE-" + filename);
                    if (imageFile == null) {
                        return;
                    }

                    ScoutAuthState.shared.pitScoutRecord.set(PitScoutRecord.ROBOT_SIDE_FILENAME, imageFile.getName());
                    Debug.log("Set " + PitScoutRecord.ROBOT_SIDE_FILENAME + " to " + imageFile.getName());

                    Uri imageUri = FileProvider.getUriForFile(PitScoutingActivity.this, "com.frc63175985.csp.fileprovider", imageFile);
                    pictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);

                    startActivityForResult(pictureIntent, REQUEST_SIDE_ROBOT_IMAGE);
                } else {
                    Toast.makeText(PitScoutingActivity.this, "Could not start camera", Toast.LENGTH_LONG).show();
                }
            }
        });

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

                String teamName = ScoutAuthState.shared.pitScoutRecord.str(PitScoutRecord.TEAM_NUMBER);
                if (teamName.isEmpty() || teamName.equals("--------")) {
                    Toast.makeText(PitScoutingActivity.this, "Team Name cannot be empty", Toast.LENGTH_SHORT).show();
                    return;
                }

                final File pitScoutingFile = FileManager.shared.savePit();

                new AlertDialog.Builder(PitScoutingActivity.this)
                        .setTitle("Pit Scouting Exported!")
                        .setMessage("Exported to path: " + pitScoutingFile.getAbsolutePath() + "\nDo you want to share this Pit Scouting file?")
                        .setNegativeButton(android.R.string.no, null)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                                Uri uri = FileProvider.getUriForFile(
                                        PitScoutingActivity.this,
                                        PitScoutingActivity.this.getApplicationContext()
                                                .getPackageName() + ".fileprovider", pitScoutingFile);
                                shareIntent.setDataAndType(uri, "text/plain");
                                shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                                shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
                                startActivity(Intent.createChooser(shareIntent, "Share file using"));
                            }
                        })
                        .setOnDismissListener(new DialogInterface.OnDismissListener() {
                            @Override
                            public void onDismiss(DialogInterface dialog) {
                                // Ask if they want to reset everything
                                new AlertDialog.Builder(PitScoutingActivity.this)
                                        .setTitle("Clear")
                                        .setMessage("Would you like to clear the screen?")
                                        .setNegativeButton(android.R.string.no, null)
                                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                ScoutAuthState.shared.pitScoutRecord.clear();
                                                finish();
                                            }
                                        })
                                        .show();
                            }
                        })
                        .show();
            }
        });

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK) {
            return;
        }

        if (requestCode == REQUEST_FRONT_ROBOT_IMAGE || requestCode == REQUEST_SIDE_ROBOT_IMAGE) {
            // Read image
            String key = requestCode == REQUEST_FRONT_ROBOT_IMAGE ?
                    PitScoutRecord.ROBOT_FRONT_FILENAME : PitScoutRecord.ROBOT_SIDE_FILENAME;
            final String filename = ScoutAuthState.shared.pitScoutRecord.str(key);
            if (filename.isEmpty()) {
                Debug.log("Filename is empty");
                return;
            }

            Bitmap image = FileManager.shared.readImage(filename);
            if (requestCode == REQUEST_FRONT_ROBOT_IMAGE) {
                ((ImageView)findViewById(R.id.pit_image_front_imageView)).setImageBitmap(image);
            } else {
                ((ImageView)findViewById(R.id.pit_image_side_imageView)).setImageBitmap(image);
            }

            new AlertDialog.Builder(this)
                    .setTitle("Share Robot Photo")
                    .setMessage("Do you want to share this robot photo?")
                    .setNegativeButton(android.R.string.no, null)
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent shareIntent = new Intent(Intent.ACTION_SEND);
                            Uri uri = FileProvider.getUriForFile(
                                    PitScoutingActivity.this,
                                    PitScoutingActivity.this.getApplicationContext()
                                            .getPackageName() + ".fileprovider", FileManager.shared.getRobotPicture(filename));
                            shareIntent.setDataAndType(uri, "image/*");
                            shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                            shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
                            startActivity(Intent.createChooser(shareIntent, "Share file using"));
                        }
                    })
                    .show();
        }
    }
}
