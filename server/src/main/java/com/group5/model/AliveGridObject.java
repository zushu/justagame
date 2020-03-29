package com.group5.model;

import com.group5.helper.Vector2D;
import java.util.List;


public abstract class AliveGridObject extends GridObject {
    private double health;
    private boolean alive;

    public AliveGridObject() {}

    public AliveGridObject(Vector2D position, Vector2D direction, double speed, double width, double height, double health) {
        super(position, direction, speed, width, height);
        this.health = health;
        this.alive = true;
    }

    public double getHealth() {
        return health;
    }

    public void setHealth(double health) {
        this.health = health;
    }

    public void getHit(double damage){
        this.health -= damage;
        if (this.health <= 0.0d){
            this.alive = false;
        }
    }

}