package com.frc63175985.csp;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.inputmethod.InputMethodManager;

public class MatchScoutingActivity extends FragmentActivity {
    // Scouting Views
    private Fragment[] fragments = new Fragment[4];
    private String[] fragmentTitles = {"Start", "Sandstorm", "TeleOp", "Comments"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_scouting);

        fragments[0] = new StartFragment();
        fragments[1] = new SandstormFragment();
        fragments[2] = new TeleOpFragment();
        fragments[3] = new CommentsFragment();

        final ViewPager pager = findViewById(R.id.match_scouting_pager);
        pager.setAdapter(new MatchScoutingFragmentAdapter(getSupportFragmentManager()));
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {}

            @Override
            public void onPageSelected(int position) {
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                if (getCurrentFocus() != null) {
                    imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {}
        });
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