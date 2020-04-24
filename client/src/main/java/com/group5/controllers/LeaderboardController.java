package com.group5.controllers;

import com.group5.MainClientApplication;
import javafx.event.ActionEvent;

import java.io.IOException;

public class LeaderboardController {

    public void backToLogin(ActionEvent event) throws IOException {
        MainClientApplication.setRoot("login");
    }
}
