package com.endava.androidamweek.utils;

import com.endava.androidamweek.data.localDB.LocalDatabase;
import com.endava.androidamweek.data.model.Database;
import com.endava.androidamweek.data.model.Quizz;
import com.endava.androidamweek.data.model.Speaker;
import com.endava.androidamweek.data.model.Training;
import com.endava.androidamweek.data.model.User;
import com.endava.androidamweek.data.model.UserTraining;
import com.google.firebase.database.DatabaseReference;

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

    public User getUserById(String id) {
        for (int i = 0; i < LocalDatabase.getInstance().getUsers().size(); i++)

            if (LocalDatabase.getInstance().getUsers().get(i).getId().equals(id)) {

                return LocalDatabase.getInstance().getUsers().get(i);
            }

        return null;

    }

    public void addTrainingToUser(String userId, Training training) {
        User user = getUserById(userId);
        DatabaseReference userTrainingReference = Database.getInstance().getUsersReferece().child(user.getFirebaseFieldName()).child("training");
        DatabaseReference newUserTraining = userTrainingReference.push();

        UserTraining userTraining = new UserTraining();
        userTraining.setTrainingId(training.getId());

        user.getTraining().add(userTraining);

        newUserTraining.setValue(userTraining);


    }

    public boolean userHasCurrentTraining(String userId, Training trainig) {
        User user = getUserById(userId);
        boolean userHasCurrentTraining = false;
        for (int i = 0; i < user.getTraining().size(); i++) {

            if (user.getTraining().get(i).getTrainingId().equals(trainig.getId()))
                userHasCurrentTraining = true;

        }

        return userHasCurrentTraining;
    }

    public void removeTrainingToUser(String userID, Training item) {
        User user = getUserById(userID);

        UserTraining userTraining = getUserTrainingById(user, item.getId());

        Database.getInstance().getUsersReferece().child(user.getFirebaseFieldName()).child("training")
                .child(userTraining.getFirebaseFieldName()).removeValue();

    }

    private UserTraining getUserTrainingById(User user, Integer id) {
        for (int i = 0; i < user.getTraining().size(); i++) {
            if (user.getTraining().get(i).getTrainingId().equals(id))
                return user.getTraining().get(i);
        }
        return null;
    }


}