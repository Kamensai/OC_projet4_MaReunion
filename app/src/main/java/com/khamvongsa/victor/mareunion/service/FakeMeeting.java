package com.khamvongsa.victor.mareunion.service;

import com.khamvongsa.victor.mareunion.controller.ExampleMeeting;
import com.khamvongsa.victor.mareunion.controller.ExampleRoom;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static android.graphics.Color.BLUE;
import static android.graphics.Color.GREEN;
import static android.graphics.Color.RED;


public abstract class FakeMeeting {

    static ExampleRoom mMario = new ExampleRoom(0, "Mario", RED);
    static ExampleRoom mLuigi = new ExampleRoom(1, "Luigi", GREEN);
    static ExampleRoom mPeach = new ExampleRoom(2, "Peach", BLUE);
    static List<String> mParticipants = Arrays.asList("Jean", "Baptiste");

    public static final List<ExampleMeeting> EXEMPLE_REUNIONS = Arrays.asList(
    new ExampleMeeting(0, Calendar.getInstance().getTime(), Calendar.getInstance().getTime(), Calendar.getInstance().getTime(), mMario, "Arbre", mParticipants ),
    new ExampleMeeting(1, Calendar.getInstance().getTime(), Calendar.getInstance().getTime(), Calendar.getInstance().getTime(), mLuigi, "Banane", mParticipants ),
    new ExampleMeeting(2, Calendar.getInstance().getTime(), Calendar.getInstance().getTime(), Calendar.getInstance().getTime(), mPeach, "Cacahuètes", mParticipants ),
    new ExampleMeeting(3, getDateTest(), Calendar.getInstance().getTime(), Calendar.getInstance().getTime(), mPeach, "Trompettes", mParticipants ));

    public static List<ExampleMeeting> generateExempleReunions() {
        return new ArrayList<>(EXEMPLE_REUNIONS);
    }

    public static Date getDateTest() {
        Calendar mDateTest = Calendar.getInstance();
        mDateTest.set(2022,8,5);
        return mDateTest.getTime();
    }

}
