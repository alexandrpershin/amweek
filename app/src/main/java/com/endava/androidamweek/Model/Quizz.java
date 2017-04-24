package com.endava.androidamweek.Model;

import java.util.ArrayList;

/**
 * Created by Алекс on 21.04.2017.
 */

public class Quizz {
    private String date;
    private String question;
    private String time;
     private Integer questionId;
    private ArrayList<Answer> answer;


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

    public Integer getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Integer questionId) {
        this.questionId = questionId;
    }

    public ArrayList<Answer> getAnswer() {
        return answer;
    }

    public void setAnswer(ArrayList<Answer> answer) {
        this.answer = answer;
    }
}
