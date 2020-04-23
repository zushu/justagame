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
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

public class SingupController {
    @FXML
    private TextField usernameInput;
    @FXML
    private TextField passwordInput;
    @FXML
    private TextField passwordInput2;

    @FXML
    private Label usernameRequired;
    @FXML
    private Label passwordRequired;
    @FXML
    private Label passwordRequired2;

    @FXML
    private Label connectionErrorLabel;

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

        JSONObject loginFormJson = new JSONObject();
        loginFormJson.put("name", usernameInput.getText() );
        loginFormJson.put("password", passwordInput.getText() );
        System.out.println(loginFormJson);

        HttpHeaders header =  new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>(loginFormJson.toString(), header);

        RestTemplate restTemplate = new RestTemplate();
        try {
            ResponseEntity<String> response = restTemplate.postForEntity("http://localhost:8080/api/user/signup", request, String.class);
            System.out.println(response.getBody());

            //response şöyle ise login olmuş gibi oyuna yönlendir {"id":8,"name":"test","password":"8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92"}
        } catch (ResourceAccessException e) {
            connectionErrorLabel.setVisible(true);
            System.out.println(e.getMessage());
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void backToMenu(ActionEvent event) throws IOException {
        System.out.println("mainmenu button clicked");
        MainClientApplication.setRoot("index");
    }
}
