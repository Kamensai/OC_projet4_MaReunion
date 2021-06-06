package com.khamvongsa.victor.mareunion.service;

import com.khamvongsa.victor.mareunion.model.ExampleMeeting;
import com.khamvongsa.victor.mareunion.model.ExampleRoom;

import java.util.Calendar;
import java.util.List;

public interface MeetingApiService {

    List<ExampleRoom> getRooms();

    List<ExampleMeeting> getMeetings();

    void deleteMeeting(ExampleMeeting reunion);

    List<ExampleMeeting> getMeetingsByRooms(String salleChoisie);

    List<ExampleMeeting> getMeetingsByDate(Calendar dateChoisie);

    List<String> filterAvailableRooms(List<ExampleMeeting> reunions, List<ExampleRoom> rooms, Calendar startDate, Calendar startHour, Calendar EndHour);

    void createMeeting(ExampleMeeting reunion);

}
