package com.group5.controllers;

import com.group5.Constants;
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
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

public class LoginController {
    @FXML private TextField usernameInput;
    @FXML private TextField passwordInput;

    @FXML private Label usernameRequired;
    @FXML private Label passwordRequired;

    @FXML private Label connectionErrorLabel;
    @FXML private Label loginErrorLabel;

    /**
     * This method validates form input and sends authorization request to the server
     * @param event
     */
    public void requestLogin(ActionEvent event){
        connectionErrorLabel.setVisible(false);
        loginErrorLabel.setVisible(false);

        if(usernameInput.getText().isEmpty() || passwordInput.getText().isEmpty()){
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
            return;
        }else{
            usernameRequired.setVisible(false);
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
            ResponseEntity<String> response = restTemplate.postForEntity(Constants.SERVER_USER_API_URL + "/login", request, String.class);
            System.out.println(response.getBody());

            if(response.getBody().equals("false")){
                loginErrorLabel.setText("Username or password is incorrect!");
                loginErrorLabel.setVisible(true);
            }else{
                MainClientApplication.setLoggedUserId(Integer.parseInt(response.getBody()));
                MainClientApplication.setRoot("index");
            }

        } catch (ResourceAccessException e) {
            connectionErrorLabel.setVisible(true);
            System.out.println(e.getMessage());
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Redirect window to signup page
     * @param event
     * @throws IOException
     */
    public void gotoSignupPage(ActionEvent event) throws IOException {
        MainClientApplication.setRoot("signup");
    }

    /**
     * Redirect window to leaderboard
     * @param event
     * @throws IOException
     */
    public void gotoLeaderboardPage(ActionEvent event) throws IOException {
        MainClientApplication.setRoot("leaderboard");
    }

}
