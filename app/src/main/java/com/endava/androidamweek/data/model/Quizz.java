package com.endava.androidamweek.data.model;

import java.util.ArrayList;

public class Quizz {
    private String date;
    private String question;
    private String time;
    private String title;
    private String stream;
//    private String codeSnippet;
    private Boolean quizzStatus;
    private ArrayList<Answer> answer;

//    public void setCodeSnippet(String codeSnippet) {
//        this.codeSnippet = codeSnippet;
//    }
//
//    public String getCodeSnippet() {
//
//        return codeSnippet;
//    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStream() {
        return stream;
    }

    public void setStream(String stream) {
        this.stream = stream;
    }

    public Boolean getQuizzStatus() {
        return quizzStatus;
    }

    public void setQuizzStatus(Boolean quizzStatus) {
        this.quizzStatus = quizzStatus;
    }

    public ArrayList<Answer> getAnswer() {
        return answer;
    }

    public void setAnswer(ArrayList<Answer> answer) {
        this.answer = answer;
    }
}
