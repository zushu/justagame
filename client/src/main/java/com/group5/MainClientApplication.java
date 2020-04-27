package com.group5;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;


/**
 * JavaFX App
 */
public class MainClientApplication extends Application {

    private static Scene scene;
    private static Integer loggedUserId = 0;
    public static Stage mainStage;

    @Override
    public void start(Stage stage) throws IOException {
        mainStage = stage;
        scene = new Scene(loadFXML("login"));
        stage.setTitle("Just a Game");
        stage.setScene(scene);
        stage.show();
    }

    public static Scene getScene(){
        return scene;
    }

    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainClientApplication.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static Integer getLoggedUserId(){
        return loggedUserId;
    }

    public static void setLoggedUserId(Integer userId){
        loggedUserId = userId;
    }

    public static void main(String[] args) {
        launch();
    }

}