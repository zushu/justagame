package com.group5.model;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;
import java.util.List;


// TODO: DECIDE, SHOULD I CREATE A MainObject INTERFACE FOR SpaceShip AND Alien?
// THEY HAVE A LOT OF FUNCTIONS IN COMMON, DIFFERENT FROM BULLET
public class SpaceShip extends GridObject {
    private int health;
    private double shootFrequency; // can change
    private List<Bullet> bulletList;
    public SpaceShip() {}
    public SpaceShip(Vector2D position, Vector2D direction, double speed, double width, double height, List<Bullet> bulletList, double shootFrequency, int health)
    {
        super(position, direction, speed, width, height);
        this.bulletList = bulletList; // TODO: check, is it correct ???
        this.shootFrequency = shootFrequency;
        this.health = health;
    }

    public List<Bullet> getBulletList() {
        return bulletList;
    }

    public double getShootFrequency() {
        return shootFrequency;
    }

    public int getHealth() {
        return health;
    }

    public void setBulletList(List<Bullet> bulletList) {
        this.bulletList = bulletList;
    }

    public void setShootFrequency(double shootFrequency) {
        this.shootFrequency = shootFrequency;
    }

    public void setHealth(int health) {
        this.health = health;
    }   

    /*public boolean collidesWithBullets(List<Bullet> alienBullets)
    {
        return false;
    }*/

}