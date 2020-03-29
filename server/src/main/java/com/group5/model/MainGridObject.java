package com.group5.model;

import com.group5.helper.Vector2D;
import java.util.List;

// for aliens and spaceship

public abstract class MainGridObject extends GridObject {
    private double health;
    private boolean alive;
    public MainGridObject() {}
    public MainGridObject(Vector2D position, Vector2D direction, double speed, double width, double height, double health) {
        super(position, direction, speed, width, height);
        this.health = health;
        if (health <= 0) {
            this.alive = false;
        }
        this.alive = true;
    }

    public double getHealth() {
        return health;
    }

    public void setHealth(double health) {
        this.health = health;
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
        if (enemyBullet.getFired() == true)
        {
            if (! (enemyBullet.getRightBoundary() <= leftBoundary || enemyBullet.getLeftBoundary() >= rightBoundary || enemyBullet.getUpperBoundary() <= lowerBoundary || enemyBullet.getLowerBoundary() >= upperBoundary))
                {   
                    //this.health -= enemyBullet.getDamage();
                    return true;
                }
        }
        return false;
    }

}