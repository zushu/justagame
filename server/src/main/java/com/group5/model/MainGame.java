package com.group5.model;

public class MainGame{
    //main gameplay will go here
    private Integer gameLevel;
    private Grid grid;

    public MainGame(){
        this.gameLevel = 1;
    }

    public MainGame(Grid grid){
        this.grid = grid;
    }

    public Grid getGrid(){
        return this.grid;
    }

    public Integer getGameLevel(){
        return this.gameLevel;
    }

    public void incrementGameLevel(){
        this.gameLevel++;
    }

    public void gamePlay(){

        Grid grid = 
    }

}