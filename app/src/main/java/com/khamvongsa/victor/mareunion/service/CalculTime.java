package com.khamvongsa.victor.mareunion.service;


public class CalculTime implements CalculTimeService{


    @Override
    public int calculMinuteLeft(int totalMinute, int totalHour) {
        int mReunionMinuteTime;
        int mTotalHour;

        if (totalMinute >= 60 && totalHour > 1) {
            mReunionMinuteTime = totalMinute - 60*totalHour;
            return mReunionMinuteTime;
        }
        else if (totalMinute >= 60) {
            mReunionMinuteTime = totalMinute - 60;
            return mReunionMinuteTime;
        }
        else {
            return totalMinute;
        }
    }

    @Override
    public int calculHourLeft(int totalMinute, int totalHour) {
        int mReunionHourTime;
        if (totalMinute < 60 && totalHour == 1) {
            mReunionHourTime = totalHour - 1;
            return mReunionHourTime;
        }
        else if (totalMinute == 60 && totalHour == 1) {
            return totalHour;
        }
        else if (totalMinute >= 60 && totalHour > 1){
            return totalHour;
        }
        else {
            return totalHour;
        }
    }
}
