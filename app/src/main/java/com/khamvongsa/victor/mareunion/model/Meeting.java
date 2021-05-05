package com.khamvongsa.victor.mareunion.model;

import java.util.Date;
import java.util.List;


public class Meeting {

    private long id;

    private Date debut;

    private Date fin;

    private Room mRoom;

    private String sujet;

    private List<String> participant;


    /**
     * Constructor
     */
    public Meeting(long id, Date debut, Date fin, Room room, String sujet,
                   List<String> participant) {
        this.id = id;
        this.debut = debut;
        this.fin = fin;
        this.mRoom = room;
        this.sujet = sujet;
        this.participant = participant;
    }

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

    public Date getFin() {
        return fin;
    }

    public void setFin(Date fin) {
        this.fin = fin;
    }

    public Room getRoom() {
        return mRoom;
    }

    public void setRoom(Room room) {
        this.mRoom = room;
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
