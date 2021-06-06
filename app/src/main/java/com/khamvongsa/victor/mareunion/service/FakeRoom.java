package com.khamvongsa.victor.mareunion.service;


import com.khamvongsa.victor.mareunion.model.ExampleRoom;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static android.graphics.Color.BLUE;
import static android.graphics.Color.GREEN;
import static android.graphics.Color.RED;

public abstract class FakeRoom {

    public static final List<ExampleRoom> SALLES_DISPONIBLES = Arrays.asList(
            new ExampleRoom(0,"Mario", RED),
            new ExampleRoom(1,"Luigi", GREEN),
            new ExampleRoom(2,"Peach", BLUE));

    public static List<ExampleRoom> getSallesDisponibles() {
        return new ArrayList<>(SALLES_DISPONIBLES);
    }
}
