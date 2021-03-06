package com.khamvongsa.victor.mareunion.service;

import com.khamvongsa.victor.mareunion.model.ExampleMeeting;
import com.khamvongsa.victor.mareunion.model.ExampleRoom;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static android.graphics.Color.BLUE;
import static android.graphics.Color.GREEN;
import static android.graphics.Color.RED;


public final class FakeMeeting {

    static final ExampleRoom mMario = new ExampleRoom(0, "Mario", RED);
    static final ExampleRoom mLuigi = new ExampleRoom(1, "Luigi", GREEN);
    static final ExampleRoom mPeach = new ExampleRoom(2, "Peach", BLUE);
    static final List<String> mParticipants = Arrays.asList("Jean", "Baptiste");

    public static final List<ExampleMeeting> EXEMPLE_REUNIONS = Arrays.asList(
    new ExampleMeeting(0, Calendar.getInstance().getTime(), Calendar.getInstance().getTime(), Calendar.getInstance().getTime(), mMario, "Arbre", mParticipants ),
    new ExampleMeeting(1, Calendar.getInstance().getTime(), Calendar.getInstance().getTime(), Calendar.getInstance().getTime(), mLuigi, "Banane", mParticipants ),
    new ExampleMeeting(2, Calendar.getInstance().getTime(), Calendar.getInstance().getTime(), Calendar.getInstance().getTime(), mPeach, "Cacahuètes", mParticipants ),
    new ExampleMeeting(3, getDateTest(), getStartTimeTest(), getEndTimeTest(), mPeach, "Trompettes", mParticipants ),
    new ExampleMeeting(4, getDateSecondTest(), getStartTimeSecondTest(), getEndTimeSecondTest(), mPeach, "Piano", mParticipants ));

    public static List<ExampleMeeting> generateExempleReunions() {
        return new ArrayList<>(EXEMPLE_REUNIONS);
    }

    public static Date getDateTest() {
        Calendar mDateTest = Calendar.getInstance();
        mDateTest.set(2022,8,5);
        return mDateTest.getTime();
    }

    public static Date getStartTimeTest() {
        Calendar mStartTimeTest = Calendar.getInstance();
        mStartTimeTest.set(0,0,0,14,20);
        return mStartTimeTest.getTime();
    }

    public static Date getEndTimeTest() {
        Calendar mEndTimeTest = Calendar.getInstance();
        mEndTimeTest.set(0,0,0,15,40);
        return mEndTimeTest.getTime();
    }

    public static Date getDateSecondTest() {
        Calendar mDateTest = Calendar.getInstance();
        mDateTest.set(2023,8,5);
        return mDateTest.getTime();
    }

    public static Date getStartTimeSecondTest() {
        Calendar mStartTimeTest = Calendar.getInstance();
        mStartTimeTest.set(0,0,0,10,20);
        return mStartTimeTest.getTime();
    }

    public static Date getEndTimeSecondTest() {
        Calendar mEndTimeTest = Calendar.getInstance();
        mEndTimeTest.set(0,0,0,10,40);
        return mEndTimeTest.getTime();
    }

}
