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

        // Si l'heure de départ dépasse l'heure de fin de réunion, retourne une liste vide
        if (mStartHourChosen > mEndHourChosen || (mStartHourChosen == mEndHourChosen && mStartMinuteChosen > mEndMinuteChosen)) {
            return listRooms;
        }

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
                if (sameRoomAndDate(meeting, room, startDate, date)
                        && mStartHourChosen == mStart.get(Calendar.HOUR_OF_DAY)
                        && mStartHourChosen == mEnd.get(Calendar.HOUR_OF_DAY)
                        && mEndHourChosen == mStart.get(Calendar.HOUR_OF_DAY)
                        && mEndHourChosen == mEnd.get(Calendar.HOUR_OF_DAY)
                        && mStartMinuteChosen >= mStart.get(Calendar.MINUTE)
                        && mStartMinuteChosen < mEnd.get(Calendar.MINUTE)) {
                    listRooms.remove(room); }

                // Même heure de départ et de fin, mais la réunion choisie commence avant, mais déborde sur la réunion déjà présente.
                else if (sameRoomAndDate(meeting, room, startDate, date)
                        && mStartHourChosen == mStart.get(Calendar.HOUR_OF_DAY)
                        && mStartHourChosen == mEnd.get(Calendar.HOUR_OF_DAY)
                        && mEndHourChosen == mStart.get(Calendar.HOUR_OF_DAY)
                        && mEndHourChosen == mEnd.get(Calendar.HOUR_OF_DAY)
                        && mStartMinuteChosen < mEnd.get(Calendar.MINUTE)
                        && mStartMinuteChosen < mStart.get(Calendar.MINUTE)
                        && mEndMinuteChosen > mStart.get(Calendar.MINUTE)) {
                    listRooms.remove(room);
                }

                // Si la réunion choisie commence avant mais qu'elle déborde sur la réunion déjà présente
                else if (sameRoomAndDate(meeting, room, startDate, date)
                        && mStartHourChosen < mStart.get(Calendar.HOUR_OF_DAY)
                        && mEndHourChosen >= mStart.get(Calendar.HOUR_OF_DAY)
                        && mEndMinuteChosen > mStart.get(Calendar.MINUTE)) {
                    listRooms.remove(room); }

                // Si la réunion choisie commence pendant une réunion déjà présente à la même heure de départ
                else if (sameRoomAndDate(meeting, room, startDate, date)
                        && mStartHourChosen == mStart.get(Calendar.HOUR_OF_DAY)
                        && mStartHourChosen < mEnd.get(Calendar.HOUR_OF_DAY)
                        && mStartMinuteChosen >= mStart.get(Calendar.MINUTE)) {
                    listRooms.remove(room); }

                // Si la réunion commence pendant une réunion déjà présente à une heure de départ différente et inférieure à l'heure de fin
                else if (sameRoomAndDate(meeting, room, startDate, date)
                        && mStartHourChosen > mStart.get(Calendar.HOUR_OF_DAY)
                        && mStartHourChosen < mEnd.get(Calendar.HOUR_OF_DAY)) {
                    listRooms.remove(room);
                }

                // Si la réunion commence pendant une réunion déjà présente à une heure de départ différente, mais la même heure de fin
                else if (sameRoomAndDate(meeting, room, startDate, date)
                        && mStartHourChosen > mStart.get(Calendar.HOUR_OF_DAY)
                        && mStartHourChosen == mEnd.get(Calendar.HOUR_OF_DAY)
                        && mEndHourChosen == mEnd.get(Calendar.HOUR_OF_DAY)
                        && mStartMinuteChosen < mEnd.get(Calendar.MINUTE)) {
                    listRooms.remove(room);
                }

                // Si la réunion choisie commence avant une réunion déjà présente et finit après
                else if (sameRoomAndDate(meeting, room, startDate, date)
                        && mStartHourChosen < mStart.get(Calendar.HOUR_OF_DAY)
                        && mEndHourChosen > mEnd.get(Calendar.HOUR_OF_DAY)) {
                    listRooms.remove(room);
                }

                // Si la réunion choisie commence avant une réunion déjà présente et finit à la même heure, mais plus tard
                else if (sameRoomAndDate(meeting, room, startDate, date)
                        && mStartHourChosen < mStart.get(Calendar.HOUR_OF_DAY)
                        && mEndHourChosen == mEnd.get(Calendar.HOUR_OF_DAY)) {
                    listRooms.remove(room);
                }

                // Si la réunion choisie commence avant une réunion déjà présente et finit après
                else if (sameRoomAndDate(meeting, room, startDate, date)
                        && mStartHourChosen < mStart.get(Calendar.HOUR_OF_DAY)
                        && mEndHourChosen > mStart.get(Calendar.HOUR_OF_DAY)
                        && mEndHourChosen < mEnd.get(Calendar.HOUR_OF_DAY)) {
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

    @Override
    public void createMeeting(ExampleMeeting meeting) {
        meetings.add(meeting);
    }

    @Override
    public void deleteMeeting(ExampleMeeting meeting) {
         meetings.remove(meeting);
    }


}
