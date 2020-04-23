package com.group5.controllers;

import com.group5.MainClientApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.io.IOException;


public class LoginController {
    @FXML
    private TextField usernameInput;

    @FXML
    private TextField passwordInput;

    public void requestLogin(ActionEvent event){
        if(usernameInput.getText().isEmpty() ||  passwordInput.getText().isEmpty()) {
            System.out.println("Please enter username and password to login.");
        }

        //JSONObject loginFormJson = new JSONObject();
        //loginFormJson.put("username", usernameInput.getText() );
        //loginFormJson.put("password", passwordInput.getText() );

        System.out.println("login button clicked from login page");
    }

    public void backToMenu(ActionEvent event) throws IOException {
        System.out.println("mainmenu button clicked");
        MainClientApplication.setRoot("index");
    }

}
