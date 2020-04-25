package com.group5.controllers;

import com.group5.MainClientApplication;
import com.group5.game.Bullet;
import com.group5.game.MainGame;
import com.group5.game.SpaceShip;
import com.group5.helper.Vector2D;
import javafx.animation.AnimationTimer;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
//import javafx.scene.canvas.Canvas;
//import javafx.scene.canvas.GraphicsContext;

import java.util.List;

// TODO: OBJECTS WILL BE CANVAS OBJECTS
public class LevelOneController {
    private Stage stage;
    private Scene sceneLevelOne;
    private Pane rootOne = new Pane();
    //private GraphicsContext gfxContext; // canvas for our screen, we will draw objects on it

    AnimationTimer timer;

    // prefHeight="800.0" prefWidth="600.0"
    MainGame mainGame = new MainGame();
    //Image spaceShipImage = new Image("/assets/spaceship.png");


    public LevelOneController(Stage stage) {
        mainGame.setFirstLevelAliens();
        //mainGame.getGrid().getSpaceShipList();
    }






}
