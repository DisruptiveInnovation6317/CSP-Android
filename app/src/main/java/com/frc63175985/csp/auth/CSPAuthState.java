package com.frc63175985.csp.auth;

public class CSPAuthState {
    public static final CSPAuthState shared = new CSPAuthState();
    private CSPAuthStateListener stateListener;
    public String scout, tournament;

    private CSPAuthState() {

    }

    public void setStateListener(CSPAuthStateListener listener) {
        stateListener = listener;
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
        stateListener.authStateChanged(true);

        return true;
    }

    public void logOut() {
        scout = null;
        tournament = null;

        stateListener.authStateChanged(false);
    }

    public boolean isLoggedIn() {
        return scout != null;
    }
}
