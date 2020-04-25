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

public class SingupController {
    @FXML private TextField usernameInput;
    @FXML private TextField passwordInput;
    @FXML private TextField passwordInput2;

    @FXML private Label usernameRequired;
    @FXML private Label passwordRequired;
    @FXML private Label passwordRequired2;

    @FXML private Label connectionErrorLabel;

    public void requestSignup(ActionEvent event){
        connectionErrorLabel.setVisible(false);
        if(usernameInput.getText().isEmpty()) {
            usernameRequired.setVisible(true);
        }else{
            usernameRequired.setVisible(false);
        }
        if(passwordInput.getText().isEmpty() || passwordInput2.getText().isEmpty()){
            if(passwordInput.getText().isEmpty()){
                passwordRequired.setVisible(true);
            }else{
                passwordRequired.setVisible(false);
            }
            if(passwordInput2.getText().isEmpty()){
                passwordRequired2.setText("*password again required");
                passwordRequired2.setVisible(true);
            }else{
                passwordRequired2.setVisible(false);
            }
            return;
        }else{
            passwordRequired.setVisible(false);
            passwordRequired2.setVisible(false);
            connectionErrorLabel.setVisible(false);
            if(!passwordInput.getText().equals(passwordInput2.getText())){
                passwordRequired2.setText("*passwords must match");
                passwordRequired2.setVisible(true);
                return;
            }else{
                passwordRequired2.setVisible(false);
            }
        }

        JSONObject signupFormJson = new JSONObject();
        signupFormJson.put("name", usernameInput.getText() );
        signupFormJson.put("password", passwordInput.getText() );
        System.out.println(signupFormJson);

        HttpHeaders header =  new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>(signupFormJson.toString(), header);

        RestTemplate restTemplate = new RestTemplate();
        try {
            ResponseEntity<String> response = restTemplate.postForEntity(Constants.SERVER_USER_API_URL + "/signup", request, String.class);
            System.out.println(response.getBody());

            JSONObject userRecord = new JSONObject(response.getBody());
            System.out.println(userRecord);

            MainClientApplication.setLoggedUserId((Integer) userRecord.get("id"));

            //redirect like logged in after signup
            MainClientApplication.setRoot("index");

        } catch (ResourceAccessException e) {
            connectionErrorLabel.setVisible(true);
            System.out.println(e.getMessage());
        }catch (Exception e) {
            connectionErrorLabel.setVisible(true);
            e.printStackTrace();
        }
    }

    public void backToLogin(ActionEvent event) throws IOException {
        MainClientApplication.setRoot("login");
    }
}
