package com.khamvongsa.victor.mareunion.controller;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class ListReunionPagerAdapter extends FragmentPagerAdapter {


    public ListReunionPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return ReunionFragment.newInstance();
    }


    @Override
    public int getCount() {
        return 1;
    }
}
