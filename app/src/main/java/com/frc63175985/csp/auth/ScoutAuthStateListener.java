package com.frc63175985.csp.auth;

public interface ScoutAuthStateListener {
    /**
     * Called when this {@link ScoutAuthState} logs in or logs out
     * @param loggedIn Whether this {@link ScoutAuthState} is logged in
     */
    void authStateChanged(boolean loggedIn);
}
