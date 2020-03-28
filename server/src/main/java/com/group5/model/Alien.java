package com.group5.model;

import com.group5.helper.Vector2D;
//import java.util.List;

public class Alien extends GridObject {
    private double health;
    public Alien() {
        //super(Vector2D(0, 0), Vector2D(0, 0), 0, 0, 0);
    }
    public Alien(Vector2D position, Vector2D direction, double speed, double width, double height, double health) {
        super(position, direction, speed, width, height);
        this.health = health;
    }

    public double getHealth() {
        return health;
    }

    public void setHealth(double health) {
        this.health = health;
    }
}