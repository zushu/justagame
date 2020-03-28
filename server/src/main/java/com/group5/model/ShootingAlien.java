package com.group5.model;

import com.group5.helper.Vector2D;
import java.util.List;

public class ShootingAlien extends Alien {
    private List<Bullet> bulletList;
    private double shootFrequency;

    public ShootingAlien() {}
    public ShootingAlien(Vector2D position, Vector2D direction, double speed, double width, double height, double health, List<Bullet> bulletList, double shootFrequency)
    {
        super(position, direction, speed, width, height, health);
        this.bulletList = bulletList;
        this.shootFrequency = shootFrequency;
    }

    public List<Bullet> getBulletList() {
        return bulletList;
    }

    public double getShootFrequency() {
        return shootFrequency;
    }

    public void setBulletList(List<Bullet> bulletList) {
        this.bulletList = bulletList;
    }

    public void setShootFrequency(double shootFrequency) {
        this.shootFrequency = shootFrequency;
    }
}