package com.group5.controllers;

import com.group5.Constants;

import com.group5.MainClientApplication;
import com.group5.game.*;
import com.group5.helper.Vector2D;
import javafx.animation.AnimationTimer;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.awt.*;
import java.io.*;
import java.net.Socket;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Iterator;

/**
 * This class is a controller for the game the user starts to play after logging in.
 * There are 4 levels in the game.
 * Four types of aliens are distributed in 4 levels.
 * Before each level starts, a level transition scene is shown for a short period of time.
 */

public class GameController implements Initializable {

    @FXML private AnchorPane gameGrid;
    @FXML private Label levelNumberLabel;
    @FXML private Label healthLabel;
    @FXML private Label scoreLabel;
    @FXML private Label gameoverLabel;
    @FXML private Label gameoverScoreLabel;
    @FXML private Label gameoverScore;
    @FXML private Button playAgainButton;
    @FXML private Button exitButton;
    @FXML private Label levelTransitionLabel;
    @FXML private Label gameFinishedLabel;
    @FXML private Label connectionErrorLabel;

    private Integer gameScore = 0;
    private Integer levelNo = 1;
    private Boolean isGameOver = false;
    private Boolean isGameFinished = false;
    private boolean levelInitializationFlag = true; // to add aliens to the grid before updating it at every frame
    private boolean levelTransitionFlag = true;     // this flag blocks firing while level transitions
    private boolean waitingForPlayAgainFlag = false;    // this flag is to wait game while game is ended or game is over

    private SpaceShip spaceShip = new SpaceShip(280, 720, 30, 30, Constants.SPACESHIP_COLOR, 1, new Vector2D(0, 0), 1000, 10);
    List<Vector2D> positionsList = createUniformAlienPositions( Constants.ROW_COUNT, Constants.COLUMN_COUNT, Constants.ROW_PADDING);

    private SpaceShip rivalSpaceShip = new SpaceShip(280, 720, 30, 30, Constants.RIVAL_SPACESHIP_COLOR, 1, new Vector2D(0, 0), 1000, 10);

    private double customTimer = 0.0d;
    private double customTimer2 = 0;

    final KeyCombination keyComb1 = new KeyCodeCombination(KeyCode.DIGIT9,KeyCombination.CONTROL_DOWN,KeyCombination.SHIFT_DOWN);

    /**
     * This is to increment level with ctrl+shift+9 combination
     * @param scene Our main scene in MainClientApplication.java
     */
    private void addKeyHandler(Scene scene){
        scene.setOnKeyPressed((final KeyEvent keyEvent) -> {
            if (keyComb1.match(keyEvent)) {
                System.out.println("skip level!!");
                if(levelNo < 5){
                    levelInitializationFlag = true;
                    levelNo++;
                    clearRemainingAliens();
                }else if (levelNo == 5){
                    isGameFinished = true;
                }
            }
        });
    }

    /**
     * When playAgainButton is pressed this method starts game again
     */
    public void playAgainButtonPressed(){
        connectionErrorLabel.setVisible(false);
        waitingForPlayAgainFlag = false;
        gameGrid.getChildren().add(spaceShip);
        gameFinishedLabel.setVisible(false);
        gameoverLabel.setVisible(false);
        gameoverScoreLabel.setVisible(false);
        gameoverScore.setText("");
        gameoverScore.setVisible(false);
        playAgainButton.setVisible(false);
        exitButton.setVisible(false);
        levelNumberLabel.setText("LEVEL 1");
        levelNo = 1;
        spaceShip.setHealth(Constants.SPACESHIP_HEALTH);
        healthLabel.textProperty().bind(new SimpleDoubleProperty(spaceShip.getHealth()).asString());
        gameScore = 0;
        scoreLabel.textProperty().bind(new SimpleIntegerProperty(gameScore).asString());
        setFirstLevelAliens();
        isGameOver = false;
        isGameFinished = false;
    }

    /**
     * When exitButton is pressed this methods redirects to main menu namely index page
     * @throws IOException
     */
    public void exitButtonPressed() throws IOException {
        MainClientApplication.setRoot("index");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addKeyHandler(MainClientApplication.getScene());
        gameGrid.prefHeight(Constants.GRID_HEIGHT);
        gameGrid.prefWidth(Constants.GRID_WIDTH);

        healthLabel.textProperty().bind(new SimpleDoubleProperty(spaceShip.getHealth()).asString());
        scoreLabel.textProperty().bind(new SimpleIntegerProperty(gameScore).asString());

        gameGrid.getChildren().add(spaceShip);

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (isGameOver && !waitingForPlayAgainFlag) {
                    gameOverHandler();
                    waitingForPlayAgainFlag = true;
                }else if(isGameFinished && !waitingForPlayAgainFlag){
                    gameFinishedHandler();
                    waitingForPlayAgainFlag = true;
                }else{
                    if(waitingForPlayAgainFlag){
                        return;
                    }
                    moveSpaceShipWithMouse();
                    update();
                }
            }
        };
        timer.start();
    }

    /**
     * This method returns list of all the aliens in the gameGrid
     * @return  all aliens in the gameGrid
     */
    public ArrayList<Alien> Aliens() {
        ArrayList<Alien> aliens = new ArrayList<>();
        for (Object object : gameGrid.getChildren()) {
            if (object instanceof Alien)
                aliens.add((Alien) object);
        }
        return aliens;
    }

    /**
     * Runs continuously to update gameGrid
     */
    private void update() {
        if (levelNo == 1){
            if (levelInitializationFlag == true) {
                levelTransition(1);
                setFirstLevelAliens();
            }else {
                updateGeneral( Constants.LEVEL1_TIMESTEP_INCREMENT);
            }
        }else if (levelNo == 2){
            if (levelInitializationFlag == true) {
                levelTransition(2);
                setSecondLevelAliens();
            }else {
                updateGeneral( Constants.LEVEL2_TIMESTEP_INCREMENT);
            }
        }else if (levelNo == 3){
            if (levelInitializationFlag == true) {
                levelTransition(3);
                setThirdLevelAliens();
            }else {
                updateGeneral( Constants.LEVEL3_TIMESTEP_INCREMENT);
            }
        }
        else if (levelNo == 4){
            if (levelInitializationFlag == true) {
                levelTransition(4);
                setFourthLevelAliens();
            }else {
                updateGeneral( Constants.LEVEL4_TIMESTEP_INCREMENT);
            }
        }
        else if (levelNo == 5){
            if (levelInitializationFlag == true) {
                levelTransition(5);
                setFifthLevelAlien();
                multiplayerLevelInitialize();
            }else {
                updateGeneral( Constants.LEVEL4_TIMESTEP_INCREMENT);
            }
        }
    }

    /**
     * This method is used at every level to update objects.
     * @param customTimerIncrement  At each update we increase our timer this much. Fire rate of spaceShip increases along with it.
     */
    public void updateGeneral( double customTimerIncrement) {
        if (levelTransitionFlag && customTimer<15){     //this if block provides busy wait for a while at level transitions.
            customTimer += 0.1d;
            return;
        }else{
            levelTransitionFlag = false;
            levelTransitionLabel.setVisible(false);
        }
        gameScore += levelNo;
        scoreLabel.textProperty().bind(new SimpleIntegerProperty(gameScore).asString());

        customTimer += customTimerIncrement;
        if (customTimer > 1) {
            customTimer = 0.0d;
            Bullet spaceshipBullet = new Bullet((int) (spaceShip.getTranslateX() + (spaceShip.getWidth() / 2)) - 2, (int) spaceShip.getTranslateY(),
                    Constants.BULLET_WIDTH, Constants.BULLET_HEIGHT, Constants.SPACESHIP_BULLET_COLOR, 5.0d, new Vector2D(0, -1), Constants.SPACESHIP_BULLET_DAMAGE);
            gameGrid.getChildren().add(spaceshipBullet);
        }
        customTimer2 += Constants.CUSTOM_TIME_STEP_ALIEN_BULLET;
        if (customTimer2 > Constants.TOTAL_TIME) {
            customTimer2 = 0.0d;


            if (levelNo == 3 || levelNo == 4) {
                for (Alien alien : Aliens()) {
                    // shooting or defensive-shooting
                    if (alien.getAlive() && (alien.getColor() == Constants.SHOOTING_ALIEN_COLOR || alien.getColor() == Constants.HARD_ALIEN_COLOR)) {
                        Bullet alienBullet = new Bullet((int) (alien.getTranslateX() + alien.getWidth() / 2 - 2), (int) alien.getTranslateY(),
                                Constants.BULLET_WIDTH, Constants.BULLET_HEIGHT, Constants.ALIEN_BULLET_COLOR, 5.0d, new Vector2D(0, 1), Constants.ALIEN_BULLET_DAMAGE);
                        gameGrid.getChildren().add(alienBullet);
                    }
                }
            }
        }


        Aliens().stream().filter(e -> e.getAlive()).forEach(alien -> {
            if (spaceShip.getBoundsInParent().intersects(((Node) alien).getBoundsInParent())) {
                alien.setAlive(false);
                spaceShip.getHit(alien.getHealth());        //SpaceShip gets damage as much as aliens health when a collision occurs
                healthLabel.textProperty().bind(new SimpleDoubleProperty(spaceShip.getHealth()).asString());
                if (spaceShip.getHealth() <= 0) {
                    spaceShip.setAlive(false);
                    isGameOver = true;
                }
            }
        });

        Iterator<Node> it = gameGrid.getChildren().iterator();
        while (it.hasNext()) {
            Object o2 = it.next();
            if (isGameOver && ((o2 instanceof Bullet) || (o2 instanceof Alien) || (o2 instanceof SpaceShip))) {
                it.remove();
            } else if (o2 instanceof Bullet) {
                Bullet bullet = (Bullet) o2;

                // spaceship bullet
                if (bullet.getDirection().y == -1) {
                    bullet.setTranslateY(bullet.getTranslateY() - Constants.BULLET_SPEED);
                    if (bullet.getTranslateY() <= -15) {
                        it.remove();
                    } else {
                        Iterator<Alien> it_alien = Aliens().iterator();
                        while (it_alien.hasNext()) {
                            Alien alien = it_alien.next();
                            if (bullet.getBoundsInParent().intersects(((Node) alien).getBoundsInParent())) {
                                alien.getHit(bullet.getDamage());
                                gameScore +=  Integer.valueOf((int)(bullet.getDamage()/5));      //update score as bullets hit aliens
                                it.remove();
                                if (alien.getHealth() <= 0) {
                                    alien.setAlive(false);
                                }
                                break;
                            }
                        }
                    }
                }
                else { // alien bullet
                    bullet.setTranslateY(bullet.getTranslateY() + Constants.BULLET_SPEED);
                    if (bullet.getTranslateY() >= gameGrid.getPrefHeight() + 15) {
                        it.remove();
                    }
                    else {
                        if (bullet.getBoundsInParent().intersects(((Node) spaceShip).getBoundsInParent())) {
                            it.remove();
                            spaceShip.getHit(bullet.getDamage());        //SpaceShip gets damaged by alien bullet
                            healthLabel.textProperty().bind(new SimpleDoubleProperty(spaceShip.getHealth()).asString());
                            if (spaceShip.getHealth() <= 0) {
                                spaceShip.setAlive(false);
                                isGameOver = true;
                            }
                        }
                    }
                }
            }
            else if (o2 instanceof Alien) {
                Alien alien2 = (Alien) o2;
                if (!alien2.getAlive()) {
                    it.remove();
                }
            }
        }

        // If no aliens left increase level or finish game
        if (!isGameOver && Aliens().isEmpty()) {
            if(levelNo != 4){
                levelNo += 1;
                levelInitializationFlag = true;
            }else if(levelNo == 4){
                isGameFinished = true;
            }

        }
    }

    /**
     * This method creates the aliens of the grid for the first level of the game.
     */
    public void setFirstLevelAliens(){
        for (int i = 0; i < Constants.ROW_COUNT; i++) {
            setAliens(Constants.NORMAL_ALIEN_COLOR, Constants.NORMAL_ALIEN_HEALTH, i+1);
        }
    }
    /**
     * This method creates the aliens of the grid for the third level of the game.
     */
    public void setSecondLevelAliens(){
        for (int i = 0; i < Constants.ROW_COUNT / 2; i++) {
            setAliens(Constants.NORMAL_ALIEN_COLOR, Constants.NORMAL_ALIEN_HEALTH, i+1);
            setAliens(Constants.DEFENSIVE_ALIEN_COLOR, Constants.DEFENSIVE_ALIEN_HEALTH, i + Constants.ROW_COUNT / 2 + 1);
        }
    }
    /**
     * This method creates the aliens of the grid for the third level of the game.
     */
    public void setThirdLevelAliens(){
        setAliens(Constants.NORMAL_ALIEN_COLOR, Constants.NORMAL_ALIEN_HEALTH, 1);
        setAliens(Constants.NORMAL_ALIEN_COLOR, Constants.NORMAL_ALIEN_HEALTH, 2);
        setAliens(Constants.DEFENSIVE_ALIEN_COLOR, Constants.DEFENSIVE_ALIEN_HEALTH, 3);
        setAliens(Constants.SHOOTING_ALIEN_COLOR, Constants.SHOOTING_ALIEN_HEALTH, 4);
    }

    /**
     * This method creates the aliens of the grid for the fourth level of the game.
     */
    public void setFourthLevelAliens() {
        setAliens(Constants.NORMAL_ALIEN_COLOR, Constants.NORMAL_ALIEN_HEALTH, 1);
        setAliens(Constants.DEFENSIVE_ALIEN_COLOR, Constants.DEFENSIVE_ALIEN_HEALTH, 2);
        setAliens(Constants.SHOOTING_ALIEN_COLOR, Constants.SHOOTING_ALIEN_HEALTH, 3);
        setAliens(Constants.HARD_ALIEN_COLOR, Constants.DEFENSIVE_ALIEN_HEALTH, 4);
    }

    public void setFifthLevelAlien()
    {
        Vector2D downVector = new Vector2D(0.0d, -1.0d);
        Alien newAlien = new Alien(((int) (Constants.COLUMN_COUNT / 2)), (int) Constants.FINAL_ALIEN_HEIGHT / 2 , Constants.FINAL_ALIEN_WIDTH, Constants.FINAL_ALIEN_HEIGHT, Constants.FINAL_ALIEN_COLOR, 0, downVector, Constants.FINAL_ALIEN_HEALTH);
        gameGrid.getChildren().add((Node) newAlien);
    }

    public void multiplayerLevelInitialize(){
        gameGrid.getChildren().add(rivalSpaceShip);
        DataInputStream fromServer;
        DataOutputStream toServer;
        try {
            Socket socket = new Socket(Constants.MULTIPLAYER_SERVER_IP, Constants.MULTIPLAYER_SERVER_PORT);
//            fromServer = new DataInputStream(socket.getInputStream());
//            toServer = new DataOutputStream(socket.getOutputStream());
//
//            String response = fromServer.readUTF();
//            String message = new String(response.getBytes(), "UTF-8");
//            System.out.println(message);
//
//            new DataOutputStream(toServer).writeUTF("response from CLIENT CLIENT CLIENT CLIENT");

            ObjectOutputStream toServerObj = new ObjectOutputStream(socket.getOutputStream());
            MultiplayerMessage msgToServer = new MultiplayerMessage("client", 888, new Point(222,333), Constants.STATUS_CONTINUING);
            toServerObj.writeObject(msgToServer);

            try {
                ObjectInputStream fromServerObj = new ObjectInputStream(socket.getInputStream());
                MultiplayerMessage msgFromServer = (MultiplayerMessage)fromServerObj.readObject();

                System.out.println(msgFromServer.getName()+"  "+msgFromServer.getHealth()+"  "+msgFromServer.getPosition()+"  "+msgFromServer.getGameStatus());

                rivalSpaceShip.setTranslateX(msgFromServer.getPosition().x);
                rivalSpaceShip.setTranslateY(msgFromServer.getPosition().y);


                //msgFromServer.getPosition()
            }catch (Exception e){
                e.printStackTrace();
            }


        }
        catch (Exception ex) {
            ex.printStackTrace();
        }

    }



    /**
     * This method is used to create and place aliens to the gameGrid
     * @param color Color of the created aliens
     * @param health Health of the created aliens
     * @param rowNumber Place created aliens to this row
     */
    public void setAliens(Color color, double health, int rowNumber) {
        int alienWidth = 15;
        int alienHeight = 15;
        Vector2D downVector = new Vector2D(0.0, 1.0d);

        for (int i=0; i < Constants.COLUMN_COUNT; i++) {
            Alien newAlien = new Alien((int) positionsList.get((rowNumber-1)*Constants.COLUMN_COUNT +i).x, (int) positionsList.get((rowNumber-1)*Constants.COLUMN_COUNT+i).y, alienWidth, alienHeight, color, 0, downVector, health);
            gameGrid.getChildren().add((Node) newAlien);
        }
    }


    /**
     * This method just prepares a list of positions(distributed uniformly) to use while placing aliens to the grid.
     * @param rowCount Aliens will be aligned in rowCount many rows
     * @param columnCount  Aliens will be aligned in columnCount many columns
     * @param rowPadding  Rows will be rowPadding much padded from the top of the grid.
     * @return List<Vector2D> This is the list of Alien positions.
     */
    private List<Vector2D> createUniformAlienPositions( Integer rowCount, Integer columnCount, double rowPadding){
        double columnPadding = Constants.GRID_WIDTH / columnCount;
        List<Vector2D> positionsList = new ArrayList<>(rowCount*columnCount);
        for(int i=0; i<rowCount; i++){
            for(int j=0; j<columnCount; j++){
                positionsList.add(new Vector2D( (columnPadding/2) + (j*columnPadding), (rowPadding + (i*rowPadding)) ));
            }
        }
        return positionsList;
    }

    /**
     * This method makes our SpaceShip to follow mouse
     */
    public void moveSpaceShipWithMouse(){
        Point mouse = MouseInfo.getPointerInfo().getLocation();

        double newXCoordinate = mouse.getX()-MainClientApplication.mainStage.getX()-(spaceShip.getWidth()/2)-5;
        double newYCoordinate = mouse.getY()-MainClientApplication.mainStage.getY()-(spaceShip.getHeight()/2)-30;

        if(newXCoordinate < 0){
            newXCoordinate=0;
        }else if(newXCoordinate > gameGrid.getPrefWidth()-spaceShip.getWidth()){
            newXCoordinate = gameGrid.getPrefWidth()-spaceShip.getWidth();
        }
        if(newYCoordinate < 0){
            newYCoordinate=0;
        }else if(newYCoordinate > gameGrid.getPrefHeight()-spaceShip.getHeight()){
            newYCoordinate = gameGrid.getPrefHeight()-spaceShip.getHeight();
        }
        spaceShip.setTranslateX(newXCoordinate);
        spaceShip.setTranslateY(newYCoordinate);
    }
    /**
     * This method is called when isGameOver flag is true. It sends score to server.
     */
    public void gameOverHandler(){
        gameoverLabel.setVisible(true);
        gameoverScoreLabel.setVisible(true);
        gameoverScore.setText(Integer.toString(gameScore));
        gameoverScore.setVisible(true);
        playAgainButton.setVisible(true);
        exitButton.setVisible(true);
        sendScoreToServer();
    }
    /**
     * This method removes remaining bullets from the AnchorPane named gameGrid
     */
    public void clearRemainingBullets(){
        Iterator<Node> it = gameGrid.getChildren().iterator();
        while (it.hasNext()) {
            Object o2 = it.next();
            if (o2 instanceof Bullet) {
                it.remove();
            }
        }
    }
    /**
     * This method removes remaining aliens from the AnchorPane named gameGrid
     */
    public void clearRemainingAliens(){
        Iterator<Node> it = gameGrid.getChildren().iterator();
        while (it.hasNext()) {
            Object o2 = it.next();
            if (o2 instanceof IAlien) {
                it.remove();
            }
        }
    }

    /**
     * This method shows a label on level transitions
     * @param newLevelNumber The new level number
     */
    public void levelTransition(Integer newLevelNumber){
        String levelNoString = "LEVEL "+newLevelNumber;
        levelNumberLabel.setText(levelNoString);
        levelInitializationFlag = false;
        levelTransitionFlag = true;
        clearRemainingBullets();
        levelTransitionLabel.setText(levelNoString);
        levelTransitionLabel.setVisible(true);
    }

    /**
     * This method is called when isGameFinished flag is true. Sends final score to the server.
     */
    public void gameFinishedHandler(){
        Iterator<Node> it = gameGrid.getChildren().iterator();
        while (it.hasNext()) {
            Object o2 = it.next();
            if( o2 instanceof Bullet || o2 instanceof IAlien || o2 instanceof SpaceShip){
                it.remove();
            }
        }

        levelTransitionLabel.setVisible(false);
        gameFinishedLabel.setVisible(true);
        gameoverScoreLabel.setVisible(true);
        gameoverScore.setText(Integer.toString(gameScore));
        gameoverScore.setVisible(true);
        playAgainButton.setVisible(true);
        exitButton.setVisible(true);
        sendScoreToServer();
    }

    /**
     * This method sends gameScore to the server in order to save it in database
     */
    public void sendScoreToServer(){
        if ( MainClientApplication.getLoggedUserId() == 0){
            return;
        }
        JSONObject userAsJson = new JSONObject();
        userAsJson.put("id", MainClientApplication.getLoggedUserId());

        JSONObject scoreAsJson = new JSONObject();
        scoreAsJson.put("score", Integer.toString(gameScore) );
        String endTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern(Constants.SCOREBOARD_DATE_FORMAT_STRING));
        scoreAsJson.put("endTime", endTime );
        String userString = "{\"id\" : \"" +MainClientApplication.getLoggedUserId()+ "\"}";
        scoreAsJson.put("user",userAsJson);
        System.out.println(scoreAsJson);

        HttpHeaders header =  new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>(scoreAsJson.toString(), header);

        RestTemplate restTemplate = new RestTemplate();
        try {
            ResponseEntity<String> response = restTemplate.postForEntity(Constants.SERVER_SCORE_API_URL + "/add", request, String.class);
            System.out.println(response.getBody());

        } catch (ResourceAccessException e) {
            connectionErrorLabel.setVisible(true);
            System.out.println(e.getMessage());
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

}
