package com.group5.controllers;

import com.group5.Constants;

import com.group5.MainClientApplication;
import com.group5.game.*;
import com.group5.helper.Vector2D;
import javafx.animation.AnimationTimer;
import javafx.animation.TranslateTransition;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.Iterator;

public class GameController implements Initializable {

    @FXML private AnchorPane gameGrid;
    @FXML private Label healthLabel;
    @FXML private Label scoreLabel;
    @FXML private Label gameoverLabel;
    @FXML private Label gameoverScoreLabel;
    @FXML private Label gameoverScore;
    @FXML private Button playAgainButton;
    @FXML private Button exitButton;

    private Double gameScore = 0.0d;
    private Integer levelNo = 1;
    private Boolean isGameOver = false;
    private boolean entry = true; // to add aliens to the grid before updating it at every frame
    private boolean finishedGame = false; // finished all 4 levels

    private SpaceShip spaceShip = new SpaceShip(280, 720, 30, 30, Color.BLUE, 1, new Vector2D(0, 0), 1000, 10);
    List<Vector2D> positionsList = createUniformAlienPositions( Constants.ROW_COUNT, Constants.COLUMN_COUNT, Constants.ROW_PADDING);

    private double customTimer = 0.0d;
    private double customTimer2 = 0;

    public void playAgainButtonPressed(){
        gameoverLabel.setVisible(false);
        gameoverScoreLabel.setVisible(false);
        gameoverScore.setText("");
        gameoverScore.setVisible(false);
        playAgainButton.setVisible(false);
        exitButton.setVisible(false);

        levelNo = 1;
        spaceShip.setHealth(Constants.SPACESHIP_HEALTH);   //TODO move to constants
        healthLabel.textProperty().bind(new SimpleDoubleProperty(spaceShip.getHealth()).asString());
        gameScore = 0.0;
        scoreLabel.textProperty().bind(new SimpleDoubleProperty(gameScore).asString());
        setFirstLevelAliens();
        isGameOver = false;
    }
    public void exitButtonPressed() throws IOException {
        MainClientApplication.setRoot("index");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        gameGrid.prefHeight(Constants.GRID_HEIGHT);
        gameGrid.prefWidth(Constants.GRID_WIDTH);

        healthLabel.textProperty().bind(new SimpleDoubleProperty(spaceShip.getHealth()).asString());
        scoreLabel.textProperty().bind(new SimpleDoubleProperty(gameScore).asString());

        gameGrid.getChildren().add(spaceShip);
        setFirstLevelAliens();

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                //TranslateTransition tt = new TranslateTransition(Duration.millis(250), (Node)spaceShip);
                if (isGameOver) {
                    gameOverHandler();
                }
                /*if (finishedGame) {
                    timer.stop();
                }*/
                else{
                    moveSpaceShipWithMouse();
                    update();
                }
            }
        };
        timer.start();
    }

    public ArrayList<Alien> Aliens() {
        ArrayList<Alien> aliens = new ArrayList<>();
        for (Object object : gameGrid.getChildren()) {
            if (object instanceof Alien)
                aliens.add((Alien) object);
        }
        return aliens;
    }

    private void update() {
        if (levelNo == 1){
            if (entry == true) {
                setFirstLevelAliens();
                entry = false;
            }
            else {
                gameScore += Constants.LEVEL1_SCORE_INCREMENT;
                scoreLabel.textProperty().bind(new SimpleDoubleProperty(gameScore).asString());     //increases score as time passes
                updateForLevelOne();
            }

        }else if (levelNo == 2){
            if (entry == true) {
                setSecondLevelAliens();
                entry = false;
            }
            else {
                gameScore += Constants.LEVEL2_SCORE_INCREMENT;
                updateForLevelTwo();
            }


        }else if (levelNo == 3){
            if (entry == true) {
                setThirdLevelAliens();
                entry = false;
            }
            else {
                gameScore += Constants.LEVEL3_SCORE_INCREMENT;
                updateForLevelThree();
            }


        }
        else if (levelNo == 4){
            if (entry == true) {
                setFourthLevelAliens();
                entry = false;
            }
            else {
                gameScore += Constants.LEVEL4_SCORE_INCREMENT;
                updateForLevelFour();
            }


        }
    }

    public void updateGeneral(int currentLevelNo, double customTimerIncrement) {
        customTimer += customTimerIncrement;         //TODO level2 de ateş hızını artırmak için bu değişkenle oynanabilir
        if (customTimer > 1) {
            customTimer = 0.0d;
            Bullet spaceshipBullet = new Bullet((int) (spaceShip.getTranslateX() + (spaceShip.getWidth() / 2)) - 2, (int) spaceShip.getTranslateY(), Constants.BULLET_WIDTH, Constants.BULLET_HEIGHT, Color.BLACK, 5.0d, new Vector2D(0, -1), Constants.SPACESHIP_BULLET_DAMAGE);
            gameGrid.getChildren().add(spaceshipBullet);
        }
        customTimer2 += Constants.CUSTOM_TIME_STEP_ALIEN_BULLET;
        if (customTimer2 > Constants.TOTAL_TIME) {
            customTimer2 = 0.0d;


            if (currentLevelNo == 3 || currentLevelNo == 4) {
                for (Alien alien : Aliens()) {
                    // shooting or defensive-shooting
                    if (alien.getAlive() && (alien.getColor() == Color.GREEN || alien.getColor() == Color.ORANGE)) {
                        Bullet alienBullet = new Bullet((int) (alien.getTranslateX() + alien.getWidth() / 2 - 2), (int) alien.getTranslateY(), Constants.BULLET_WIDTH, Constants.BULLET_HEIGHT, Color.VIOLET, 5.0d, new Vector2D(0, 1), Constants.ALIEN_BULLET_DAMAGE);
                        gameGrid.getChildren().add(alienBullet);
                    }
                }

            }
        }


        Aliens().stream().filter(e -> e.getAlive()).forEach(alien -> {
            if (spaceShip.getBoundsInParent().intersects(((Node) alien).getBoundsInParent())) {
                System.out.println("collision!!");
                alien.setAlive(false);
                spaceShip.getHit(alien.getHealth());        //SpaceShip gets damage as much as aliens health when a collision occurs
                healthLabel.textProperty().bind(new SimpleDoubleProperty(spaceShip.getHealth()).asString());
                if (spaceShip.getHealth() <= 0) {
                    spaceShip.setAlive(false);
                    isGameOver = true;
                    System.out.println("GAMEOVER");
                }
            }
        });

        Iterator<Node> it = gameGrid.getChildren().iterator();
        while (it.hasNext()) {
            Object o2 = it.next();
            if (isGameOver && ((o2 instanceof Bullet) || (o2 instanceof Alien))) {
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
                            System.out.println("collision!!");
                            spaceShip.getHit(bullet.getDamage());        //SpaceShip gets damaged by alien bullet
                            healthLabel.textProperty().bind(new SimpleDoubleProperty(spaceShip.getHealth()).asString());
                            if (spaceShip.getHealth() <= 0) {
                                spaceShip.setAlive(false);
                                isGameOver = true;
                                System.out.println("GAMEOVER");
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

        if (!isGameOver && Aliens().isEmpty()) {
            if (currentLevelNo == 1) {
                levelNo = 2;
                entry = true;
            }
            if (currentLevelNo == 2) {
                levelNo = 3;
                entry = true;
            }
            if (currentLevelNo == 3) {
                levelNo = 4;
                entry = true;
            }
            if (currentLevelNo == 4) {
                //timer.stop();
                finishedGame = true;
            }


        }

    }

    public void updateForLevelOne() {
        updateGeneral(1, Constants.LEVEL1_TIMESTEP_INCREMENT);
    }

    public void updateForLevelTwo() {
        // changed to 0.05 only for observe the death of defensive aliens
        updateGeneral(2, Constants.LEVEL2_TIMESTEP_INCREMENT);
    }

    public void updateForLevelThree() {
        updateGeneral(3, Constants.LEVEL3_TIMESTEP_INCREMENT);

    }

    public void updateForLevelFour() {
        updateGeneral(4, Constants.LEVEL4_TIMESTEP_INCREMENT);
    }

    /**
     * This method creates the aliens of the grid for the first level of the game.
     */
    public void setFirstLevelAliens(){
        for (int i = 0; i < Constants.ROW_COUNT; i++) {
            setAliens(Color.RED, Constants.NORMAL_ALIEN_HEALTH, i+1);
        }
    }

    /**
     * This method creates the aliens of the grid for the second level of the game.
     */
    public void setThirdLevelAliens(){
        setAliens(Color.RED, Constants.NORMAL_ALIEN_HEALTH, 1);
        setAliens(Color.RED, Constants.NORMAL_ALIEN_HEALTH, 2);
        setAliens(Color.YELLOW, Constants.DEFENSIVE_ALIEN_HEALTH, 3);
        setAliens(Color.GREEN, Constants.SHOOTING_ALIEN_HEALTH, 4);
    }

    public void setFourthLevelAliens() {
        setAliens(Color.RED, Constants.NORMAL_ALIEN_HEALTH, 1);
        setAliens(Color.YELLOW, Constants.DEFENSIVE_ALIEN_HEALTH, 2);
        setAliens(Color.GREEN, Constants.SHOOTING_ALIEN_HEALTH, 3);
        setAliens(Color.ORANGE, Constants.DEFENSIVE_ALIEN_HEALTH, 4);
    }

    public void setSecondLevelAliens(){
        for (int i = 0; i < Constants.ROW_COUNT / 2; i++) {
            setAliens(Color.RED, Constants.NORMAL_ALIEN_HEALTH, i+1);
            setAliens(Color.YELLOW, Constants.DEFENSIVE_ALIEN_HEALTH, i + Constants.ROW_COUNT / 2 + 1);
        }
    }

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
    public void gameOverHandler(){
        gameoverLabel.setVisible(true);
        gameoverScoreLabel.setVisible(true);
        gameoverScore.setText(Double.toString(gameScore));
        gameoverScore.setVisible(true);
        playAgainButton.setVisible(true);
        exitButton.setVisible(true);
    }


}
