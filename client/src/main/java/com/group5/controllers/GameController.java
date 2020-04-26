package com.group5.controllers;

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
    @FXML private Label levelNumberLabel;
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
    //private int level = 1;
    private boolean entry = true; // to add aliens to the grid before updating it at every frame

    private SpaceShip spaceShip = new SpaceShip(280, 720, 30, 30, Color.BLUE, 1, new Vector2D(0, 0), 1000, 10);

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
        spaceShip.setHealth(1000.0d);   //TODO move to constants
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
        gameGrid.prefHeight(800);
        gameGrid.prefWidth(600);

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
                }else{
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
                levelNumberLabel.setText("LEVEL 1");
                setFirstLevelAliens();


                entry = false;
            }
            else {
                gameScore += 0.25;
                scoreLabel.textProperty().bind(new SimpleDoubleProperty(gameScore).asString());     //increases score as time passes
                updateForLevelOne();
            }

        }else if (levelNo == 2){
            if (entry == true) {
                levelNumberLabel.setText("LEVEL 2");
                setSecondLevelAliens();
                entry = false;
            }
            else {
                gameScore += 0.5;
                updateForLevelTwo();
            }


        }else if (levelNo == 3){
            if (entry == true) {
                levelNumberLabel.setText("LEVEL 3");
                setThirdLevelAliens();
                entry = false;
            }
            else {
                gameScore += 1;
                updateForLevelThree();
            }


        }
        else if (levelNo == 4){
            gameScore += 2;


        }
    }

    public void updateGeneral(int currentLevelNo, double customTimerIncrement) {
        customTimer += customTimerIncrement;         //TODO level2 de ateş hızını artırmak için bu değişkenle oynanabilir
        if (customTimer > 1) {
            customTimer = 0.0d;
            Bullet spaceshipBullet = new Bullet((int) (spaceShip.getTranslateX() + (spaceShip.getWidth() / 2)) - 2, (int) spaceShip.getTranslateY(), 5, 15, Color.BLACK, 5.0d, new Vector2D(0, -1), 100.0d);
            gameGrid.getChildren().add(spaceshipBullet);
        }
        customTimer2 += 0.01;
        if (customTimer2 > 1) {
            customTimer2 = 0.0d;


            if (currentLevelNo == 3) {
                for (Alien alien : Aliens()) {
                    //alien = (Alien) alien;
                    if (alien.getAlive() && alien.getColor() == Color.GREEN) {
                        Bullet alienBullet = new Bullet((int) (alien.getTranslateX() + alien.getWidth() / 2 - 2), (int) alien.getTranslateY(), 5, 15, Color.VIOLET, 5.0d, new Vector2D(0, 1), 10);
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
                    bullet.setTranslateY(bullet.getTranslateY() - 5);
                    if (bullet.getTranslateY() <= -15) {
                        it.remove();
                    } else {
                        Iterator<Alien> it_alien = Aliens().iterator();
                        while (it_alien.hasNext()) {
                            Alien alien = it_alien.next();
                            if (bullet.getBoundsInParent().intersects(((Node) alien).getBoundsInParent())) {
                                alien.getHit(bullet.getDamage());
                                gameScore += bullet.getDamage()/5;      //update score as bullets hit aliens
                                it.remove();
                                if (alien.getHealth() <= 0) {
                                    alien.setAlive(false);
                                    //it_alien.remove();
                                }
                                break;
                            }
                        }
                    }
                }
                else { // alien bullet
                    bullet.setTranslateY(bullet.getTranslateY() + 5);
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
        /*if (currentLevelNo == 4) {
            levelNo = 5;
            entry = true;
        }*/


        }

    }

    public void updateForLevelOne() {
        updateGeneral(1, 0.01);
    }

    public void updateForLevelTwo() {
        // changed to 0.05 only for observe the death of defensive aliens
        updateGeneral(2, 0.2);
    }

    public void updateForLevelThree() {
        updateGeneral(3, 0.1);

    }

    /**
     * This method creates the aliens of the grid for the first level of the game.
     */
    public void setFirstLevelAliens(){
        double alienWidth = 15.0d;
        double alienHeight = 15.0d;
        Vector2D downVector = new Vector2D(0.0, 1.0d);

        Double rowPadding = 60.0d;
        Integer rowCount = 4;
        Integer columnCount = 6;
        Integer alienCount = rowCount * columnCount;
        List<Vector2D> positionsList = createUniformAlienPositions( rowCount, columnCount, rowPadding);
        for(int i=0; i<alienCount; i++){
            Alien newAlien = new Alien((int) positionsList.get(i).x, (int) positionsList.get(i).y, (int) alienWidth, (int) alienHeight, Color.RED, 0, downVector, 100.0d);

            gameGrid.getChildren().add(newAlien);
        }
    }

    /**
     * This method creates the aliens of the grid for the third level of the game.
     */
    public void setThirdLevelAliens(){
        double alienWidth = 15.0d;
        double alienHeight = 15.0d;
        Vector2D downVector = new Vector2D(0.0, 1.0d);
        Double rowPadding = 60.0d;
        Integer rowCount = 4;
        Integer columnCount = 6;
        Integer alienCount = rowCount * columnCount;
        List<Vector2D> positionsList = createUniformAlienPositions( rowCount, columnCount, rowPadding);
        for(int i=0; i<6; i++){
            //DefensiveAlien newAlien1 = new DefensiveAlien(new Alien((int) positionsList.get(i).x, (int) positionsList.get(i).y, (int) alienWidth, (int) alienHeight, Color.YELLOW, 0, downVector, 200.0d));
            Alien newAlien1 = new Alien((int) positionsList.get(12+i).x, (int) positionsList.get(12+i).y, (int) alienWidth, (int) alienHeight, Color.YELLOW, 0, downVector, 400.0d);

            gameGrid.getChildren().add((Node) newAlien1);

        }
        for(int i=0; i<12; i++){
            Alien newAlien = new Alien((int) positionsList.get(i).x, (int) positionsList.get(i).y, (int) alienWidth, (int) alienHeight, Color.RED, 0, downVector, 200.0d);
            gameGrid.getChildren().add((Node) newAlien);
        }

        // shooting aliens
        for(int i=0; i<6; i++){
            Alien newAlien = new Alien((int) positionsList.get(18+i).x, (int) positionsList.get(18+i).y, (int) alienWidth, (int) alienHeight, Color.GREEN, 0, downVector, 200.0d);
            gameGrid.getChildren().add((Node) newAlien);
        }

    }

    /**
     * This method creates the aliens of the grid for the second level of the game.
     */
    public void setSecondLevelAliens(){
        double alienWidth = 15.0d;
        double alienHeight = 15.0d;
        Vector2D downVector = new Vector2D(0.0, 1.0d);
        Double rowPadding = 60.0d;
        Integer rowCount = 4;
        Integer columnCount = 6;
        Integer alienCount = rowCount * columnCount;
        List<Vector2D> positionsList = createUniformAlienPositions( rowCount, columnCount, rowPadding);
        for(int i=0; i<12; i++){
            //DefensiveAlien newAlien1 = new DefensiveAlien(new Alien((int) positionsList.get(i).x, (int) positionsList.get(i).y, (int) alienWidth, (int) alienHeight, Color.YELLOW, 0, downVector, 200.0d));
            Alien newAlien1 = new Alien((int) positionsList.get(i).x, (int) positionsList.get(i).y, (int) alienWidth, (int) alienHeight, Color.YELLOW, 0, downVector, 400.0d);

            gameGrid.getChildren().add((Node) newAlien1);

        }
        for(int i=0; i<12; i++){
            Alien newAlien = new Alien((int) positionsList.get(12+i).x, (int) positionsList.get(12+i).y, (int) alienWidth, (int) alienHeight, Color.RED, 0, downVector, 200.0d);
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
        double columnPadding = gameGrid.getPrefWidth() / columnCount;
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
