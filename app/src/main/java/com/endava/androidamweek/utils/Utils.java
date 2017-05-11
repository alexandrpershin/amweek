package com.endava.androidamweek.utils;

import com.endava.androidamweek.data.localDB.LocalDatabase;
import com.endava.androidamweek.data.model.Quizz;
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

    public String getSpeakerName(int id) {

        for (int i = 0; i < LocalDatabase.getInstance().getSpeakers().size(); i++) {
            if (LocalDatabase.getInstance().getSpeakers().get(i).getId() == id)
                return LocalDatabase.getInstance().getSpeakers().get(i).getName();
        }
        return "Speaker";
    }

    public Speaker getSpeaker(int speakerId) {

        for (int i = 0; i < LocalDatabase.getInstance().getSpeakers().size(); i++) {
            if (LocalDatabase.getInstance().getSpeakers().get(i).getId() == speakerId)
                return LocalDatabase.getInstance().getSpeakers().get(i);
        }
        return null;
    }

    public List<Training> getSpeakerTrainings(int speakerId) {
        ArrayList<Training> list = new ArrayList<>();
        for (int i = 0; i < LocalDatabase.getInstance().getTrainings().size(); i++)
            if (LocalDatabase.getInstance().getTrainings().get(i).getSpeakerId() == speakerId) {
                list.add(LocalDatabase.getInstance().getTrainings().get(i));
            }
        return list;
    }

    public List<Training> getCurrentDayTrainings(int dayOfWeek) {
        ArrayList<Training> list = new ArrayList<>();
        for (int i = 0; i < LocalDatabase.getInstance().getTrainings().size(); i++) {
            if (LocalDatabase.getInstance().getTrainings().get(i).getDay() == dayOfWeek) {
                list.add(LocalDatabase.getInstance().getTrainings().get(i));
            }
        }
        return list;
    }

    public String getSpeakerImage(int id) {

        for (int i = 0; i < LocalDatabase.getInstance().getSpeakers().size(); i++) {
            if (LocalDatabase.getInstance().getSpeakers().get(i).getId() == id)
                return LocalDatabase.getInstance().getSpeakers().get(i).getImageId();
        }
        return "image";
    }


    public List<Quizz> getQuizzes() {
        return LocalDatabase.getInstance().getQuizzes();

    }
}