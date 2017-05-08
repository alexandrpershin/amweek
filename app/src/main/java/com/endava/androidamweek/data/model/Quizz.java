package com.endava.androidamweek.data.model;

import java.util.ArrayList;


public class Quizz {
    private String date;
    private String time;
    private String codeSnippet;
    private String conditions;
    private String correctAnswer;
    private String winner;
    private String title;
    private Boolean status;
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
}
