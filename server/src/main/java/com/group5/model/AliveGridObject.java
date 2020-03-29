package com.group5.model;

import com.group5.helper.Vector2D;
import java.util.List;

// for aliens and spaceship

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

    // to check collision with all bullets 
    // if hit by one, update health and return.
    // to be called in a loop
    protected boolean collidesWithBullets(List<Bullet> enemyBullets)
    {
        boolean collides = false;

        for (Bullet enemyBullet : enemyBullets)
        {
            if (collidesWithBullet(enemyBullet))
            {
                collides = true;
                if (this.health - enemyBullet.getDamage() <= 0.0d) 
                {
                    this.health = 0.0d;
                    this.alive = false;
                }
                else 
                {
                    this.health -= enemyBullet.getDamage();
                }

                break;
            }
        }
        return collides;
    }

    protected boolean collidesWithBullet(Bullet enemyBullet) {
        if (! (enemyBullet.getRightBoundary() <= leftBoundary || enemyBullet.getLeftBoundary() >= rightBoundary || enemyBullet.getUpperBoundary() <= lowerBoundary || enemyBullet.getLowerBoundary() >= upperBoundary))
        {   
            //this.health -= enemyBullet.getDamage();
            return true;
        }
        return false;
    }

    // not a getter
    protected void getHit(double bulletDamage) {

        if (this.health - bulletDamage <= 0.0d) 
        {
            this.health = 0.0d;
            this.alive = false;
        }
        else 
        {
            this.health -= bulletDamage;
        }
    }

}