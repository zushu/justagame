package com.group5.game;

import com.group5.helper.Vector2D;
import java.util.List;
import java.util.ArrayList;
import javafx.scene.paint.Color;

public class MainGame{
    private Grid grid;

    public MainGame(){
        initializeGrid();
    }

    public Grid getGrid(){
        return this.grid;
    }

    /** 
    * This method is used to initialize grid.
    */
    public void initializeGrid(){
        double gridWidth = 600.0d;
        double gridHeight = 900.0d;

        Vector2D initialShipPosition = new Vector2D(gridWidth/2, 50.0d);
        Vector2D upVector = new Vector2D(0.0d,-1.0d);
        double spaceShipWidth = 25.0d;
        double spaceShipHeight = 40.0d;
        List<Bullet> spaceShipBulletList = new ArrayList<Bullet>();
        double spaceShipShootingFrequency = 1.0d;
        double spaceShipHealth = 3000.0d;
        Color color = Color.BLUE;
        Vector2D downVector = new Vector2D(0.0d, 1.0d);
        /*SpaceShip baseShip = new SpaceShip( initialShipPosition, upVector, 0.0d, spaceShipWidth, spaceShipHeight,
                                            spaceShipBulletList, spaceShipShootingFrequency, spaceShipHealth ,
                                            new Bullet(initialShipPosition, downVector, 20.0d, 5.0d, 8.0d, 300.0d));*/
        SpaceShip baseShip = new SpaceShip((int) (gridWidth/2 - spaceShipWidth/2), (int) (gridHeight - spaceShipHeight), (int) spaceShipWidth, (int) spaceShipHeight, color, 0, upVector, spaceShipHealth, spaceShipShootingFrequency);
        List<SpaceShip> spaceShipList = new ArrayList<>();
        spaceShipList.add(baseShip);

        List<IAlien> alienList = new ArrayList<>();
        Grid grid = new Grid( gridWidth, gridHeight, spaceShipList, alienList);

        this.grid = grid;
    }

    /** 
    * This method creates the aliens of the grid for the first level of the game.
    */
    public void setFirstLevelAliens(){
        List<IAlien> alienList = new ArrayList<>();
        double alienWidth = 15.0d;
        double alienHeight = 15.0d;
        Vector2D downVector = new Vector2D(0.0, 1.0d);

        List<Vector2D> positionsList = createUniformAlienPositions( 6, 10, 50.0d);
        for(int i=0; i<60; i++){
            //alienList.add(new Alien(positionsList.get(i), downVector, 0.0d, alienWidth, alienHeight, 200.0d));
            alienList.add(new Alien((int) positionsList.get(i).x, (int) positionsList.get(i).y, (int) alienWidth, (int) alienHeight, Color.RED, 0, downVector, 200.0d));
        }

        this.getGrid().setAlienList(alienList);
    }

    /** 
    * This method creates the aliens of the grid for the second level of the game.
    */
    public void setSecondLevelAliens(){
        List<IAlien> alienList = new ArrayList<>();
        double alienWidth = 15.0d;
        double alienHeight = 15.0d;
        Vector2D downVector = new Vector2D(0.0d, 1.0d);

        List<Vector2D> positionsList = createUniformAlienPositions( 6, 10, 50.0d);
        for(int i=0; i<30; i++){
            alienList.add(new DefensiveAlien(new Alien((int) positionsList.get(i).x, (int) positionsList.get(i).y, (int) alienWidth, (int) alienHeight, Color.YELLOW, 0, downVector, 200.0d)));
        }
        for(int i=0; i<30; i++){
            alienList.add(new Alien((int) positionsList.get(30+i).x, (int) positionsList.get(30+i).y, (int) alienWidth, (int) alienHeight, Color.RED, 0, downVector, 200.0d));
        }

        this.getGrid().setAlienList(alienList);
    }

    /** 
    * This method creates the aliens of the grid for the third level of the game.
    */
    public void setThirdLevelAliens(){
        List<IAlien> alienList = new ArrayList<>();
        double alienWidth = 15.0d;
        double alienHeight = 15.0d;
        Vector2D downVector = new Vector2D(0.0d, 1.0d);

        List<Vector2D> positionsList = createUniformAlienPositions( 6, 10, 50.0d);
        for(int i=0; i<20; i++){
            alienList.add(new DefensiveAlien(new Alien((int) positionsList.get(i).x, (int) positionsList.get(i).y, (int) alienWidth, (int) alienHeight, Color.YELLOW, 0, downVector, 200.0d)));
        }
        double alienShootingFrequency = 1.0d;
        for(int i=0; i<20; i++){
            alienList.add(new ShootingAlien(new Alien((int) positionsList.get(20+i).x, (int) positionsList.get(20+i).y, (int) alienWidth, (int) alienHeight, Color.ORANGE, 0, downVector, 200.0d), alienShootingFrequency, new Bullet((int) positionsList.get(20+i).x, (int) positionsList.get(20+i).y, 5, 8, Color.VIOLET, 20, downVector, 300.0d)));

                    //new Bullet(positionsList.get(20+i),downVector, 20.0d,5.0d, 8.0d, 300.0d)));
        }
        for(int i=0; i<20; i++){
            alienList.add(new Alien((int) positionsList.get(40+i).x, (int) positionsList.get(40+i).y, (int) alienWidth, (int) alienHeight, Color.RED, 0, downVector, 200.0d));
        }

        this.getGrid().setAlienList(alienList);
    }

    /** 
    * This method creates the aliens of the grid for the forth level of the game.
    */
   public void setForthLevelAliens(){
        List<IAlien> alienList = new ArrayList<>();
        double alienWidth = 15.0d;
        double alienHeight = 15.0d;
        Vector2D downVector = new Vector2D(0.0d, 1.0d);

        List<Vector2D> positionsList = createUniformAlienPositions( 6, 10, 50.0d);
        for(int i=0; i<20; i++){
            alienList.add(new DefensiveAlien(new Alien((int) positionsList.get(i).x, (int) positionsList.get(i).y, (int) alienWidth, (int) alienHeight, Color.YELLOW, 0, downVector, 200.0d)));
        }
        double alienShootingFrequency = 1.0d;
        for(int i=0; i<20; i++){
            alienList.add(new ShootingAlien(new Alien((int) positionsList.get(20+i).x, (int) positionsList.get(20+i).y, (int) alienWidth, (int) alienHeight, Color.YELLOW, 0, downVector, 200.0d),
                                        alienShootingFrequency, new Bullet((int) positionsList.get(20+i).x, (int) positionsList.get(20+i).y, 5, 8, Color.VIOLET, 20, downVector, 300.0d)));
        }
        for(int i=0; i<20; i++){
            alienList.add(new DefensiveAlien(new ShootingAlien(new Alien((int) positionsList.get(40+i).x, (int) positionsList.get(40+i).y, (int) alienWidth, (int) alienHeight, Color.YELLOW, 0, downVector, 200.0d),
                                        alienShootingFrequency, new Bullet((int) positionsList.get(40+i).x, (int) positionsList.get(40+i).y, 5, 8, Color.VIOLET, 30, downVector, 400.0d))));

                    //new Bullet(positionsList.get(40+i),downVector, 30.0d, 5.0d, 8.0d, 400.0d))));
        }

        this.getGrid().setAlienList(alienList);
    }


    /** 
    * This method just prepares a list of positions(distributed uniformly) to use while placing aliens to the grid.
    * @param rowCount Aliens will be aligned in rowCount many rows
    * @param columnCount  Aliens will be aligned in columnCount many columns
    * @param rowPadding  Rows will be rowPadding much padded from the top of the grid.
    * @return List<Vector2D> This is the list of Alien positions.
    */
    private List<Vector2D> createUniformAlienPositions( Integer rowCount, Integer columnCount, double rowPadding){
        double columnPadding = this.getGrid().getWidth() / columnCount;

        List<Vector2D> positionsList = new ArrayList<>(rowCount*columnCount);
        for(int i=0; i<rowCount; i++){
            for(int j=0; j<columnCount; j++){
                // TODO: CHECK
                positionsList.add(new Vector2D( (columnPadding/2) + (j*columnPadding), (rowPadding + (i*rowPadding)) ));
            } 
        } 
        return positionsList;
    }
}