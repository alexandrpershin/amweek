package com.endava.androidamweek.data.model;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Database {

    private static Database database;

    private DatabaseReference databaseReference;
    private DatabaseReference quizzesReferece;
    private DatabaseReference usersReferece;
    private DatabaseReference trainingsReferece;
    private DatabaseReference speakersReferece;

    private ArrayList<Training> trainings;
    private ArrayList<Speaker> speakers;
    private ArrayList<User> users;
    private ArrayList<Quizz> quizzes;


    private Database() {
        databaseReference = FirebaseDatabase.getInstance().getReference();
        quizzesReferece = databaseReference.child("quizzes");
        usersReferece = databaseReference.child("users");
        trainingsReferece = databaseReference.child("trainings");
        speakersReferece = databaseReference.child("speakers");

        getDataFromFirebase();
    }

    public static Database getInstance() {
        if (database == null)
            database = new Database();

        return database;
    }

    private void getDataFromFirebase() {

        getQuizzesFromFirebase();
        getTrainingsFromFirebase();
        getSpeakersFromFirebase();
        getUsersFromFirebase();
    }


    public void getTrainingsFromFirebase() {
        getTrainingsReferece().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Map<String, Training> map = new HashMap<>();
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    Training training = data.getValue(Training.class);
                    map.put(data.getKey(), training);
                }
                trainings = new ArrayList<>(map.values());

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    public void getSpeakersFromFirebase() {
        getSpeakersReferece().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Map<String, Speaker> map = new HashMap<>();

                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    Speaker speaker = data.getValue(Speaker.class);
                    map.put(data.getKey(), speaker);
                }
                speakers = new ArrayList<>(map.values());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    private void getUsersFromFirebase() {
        getUsersReferece().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Map<String, User> map = new HashMap<>();
                ArrayList<UserTraining> trainingslist = new ArrayList<>();

                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    User user = data.getValue(User.class);

                    for (DataSnapshot dataTrainings : data.child("training").getChildren()) {
                        UserTraining speakerTrainings = dataTrainings.getValue(UserTraining.class);
                        speakerTrainings.setFirebaseFieldName(dataTrainings.getKey());
                        trainingslist.add(speakerTrainings);
                    }
                    user.setTrainings(trainingslist);
                    user.setFirebaseFieldName(data.getKey());
                    map.put(data.getKey(), user);
                    trainingslist = new ArrayList<>();
                }
                users = new ArrayList<>(map.values());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void getQuizzesFromFirebase() {
        getQuizzesReferece().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Map<String, Quizz> map = new HashMap<>();
                ArrayList<Answer> answerslist = new ArrayList<>();

                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    Quizz quizz = data.getValue(Quizz.class);

                    for (DataSnapshot dataTrainings : data.child("userAnswer").getChildren()) {
                        Answer answer = dataTrainings.getValue(Answer.class);
                        answerslist.add(answer);
                    }
                    quizz.setUserAnswers(answerslist);
                    quizz.setFirebaseFieldName(data.getKey());
                    map.put(data.getKey(), quizz);
                    answerslist = new ArrayList<Answer>();
                }
                quizzes = new ArrayList<>(map.values());

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public DatabaseReference getDatabaseReference() {
        return databaseReference;
    }

    public DatabaseReference getQuizzesReferece() {
        return quizzesReferece;
    }

    public DatabaseReference getUsersReferece() {
        return usersReferece;
    }

    public DatabaseReference getTrainingsReferece() {
        return trainingsReferece;
    }

    public DatabaseReference getSpeakersReferece() {
        return speakersReferece;
    }

    public ArrayList<Training> getTrainings() {
        return trainings;
    }


    public ArrayList<Speaker> getSpeakers() {
        return speakers;
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public ArrayList<Quizz> getQuizzes() {
        return quizzes;
    }

}
