package com.frc63175985.csp;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.frc63175985.csp.auth.ScoutAuthState;

public class WelcomeFragment extends Fragment implements View.OnClickListener {
    private EditText scoutNameEditText, tournamentNameEditText;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_welcome, container, false);

        if (getActivity() != null) {
            getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
        }

        if (ScoutAuthState.shared.isLoggedIn()) {
            view.findViewById(R.id.welcome_loggedIn).setVisibility(View.VISIBLE);
            view.findViewById(R.id.welcome_notLoggedIn).setVisibility(View.GONE);

            TextView welcomeBackTextView = view.findViewById(R.id.welcome_welcomeBack_textView);
            welcomeBackTextView.setText(String.format("Welcome %s!", ScoutAuthState.shared.scout));

            Button logoutButton = view.findViewById(R.id.welcome_logout_button);
            logoutButton.setOnClickListener(this);
        } else {
            view.findViewById(R.id.welcome_notLoggedIn).setVisibility(View.VISIBLE);
            view.findViewById(R.id.welcome_loggedIn).setVisibility(View.GONE);

            Button loginButton = view.findViewById(R.id.welcome_login_button);
            loginButton.setOnClickListener(this);

            scoutNameEditText = view.findViewById(R.id.welcome_scout_login_editText);
            tournamentNameEditText = view.findViewById(R.id.welcome_tournament_login_editText);
        }

        return view;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.welcome_logout_button) {
            ScoutAuthState.shared.logOut();
            refreshFragment();
        } else if (v.getId() == R.id.welcome_login_button) {
            String scoutName = scoutNameEditText.getText().toString();
            String tournamentName = tournamentNameEditText.getText().toString();

            if (ScoutAuthState.shared.login(scoutName, tournamentName)) {
                refreshFragment();
            } else {
                Toast.makeText(getContext(),
                        "Invalid Scout or Tournament", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * Refresh this {@link Fragment}
     */
    private void refreshFragment() {
        FragmentManager fm = getFragmentManager();
        if (fm == null) {
            MainActivity.log("Could not refresh Welcome fragment");
            return;
        }

        fm.beginTransaction().detach(this).attach(this).commit();
    }
}
