package com.khamvongsa.victor.mareunion.model;


import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import com.khamvongsa.victor.mareunion.controller.ExampleRoom;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class AddMeetingViewModel  {
    // Toutes les infos pour l'écran
    private String subject;
    private Calendar startDate;
    private Calendar startHour;
    private Calendar endHour;
    private ExampleRoom room;
    private List<String> listParticipants;

    public AddMeetingViewModel(){ }
/*
    protected AddMeetingViewModel(Parcel in) {
        subject = in.readString();
        if (in.readBoolean()) {
            // Tentative de récupération de startDate
            startDate = Calendar.getInstance();
            startDate.setTimeInMillis(in.readLong());
        }
        room = in.readParcelable(ExampleRoom.class.getClassLoader());
        listParticipants = in.createStringArrayList();
    }

    public static final Creator<AddMeetingViewModel> CREATOR = new Creator<AddMeetingViewModel>() {
        @Override
        public AddMeetingViewModel createFromParcel(Parcel in) {
            return new AddMeetingViewModel(in);
        }

        @Override
        public AddMeetingViewModel[] newArray(int size) {
            return new AddMeetingViewModel[size];
        }
    };

 */

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
/*
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(subject);
        if (startDate != null) {
            dest.writeBoolean(true);
            dest.writeLong(startDate.getTimeInMillis());
        }
        else {
            dest.writeBoolean(false);
        }
        dest.writeParcelable(room, flags);
        dest.writeStringList(listParticipants);
    }

 */
}

