package com.khamvongsa.victor.mareunion.service;

import com.khamvongsa.victor.mareunion.controller.ExempleReunion;
import com.khamvongsa.victor.mareunion.controller.ExempleSalle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import static android.graphics.Color.BLUE;


public abstract class FakeReunion {

    static ExempleSalle mMario = new ExempleSalle(0, "Yoshi", BLUE);
    static List<String> mParticipants = Arrays.asList("Jean", "Baptiste");

    public static final List<ExempleReunion> EXEMPLE_REUNIONS = Arrays.asList(
    new ExempleReunion(0, Calendar.getInstance().getTime(), mMario, "Test", mParticipants ));

    public static List<ExempleReunion> generateExempleReunions() {
        return new ArrayList<>(EXEMPLE_REUNIONS);
    }

}
