package com.khamvongsa.victor.mareunion.service;


import com.khamvongsa.victor.mareunion.model.ExampleMeeting;
import com.khamvongsa.victor.mareunion.model.ExampleRoom;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class FakeMeetingApiService implements MeetingApiService {

    private final List<ExampleMeeting> meetings = FakeMeeting.generateExempleReunions();
    private final List<ExampleRoom> rooms = FakeRoom.getSallesDisponibles();

    // Ces deux méthodes servent à protéger les données dans la partie service
    @Override
    public List<ExampleRoom> getRooms() {
        return new ArrayList<>(rooms) ;
    }

    @Override
    public List<ExampleMeeting> getMeetings() { return new ArrayList<>(meetings) ; }

    @Override
    public List<ExampleMeeting> getMeetingsByRooms(String roomChosen) {
        List<ExampleMeeting> mListByRoom = new ArrayList<>();
        for (int i = 0; i < meetings.size(); i++) {
            ExampleMeeting meeting = meetings.get(i);
            if (roomChosen.equalsIgnoreCase(meeting.getRoom().getName())) {
                mListByRoom.add(meeting);
            }
        }
        return mListByRoom;
    }

    @Override
    public List<ExampleMeeting> getMeetingsByDate(Calendar chosenDate) {
        List<ExampleMeeting> mListByDate = new ArrayList<>();

        for (int i = 0; i < meetings.size(); i++) {
            ExampleMeeting meetingDate = meetings.get(i);
            final Calendar instance = Calendar.getInstance();
            instance.setTime(meetingDate.getStartDate());
            if (chosenDate.get(Calendar.DAY_OF_MONTH) == instance.get(Calendar.DAY_OF_MONTH)
            && chosenDate.get(Calendar.YEAR) == instance.get(Calendar.YEAR)
            && chosenDate.get(Calendar.MONTH) == instance.get(Calendar.MONTH)
            ) {
                mListByDate.add(meetingDate);
            }
        }
        return mListByDate;
    }

    @Override
    public List<String> filterAvailableRooms(List<ExampleMeeting> meetings, List<ExampleRoom> rooms, Calendar startDate, Calendar startHour, Calendar endHour) {
        List<String> listRooms = new ArrayList<>();
        int mStartHourChosen = startHour.get(Calendar.HOUR_OF_DAY);
        int mStartMinuteChosen = startHour.get(Calendar.MINUTE);
        int mEndHourChosen = endHour.get(Calendar.HOUR_OF_DAY);
        int mEndMinuteChosen = endHour.get(Calendar.MINUTE);
        final Calendar cldr = Calendar.getInstance();
        int todayHour = cldr.get(Calendar.HOUR_OF_DAY);
        int todayMinutes = cldr.get(Calendar.MINUTE);

        // Si l'heure de départ est inférieure à l'heure actuelle.
        if ((sameDate(startDate, cldr) && mStartHourChosen < todayHour)
                || (sameDate(startDate, cldr) && (mStartHourChosen == todayHour && mStartMinuteChosen < todayMinutes))) { return listRooms; }
        // Si l'heure de départ dépasse l'heure de fin de réunion, retourne une liste vide
        else if (mStartHourChosen > mEndHourChosen || (mStartHourChosen == mEndHourChosen && mStartMinuteChosen > mEndMinuteChosen)) { return listRooms; }

        for (int i = 0; i < rooms.size(); i++) {
            String room = rooms.get(i).getName();
            listRooms.add(room);
            for (int y = 0; y < meetings.size(); y++) {
                ExampleMeeting meeting = meetings.get(y);
                final Calendar date = Calendar.getInstance();
                final Calendar mStart = Calendar.getInstance();
                final Calendar mEnd = Calendar.getInstance();
                date.setTime(meeting.getStartDate());
                mStart.setTime(meeting.getStartHour());
                mEnd.setTime(meeting.getEndHour());

                // Même heure, mais la réunion choisie commence en même temps ou pendant l'heure d'une réunion déjà présente
                if (sameRoomAndDate(meeting, room, startDate, date) && sameHourButStartDuringMeeting(mStart,mEnd,mStartHourChosen,mEndHourChosen,mStartMinuteChosen)) {
                    listRooms.remove(room); }
                // Même heure de départ et de fin, mais la réunion choisie commence avant, mais déborde sur la réunion déjà présente.
                else if (sameRoomAndDate(meeting, room, startDate, date) && sameStartHourAndEndHourButStartBefore(mStart, mEnd, mStartHourChosen, mEndHourChosen, mStartMinuteChosen, mEndMinuteChosen)) {
                    listRooms.remove(room); }
                // Si la réunion choisie commence avant mais qu'elle déborde sur la réunion déjà présente
                else if (sameRoomAndDate(meeting, room, startDate, date) && startBeforeButOverflowMeeting( mStart,  mEnd,  mStartHourChosen,  mEndHourChosen,  mEndMinuteChosen )) {
                    listRooms.remove(room); }
                // Si la réunion choisie commence pendant une réunion déjà présente à la même heure et minute de départ
                else if (sameRoomAndDate(meeting, room, startDate, date) && sameHourAndStartDuringMeeting( mStart, mEnd, mStartHourChosen, mStartMinuteChosen)) {
                    listRooms.remove(room); }
                // Si la réunion commence pendant une réunion déjà présente à une heure de départ différente et inférieure à l'heure de fin
                else if (sameRoomAndDate(meeting, room, startDate, date) && differentHourAndStartDuringMeeting(mStart, mEnd, mStartHourChosen)) {
                    listRooms.remove(room); }
                // Si la réunion commence pendant une réunion déjà présente à une heure de départ différente, mais la même heure de fin mais minute inférieure
                else if (sameRoomAndDate(meeting, room, startDate, date) && differentStartHourAndStartDuringMeeting(mStart, mEnd, mStartHourChosen, mStartMinuteChosen, mEndHourChosen)) {
                    listRooms.remove(room); }
                // Si la réunion choisie commence avant une réunion déjà présente et finit après
                else if (sameRoomAndDate(meeting, room, startDate, date) && startBeforeAndEndAfterMeeting(mStart,  mEnd, mStartHourChosen, mEndHourChosen)) {
                    listRooms.remove(room); }
                // Si la réunion choisie commence avant une réunion déjà présente et finit à la même heure
                else if (sameRoomAndDate(meeting, room, startDate, date) && startBeforeAndSameEndHour(mStart, mEnd, mStartHourChosen, mEndHourChosen)) {
                    listRooms.remove(room); }
                // Si la réunion choisie commence avant une réunion déjà présente et finit pendant une réunion présente
                else if (sameRoomAndDate(meeting, room, startDate, date) && startBeforeAndEndDuringMeeting(mStart, mEnd, mStartHourChosen, mEndHourChosen)) {
                    listRooms.remove(room);
                }
            }
        }
        return listRooms;
    }

    public boolean sameRoomAndDate(ExampleMeeting meeting, String room, Calendar startDate, Calendar date ){
        return (room.equalsIgnoreCase(meeting.getRoom().getName())
                && startDate.get(Calendar.DAY_OF_MONTH) == date.get(Calendar.DAY_OF_MONTH)
                && startDate.get(Calendar.MONTH) == date.get(Calendar.MONTH)
                && startDate.get(Calendar.YEAR) == date.get(Calendar.YEAR));
    }

    public boolean sameDate(Calendar startDate, Calendar date ){
        return (startDate.get(Calendar.DAY_OF_MONTH) == date.get(Calendar.DAY_OF_MONTH)
                && startDate.get(Calendar.MONTH) == date.get(Calendar.MONTH)
                && startDate.get(Calendar.YEAR) == date.get(Calendar.YEAR));
    }

    public boolean sameHourButStartDuringMeeting(Calendar mStart, Calendar mEnd, int mStartHourChosen, int mEndHourChosen, int mStartMinuteChosen){
        return (mStartHourChosen == mStart.get(Calendar.HOUR_OF_DAY)
                && mStartHourChosen == mEnd.get(Calendar.HOUR_OF_DAY)
                && mEndHourChosen == mStart.get(Calendar.HOUR_OF_DAY)
                && mEndHourChosen == mEnd.get(Calendar.HOUR_OF_DAY)
                && mStartMinuteChosen >= mStart.get(Calendar.MINUTE)
                && mStartMinuteChosen < mEnd.get(Calendar.MINUTE));
    }

    public boolean sameStartHourAndEndHourButStartBefore(Calendar mStart, Calendar mEnd, int mStartHourChosen, int mEndHourChosen, int mStartMinuteChosen, int mEndMinuteChosen){
        return (mStartHourChosen == mStart.get(Calendar.HOUR_OF_DAY)
                && mStartHourChosen == mEnd.get(Calendar.HOUR_OF_DAY)
                && mEndHourChosen == mStart.get(Calendar.HOUR_OF_DAY)
                && mEndHourChosen == mEnd.get(Calendar.HOUR_OF_DAY)
                && mStartMinuteChosen < mEnd.get(Calendar.MINUTE)
                && mStartMinuteChosen < mStart.get(Calendar.MINUTE)
                && mEndMinuteChosen > mStart.get(Calendar.MINUTE));
    }

    public boolean startBeforeButOverflowMeeting(Calendar mStart, Calendar mEnd, int mStartHourChosen, int mEndHourChosen, int mEndMinuteChosen){
        return (mStartHourChosen < mStart.get(Calendar.HOUR_OF_DAY)
                && mEndHourChosen >= mStart.get(Calendar.HOUR_OF_DAY)
                && mEndMinuteChosen > mStart.get(Calendar.MINUTE));
    }

    public boolean sameHourAndStartDuringMeeting(Calendar mStart, Calendar mEnd, int mStartHourChosen, int mStartMinuteChosen){
        return (mStartHourChosen == mStart.get(Calendar.HOUR_OF_DAY)
                && mStartHourChosen < mEnd.get(Calendar.HOUR_OF_DAY)
                && mStartMinuteChosen >= mStart.get(Calendar.MINUTE));
    }

    public boolean differentHourAndStartDuringMeeting(Calendar mStart, Calendar mEnd, int mStartHourChosen){
        return (mStartHourChosen > mStart.get(Calendar.HOUR_OF_DAY)
                && mStartHourChosen < mEnd.get(Calendar.HOUR_OF_DAY));
    }

    public boolean differentStartHourAndStartDuringMeeting(Calendar mStart, Calendar mEnd, int mStartHourChosen, int mStartMinuteChosen, int mEndHourChosen){
        return (mStartHourChosen > mStart.get(Calendar.HOUR_OF_DAY)
                && mStartHourChosen == mEnd.get(Calendar.HOUR_OF_DAY)
                && mEndHourChosen == mEnd.get(Calendar.HOUR_OF_DAY)
                && mStartMinuteChosen < mEnd.get(Calendar.MINUTE));
    }

    public boolean startBeforeAndEndAfterMeeting(Calendar mStart, Calendar mEnd, int mStartHourChosen, int mEndHourChosen){
        return (mStartHourChosen < mStart.get(Calendar.HOUR_OF_DAY)
                && mEndHourChosen > mEnd.get(Calendar.HOUR_OF_DAY));
    }

    public boolean startBeforeAndSameEndHour(Calendar mStart, Calendar mEnd, int mStartHourChosen, int mEndHourChosen){
        return (mStartHourChosen < mStart.get(Calendar.HOUR_OF_DAY)
                && mEndHourChosen == mEnd.get(Calendar.HOUR_OF_DAY));
    }

    public boolean startBeforeAndEndDuringMeeting(Calendar mStart, Calendar mEnd, int mStartHourChosen, int mEndHourChosen){
        return (mStartHourChosen < mStart.get(Calendar.HOUR_OF_DAY)
                && mEndHourChosen > mStart.get(Calendar.HOUR_OF_DAY)
                && mEndHourChosen < mEnd.get(Calendar.HOUR_OF_DAY));
    }

    @Override
    public void createMeeting(ExampleMeeting meeting) {
        meetings.add(meeting);
    }

    @Override
    public void deleteMeeting(ExampleMeeting meeting) {
         meetings.remove(meeting);
    }
}