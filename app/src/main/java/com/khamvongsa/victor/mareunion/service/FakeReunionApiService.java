package com.khamvongsa.victor.mareunion.service;

import com.khamvongsa.victor.mareunion.controller.ExempleReunion;
import com.khamvongsa.victor.mareunion.controller.ExempleSalle;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class FakeReunionApiService implements ReunionApiService {

    private final List<ExempleReunion> reunions = FakeReunion.generateExempleReunions();
    private final List<ExempleSalle> salles = FakeSalle.getSallesDisponibles();

    @Override
    public List<ExempleSalle> getSalles() {
        return new ArrayList<>(salles) ;
    }

    @Override
    public List<ExempleReunion> getReunions() {
        return new ArrayList<>(reunions) ;
        // pour protéger les données dans la partie service
    }

    @Override
    public List<ExempleReunion> getReunionsByRooms(String salleChoisie) {
        List<ExempleReunion> mListBySalle = new ArrayList<>();
        for (int i = 0; i < reunions.size(); i++) {
            ExempleReunion reunionSalle = reunions.get(i);
            if (salleChoisie.equalsIgnoreCase(reunionSalle.getSalle().getNom())) {
                mListBySalle.add(reunionSalle);
            }
        }
        return mListBySalle;
    }

    @Override
    public List<ExempleReunion> getReunionsByDate(Calendar dateChoisie) {
        List<ExempleReunion> mListByDate = new ArrayList<>();

        for (int i = 0; i < reunions.size(); i++) {
            ExempleReunion reunionDate = reunions.get(i);
            final Calendar instance = Calendar.getInstance();
            instance.setTime(reunionDate.getDebut());
            if (dateChoisie.get(Calendar.DAY_OF_MONTH) == instance.get(Calendar.DAY_OF_MONTH)
            && dateChoisie.get(Calendar.YEAR) == instance.get(Calendar.YEAR)
            && dateChoisie.get(Calendar.MONTH) == instance.get(Calendar.MONTH)
            ) {
                mListByDate.add(reunionDate);
            }
        }
        return mListByDate;
    }

    @Override
    public List<String> FilterAvailableRooms(List<ExempleReunion> reunions, List<ExempleSalle> rooms, Calendar startDate, Calendar startHour, Calendar endHour) {
        List<String> listRooms = new ArrayList<>();
        int mStartHourChosen = startHour.get(Calendar.HOUR_OF_DAY);
        int mStartMinuteChosen = startHour.get(Calendar.MINUTE);
        int mEndHourChosen = endHour.get(Calendar.HOUR_OF_DAY);
        int mEndMinuteChosen = endHour.get(Calendar.MINUTE);

        for (int i = 0; i < rooms.size(); i++) {
            String salle = rooms.get(i).getNom();
            listRooms.add(salle);

            for (int y = 0; y < reunions.size(); y++) {

                ExempleReunion reunion = reunions.get(y);
                final Calendar date = Calendar.getInstance();
                final Calendar mStart = Calendar.getInstance();
                final Calendar mEnd = Calendar.getInstance();
                date.setTime(reunion.getDebut());
                mStart.setTime(reunion.getStartHour());
                mEnd.setTime(reunion.getEndHour());
                
                // TODO : Vérifier le tri des salles

                if (salle.equalsIgnoreCase(reunion.getSalle().getNom())
                        && startDate.get(Calendar.DAY_OF_MONTH) == date.get(Calendar.DAY_OF_MONTH)
                        && startDate.get(Calendar.MONTH) == date.get(Calendar.MONTH)
                        && startDate.get(Calendar.YEAR) == date.get(Calendar.YEAR)
                        && mStartHourChosen >= mStart.get(Calendar.HOUR_OF_DAY)
                        && mStartHourChosen < mEnd.get(Calendar.HOUR_OF_DAY)
                        && mEndHourChosen > mStart.get(Calendar.HOUR_OF_DAY)
                        && mEndHourChosen <= mEnd.get(Calendar.HOUR_OF_DAY)
                        && mStartMinuteChosen >= mStart.get(Calendar.MINUTE)
                        && mStartMinuteChosen < mEnd.get(Calendar.MINUTE)
                        && mEndMinuteChosen > mStart.get(Calendar.MINUTE)
                        && mEndMinuteChosen >=  mEnd.get(Calendar.MINUTE)) {
                    listRooms.remove(salle); }
            }
        }
        return listRooms;
    }

    @Override
    public void createReunion(ExempleReunion reunion) {
        reunions.add(reunion);
    }

    @Override
    public void deleteReunion(ExempleReunion reunion) {
         reunions.remove(reunion);
    }


}
