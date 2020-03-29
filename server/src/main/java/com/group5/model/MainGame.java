package com.group5.model;

import com.group5.helper.Vector2D;
import java.util.List;
import java.util.ArrayList;

public class MainGame{
    private Grid grid;

    public MainGame(){
        initializeGrid();
    }

    public Grid getGrid(){
        return this.grid;
    }

    public void initializeGrid(){
        double gridWidth = 600.0d;
        double gridHeight = 900.0d;

        Vector2D initialShipPosition = new Vector2D(gridWidth/2, 50.0d);
        Vector2D upVector = new Vector2D(0.0d,1.0d);
        double spaceShipWidth = 25.0d;
        double spaceShipHeight = 40.0d;
        List<Bullet> spaceShipBulletList = new ArrayList<Bullet>();
        double spaceShipShootingFrequency = 1.0d;
        double spaceShipHealth = 3000.0d;
        Vector2D downVector = new Vector2D(0.0d, -1.0d);
        SpaceShip baseShip = new SpaceShip( initialShipPosition, upVector, 0.0d, spaceShipWidth, spaceShipHeight, 
                                            spaceShipBulletList, spaceShipShootingFrequency, spaceShipHealth ,
                                            new Bullet(initialShipPosition, downVector, 20.0d, 5.0d, 8.0d, 300.0d));
        List<SpaceShip> spaceShipList = new ArrayList<>();
        spaceShipList.add(baseShip);

        List<IAlien> alienList = new ArrayList<>();
        Grid grid = new Grid( gridWidth, gridHeight, spaceShipList, alienList);

        this.grid = grid;
    }

    public void setFirstLevelAliens(){
        List<IAlien> alienList = new ArrayList<>();
        double alienWidth = 15.0d;
        double alienHeight = 15.0d;
        Vector2D downVector = new Vector2D(0.0, -1.0d);

        List<Vector2D> positionsList = createUniformAlienPositions( 6, 10, 50.0d);
        for(int i=0; i<60; i++){
            alienList.add(new Alien(positionsList.get(i), downVector, 0.0d, alienWidth, alienHeight, 200.0d));
        }

        this.getGrid().setAlienList(alienList);
    }

    public void setSecondLevelAliens(){
        List<IAlien> alienList = new ArrayList<>();
        double alienWidth = 15.0d;
        double alienHeight = 15.0d;
        Vector2D downVector = new Vector2D(0.0d, -1.0d);

        List<Vector2D> positionsList = createUniformAlienPositions( 6, 10, 50.0d);
        for(int i=0; i<30; i++){
            alienList.add(new DefensiveAlien(new Alien(positionsList.get(i), downVector, 0.0d, alienWidth, alienHeight, 200.0d)));
        }
        for(int i=0; i<30; i++){
            alienList.add(new Alien(positionsList.get(30+i), downVector, 0.0d, alienWidth, alienHeight, 200.0d));
        }

        this.getGrid().setAlienList(alienList);
    }

    public void setThirdLevelAliens(){
        List<IAlien> alienList = new ArrayList<>();
        double alienWidth = 15.0d;
        double alienHeight = 15.0d;
        Vector2D downVector = new Vector2D(0.0d, -1.0d);

        List<Vector2D> positionsList = createUniformAlienPositions( 6, 10, 50.0d);
        for(int i=0; i<20; i++){
            alienList.add(new DefensiveAlien(new Alien(positionsList.get(i), downVector, 0.0d, alienWidth, alienHeight, 200.0d)));
        }
        double alienShootingFrequency = 1.0d;
        for(int i=0; i<20; i++){
            alienList.add(new ShootingAlien(new Alien(positionsList.get(20+i), downVector, 0.0d, alienWidth, alienHeight, 200.0d), 
                                        alienShootingFrequency, new Bullet(positionsList.get(20+i),downVector, 20.0d,5.0d, 8.0d, 300.0d)));
        }
        for(int i=0; i<20; i++){
            alienList.add(new Alien(positionsList.get(40+i), downVector, 0.0d, alienWidth, alienHeight, 200.0d));
        }

        this.getGrid().setAlienList(alienList);
    }



    private List<Vector2D> createUniformAlienPositions( Integer rowCount, Integer columnCount, double rowPadding){
        double columnPadding = this.getGrid().getWidth() / columnCount;

        List<Vector2D> positionsList = new ArrayList<>(rowCount*columnCount);
        for(int i=0; i<rowCount; i++){
            for(int j=0; j<rowCount; j++){
                positionsList.add(new Vector2D( (columnPadding/2) + (j*columnPadding), this.getGrid().getHeight() - (rowPadding + (i*rowPadding)) ));
            } 
        } 
        return positionsList;
    }
}