package com.endava.androidamweek.utils;

import com.endava.androidamweek.data.model.Speaker;
import com.endava.androidamweek.data.model.Training;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Utils {

    public static int getWeekDay() {

        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        return day - 1;
    }

    public String getSpeakerName(List<Speaker> speakers, int id) {

        for (int i = 0; i < speakers.size(); i++) {
            if (speakers.get(i).getId() == id)
                return speakers.get(i).getName();
        }
        return "Speaker";
    }

    public Speaker getSpeaker(List<Speaker> speakers, int speakerId) {

        for (int i = 0; i < speakers.size(); i++) {
            if (speakers.get(i).getId() == speakerId)
                return speakers.get(i);
        }
        return null;
    }

    public List<Training> getSpeakerTrainings(List<Training> trainings, int speakerId) {
        ArrayList<Training> list = new ArrayList<>();
        for (int i = 0; i < trainings.size(); i++)
            if (trainings.get(i).getSpeakerId() == speakerId) {
                list.add(trainings.get(i));
            }
        return list;
    }

    public List<Training> getCurrentDayTrainings(List<Training> trainings, int dayOfWeek) {
        ArrayList<Training> list = new ArrayList<>();
        for (int i = 0; i < trainings.size(); i++) {
            if (trainings.get(i).getDay() == dayOfWeek) {
                list.add(trainings.get(i));
            }
        }
        return list;
    }

}