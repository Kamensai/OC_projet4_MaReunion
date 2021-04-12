package com.khamvongsa.victor.mareunion.service;


public class CalculTime implements CalculTimeService{


    @Override
    public int calculMinuteLeft(int totalMinute, int totalHour, int startMinute, int endMinute) {
        int mReunionMinuteTime;

        if (totalMinute >= 60 && totalHour > 1 && endMinute > startMinute) {
            mReunionMinuteTime = totalMinute - 60*totalHour;
            if (mReunionMinuteTime == 60) {
                return mReunionMinuteTime - 60;
            }
            else {
                return mReunionMinuteTime;
            }
        }
        else if (totalMinute >= 60 && totalHour > 1) {
            mReunionMinuteTime = (totalMinute+60) - 60*totalHour;
            if (mReunionMinuteTime == 60) {
                return mReunionMinuteTime - 60;
            }
            else {
                return mReunionMinuteTime;
            }
        }
        else if (totalMinute >= 60) {
            mReunionMinuteTime = totalMinute - 60;
            if (mReunionMinuteTime == 60) {
                return mReunionMinuteTime - 60;
            }
            else {
                return mReunionMinuteTime;
            }
        }
        else {
            return totalMinute;
        }
    }

    @Override
    public int calculHourLeft(int totalMinute, int totalHour, int startMinute, int endMinute) {
        int mReunionHourTime;
        if (totalMinute < 60 && totalHour == 1) {
            mReunionHourTime = totalHour - 1;
            return mReunionHourTime;
        }
        else if (totalMinute == 60 && totalHour == 1) {
            return totalHour;
        }
        else if (totalMinute == 60 && totalHour > 1) {
            return totalHour;
        }
        else if (totalMinute > 60 && totalHour > 1 && startMinute == endMinute){
            return totalHour;
        }
        else if (totalMinute > 60 && totalHour > 1){
            mReunionHourTime = totalHour - 1;
            return mReunionHourTime;
        }
        else {
            return totalHour;
        }
    }
}
