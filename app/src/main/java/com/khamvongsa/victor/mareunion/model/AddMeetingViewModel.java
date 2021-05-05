package com.khamvongsa.victor.mareunion.model;


import android.text.TextUtils;

import com.khamvongsa.victor.mareunion.controller.ExampleRoom;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class AddMeetingViewModel {
    // Toutes les infos pour l'écran
    private String subject;
    private Calendar startDate;
    private Calendar startHour;
    private Calendar endHour;
    private ExampleRoom room;
    private List<String> listParticipants;




    public boolean isSubjectFilled() {
        if(!TextUtils.isEmpty(subject)) {
            return true;
        }
        return false;
    }

    public boolean isStartDateFilled() {
        if(startDate != null) {
            return true;
        }
        return false;
    }

    public boolean isStartHourFilled() {
        if(startHour != null) {
            return true;
        }
        return false;
    }

    public boolean isEndHourFilled() {
        if(endHour != null) {
            return true;
        }
        return false;
    }

    public boolean isRoomFilled() {
        if(room != null) {
            return true;
        }
        return false;
    }

    public boolean isListParticipantsFilled() {
        if(listParticipants.size() > 0) {
            return true;
        }
        return false;
    }

    public void setStartDate(Calendar date) {
        this.startDate = date;
    }

    public void setStartHour(Calendar startHour) {
        this.startHour = startHour;
    }

    public void setEndHour(Calendar endHour) {
        this.endHour = endHour;
    }

    public void setRoom(ExampleRoom room) {
        this.room = room;
    }

    public void setParticipants(List<String> participants) {
        this.listParticipants = participants;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getSubject() {
        return subject;
    }

    public Calendar getStartDate() {
        return startDate;
    }

    public Calendar getStartHour() {
        return startHour;
    }

    public Calendar getEndHour() {
        return endHour;
    }

    public ExampleRoom getRoom() {
        return room;
    }

    public List<String> getListParticipants() {
        return listParticipants;
    }
}


