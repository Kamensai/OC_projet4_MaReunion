package com.khamvongsa.victor.mareunion.utils;


import android.view.View;

import com.google.android.material.chip.Chip;
import com.khamvongsa.victor.mareunion.R;

import org.hamcrest.Matcher;

import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.matcher.ViewMatchers;

public class DeleteChipViewAction implements ViewAction {
    @Override
    public Matcher<View> getConstraints() {
        return ViewMatchers.isAssignableFrom(Chip.class);
    }

    @Override
    public String getDescription() {
        return "Click on specific button";
    }

    @Override
    public void perform(UiController uiController, View view) {
        Chip itemView = view.findViewById(R.id.chip);
        // Maybe check for null
        itemView.performCloseIconClick();
    }
}