package com.khamvongsa.victor.mareunion.service;


import com.khamvongsa.victor.mareunion.controller.ExempleReunion;
import com.khamvongsa.victor.mareunion.controller.ExempleSalle;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import androidx.appcompat.view.menu.ExpandedMenuView;

public class FakeReunionApiService implements ReunionApiService {

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
    public List<ExempleReunion> getReunionsBySalle(String salleChoisie) {
        List<ExempleReunion> mListBySalle = new ArrayList<>();
        for (int i = 0; i < reunions.size(); i++) {
            ExempleReunion reunionSalle = reunions.get(i);
            if (salleChoisie.equalsIgnoreCase(reunionSalle.getSalle().getNom())) {
                mListBySalle.add(reunionSalle);
            }
        }
        return mListBySalle;
    }

    @Override
    public List<ExempleReunion> getReunionsByDate(Calendar dateChoisie) {
        List<ExempleReunion> mListByDate = new ArrayList<>();
        for (int i = 0; i < reunions.size(); i++) {
            ExempleReunion reunionDate = reunions.get(i);
            if (dateChoisie.equals(reunionDate.getDebut())) {
                mListByDate.add(reunionDate);
            }
        }
        return mListByDate;
    }

    @Override
    public void createReunion(ExempleReunion reunion) {
        reunions.add(reunion);
    }

    @Override
    public void deleteReunion(ExempleReunion reunion) {
         reunions.remove(reunion);
    }
}
