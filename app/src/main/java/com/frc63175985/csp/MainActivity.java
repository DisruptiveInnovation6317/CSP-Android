package com.frc63175985.csp;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.frc63175985.csp.auth.CSPAuthState;
import com.frc63175985.csp.auth.CSPAuthStateListener;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, CSPAuthStateListener {
    private static final String LOG_TAG = "CSP-Android";

    private NavigationView navigationView;

    // Fragments
    private WelcomeFragment welcomeFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        welcomeFragment = new WelcomeFragment();
        FragmentTransaction fm = getSupportFragmentManager().beginTransaction();
        fm.replace(R.id.content_frame, welcomeFragment).commit();

        CSPAuthState.shared.setStateListener(this);
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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_pit_scout) {

        } else if (id == R.id.nav_match_scout) {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public static void log(Object o) {
        Log.d(LOG_TAG, o.toString());
    }

    @Override
    public void authStateChanged(boolean loggedIn) {
        TextView scoutTextView = findViewById(R.id.nav_scout_name);
        TextView tournamentTextView = findViewById(R.id.nav_tournament_name);

        navigationView.getMenu().getItem(0).setEnabled(loggedIn);
        navigationView.getMenu().getItem(1).setEnabled(loggedIn);

        if (loggedIn) {
            // Set title and subtitle to scout and matchlist to user
            scoutTextView.setText(CSPAuthState.shared.scout);
            tournamentTextView.setText(CSPAuthState.shared.tournament);
        } else {
            // Set title and subtitle to scout and matchlist to placeholders
            scoutTextView.setText(R.string.nav_header_title);
            tournamentTextView.setText(R.string.nav_header_subtitle);
        }
    }
}
