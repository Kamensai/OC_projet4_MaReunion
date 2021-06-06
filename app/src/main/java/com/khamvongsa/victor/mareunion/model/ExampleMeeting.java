package com.khamvongsa.victor.mareunion.model;

import java.util.Comparator;
import java.util.Date;
import java.util.List;


public class ExampleMeeting {
    private long id;

    private Date startDate;

    private Date startHour;

    private Date endHour;

    private ExampleRoom room;

    private String subject;

    private List<String> participant;


    /**
     * Constructor
     */
    public ExampleMeeting(long id, Date startDate, Date startHour, Date endHour, ExampleRoom room, String subject, List<String> participant) {
        this.id = id;
        this.startDate = startDate;
        this.startHour = startHour;
        this.endHour = endHour;
        this.room = room;
        this.subject = subject;
        this.participant = participant;
    }

    public final static Comparator<ExampleMeeting> MeetingDateComparator = (r1, r2) -> r1.getStartDate().compareTo(r2.getStartDate());

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
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
