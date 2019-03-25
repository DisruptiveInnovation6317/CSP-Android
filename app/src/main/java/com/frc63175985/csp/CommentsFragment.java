package com.frc63175985.csp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.frc63175985.csp.auth.Match;
import com.frc63175985.csp.auth.ScoutAuthState;

import java.io.File;

public class CommentsFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_comments, container, false);

        String[] driveRatings = {"--------", "Needs", "Average", "Good", "Amazing"};
        Match.GUI.bindSpinner(getContext(), view, R.id.comments_drive_rating_spinner,
                Match.DRIVE_RATING, driveRatings);

        Match.GUI.bindEditText(view, R.id.comments_comments_editText, Match.COMMENTS);
        Match.GUI.bindCheckbox(view, R.id.comments_highlight_checkBox, Match.HIGHLIGHT);
        Match.GUI.bindCheckbox(view, R.id.comments_warning_checkBox, Match.WARNING);
        Match.GUI.bindCheckbox(view, R.id.comments_worked_with_alliance_checkBox, Match.WORK_WITH_ALLIANCE);
        Match.GUI.bindCheckbox(view, R.id.comments_recovered_checkBox, Match.RECOVER);
        Match.GUI.bindCheckbox(view, R.id.comments_strategy_checkBox, Match.WORKED_STRATEGY);
        Match.GUI.bindCheckbox(view, R.id.comments_solo_checkBox, Match.WORKED_SOLO);
        Match.GUI.bindCheckbox(view, R.id.comments_good_defence_checkBox, Match.EFFECTIVE_DEFENCE);
        Match.GUI.bindCheckbox(view, R.id.comments_ranking_1, Match.RANKING_1);

        view.findViewById(R.id.comments_finalize_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String contents = ScoutAuthState.shared.currentMatch.export();
                if (contents == null) {
                    Toast.makeText(getContext(), "Error in validation", Toast.LENGTH_LONG).show();
                    return;
                }

                if (ScoutAuthState.shared.currentMatch.str(Match.TEAM_NUMBER).isEmpty()) {
                    Toast.makeText(getContext(), "Team Number cannot be empty", Toast.LENGTH_LONG).show();
                    return;
                } else if (ScoutAuthState.shared.currentMatch.str(Match.MATCH_NUMBER).isEmpty()) {
                    Toast.makeText(getContext(), "Match Number cannot be empty", Toast.LENGTH_LONG).show();
                    return;
                }

                final File matchFile = FileManager.shared.saveMatch();
                AlertDialog dialog = QrHelper.qrDialogFromString(getContext(), "Match", contents);
                if (dialog != null) {
                    dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialog) {
                            new AlertDialog.Builder(getContext())
                                    .setTitle("Match Scouting Exported!")
                                    .setMessage("Exported to path: " + matchFile.getAbsolutePath() + "\nDo you want to share this Match Scouting file?")
                                    .setNegativeButton(android.R.string.no, null)
                                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            Intent shareIntent = new Intent(Intent.ACTION_SEND);
                                            Uri uri = FileProvider.getUriForFile(
                                                    getContext(),
                                                    getContext().getApplicationContext()
                                                            .getPackageName() + ".fileprovider", matchFile);
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
                                            new AlertDialog.Builder(getContext())
                                                    .setTitle("Clear")
                                                    .setMessage("Would you like to clear the screen?")
                                                    .setNegativeButton(android.R.string.no, null)
                                                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                                        @Override
                                                        public void onClick(DialogInterface dialog, int which) {
                                                            ScoutAuthState.shared.currentMatch.clear();
                                                            getActivity().finish();
                                                        }
                                                    })
                                                    .show();
                                        }
                                    })
                                    .show();
                        }
                    });
                    dialog.show();
                }
            }
        });

        return view;
    }
}
