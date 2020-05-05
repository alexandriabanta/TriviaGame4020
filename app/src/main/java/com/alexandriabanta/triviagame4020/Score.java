package com.alexandriabanta.triviagame4020;

import java.util.Date;

public class Score {



    private int score_id;
    private int score;
    private int num_of_questions;
    private String category;
    private String difficulty;
    private String type;
    private Date date_created;

    public Score()
    {

    }

    public Score(int score_id, int score, int num_of_questions, String category, String difficulty, String type, Date date_created) {
        this.score_id = score_id;
        this.score = score;
        this.num_of_questions = num_of_questions;
        this.category = category;
        this.difficulty = difficulty;
        this.type = type;
        this.date_created = date_created;
    }

    public int getScore_id() {
        return score_id;
    }

    public void setScore_id(int score_id) {
        this.score_id = score_id;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getNum_of_questions() {
        return num_of_questions;
    }

    public void setNum_of_questions(int num_of_questions) {
        this.num_of_questions = num_of_questions;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getDate_created() {
        return date_created;
    }

    public void setDate_created(Date date_created) {
        this.date_created = date_created;
    }
}
