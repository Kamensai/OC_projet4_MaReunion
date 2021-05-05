package com.khamvongsa.victor.mareunion.service;

import com.khamvongsa.victor.mareunion.controller.ExampleMeeting;
import com.khamvongsa.victor.mareunion.controller.ExampleRoom;

import java.util.Calendar;
import java.util.List;

public interface MeetingApiService {

    List<ExampleRoom> getSalles();

    List<ExampleMeeting> getReunions();

    void deleteReunion(ExampleMeeting reunion);

    List<ExampleMeeting> getReunionsByRooms(String salleChoisie);

    List<ExampleMeeting> getReunionsByDate(Calendar dateChoisie);

    List<String> FilterAvailableRooms(List<ExampleMeeting> reunions, List<ExampleRoom> rooms, Calendar startDate, Calendar startHour, Calendar EndHour);

    void createReunion(ExampleMeeting reunion);

}
