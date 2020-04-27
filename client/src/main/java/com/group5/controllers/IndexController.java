package com.group5.controllers;

import com.group5.MainClientApplication;
import javafx.event.ActionEvent;

import java.io.IOException;

public class IndexController {

    /**
     * Redirect to login page and clear user data
     * @param event
     * @throws IOException
     */
    public void logout(ActionEvent event) throws IOException {
        MainClientApplication.setLoggedUserId(0);
        MainClientApplication.setRoot("login");
    }

    /**
     * Redirect window to password change
     * @param event
     * @throws IOException
     */
    public void gotoPasswordChangePage(ActionEvent event) throws IOException {
        MainClientApplication.setRoot("passwordChange");
    }

    /**
     * Redirect window to leaderboard
     * @param event
     * @throws IOException
     */
    public void gotoLeaderboardPage(ActionEvent event) throws IOException {
        MainClientApplication.setRoot("leaderboard");
    }

    /**
     * Starts game
     * @param event
     * @throws IOException
     */
    public void playGame(ActionEvent event) throws IOException {
        MainClientApplication.setRoot("game");
    }
}
