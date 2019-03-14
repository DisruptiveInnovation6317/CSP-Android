package com.frc63175985.csp;

import android.util.Log;

import javax.annotation.Nullable;

/**
 * Used for enabling debugging features
 */
public class Debug {
    public static final String TAG = "CSP_LOG";
    public static final boolean AUTO_SIGNIN = false;
    public static final boolean EMULATE_QR_SCAN = false;
    public static final boolean LOG_GENERATED_QR_CODE = false;
    public static final boolean LOG_DATABASE_SET = false;

    public static void log(@Nullable Object o) {
        if (o == null) {
            Log.v(TAG, "null");
        } else {
            Log.v(TAG, o.toString());
        }
    }
}
