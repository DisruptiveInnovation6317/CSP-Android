package com.frc63175985.csp;

import android.app.AlertDialog;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.frc63175985.csp.auth.Match;
import com.frc63175985.csp.auth.ScoutAuthState;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

public class CommentsFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_comments, container, false);

        String[] driveRatings = {"Driver Rating", "Terrible", "Average", "Good", "Amazing"};
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

        view.findViewById(R.id.comments_finalize_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String contents = ScoutAuthState.shared.currentMatch.export();
                if (contents == null) {
                    Toast.makeText(getContext(), "Error in validation", Toast.LENGTH_LONG).show();
                    return;
                }

                AlertDialog dialog = QrHelper.qrDialogFromString(getContext(), "Match", contents);
                if (dialog != null) {
                    dialog.show();
                }
            }
        });

        return view;
    }
}
