package com.khamvongsa.victor.mareunion.service;

public interface CalculTimeService {

    int calculMinuteLeft(int totalMinute, int totalHour, int startMinute, int endMinute);

    int calculHourLeft(int totalMinute, int totalHour, int startMinute, int endMinute);
}
