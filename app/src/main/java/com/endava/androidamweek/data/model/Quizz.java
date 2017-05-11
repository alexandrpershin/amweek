package com.endava.androidamweek.data.model;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;
import java.util.ArrayList;

@DatabaseTable
public class Quizz  implements Serializable{
    @DatabaseField (generatedId = true)
    private Integer quizzId;

    @DatabaseField
    private String date;
    @DatabaseField
    private String time;
    @DatabaseField
    private String codeSnippet;
    @DatabaseField
    private String conditions;
    @DatabaseField
    private String correctAnswer;
    @DatabaseField
    private String winner;
    @DatabaseField
    private String title;
    @DatabaseField
    private Boolean status;
    @DatabaseField
    private String firebaseFieldName;

    @DatabaseField(dataType= DataType.SERIALIZABLE)
    private ArrayList<Answer> userAnswers;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getCodeSnippet() {
        return codeSnippet;
    }

    public void setCodeSnippet(String codeSnippet) {
        this.codeSnippet = codeSnippet;
    }

    public String getConditions() {
        return conditions;
    }

    public void setConditions(String conditions) {
        this.conditions = conditions;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public String getWinner() {
        return winner;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public ArrayList<Answer> getUserAnswers() {
        return userAnswers;
    }

    public void setUserAnswers(ArrayList<Answer> userAnswers) {
        this.userAnswers = userAnswers;
    }

    public String getFirebaseFieldName() {
        return firebaseFieldName;
    }

    public void setFirebaseFieldName(String firebaseFieldName) {
        this.firebaseFieldName = firebaseFieldName;
    }
}
