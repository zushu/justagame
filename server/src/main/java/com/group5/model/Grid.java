package com.group5.model;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;
import java.util.List;


public class Grid {
    // double or int?
    private int width; // number of cells horizontally
    private int height; // // number of cells vertically
    private List<SpaceShip> spaceShipList; // one element in single player mode
    private List<Alien> alienList;

    public Grid() {}
    public Grid(int width, int height, List<SpaceShip> spaceShipList, List<Alien> alienList)
    {
        this.width = width;
        this.height = height;
        this.spaceShipList = spaceShipList;
        this.alienList = alienList;
    }
}