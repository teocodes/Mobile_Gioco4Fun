package com.example.matteo.model;

/**
 * Created by matteo on 16/03/2016.
 */
public class Question {

    private String contentQuestion;
    private int category;

    public Question(String contentQuestion, int category) {
        this.contentQuestion = contentQuestion;
        this.category = category;
    }

    public String getContentQuestion() {
        return contentQuestion;
    }

    public void setContentQuestion(String contentQuestion) {
        this.contentQuestion = contentQuestion;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }
}
