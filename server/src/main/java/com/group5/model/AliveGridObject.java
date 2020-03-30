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

<<<<<<< HEAD
    // not a getter
    public void getHit(double bulletDamage) {
=======
    /** 
    * Alive objects get damage via this method
    * @param bulletDamage  objects gets that much damage
    */
    protected void getHit(double bulletDamage) {
>>>>>>> c5fb6d1d1c69348b51f72f99e9e34c3c73aa62db
        this.health -= bulletDamage;
        if (this.health <= 0.0d) 
        {
            this.health = 0.0d;
            this.alive = false;
        }
    }

}