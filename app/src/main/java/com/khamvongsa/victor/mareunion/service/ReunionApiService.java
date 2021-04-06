package com.khamvongsa.victor.mareunion.service;

import com.khamvongsa.victor.mareunion.controller.ExempleReunion;
import com.khamvongsa.victor.mareunion.controller.ExempleSalle;

import java.util.Calendar;
import java.util.List;

public interface ReunionApiService {

    List<ExempleSalle> getSalles();

    List<ExempleReunion> getReunions();

    void deleteReunion(ExempleReunion reunion);

    List<ExempleReunion> getReunionsByRooms(String salleChoisie);

    List<ExempleReunion> getReunionsByDate(Calendar dateChoisie);

    List<String> FilterAvailableRooms(List<ExempleReunion> reunions, List<ExempleSalle> rooms, Calendar startDate, Calendar startHour, Calendar EndHour);

    void createReunion(ExempleReunion reunion);

}
