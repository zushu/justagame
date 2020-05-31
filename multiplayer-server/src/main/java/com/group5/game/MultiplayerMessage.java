package com.group5.game;

import com.group5.Constants;

import java.awt.*;
import java.io.Serializable;

public class MultiplayerMessage implements Serializable {
    private String name;
    private double health;
    private Point position;
    private int gameStatus;
    private int score;
    private double alienHealth;
    private double time;

    public MultiplayerMessage(){
        this.gameStatus = Constants.STATUS_CONTINUING;
    }

    public MultiplayerMessage(String name, double health, Point position, int gameStatus, int score, double alienHealth){
        this.name = name;
        this.health = health;
        this.position = position;
        this.gameStatus = gameStatus;
        this.score = score;
        this.alienHealth = alienHealth;
        this.time = 0.0d;
    }

    public void print(){
        System.out.println(this.name+"  "+this.health+"  "+this.position+"  "+this.gameStatus+"  "+this.score);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getHealth() {
        return health;
    }

    public void setHealth(double health) {
        this.health = health;
    }

    public Point getPosition() {
        return position;
    }

    public void setPosition(Point position) {
        this.position = position;
    }

    public int getGameStatus() {
        return gameStatus;
    }

    public void setGameStatus(int gameStatus) {
        this.gameStatus = gameStatus;
    }

    public int getScore(){
        return this.score;
    }

    public void setScore(int score){
        this.score = score;
    }

    public double getAlienHealth(){
        return this.alienHealth;
    }

    public void setAlienHealth(double alienHealth){
        this.alienHealth = alienHealth;
    }

    public double getTime(){
        return this.time;
    }

    public void setTime(double time){
        this.time = time;
    }
}
