package com.group5.models;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Score {
    private final SimpleIntegerProperty rank;
    private final SimpleIntegerProperty score;
    private final SimpleStringProperty endTime;
    private final SimpleStringProperty username;

    public Score(Integer rank, Integer score, String endTime, String username){
        this.rank = new SimpleIntegerProperty(rank);
        this.score = new SimpleIntegerProperty(score);
        this.endTime = new SimpleStringProperty(endTime);
        this.username = new SimpleStringProperty(username);
    }

    public Integer getRank(){
        return rank.get();
    }
    public Integer getScore(){
        return score.get();
    }
    public String getEndTime(){
        return endTime.get();
    }
    public String getUsername(){
        return username.get();
    }
}
