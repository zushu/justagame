package com.group5.controllers;

import com.group5.MainClientApplication;
import javafx.event.ActionEvent;

import java.io.IOException;

public class IndexController {

    public void logout(ActionEvent event) throws IOException {
        MainClientApplication.setLoggedUserId(0);
        MainClientApplication.setRoot("login");
    }

    public void gotoPasswordChangePage(ActionEvent event) throws IOException {
        MainClientApplication.setRoot("passwordChange");
    }

    public void gotoLeaderboardPage(ActionEvent event) throws IOException {
        MainClientApplication.setRoot("leaderboard");
    }

    //TODO: implement game initialization
    public void playGame(ActionEvent event) throws IOException {
        System.out.println("playGame button clicked");
    }
}
