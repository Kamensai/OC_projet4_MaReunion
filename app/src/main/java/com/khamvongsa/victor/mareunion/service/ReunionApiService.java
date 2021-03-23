package com.khamvongsa.victor.mareunion.service;

import com.khamvongsa.victor.mareunion.controller.ExempleReunion;
import com.khamvongsa.victor.mareunion.controller.ExempleSalle;

import java.util.List;

public interface ReunionApiService {

    List<ExempleSalle> getSalles();

    List<ExempleReunion> getReunions();

    // Get reunionbyDate

    void clickOnDeleteListener(ExempleReunion reunion);

    void createReunion(ExempleReunion reunion);

}
