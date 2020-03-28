package com.group5.model;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;
import java.util.List;

public class Alien extends GridObject {
    private int health;
    private List<Bullet> bulletList;
    private double shootFrequency;
    public Alien() {
        //super(Vector2D(0, 0), Vector2D(0, 0), 0, 0, 0);
    }
    public Alien(Vector2D position, Vector2D direction, double speed, double width, double height, List<Bullet> bulletList, double shootFrequency) {
        super(position, direction, speed, width, height);
        this.bulletList = bulletList; // TODO: check, is it correct ???
        this.shootFrequency = shootFrequency;
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
}