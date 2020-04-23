package com.group5.controllers;

import com.group5.MainClientApplication;
import javafx.event.ActionEvent;

import java.io.IOException;

public class IndexController {

    public void gotoLoginPage(ActionEvent event) throws IOException {
        System.out.println("login button clicked");
        MainClientApplication.setRoot("login");
    }

    public void gotoSignupPage(ActionEvent event){
        System.out.println("signup button clicked");
    }

    public void gotoLeaderboardPage(ActionEvent event){
        System.out.println("leaderboard button clicked");
    }
}
