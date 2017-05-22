package com.endava.androidamweek.data.localDB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.endava.androidamweek.data.model.Answer;
import com.endava.androidamweek.data.model.Quizz;
import com.endava.androidamweek.data.model.Speaker;
import com.endava.androidamweek.data.model.Training;
import com.endava.androidamweek.data.model.User;
import com.endava.androidamweek.data.model.UserTraining;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;


public class DatabaseHelper extends OrmLiteSqliteOpenHelper {


    private static final String DATABASE_NAME = "AMWeekDB.db";
    private static final int DATABASE_VERSION = 42;

    private Dao<User, Integer> userDao;
    private Dao<UserTraining, Integer> userTrainingDao;
    private Dao<Quizz, Integer> quizzDao;
    private Dao<Speaker, Integer> speakerDao;
    private Dao<Answer, Integer> answerDao;
    private Dao<Training, Integer> trainingDao;


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void clearTrainingsTable() {
        try {
            TableUtils.clearTable(connectionSource, Training.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void clearAnswersTable() {
        try {
            TableUtils.clearTable(connectionSource, Answer.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void clearQuizzesTable() {
        try {
            TableUtils.clearTable(connectionSource, Quizz.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void clearUsersTable() {
        try {
            TableUtils.clearTable(connectionSource, User.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void clearUserTrainingTable() {
        try {
            TableUtils.clearTable(connectionSource, UserTraining.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void clearSpeakersTable() {
        try {
            TableUtils.clearTable(connectionSource, Speaker.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onCreate(SQLiteDatabase sqliteDatabase, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, User.class);
            TableUtils.createTable(connectionSource, UserTraining.class);
            TableUtils.createTable(connectionSource, Training.class);
            TableUtils.createTable(connectionSource, Speaker.class);
            TableUtils.createTable(connectionSource, Quizz.class);
            TableUtils.createTable(connectionSource, Answer.class);
        } catch (SQLException e) {
            Log.e(DatabaseHelper.class.getName(), "Unable to create datbases", e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqliteDatabase, ConnectionSource connectionSource, int oldVer, int newVer) {
        try {
            TableUtils.dropTable(connectionSource, User.class, true);
            TableUtils.dropTable(connectionSource, UserTraining.class, true);
            TableUtils.dropTable(connectionSource, Training.class, true);
            TableUtils.dropTable(connectionSource, Answer.class, true);
            TableUtils.dropTable(connectionSource, Quizz.class, true);
            TableUtils.dropTable(connectionSource, Speaker.class, true);
            onCreate(sqliteDatabase, connectionSource);
        } catch (SQLException e) {
            Log.e(DatabaseHelper.class.getName(), "Unable to upgrade database from version " + oldVer + " to new "
                    + newVer, e);
        }
    }

    public Dao<User, Integer> getUserDao() throws SQLException {
        if (userDao == null) {
            userDao = getDao(User.class);
        }
        return userDao;
    }

    public Dao<UserTraining, Integer> getUserTrainingDao() throws SQLException {
        if (userTrainingDao == null) {
            userTrainingDao = getDao(UserTraining.class);
        }
        return userTrainingDao;
    }

    public Dao<Training, Integer> getTrainingDao() throws SQLException {
        if (trainingDao == null) {
            trainingDao = getDao(Training.class);
        }
        return trainingDao;
    }

    public Dao<Speaker, Integer> getSpeakerDao() throws SQLException {
        if (speakerDao == null) {
            speakerDao = getDao(Speaker.class);
        }
        return speakerDao;
    }

    public Dao<Answer, Integer> getAnswerDao() throws SQLException {
        if (answerDao == null) {
            answerDao = getDao(Answer.class);
        }
        return answerDao;
    }

    public Dao<Quizz, Integer> getQuizzDao() throws SQLException {
        if (quizzDao == null) {
            quizzDao = getDao(Quizz.class);
        }
        return quizzDao;
    }

}