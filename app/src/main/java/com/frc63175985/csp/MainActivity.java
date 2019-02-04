package com.frc63175985.csp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.frc63175985.csp.auth.ScoutAuthState;
import com.frc63175985.csp.auth.ScoutAuthStateListener;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, ScoutAuthStateListener {
    private static final String LOG_TAG = "CSP_Android";

    private NavigationView navigationView;

    // Fragments
    private Fragment welcomeFragment = new WelcomeFragment();
    private Fragment pitScoutingFragment = new PitScoutingFragment();
    private Fragment matchScoutingFragment = new MatchScoutingFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        FragmentTransaction fm = getSupportFragmentManager().beginTransaction();
        fm.replace(R.id.content_frame, welcomeFragment).commit();

        ScoutAuthState.shared.addStateListener(this);

        View headerView = navigationView.getHeaderView(0)
                .findViewById(R.id.nav_header_main_linearLayout);
        headerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigationView.setCheckedItem(navigationView.getMenu().getItem(0));
                onNavigationItemSelected(navigationView.getMenu().getItem(0));
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        FragmentTransaction fm = getSupportFragmentManager().beginTransaction();

        if (id == R.id.nav_welcome_screen) {
            setTitle(getResources().getString(R.string.app_name));
            fm.replace(R.id.content_frame, welcomeFragment);
        } else if (id == R.id.nav_pit_scout) {
            setTitle(getResources().getString(R.string.pit_scout));
            fm.replace(R.id.content_frame, pitScoutingFragment);
        } else if (id == R.id.nav_match_scout) {
            setTitle(getResources().getString(R.string.match_scout));
            fm.replace(R.id.content_frame, matchScoutingFragment);
        }

        fm.commit();

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void authStateChanged(boolean loggedIn) {
        TextView scoutTextView = findViewById(R.id.nav_scout_name);
        TextView tournamentTextView = findViewById(R.id.nav_tournament_name);

        navigationView.getMenu().getItem(1).setEnabled(loggedIn);
        navigationView.getMenu().getItem(2).setEnabled(loggedIn);

        if (loggedIn) {
            // Set title and subtitle to scout and matchlist to user
            scoutTextView.setText(ScoutAuthState.shared.scout);
            tournamentTextView.setText(ScoutAuthState.shared.tournament);
        } else {
            // Set title and subtitle to scout and matchlist to placeholders
            scoutTextView.setText(R.string.nav_header_title);
            tournamentTextView.setText(R.string.nav_header_subtitle);
        }
    }

    /**
     * Log anything to Logcat using the tag {@code LOG_TAG}
     * @param o the object to be logged
     */
    public static void log(Object o) {
        Log.d(LOG_TAG, o.toString());
    }
}
