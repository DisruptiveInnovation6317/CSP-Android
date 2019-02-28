package com.frc63175985.csp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

public class MatchScoutingActivity extends FragmentActivity {
    // Scouting Views
    private Fragment[] fragments = new Fragment[4];
    private String[] fragmentTitles = {"Start", "Autonomous", "TeleOp", "Info"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_scouting);

        fragments[0] = new StartFragment();
        fragments[1] = new AutonomousFragment();
        fragments[2] = new TeleOpFragment();
        fragments[3] = new MatchInfoFragment();

        final ViewPager pager = findViewById(R.id.match_scouting_pager);
        pager.setAdapter(new MatchScoutingFragmentAdapter(getSupportFragmentManager()));
    }

    private class MatchScoutingFragmentAdapter extends FragmentStatePagerAdapter {
        public MatchScoutingFragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return fragmentTitles[position];
        }

        @Override
        public Fragment getItem(int i) {
            return fragments[i];
        }

        @Override
        public int getCount() {
            return fragments.length;
        }
    }
}