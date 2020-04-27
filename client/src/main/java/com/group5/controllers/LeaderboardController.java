package com.group5.controllers;

import com.group5.Constants;
import com.group5.MainClientApplication;
import com.group5.models.Score;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class LeaderboardController implements Initializable {
    @FXML private TableView<Score> table;
    @FXML private TableColumn<Score, Integer> rank;
    @FXML private TableColumn<Score, String> username;
    @FXML private TableColumn<Score, Integer> score;

    @FXML private ComboBox<String> scoreCombobox;

    @FXML private Label connectionErrorLabel;

    public ObservableList<Score> list = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        connectionErrorLabel.setVisible(false);
        scoreCombobox.getItems().addAll("All Times", "Last 7 days", "Last 30 days");
        scoreCombobox.getSelectionModel().select("All Times");

        rank.setCellValueFactory(new PropertyValueFactory<Score, Integer>("rank"));
        username.setCellValueFactory(new PropertyValueFactory<Score, String>("username"));
        score.setCellValueFactory(new PropertyValueFactory<Score, Integer>("score"));

        fillTable(Constants.ALL_TIMES_DATE_STRING);
    }

    /**
     * This method fills Leaderboard for the data existing after the given date
     * @param dateStr Leaderboard will be filled with the data starting from dateStr until now.
     */
    public void fillTable(String dateStr){
        list.clear();

        HttpHeaders header =  new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>(dateStr, header);

        RestTemplate restTemplate = new RestTemplate();
        try {
            ResponseEntity<String> response = restTemplate.postForEntity(Constants.SERVER_SCORE_API_URL+"/scoreboard", request, String.class);
            JSONArray leaderboardArr = new JSONArray(response.getBody());
            table.getItems().clear();
            for(int i =0; i<leaderboardArr.length(); i++){
                JSONObject obj = (JSONObject) leaderboardArr.get(i);
                table.getItems().add(new Score((Integer)(i+1),(Integer)obj.get("score"),(String) obj.get("endTime"),(String)obj.getJSONObject("user").get("name")));
                list.add(new Score((Integer)(i+1),(Integer)obj.get("score"),(String) obj.get("endTime"),(String)obj.getJSONObject("user").get("name")) );
            }

        } catch (ResourceAccessException e) {
            connectionErrorLabel.setVisible(true);
            System.out.println(e.getMessage());
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * When the comboBox for choosing time interval changes, this method updates Leaderboard accordingly
     * @param event
     */
    public void comboboxChanged(ActionEvent event){
        String selectedInterval = scoreCombobox.getValue();
        if(selectedInterval.equals("Last 7 days")){
            fillTable(LocalDateTime.now().minusDays(7).format(DateTimeFormatter.ofPattern(Constants.SCOREBOARD_DATE_FORMAT_STRING)));
        }else if(selectedInterval.equals("Last 30 days")){
            fillTable(LocalDateTime.now().minusDays(30).format(DateTimeFormatter.ofPattern(Constants.SCOREBOARD_DATE_FORMAT_STRING)));
        }else{
            fillTable(Constants.ALL_TIMES_DATE_STRING);
        }
    }

    /**
     * If a user is logged in redirect to main menu, else redirect to login
     * @param event
     * @throws IOException
     */
    public void backToLogin(ActionEvent event) throws IOException {
        if (MainClientApplication.getLoggedUserId()==0){
            MainClientApplication.setRoot("login");
        }else{
            MainClientApplication.setRoot("index");
        }
    }
}
