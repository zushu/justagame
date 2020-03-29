package com.group5.model;

import com.group5.helper.Vector2D;
import java.util.List;


public abstract class AliveGridObject extends GridObject {
    protected double health;
    protected Boolean alive;
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


    public Boolean getAlive() {
        return alive;
    }

    public void setAlive(Boolean alive) {
        this.alive = alive;
    }

    /** 
    * Alive objects get damage via this method
    * @param bulletDamage  objects gets that much damage
    */
    protected void getHit(double bulletDamage) {
        this.health -= bulletDamage;
        if (this.health <= 0.0d) 
        {
            this.health = 0.0d;
            this.alive = false;
        }
    }

}