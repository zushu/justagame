package com.group5.controllers;

import com.group5.MainClientApplication;
import javafx.event.ActionEvent;

import java.io.IOException;

public class IndexController {

    public void gotoLoginPage(ActionEvent event) throws IOException {
        MainClientApplication.setRoot("login");
    }

    public void gotoSignupPage(ActionEvent event) throws IOException {
        MainClientApplication.setRoot("signup");
    }

    public void gotoLeaderboardPage(ActionEvent event){
        System.out.println("leaderboard button clicked");
    }
}
