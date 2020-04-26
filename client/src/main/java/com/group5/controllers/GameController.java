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

    @FXML
    private AnchorPane gameGrid;
    @FXML
    private Label healthLabel;
    @FXML
    private Label scoreLabel;

    private Double gameScore = 0.0;
    private int level = 1;
    private boolean entry = true;

    private SpaceShip spaceShip = new SpaceShip(280, 720, 30, 30, Color.BLUE, 1, new Vector2D(0, 0), 1000, 10);
    private List<IAlien> alienList = new ArrayList<>();
    private List<Bullet> spaceShipBullets = new ArrayList<>();
    private List<Bullet> bulletsToRemove = new ArrayList<>();

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
                TranslateTransition tt = new TranslateTransition(Duration.millis(250), (Node) spaceShip);
                Point mouse = MouseInfo.getPointerInfo().getLocation();

                double newXCoordinate = mouse.getX() - MainClientApplication.mainStage.getX() - (spaceShip.getWidth() / 2) - 5;
                double newYCoordinate = mouse.getY() - MainClientApplication.mainStage.getY() - (spaceShip.getHeight() / 2) - 30;

                if (newXCoordinate < 0) {
                    newXCoordinate = 0;
                } else if (newXCoordinate > gameGrid.getPrefWidth() - spaceShip.getWidth()) {
                    newXCoordinate = gameGrid.getPrefWidth() - spaceShip.getWidth();
                }
                if (newYCoordinate < 0) {
                    newYCoordinate = 0;
                } else if (newYCoordinate > gameGrid.getPrefHeight() - spaceShip.getHeight()) {
                    newYCoordinate = gameGrid.getPrefHeight() - spaceShip.getHeight();
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

    private void update() {

        if (entry == true) {
            setAliens(level);
            entry = false;
        }
        else {
            if (level == 1) {
                updateLevel1();
            }
            else if (level == 2) {
                updateLevel2();
            }

            if (alienList.isEmpty()) {
                // go to next level
                if (level == 1) {
                    level = 2;
                    entry = true;
                    //setSecondLevelAliens();
                }
            }
        }
    }

    public void setAliens(int level) {
        if (level == 1) {
            setFirstLevelAliens();
        }
        if (level == 2) {
            setSecondLevelAliens();
        }
    }

    public void updateLevel1() {
        gameScore += 0.25;
        scoreLabel.textProperty().bind(new SimpleDoubleProperty(gameScore).asString());     //increases score as time passes

        Bullet spaceshipBullet = new Bullet((int) (spaceShip.getTranslateX() + (spaceShip.getWidth() / 2)), (int) spaceShip.getTranslateY(), 5, 15, Color.BLACK, 5.0d, new Vector2D(0, -1), 100.0d);
        gameGrid.getChildren().add(spaceshipBullet);
        spaceShipBullets.add(spaceshipBullet);

        /* TODO: CHECK: WE ADD BULLETS TO gameGrid ANYWAY, IS spaceShipBullets NECESSARY?
        IT IS NOT USED ANYWHERE ELSE, WE CAN REMOVE spaceShipBullets LIST IF IT IS NOT TO BE USED LATER.
        I DELETED BULLETS FROM gameGrid.
        */
        //Iterator<Bullet> it = spaceShipBullets.iterator();
        Iterator<Node> it = gameGrid.getChildren().iterator();
        while (it.hasNext()) {
            Object o2 = it.next();
            if (o2 instanceof IAlien) {
                IAlien alien2 = (IAlien) o2;
                if (!alien2.getAlive()) {
                    it.remove();
                }
            }
            else if (o2 instanceof Bullet) {
                Bullet bullet = (Bullet) o2;
                bullet.setTranslateY(bullet.getTranslateY() - 5);
                // TODO: FIX, <= -15 might not be the best solution, it works for now
                if (bullet.getTranslateY() <= -15) {
                    it.remove();
                }
                else {
                    Iterator<IAlien> it_alien = Aliens().iterator();
                    while (it_alien.hasNext()) {
                        IAlien alien = it_alien.next();
                        if (bullet.getBoundsInParent().intersects(((Node) alien).getBoundsInParent())) {
                            alien.getHit(bullet.getDamage());
                            //it.remove();
                            if (alien.getHealth() <= 0) {
                                alien.setAlive(false);
                                it_alien.remove();
                            }
                        }
                    }
                }
            }
        }

        alienList.stream().filter(e -> e.getAlive()).forEach(alien -> {
            if (spaceShip.getBoundsInParent().intersects(((Node) alien).getBoundsInParent())) {
                alien.setAlive(false);
                System.out.println("collision");
                //spaceShip.setAlive(false);
            }

        });
        alienList.removeIf(alien -> {
            return !alien.getAlive();
        });

        Aliens().stream().filter(e -> e.getAlive()).forEach(alien -> {
            if (spaceShip.getBoundsInParent().intersects(((Node) alien).getBoundsInParent())) {
                alien.setAlive(false);
                System.out.println("collision");
                //spaceShip.setAlive(false);
            }
        });
    }

    public void updateInitial() {
        gameScore += 0.25;
        scoreLabel.textProperty().bind(new SimpleDoubleProperty(gameScore).asString());     //increases score as time passes
        Bullet spaceshipBullet = new Bullet((int) (spaceShip.getTranslateX() + (spaceShip.getWidth() / 2)), (int) spaceShip.getTranslateY(), 5, 15, Color.BLACK, 5.0d, new Vector2D(0, -1), 100.0d);
        gameGrid.getChildren().add(spaceshipBullet);
    }


    public void updateLevel2() {

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
            IAlien newAlien = new Alien((int) positionsList.get(i).x, (int) positionsList.get(i).y, (int) alienWidth, (int) alienHeight, Color.RED, 0, downVector, 200.0d);
            alienList.add(newAlien);

            gameGrid.getChildren().add((Node) newAlien);   //TODO bunu burda yapmak yerine initialize methodunda alienList'i kullanarak yap
        }
    }

    /**
     * This method creates the aliens of the grid for the second level of the game.
     */
    public void setSecondLevelAliens(){
        List<IAlien> alienList = new ArrayList<>();
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
            IAlien newAlien1 = new Alien((int) positionsList.get(i).x, (int) positionsList.get(i).y, (int) alienWidth, (int) alienHeight, Color.YELLOW, 0, downVector, 400.0d);
            //newAlien1 = (IAlien) newAlien1;

            alienList.add(newAlien1);
            gameGrid.getChildren().add((Node) newAlien1);

        }
        for(int i=0; i<12; i++){
            IAlien newAlien = new Alien((int) positionsList.get(12+i).x, (int) positionsList.get(12+i).y, (int) alienWidth, (int) alienHeight, Color.RED, 0, downVector, 200.0d);
            alienList.add(newAlien);
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
}
