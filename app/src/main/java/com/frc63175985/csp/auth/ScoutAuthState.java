package com.frc63175985.csp.auth;

import android.os.Handler;
import android.os.Looper;

import com.frc63175985.csp.MainActivity;

import java.util.LinkedList;

public class ScoutAuthState {
    public static final ScoutAuthState shared = new ScoutAuthState();
    public String scout, tournament;
    private LinkedList<ScoutAuthStateListener> stateListeners = new LinkedList<>();

    private ScoutAuthState() {
        // Auto login if in debug mode
        if (MainActivity.DEBUG_MODE) {
            scout = "Richards, Brandon";
            tournament = "Cedar Falls";
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(1000);
                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {
                                notifyAuthStateChanged(true);
                            }
                        });
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }

    /**
     * Add a listener to monitor when the auth state changes.
     * @see ScoutAuthStateListener
     * @param listener The {@link ScoutAuthStateListener} to add
     */
    public void addStateListener(ScoutAuthStateListener listener) {
        stateListeners.add(listener);
    }

    /**
     * Attempt to log the user in
     * @param scoutName The name of the scout
     * @param tournamentName The name of the tournament
     * @return If the log in attempt was successful
     */
    public boolean login(String scoutName, String tournamentName) {
        if (scoutName == null || scoutName.isEmpty()) {
            return false;
        } else if (tournamentName == null || tournamentName.isEmpty()) {
            return false;
        }

        // Check if the name is formatted properly
        if (!scoutName.matches("^[A-Z][a-z]+, [A-Z][a-z]+$")) {
            return false;
        }

        scout = scoutName;
        tournament = tournamentName;

        notifyAuthStateChanged(true);

        return true;
    }

    /**
     * Set the scout and tournament to {@code null}
     */
    public void logOut() {
        scout = null;
        tournament = null;

        notifyAuthStateChanged(false);
    }

    /**
     * Get whether or not their is a valid user logged in
     * @return is there a user logged in
     */
    public boolean isLoggedIn() {
        return scout != null;
    }

    /**
     * Loop through all listeners and call
     * {@link ScoutAuthStateListener#authStateChanged(boolean)} on each one
     * @param loggedIn the new state of the user
     */
    private void notifyAuthStateChanged(boolean loggedIn) {
        for (int i = 0; i < stateListeners.size(); i++) {
            stateListeners.get(i).authStateChanged(loggedIn);
        }
    }
}
