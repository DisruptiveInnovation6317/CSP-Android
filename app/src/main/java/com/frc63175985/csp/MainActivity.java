package com.frc63175985.csp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.frc63175985.csp.auth.ScoutAuthState;
import com.frc63175985.csp.auth.ScoutAuthStateListener;

public class MainActivity extends AppCompatActivity implements ScoutAuthStateListener, View.OnClickListener, TbaAsyncDelegate {
    private EditText scoutNameEditText, tournamentNameEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ScoutAuthState.shared.addStateListener(this);
        ScoutAuthState.shared.currentMatch.loadPreferences(this);

        scoutNameEditText = findViewById(R.id.welcome_scout_login_editText);
        tournamentNameEditText = findViewById(R.id.welcome_tournament_login_editText);

        refreshView();
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        switch (id) {
            case R.id.welcome_logout_button:
                ScoutAuthState.shared.logOut();
                break;
            case R.id.welcome_login_button:
                String scoutName = scoutNameEditText.getText().toString();
                String tournamentName = tournamentNameEditText.getText().toString();
                scoutNameEditText.clearFocus();
                tournamentNameEditText.clearFocus();
                if (!ScoutAuthState.shared.login(scoutName, tournamentName)) {
                    Toast.makeText(this, "Invalid login", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.welcome_pit_scouting_button: {
                Intent i = new Intent(MainActivity.this, PitScoutingActivity.class);
                startActivity(i);
                break;
            }
            case R.id.welcome_match_scouting_button: {
                Intent i = new Intent(MainActivity.this, MatchScoutingActivity.class);
                startActivity(i);
                break;
            }
            case R.id.welcome_qr_aggregator_button: {
                Intent i = new Intent(MainActivity.this, QrAggregatorActivity.class);
                startActivity(i);
                break;
            }
            case R.id.welcome_pull_from_tba:
                TbaCoordinator.shared.pullEventData(this);
                break;
            case R.id.launch_settings_button: {
                Intent i = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(i);
                break;
            }
        }
    }

    @Override
    public void authStateChanged(boolean loggedIn) {
        refreshView();
    }

    private void refreshView() {
        if (ScoutAuthState.shared.isLoggedIn()) {
            findViewById(R.id.welcome_loggedIn).setVisibility(View.VISIBLE);
            findViewById(R.id.welcome_notLoggedIn).setVisibility(View.GONE);

            TextView welcomeBackTextView = findViewById(R.id.welcome_welcomeBack_textView);
            welcomeBackTextView.setText(String.format("Welcome %s!", ScoutAuthState.shared.scout));

            Button logoutButton = findViewById(R.id.welcome_logout_button);
            logoutButton.setOnClickListener(this);
        } else {
            findViewById(R.id.welcome_notLoggedIn).setVisibility(View.VISIBLE);
            findViewById(R.id.welcome_loggedIn).setVisibility(View.GONE);

            Button loginButton = findViewById(R.id.welcome_login_button);
            loginButton.setOnClickListener(this);

            scoutNameEditText = findViewById(R.id.welcome_scout_login_editText);
            tournamentNameEditText = findViewById(R.id.welcome_tournament_login_editText);
        }
    }

    @Override
    public void processFinished(String[] teamNames) {
        if (teamNames == null) {
            Toast.makeText(this, "Error grabbing teams", Toast.LENGTH_SHORT).show();
        }
    }
}
