package com.khamvongsa.victor.mareunion.model;


import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AddMeetingViewModel implements Parcelable {
    // Toutes les infos pour l'écran
    private String subject;
    private Calendar startDate;
    private Calendar startHour;
    private Calendar endHour;
    private String writtenParticipant;
    private ExampleRoom room;
    private List<String> listParticipants = new ArrayList<>();

    public AddMeetingViewModel(){ }

    protected AddMeetingViewModel(Parcel in) {
        subject = in.readString();
        if (in.readBoolean()) {
            // Tentative de récupération de startDate
            startDate = Calendar.getInstance();
            startDate.setTimeInMillis(in.readLong());
        }

        if (in.readBoolean()) {
            // Tentative de récupération de startHour
            startHour = Calendar.getInstance();
            startHour.setTimeInMillis(in.readLong());
        }

        if (in.readBoolean()) {
            // Tentative de récupération de endHour
            endHour = Calendar.getInstance();
            endHour.setTimeInMillis(in.readLong());
        }
        writtenParticipant = in.readString();
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

    public boolean isSubjectFilled() {
        return !TextUtils.isEmpty(subject);
    }

    public boolean isStartDateFilled() {
        return startDate != null;
    }

    public boolean isStartHourFilled() { return startHour != null; }

    public boolean isEndHourFilled() { return endHour != null; }

    public boolean isRoomFilled() { return room != null; }

    public boolean isListParticipantsFilled() { return listParticipants.size() > 0; }

    public boolean isAllFieldsFilled() {
        return isSubjectFilled() && isStartDateFilled() && isStartHourFilled() && isEndHourFilled() && isRoomFilled() && isListParticipantsFilled();
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

    public void setWrittenParticipant(String writtenParticipant) { this.writtenParticipant = writtenParticipant; }

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

    public String getWrittenParticipant() { return writtenParticipant; }

    public ExampleRoom getRoom() {
        return room;
    }

    public List<String> getListParticipants() {
        return listParticipants;
    }

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

        if (startHour != null) {
            dest.writeBoolean(true);
            dest.writeLong(startHour.getTimeInMillis());
        }
        else {
            dest.writeBoolean(false);
        }

        if (endHour != null) {
            dest.writeBoolean(true);
            dest.writeLong(endHour.getTimeInMillis());
        }
        else {
            dest.writeBoolean(false);
        }

        dest.writeString(writtenParticipant);
        dest.writeParcelable(room, flags);
        dest.writeStringList(listParticipants);
    }

}


