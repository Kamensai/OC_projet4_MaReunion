package com.khamvongsa.victor.mareunion.controller;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class ListMeetingPagerAdapter extends FragmentPagerAdapter {


    public ListMeetingPagerAdapter(@NonNull FragmentManager fm) {
        super(fm,BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return MeetingFragment.newInstance();
    }

    @Override
    public int getCount() {
        return 1;
    }
}
