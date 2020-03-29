package com.group5.model;

import com.group5.helper.Vector2D;
import java.util.List;


public class Grid {
    private double width;                       // number of cells horizontally
    private double height;                      // number of cells vertically
    private List<SpaceShip> spaceShipList;      // one element in single player mode
    private List<Alien> alienList;
    private int level;

    public Grid() {}
    public Grid(double width, double height, List<SpaceShip> spaceShipList, List<Alien> alienList, int level)
    {
        this.width = width;
        this.height = height;
        this.spaceShipList = spaceShipList;
        this.alienList = alienList;
        this.level = level;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public List<SpaceShip> getSpaceShipList() {
        return spaceShipList;
    }

    public List<Alien> getAlienList() {
        return alienList;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public void setSpaceShipList(List<SpaceShip> spaceShipList) {
        this.spaceShipList = spaceShipList;
    }

    public void setAlienList(List<Alien> alienList) {
        this.alienList = alienList;
    } 

}