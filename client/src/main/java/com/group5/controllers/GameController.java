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
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.awt.*;
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

    private Double gameScore=0.0;

    private SpaceShip spaceShip = new SpaceShip(280, 720, 30, 30, Color.BLUE,1,new Vector2D(0,0),1000,10);
    private List<IAlien> alienList = new ArrayList<>();

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
                TranslateTransition tt = new TranslateTransition(Duration.millis(250), (Node)spaceShip);
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

                update();
            }
        };
        timer.start();

    }

    public ArrayList<IAlien> Aliens() {
        ArrayList<IAlien> aliens = new ArrayList<>();
        for (Object object : gameGrid.getChildren()) {
            if (object instanceof IAlien)
                aliens.add((IAlien) object);
        }
        return aliens;
    }

    private void update(){
        gameScore += 0.25;
        scoreLabel.textProperty().bind(new SimpleDoubleProperty(gameScore).asString());     //increases score as time passes

        alienList.stream().filter(e -> e.getAlive()).forEach(alien -> {
            if (spaceShip.getBoundsInParent().intersects(((Node)alien).getBoundsInParent())) {
                alien.setAlive(false);
                System.out.println("collision");
                //spaceShip.setAlive(false);
            }
        });
        alienList.removeIf(alien -> {
            return !alien.getAlive();
        });
        System.out.println(alienList.size());

        Aliens().stream().filter(e -> e.getAlive()).forEach(alien -> {
            if (spaceShip.getBoundsInParent().intersects(((Node)alien).getBoundsInParent())) {
                alien.setAlive(false);
                System.out.println("collision");
                //spaceShip.setAlive(false);
            }
        });

        Iterator<Node> iter = gameGrid.getChildren().iterator();
        while (iter.hasNext()) {
            Object o = iter.next();
            if (o instanceof IAlien) {
                IAlien alien = (IAlien) o;
                if (!alien.getAlive()) {
                    iter.remove();
                }
            }
        }
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
            //alienList.add(new Alien(positionsList.get(i), downVector, 0.0d, alienWidth, alienHeight, 200.0d));
            Alien newAlien = new Alien((int) positionsList.get(i).x, (int) positionsList.get(i).y, (int) alienWidth, (int) alienHeight, Color.RED, 0, downVector, 200.0d);
            alienList.add(newAlien);

            gameGrid.getChildren().add(newAlien);   //TODO bunu burda yapmak yerine initialize methodunda alienList'i kullanarak yap
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
}
