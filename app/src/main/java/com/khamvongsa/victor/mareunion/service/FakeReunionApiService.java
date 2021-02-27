package com.khamvongsa.victor.mareunion.service;


import com.khamvongsa.victor.mareunion.controller.ExempleReunion;
import com.khamvongsa.victor.mareunion.controller.ExempleSalle;

import java.util.List;

public class FakeReunionApiService implements ReunionApiService {

    private final List<ExempleReunion> reunions = FakeReunion.generateExempleReunions();
    private final List<ExempleSalle> salles = FakeSalle.getSallesDisponibles();

    @Override
    public List<ExempleSalle> getSalles() {
        return salles;
    }

    @Override
    public List<ExempleReunion> getReunions() {
        return reunions;
    }

    @Override
    public void deleteReunion(ExempleReunion reunion) {
        reunions.remove(reunion);
    }

    @Override
    public void createReunion(ExempleReunion reunion) {
        reunions.add(reunion);
    }

}
