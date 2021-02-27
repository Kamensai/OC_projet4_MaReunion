package com.khamvongsa.victor.mareunion.service;


import com.khamvongsa.victor.mareunion.controller.ExempleSalle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static android.graphics.Color.BLUE;
import static android.graphics.Color.GREEN;
import static android.graphics.Color.RED;

public abstract class FakeSalle {

    public static final List<ExempleSalle> SALLES_DISPONIBLES = Arrays.asList(
            new ExempleSalle(0,"Mario", RED),
            new ExempleSalle(1,"Luigi", GREEN),
            new ExempleSalle(2,"Peach", BLUE));

    public static List<ExempleSalle> getSallesDisponibles() {
        return new ArrayList<>(SALLES_DISPONIBLES);
    }
}
