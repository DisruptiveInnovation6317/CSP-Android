package com.frc63175985.csp;

import android.util.Log;

/**
 * Used for enabling debugging features
 */
public class Debug {
    public static final String TAG = "CSP_LOG";
    public static final boolean AUTO_SIGNIN = true;
    public static final boolean EMULATE_QR_SCAN = true;
    public static final boolean LOG_GENERATED_QR_CODE = false;
    public static final boolean LOG_DATABASE_SET = false;

    public static void log(Object o) {
        Log.v(TAG, o.toString());
    }
}
