package com.group5.model;

import com.group5.helper.Vector2D;
import java.util.List;


public abstract class AliveGridObject extends GridObject {
    protected double health;
    protected boolean alive;
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


    public boolean getAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    // not a getter
    public void getHit(double bulletDamage) {
        this.health -= bulletDamage;
        if (this.health <= 0.0d) 
        {
            this.health = 0.0d;
            this.alive = false;
        }
    }

}