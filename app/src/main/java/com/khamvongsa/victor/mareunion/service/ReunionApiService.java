package com.khamvongsa.victor.mareunion.service;

import com.khamvongsa.victor.mareunion.controller.ExempleReunion;
import com.khamvongsa.victor.mareunion.controller.ExempleSalle;

import java.util.Calendar;
import java.util.List;

public interface ReunionApiService {

    List<ExempleSalle> getSalles();

    List<ExempleReunion> getReunions();

    // TODO : Get reunionbyDate

    // TODO : Get reunionbySalle


    void deleteReunion(ExempleReunion reunion);

    List<ExempleReunion> getReunionsBySalle(String salleChoisie);

    List<ExempleReunion> getReunionsByDate(Calendar dateChoisie);

    void createReunion(ExempleReunion reunion);

}
