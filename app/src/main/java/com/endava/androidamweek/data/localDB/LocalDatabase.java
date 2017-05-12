package com.endava.androidamweek.data.localDB;

import android.content.Context;
import android.util.Log;

import com.endava.androidamweek.data.model.Database;
import com.endava.androidamweek.data.model.Quizz;
import com.endava.androidamweek.data.model.Speaker;
import com.endava.androidamweek.data.model.Training;
import com.endava.androidamweek.data.model.User;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.ArrayList;

public class LocalDatabase {

    private static LocalDatabase localDBManager;
    private ArrayList<User> users;
    private ArrayList<Training> trainings;
    private ArrayList<Speaker> speakers;
    private ArrayList<Quizz> quizzs;

    private DatabaseHelper databaseHelper;

    private Dao<Speaker, Integer> speakerDao;
    private Dao<Training, Integer> trainigDao;
    private Dao<Quizz, Integer> quizzDao;
    private Dao<User, Integer> userDao;

    private Context context;

    private LocalDatabase() {
    }

    public static LocalDatabase getInstance() {
        if (localDBManager == null)
            localDBManager = new LocalDatabase();

        return localDBManager;
    }

    public void updataData() {

        writeQuizzesToDB();
        writeSpeakersToDB();
        writeTrainingsToDB();
        writeUsersToDB();

        readFromDB();
    }


    private void initializeDao() {
        try {
            userDao = databaseHelper.getUserDao();
            quizzDao = databaseHelper.getQuizzDao();
            speakerDao = databaseHelper.getSpeakerDao();
            trainigDao = databaseHelper.getTrainingDao();
        } catch (SQLException e) {
            e.printStackTrace();
            Log.i("TAG", e.getMessage() + " Can not initialize DAO");
        }

    }

    private void writeSpeakersToDB() {
        databaseHelper.clearSpeakersTable();
        try {
            for (int i = 0; i < Database.getInstance().getSpeakers().size(); i++)
                speakerDao.create(Database.getInstance().getSpeakers().get(i));
        } catch (SQLException e) {
            e.printStackTrace();
            Log.i("TAG", e.getMessage() + " Can not write speakers to DB");
        }
    }


    private void writeUsersToDB() {
        databaseHelper.clearUsersTable();
        databaseHelper.clearUserTrainingTable();
        try {
            for (int i = 0; i < Database.getInstance().getUsers().size(); i++) {
                userDao.create(Database.getInstance().getUsers().get(i));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            Log.i("TAG", e.getMessage() + " Can not write users to DB");
        }
    }

    private void writeQuizzesToDB() {
        databaseHelper.clearQuizzesTable();
        databaseHelper.clearAnswersTable();
        try {
            for (int i = 0; i < Database.getInstance().getQuizzes().size(); i++)
                quizzDao.create(Database.getInstance().getQuizzes().get(i));
        } catch (SQLException e) {
            e.printStackTrace();
            Log.i("TAG", e.getMessage() + " Can not write quizzes to DB");
        }
    }

    private void writeTrainingsToDB() {
        databaseHelper.clearTrainingsTable();
        try {
            for (int i = 0; i < Database.getInstance().getTrainings().size(); i++)
                trainigDao.create(Database.getInstance().getTrainings().get(i));

        } catch (SQLException e) {
            e.printStackTrace();
            Log.i("TAG", e.getMessage() + " Can not write trainings to DB");
        }


    }

    public void readFromDB() {
        try {
            speakers = (ArrayList<Speaker>) speakerDao.queryForAll();
            quizzs = (ArrayList<Quizz>) quizzDao.queryForAll();
            trainings = (ArrayList<Training>) trainigDao.queryForAll();
            users = (ArrayList<User>) userDao.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
            Log.i("TAG", e.getMessage() + " Can not  read from  Dao");
        }

    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public ArrayList<Training> getTrainings() {
        return trainings;
    }

    public ArrayList<Speaker> getSpeakers() {
        return speakers;
    }

    public ArrayList<Quizz> getQuizzes() {
        return quizzs;
    }

    public void setContext(Context context) {
        this.context = context;
        databaseHelper = new DatabaseHelper(context);

        initializeDao();

    }
}
