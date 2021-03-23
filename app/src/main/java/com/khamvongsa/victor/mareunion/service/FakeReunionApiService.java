package com.khamvongsa.victor.mareunion.service;


import com.khamvongsa.victor.mareunion.controller.ExempleReunion;
import com.khamvongsa.victor.mareunion.controller.ExempleSalle;

import java.util.ArrayList;
import java.util.List;

public class FakeReunionApiService implements ReunionApiService, DeleteListener {

    private final List<ExempleReunion> reunions = FakeReunion.generateExempleReunions();
    private final List<ExempleSalle> salles = FakeSalle.getSallesDisponibles();

    @Override
    public List<ExempleSalle> getSalles() {
        return new ArrayList<>(salles) ;
    }

    @Override
    public List<ExempleReunion> getReunions() {
        return new ArrayList<>(reunions) ;
        // pour protéger les données dans la partie service
    }

    @Override
    public void createReunion(ExempleReunion reunion) {
        reunions.add(reunion);
    }

    @Override
    public void clickOnDeleteListener(ExempleReunion reunion) {
         reunions.remove(reunion);
    }
}
