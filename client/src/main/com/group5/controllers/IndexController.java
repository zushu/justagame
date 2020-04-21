package main.com.group5.controllers;

import javafx.event.ActionEvent;

public class IndexController {

    public void gotoLoginPage(ActionEvent event){
        System.out.println("login button clicked");
    }

    public void gotoSignupPage(ActionEvent event){
        System.out.println("signup button clicked");
    }

    public void gotoLeaderboardPage(ActionEvent event){
        System.out.println("leaderboard button clicked");
    }
}
