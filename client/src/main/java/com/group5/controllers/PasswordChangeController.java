package com.group5.controllers;

import com.group5.Constants;
import com.group5.MainClientApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

public class PasswordChangeController {
    @FXML private PasswordField oldPasswordInput;
    @FXML private PasswordField newPasswordInput;
    @FXML private PasswordField newPasswordInput2;

    @FXML private Label oldPasswordRequired;
    @FXML private Label newPasswordRequired;
    @FXML private Label newPasswordRequired2;

    @FXML private Label connectionErrorLabel;
    @FXML private Label passwordChangeErrorLabel;

    /**
     * Validates form input and sends password change request to the server
     * @param event
     */
    public void changePassword(ActionEvent event){
        connectionErrorLabel.setVisible(false);
        passwordChangeErrorLabel.setVisible(false);
        if (oldPasswordInput.getText().isEmpty() || newPasswordInput.getText().isEmpty() || newPasswordInput2.getText().isEmpty()){
            if(oldPasswordInput.getText().isEmpty()){
                oldPasswordRequired.setVisible(true);
            }else{
                oldPasswordRequired.setVisible(false);
            }
            if(newPasswordInput.getText().isEmpty()){
                newPasswordRequired.setVisible(true);
            }else{
                newPasswordRequired.setVisible(false);
            }
            if(newPasswordInput2.getText().isEmpty()){
                newPasswordRequired2.setText("*password again required");
                newPasswordRequired2.setVisible(true);
            }else{
                newPasswordRequired2.setVisible(false);
            }
            return;
        }else{
            oldPasswordRequired.setVisible(false);
            newPasswordRequired.setVisible(false);
            newPasswordRequired2.setVisible(false);
            connectionErrorLabel.setVisible(false);
            passwordChangeErrorLabel.setVisible(false);
            if(!newPasswordInput.getText().equals(newPasswordInput2.getText())){
                newPasswordRequired2.setText("*passwords must match");
                newPasswordRequired2.setVisible(true);
                return;
            }else{
                newPasswordRequired2.setVisible(false);
            }
        }
        JSONObject passwordChangeFormJson = new JSONObject();
        passwordChangeFormJson.put("userid",MainClientApplication.getLoggedUserId());
        passwordChangeFormJson.put("oldPassword", oldPasswordInput.getText() );
        passwordChangeFormJson.put("newPassword", newPasswordInput.getText() );

        HttpHeaders header =  new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>(passwordChangeFormJson.toString(), header);

        RestTemplate restTemplate = new RestTemplate();
        try {
            ResponseEntity<String> response = restTemplate.postForEntity(Constants.SERVER_USER_API_URL + "/passwordchange", request, String.class);
            System.out.println(response.getBody());

            if(response.getBody().equals("true")){
                MainClientApplication.setRoot("index");
            }else if (response.getBody().equals("false")){
                passwordChangeErrorLabel.setText("Old password is incorrect!");
                passwordChangeErrorLabel.setVisible(true);
            }

        } catch (ResourceAccessException e) {
            connectionErrorLabel.setVisible(true);
            System.out.println(e.getMessage());
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Redirects window to the main menu
     * @param event
     * @throws IOException
     */
    public void backToLogin(ActionEvent event) throws IOException {
        MainClientApplication.setRoot("index");
    }
}
