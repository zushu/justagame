package com.group5.controllers;

import com.group5.MainClientApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;


public class LoginController {
    @FXML
    private TextField usernameInput;
    @FXML
    private TextField passwordInput;

    @FXML
    private Label usernameRequired;
    @FXML
    private Label passwordRequired;

    public void requestLogin(ActionEvent event){
        if(usernameInput.getText().isEmpty()) {
            usernameRequired.setVisible(true);
        }else{
            usernameRequired.setVisible(false);
        }
        if(passwordInput.getText().isEmpty()){
            passwordRequired.setVisible(true);
        }else{
            passwordRequired.setVisible(false);
        }

        JSONObject loginFormJson = new JSONObject();
        loginFormJson.put("name", usernameInput.getText() );
        loginFormJson.put("password", passwordInput.getText() );
        System.out.println(loginFormJson);

        HttpHeaders header =  new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>(loginFormJson.toString(), header);

        RestTemplate restTemplate = new RestTemplate();
        try {
            ResponseEntity<String> response = restTemplate.postForEntity("http://localhost:8080/api/user/login", request, String.class);
            System.out.println(response.getBody());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void backToMenu(ActionEvent event) throws IOException {
        System.out.println("mainmenu button clicked");
        MainClientApplication.setRoot("index");
    }

}
