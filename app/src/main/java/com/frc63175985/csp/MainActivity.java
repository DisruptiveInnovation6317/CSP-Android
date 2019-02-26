package com.frc63175985.csp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.frc63175985.csp.auth.ScoutAuthState;
import com.frc63175985.csp.auth.ScoutAuthStateListener;

public class MainActivity extends AppCompatActivity implements ScoutAuthStateListener, View.OnClickListener {
    private EditText scoutNameEditText, tournamentNameEditText;

    /**
     * Use this to quicken some processes of the app.
     * Here's what it does:
     * - Auto-sign scout
     */
    public static final boolean DEBUG_MODE = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ScoutAuthState.shared.addStateListener(this);

        scoutNameEditText = findViewById(R.id.welcome_scout_login_editText);
        tournamentNameEditText = findViewById(R.id.welcome_tournament_login_editText);

        refreshView();
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        if (id == R.id.welcome_logout_button) {
            ScoutAuthState.shared.logOut();
        } else if (id == R.id.welcome_login_button) {
            String scoutName = scoutNameEditText.getText().toString();
            String tournamentName = tournamentNameEditText.getText().toString();
            scoutNameEditText.clearFocus();
            tournamentNameEditText.clearFocus();
            if (!ScoutAuthState.shared.login(scoutName, tournamentName)) {
                Toast.makeText(this, "Invalid login", Toast.LENGTH_SHORT).show();
            }
        } else if (id == R.id.welcome_match_scouting_button) {
            Intent i = new Intent(MainActivity.this, MatchScoutingActivity.class);
            startActivity(i);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return false;
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

    /**
     * Log verbosely to Logcat using the tag CSP_V
     * @param o the object to be logged
     */
    public static void logV(Object o) {
        Log.d("CSP_V", o.toString());
    }

    /**
     * Log an error to Logcat using the tag CSP_E
     * @param o the object to be logged
     */
    public static void logE(Object o) {
        Log.d("CSP_E", o.toString());
    }
}
