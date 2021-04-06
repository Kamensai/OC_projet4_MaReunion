package com.khamvongsa.victor.mareunion.service;

import com.khamvongsa.victor.mareunion.controller.ExempleReunion;
import com.khamvongsa.victor.mareunion.controller.ExempleSalle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import static android.graphics.Color.BLUE;
import static android.graphics.Color.GREEN;
import static android.graphics.Color.RED;


public abstract class FakeReunion {

    static ExempleSalle mMario = new ExempleSalle(0, "Mario", RED);
    static ExempleSalle mLuigi = new ExempleSalle(1, "Luigi", GREEN);
    static ExempleSalle mPeach = new ExempleSalle(2, "Peach", BLUE);
    static List<String> mParticipants = Arrays.asList("Jean", "Baptiste");

    public static final List<ExempleReunion> EXEMPLE_REUNIONS = Arrays.asList(
    new ExempleReunion(0, Calendar.getInstance().getTime(), Calendar.getInstance().getTime(), Calendar.getInstance().getTime(), mMario, "Arbre", mParticipants ),
    new ExempleReunion(1, Calendar.getInstance().getTime(), Calendar.getInstance().getTime(), Calendar.getInstance().getTime(), mLuigi, "Banane", mParticipants ),
    new ExempleReunion(2, Calendar.getInstance().getTime(), Calendar.getInstance().getTime(), Calendar.getInstance().getTime(), mPeach, "Cacahuètes", mParticipants ));

    public static List<ExempleReunion> generateExempleReunions() {
        return new ArrayList<>(EXEMPLE_REUNIONS);
    }

}
