package com.khamvongsa.victor.mareunion.controller;

import java.util.Comparator;
import java.util.Date;
import java.util.List;


public class ExampleMeeting {
    private long id;

    private Date debut;

    private Date startHour;

    private Date endHour;

    private ExampleRoom room;

    private String subject;

    private List<String> participant;


    /**
     * Constructor
     */
    public ExampleMeeting(long id, Date debut, Date startHour, Date endHour, ExampleRoom room, String subject, List<String> participant) {
        this.id = id;
        this.debut = debut;
        this.startHour = startHour;
        this.endHour = endHour;
        this.room = room;
        this.subject = subject;
        this.participant = participant;
    }

    public static Comparator<ExampleMeeting> MeetingRoomNameComparator = new Comparator<ExampleMeeting>() {
        @Override
        public int compare(ExampleMeeting r1, ExampleMeeting r2) {

            return r1.getRoom().getNom().compareTo(r2.getRoom().getNom());
        }
    };

    public static Comparator<ExampleMeeting> MeetingDateComparator = new Comparator<ExampleMeeting>() {
        @Override
        public int compare(ExampleMeeting r1, ExampleMeeting r2) {
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

    public Date getStartHour() {
        return startHour;
    }

    public void setStartHour(Date startHour) {
        this.startHour = startHour;
    }

    public Date getEndHour() {
        return endHour;
    }

    public void setEndHour(Date endHour) {
        this.endHour = endHour;
    }

    public ExampleRoom getRoom() {
        return room;
    }

    public void setRoom(ExampleRoom room) {
        this.room = room;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public List<String> getParticipant() {
        return participant;
    }

    public void setParticipant(List<String> participant) {
        this.participant = participant;
    }
}
