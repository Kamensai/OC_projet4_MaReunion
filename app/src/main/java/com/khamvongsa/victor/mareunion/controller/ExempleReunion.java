package com.khamvongsa.victor.mareunion.controller;

import com.khamvongsa.victor.mareunion.model.Reunion;
import com.khamvongsa.victor.mareunion.model.Salle;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.List;


public class ExempleReunion {
    private long id;

    private Date debut;

    private ExempleSalle salle;

    private String sujet;

    private List<String> participant;


    /**
     * Constructor
     */
    public ExempleReunion(long id, Date debut, ExempleSalle salle, String sujet,
                   List<String> participant) {
        this.id = id;
        this.debut = debut;
        this.salle = salle;
        this.sujet = sujet;
        this.participant = participant;
    }

    public static Comparator<ExempleReunion> ReunionDateComparator = new Comparator<ExempleReunion>() {
        @Override
        public int compare(ExempleReunion r1, ExempleReunion r2) {

            return r1.getDebut().compareTo(r2.getDebut());
        }
    };

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getDebut() {
        return debut;
    }

    public void setDebut(Date debut) {
        this.debut = debut;
    }

    public ExempleSalle getSalle() {
        return salle;
    }

    public void setSalle(ExempleSalle salle) {
        this.salle = salle;
    }

    public String getSujet() {
        return sujet;
    }

    public void setSujet(String sujet) {
        this.sujet = sujet;
    }

    public List<String> getParticipant() {
        return participant;
    }

    public void setParticipant(List<String> participant) {
        this.participant = participant;
    }
}
