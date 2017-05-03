package com.endava.androidamweek.data.model;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Database {

    private static Database database ;

    private DatabaseReference databaseReference;
    private DatabaseReference quizzesReferece;
    private DatabaseReference usersReferece;
    private DatabaseReference trainingsReferece;
    private DatabaseReference speakersReferece;

    private ArrayList<Training> trainings;
    private ArrayList<Speaker> speakers;
    private ArrayList<User> users;
    private ArrayList<Quizz> quizzes;

    private static Boolean connected;
    private static DatabaseReference connectedRef;


    private Database() {
        databaseReference = FirebaseDatabase.getInstance().getReference();
        quizzesReferece = databaseReference.child("quizzes");
        usersReferece = databaseReference.child("users");
        trainingsReferece = databaseReference.child("trainings");
        speakersReferece = databaseReference.child("speakers");

        getDataFromFirebase();
    }

    public static Database getInstance() {
        if(database==null)
            database  = new Database();

        return database;
    }

    public static   void checkInternetConnectionState(){
       connectedRef = FirebaseDatabase.getInstance().getReference(".info/connected");

        connectedRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                connected = snapshot.getValue(Boolean.class);
                if(connected==false)
                    Log.i("Connection","disconnect");
                else Log.i("Connection","connect");

            }

            @Override
            public void onCancelled(DatabaseError error) {
                System.err.println("Listener was cancelled");
            }
        });


    }

    private void getDataFromFirebase() {
        getTrainingsFromFirebase();
        getSpeakersFromFirebase();
        getUsersFromFirebase();
        getQuizzesFromFirebase();
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
                Map<String, Speaker> map = new HashMap< >();

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
                Map<String, User> map = new HashMap< >();
                ArrayList<UserTraining> trainingslist = new ArrayList<>();

                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    User user = data.getValue(User.class);

                    for (DataSnapshot dataTrainings : data.child("trainings").getChildren()) {
                        UserTraining speakerTrainings = dataTrainings.getValue(UserTraining.class);
                        trainingslist.add(speakerTrainings);
                    }
                    user.setTraining(trainingslist);
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
                Map<String, Quizz> map = new HashMap< >();
                ArrayList<Answer> answerslist = new ArrayList<>();

                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    Quizz quizz = data.getValue(Quizz.class);

                    for (DataSnapshot dataTrainings : data.child("answers").getChildren()) {
                        Answer answer = dataTrainings.getValue(Answer.class);
                        answerslist.add(answer);
                    }
                    quizz.setAnswer(answerslist);
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

    private DatabaseReference getDatabaseReference() {
        return databaseReference;
    }

    private DatabaseReference getQuizzesReferece() {
        return quizzesReferece;
    }

    private DatabaseReference getUsersReferece() {
        return usersReferece;
    }

    private DatabaseReference getTrainingsReferece() {
        return trainingsReferece;
    }

    private DatabaseReference getSpeakersReferece() {
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
