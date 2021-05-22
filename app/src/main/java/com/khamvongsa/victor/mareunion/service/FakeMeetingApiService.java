package com.khamvongsa.victor.mareunion.service;

import android.util.Log;

import com.khamvongsa.victor.mareunion.controller.ExampleMeeting;
import com.khamvongsa.victor.mareunion.controller.ExampleRoom;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class FakeMeetingApiService implements MeetingApiService {

    private final List<ExampleMeeting> meetings = FakeMeeting.generateExempleReunions();
    private final List<ExampleRoom> rooms = FakeRoom.getSallesDisponibles();
    private final static String TAG = FakeMeetingApiService.class.getSimpleName();


    @Override
    public List<ExampleRoom> getRooms() {
        return new ArrayList<>(rooms) ;
    }

    @Override
    public List<ExampleMeeting> getMeetings() {
        return new ArrayList<>(meetings) ;
        // pour protéger les données dans la partie service
    }

    @Override
    public List<ExampleMeeting> getMeetingsByRooms(String roomChosen) {
        List<ExampleMeeting> mListByRoom = new ArrayList<>();
        for (int i = 0; i < meetings.size(); i++) {
            ExampleMeeting meeting = meetings.get(i);
            if (roomChosen.equalsIgnoreCase(meeting.getRoom().getNom())) {
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
            instance.setTime(meetingDate.getDebut());
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
    public List<String> filterAvailableRooms(List<ExampleMeeting> reunions, List<ExampleRoom> rooms, Calendar startDate, Calendar startHour, Calendar endHour) {
        List<String> listRooms = new ArrayList<>();
        int mStartHourChosen = startHour.get(Calendar.HOUR_OF_DAY);
        int mStartMinuteChosen = startHour.get(Calendar.MINUTE);
        int mEndHourChosen = endHour.get(Calendar.HOUR_OF_DAY);
        int mEndMinuteChosen = endHour.get(Calendar.MINUTE);

        /*
        Log.d(TAG, "############################################");
        Log.d(TAG, "Information about my choosen date:");
        Log.d(TAG, "YEAR: " + startDate.get(Calendar.YEAR));
        Log.d(TAG, "MONTH: " + startDate.get(Calendar.MONTH));
        Log.d(TAG, "DAY: " + startDate.get(Calendar.DAY_OF_MONTH));
        Log.d(TAG, "HOUR début: " + mStartHourChosen);
        Log.d(TAG, "MINUTE début: " + mStartMinuteChosen);
        Log.d(TAG, "HOUR fin: " + mEndHourChosen);
        Log.d(TAG, "MINUTE fin: " + mEndMinuteChosen);
        Log.d(TAG, "############################################");
        */
        for (int i = 0; i < rooms.size(); i++) {
            String salle = rooms.get(i).getNom();
//            Log.d(TAG, "ADD " + salle + " into list !!!!");
            listRooms.add(salle);

            for (int y = 0; y < reunions.size(); y++) {

                ExampleMeeting reunion = reunions.get(y);
                final Calendar date = Calendar.getInstance();
                final Calendar mStart = Calendar.getInstance();
                final Calendar mEnd = Calendar.getInstance();
                date.setTime(reunion.getDebut());
                mStart.setTime(reunion.getStartHour());
                mEnd.setTime(reunion.getEndHour());

                // TODO : Vérifier le tri des salles
            /*
                Log.d(TAG, "------------------------------------------");
                Log.d(TAG, "Information about a reunion:");
                Log.d(TAG, "YEAR: " + date.get(Calendar.YEAR));
                Log.d(TAG, "MONTH: " + date.get(Calendar.MONTH));
                Log.d(TAG, "DAY: " + date.get(Calendar.DAY_OF_MONTH));
                Log.d(TAG, "HOUR début: " + mStart.get(Calendar.HOUR_OF_DAY));
                Log.d(TAG, "MINUTE début: " +  mStart.get(Calendar.MINUTE));
                Log.d(TAG, "HOUR fin: " + mEnd.get(Calendar.HOUR_OF_DAY));
                Log.d(TAG, "MINUTE fin: " + mEnd.get(Calendar.MINUTE));
                Log.d(TAG, "------------------------------------------");

                Log.d(TAG, "###########################################");
                Log.d(TAG, "salle : "+ salle +" est égale à : "+ reunion.getSalle().getNom()+". RESULTAT : " + salle.equalsIgnoreCase(reunion.getSalle().getNom()));
                Log.d(TAG, "Jour choisi : "+ startDate.get(Calendar.DAY_OF_MONTH) +" est égale au jour de la réunion : "+ date.get(Calendar.DAY_OF_MONTH)+" -- RESULTAT : " + (startDate.get(Calendar.DAY_OF_MONTH) == date.get(Calendar.DAY_OF_MONTH)));
                Log.d(TAG, "Mois choisi : "+ startDate.get(Calendar.MONTH) +" est égale au mois de la réunion : "+ date.get(Calendar.MONTH)+" -- RESULTAT : " + (startDate.get(Calendar.MONTH) == date.get(Calendar.MONTH)));
                Log.d(TAG, "Année choisie : "+ startDate.get(Calendar.YEAR) +" est égale à l'année de réunion : "+ date.get(Calendar.YEAR)+". RESULTAT : " + (startDate.get(Calendar.YEAR) == date.get(Calendar.YEAR)));
                Log.d(TAG, "Heure de départ choisie : "+ mStartHourChosen +" est supérieure ou égale à l'heure de départ de la réunion : "+ mStart.get(Calendar.HOUR_OF_DAY) +" -- RESULTAT : " + (mStartHourChosen >= mStart.get(Calendar.HOUR_OF_DAY)));
                Log.d(TAG, "Heure de départ choisie : "+ mStartHourChosen +" est inférieure à l'heure de fin de la réunion : "+ mEnd.get(Calendar.HOUR_OF_DAY) +" -- RESULTAT : " + (mStartHourChosen < mEnd.get(Calendar.HOUR_OF_DAY)));
                Log.d(TAG, "Heure de fin choisie : "+ mEndHourChosen +" est supérieure à l'heure de départ de la réunion: "+ mStart.get(Calendar.HOUR_OF_DAY)+" -- RESULTAT : " + (mEndHourChosen > mStart.get(Calendar.HOUR_OF_DAY)));
                Log.d(TAG, "Heure de fin choisie : "+ mEndHourChosen +" est inférieure ou égale à l'heure de fin de la réunion: "+ mEnd.get(Calendar.HOUR_OF_DAY) +" -- RESULTAT : " + (mEndHourChosen <= mEnd.get(Calendar.HOUR_OF_DAY)));
                Log.d(TAG, "Minute de départ choisie : "+ mStartMinuteChosen +" est est supérieure ou égale à la minute de départ de la réunion : "+ mStart.get(Calendar.MINUTE) +" -- RESULTAT : " + (mStartMinuteChosen >= mStart.get(Calendar.MINUTE)));
                Log.d(TAG, "Minute de départ choisie : "+ mStartMinuteChosen +" est inférieure à la minute de fin de la réunion : "+ mEnd.get(Calendar.MINUTE) +" -- RESULTAT : " + (mStartMinuteChosen < mEnd.get(Calendar.MINUTE)));
                Log.d(TAG, "Minute de fin choisie : "+ mEndMinuteChosen +" est supérieure à la minute de départ de la réunion : "+ mStart.get(Calendar.MINUTE) +" -- RESULTAT : " + (mEndMinuteChosen > mStart.get(Calendar.MINUTE)));
                Log.d(TAG, "Minute de fin choisie : "+ mEndMinuteChosen +" est supérieure à la minute de fin de la réunion : "+ mEnd.get(Calendar.MINUTE) +" -- RESULTAT : " + (mEndMinuteChosen <=  mEnd.get(Calendar.MINUTE)));
                Log.d(TAG, "###########################################");
                */
                // Même heure, mais la réunion choisie commence en même temps ou pendant l'heure d'une réunion déjà présente
                if (salle.equalsIgnoreCase(reunion.getRoom().getNom())
                        && startDate.get(Calendar.DAY_OF_MONTH) == date.get(Calendar.DAY_OF_MONTH)
                        && startDate.get(Calendar.MONTH) == date.get(Calendar.MONTH)
                        && startDate.get(Calendar.YEAR) == date.get(Calendar.YEAR)
                        && mStartHourChosen == mStart.get(Calendar.HOUR_OF_DAY)
                        && mStartHourChosen == mEnd.get(Calendar.HOUR_OF_DAY)
                        && mEndHourChosen == mStart.get(Calendar.HOUR_OF_DAY)
                        && mEndHourChosen == mEnd.get(Calendar.HOUR_OF_DAY)
                        && mStartMinuteChosen >= mStart.get(Calendar.MINUTE)
                        && mStartMinuteChosen < mEnd.get(Calendar.MINUTE)) {
                    //Log.d(TAG, "REMOVE " + salle + " from list !!!!");
                    listRooms.remove(salle); }

                // Même heure de départ et de fin, mais la réunion choisie commence avant, mais déborde sur la réunion déjà présente.
                else if (salle.equalsIgnoreCase(reunion.getRoom().getNom())
                        && startDate.get(Calendar.DAY_OF_MONTH) == date.get(Calendar.DAY_OF_MONTH)
                        && startDate.get(Calendar.MONTH) == date.get(Calendar.MONTH)
                        && startDate.get(Calendar.YEAR) == date.get(Calendar.YEAR)
                        && mStartHourChosen == mStart.get(Calendar.HOUR_OF_DAY)
                        && mStartHourChosen == mEnd.get(Calendar.HOUR_OF_DAY)
                        && mEndHourChosen == mStart.get(Calendar.HOUR_OF_DAY)
                        && mEndHourChosen == mEnd.get(Calendar.HOUR_OF_DAY)
                        && mStartMinuteChosen < mEnd.get(Calendar.MINUTE)
                        && mStartMinuteChosen < mStart.get(Calendar.MINUTE)
                        && mEndMinuteChosen > mStart.get(Calendar.MINUTE)) {
                    //Log.d(TAG, "REMOVE " + salle + " from list !!!!");
                    listRooms.remove(salle);
                }

                // Si la réunion choisie commence avant mais qu'elle déborde sur la réunion déjà présente
                else if (salle.equalsIgnoreCase(reunion.getRoom().getNom())
                        && startDate.get(Calendar.DAY_OF_MONTH) == date.get(Calendar.DAY_OF_MONTH)
                        && startDate.get(Calendar.MONTH) == date.get(Calendar.MONTH)
                        && startDate.get(Calendar.YEAR) == date.get(Calendar.YEAR)
                        && mStartHourChosen < mStart.get(Calendar.HOUR_OF_DAY)
                        && mEndHourChosen >= mStart.get(Calendar.HOUR_OF_DAY)
                        && mEndMinuteChosen > mStart.get(Calendar.MINUTE)) {
                    //Log.d(TAG, "REMOVE " + salle + " from list !!!!");
                    listRooms.remove(salle); }

                // Si la réunion choisie commence pendant une réunion déjà présente à la même heure de départ
                else if (salle.equalsIgnoreCase(reunion.getRoom().getNom())
                        && startDate.get(Calendar.DAY_OF_MONTH) == date.get(Calendar.DAY_OF_MONTH)
                        && startDate.get(Calendar.MONTH) == date.get(Calendar.MONTH)
                        && startDate.get(Calendar.YEAR) == date.get(Calendar.YEAR)
                        && mStartHourChosen == mStart.get(Calendar.HOUR_OF_DAY)
                        && mStartHourChosen < mEnd.get(Calendar.HOUR_OF_DAY)
                        && mStartMinuteChosen >= mStart.get(Calendar.MINUTE)) {
//                    Log.d(TAG, "REMOVE " + salle + " from list !!!!");
                    listRooms.remove(salle); }

                // Si la réunion commence pendant une réunion déjà présente à une heure de départ différente et inférieure à l'heure de fin
                else if (salle.equalsIgnoreCase(reunion.getRoom().getNom())
                        && startDate.get(Calendar.DAY_OF_MONTH) == date.get(Calendar.DAY_OF_MONTH)
                        && startDate.get(Calendar.MONTH) == date.get(Calendar.MONTH)
                        && startDate.get(Calendar.YEAR) == date.get(Calendar.YEAR)
                        && mStartHourChosen > mStart.get(Calendar.HOUR_OF_DAY)
                        && mStartHourChosen < mEnd.get(Calendar.HOUR_OF_DAY)) {
   //                 Log.d(TAG, "REMOVE " + salle + " from list !!!!");
                    listRooms.remove(salle);
                }

                // Si la réunion commence pendant une réunion déjà présente à une heure de départ différente, mais la même heure de fin
                else if (salle.equalsIgnoreCase(reunion.getRoom().getNom())
                        && startDate.get(Calendar.DAY_OF_MONTH) == date.get(Calendar.DAY_OF_MONTH)
                        && startDate.get(Calendar.MONTH) == date.get(Calendar.MONTH)
                        && startDate.get(Calendar.YEAR) == date.get(Calendar.YEAR)
                        && mStartHourChosen > mStart.get(Calendar.HOUR_OF_DAY)
                        && mStartHourChosen == mEnd.get(Calendar.HOUR_OF_DAY)
                        && mEndHourChosen == mEnd.get(Calendar.HOUR_OF_DAY)
                        && mStartMinuteChosen < mEnd.get(Calendar.MINUTE)) {
                    //Log.d(TAG, "REMOVE " + salle + " from list !!!!");
                    listRooms.remove(salle);
                }

                // Si la réunion choisie commence avant une réunion déjà présente et finit après
                else if (salle.equalsIgnoreCase(reunion.getRoom().getNom())
                        && startDate.get(Calendar.DAY_OF_MONTH) == date.get(Calendar.DAY_OF_MONTH)
                        && startDate.get(Calendar.MONTH) == date.get(Calendar.MONTH)
                        && startDate.get(Calendar.YEAR) == date.get(Calendar.YEAR)
                        && mStartHourChosen < mStart.get(Calendar.HOUR_OF_DAY)
                        && mEndHourChosen > mEnd.get(Calendar.HOUR_OF_DAY)) {
  //                  Log.d(TAG, "REMOVE " + salle + " from list !!!!");
                    listRooms.remove(salle);
                }

                // Si la réunion choisie commence avant une réunion déjà présente et finit à la même heure, mais plus tard
                else if (salle.equalsIgnoreCase(reunion.getRoom().getNom())
                        && startDate.get(Calendar.DAY_OF_MONTH) == date.get(Calendar.DAY_OF_MONTH)
                        && startDate.get(Calendar.MONTH) == date.get(Calendar.MONTH)
                        && startDate.get(Calendar.YEAR) == date.get(Calendar.YEAR)
                        && mStartHourChosen < mStart.get(Calendar.HOUR_OF_DAY)
                        && mEndHourChosen == mEnd.get(Calendar.HOUR_OF_DAY)) {
   //                 Log.d(TAG, "REMOVE " + salle + " from list !!!!");
                    listRooms.remove(salle);
                }

                // Si la réunion choisie commence avant une réunion déjà présente et finit après
                else if (salle.equalsIgnoreCase(reunion.getRoom().getNom())
                        && startDate.get(Calendar.DAY_OF_MONTH) == date.get(Calendar.DAY_OF_MONTH)
                        && startDate.get(Calendar.MONTH) == date.get(Calendar.MONTH)
                        && startDate.get(Calendar.YEAR) == date.get(Calendar.YEAR)
                        && mStartHourChosen < mStart.get(Calendar.HOUR_OF_DAY)
                        && mEndHourChosen > mStart.get(Calendar.HOUR_OF_DAY)
                        && mEndHourChosen < mEnd.get(Calendar.HOUR_OF_DAY)) {
    //                Log.d(TAG, "REMOVE " + salle + " from list !!!!");
                    listRooms.remove(salle);
                }
            }
        }
        return listRooms;
    }

    @Override
    public void createMeeting(ExampleMeeting reunion) {
        meetings.add(reunion);
    }

    @Override
    public void deleteMeeting(ExampleMeeting reunion) {
         meetings.remove(reunion);
    }


}
