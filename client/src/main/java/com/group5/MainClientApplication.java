package com.group5;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;


/**
 * JavaFX App
 * Main class to start the game
 */
public class MainClientApplication extends Application {

    private static Scene scene;
    private static Integer loggedUserId = 0;
    private static String loggedUserName = "";
    private static String rivalUserName = "";
    public static Stage mainStage;

    /**
     * mainStage is the first scene shown to the user.
     * The first scene is the login page.
     * @param stage
     * @throws IOException
     */

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

    /**
     * loads fxml file to the scene
     * @param fxml
     * @return Parent
     * @throws IOException
     */
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

    public static String getLoggedUserName(){ return  loggedUserName;}
    public static void setLoggedUserName(String username){ loggedUserName = username;}

    public static String getRivalUserName(){ return rivalUserName;}
    public static void setRivalUserName(String rivalName){ rivalUserName = rivalName;}

    public static void main(String[] args) {
        launch();
    }

}