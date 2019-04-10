package com.frc63175985.csp;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.frc63175985.csp.auth.ScoutAuthState;

public class SettingsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        ((Switch)findViewById(R.id.use_online_database_option)).setChecked(ScoutAuthState.shared.currentMatch.onlineMode);
        ((Switch)findViewById(R.id.use_online_database_option)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                ScoutAuthState.shared.currentMatch.onlineMode = isChecked;
                PreferenceManager.getDefaultSharedPreferences(SettingsActivity.this).edit().putBoolean("ONLINE_MODE", isChecked).apply();
            }
        });
    }
}
