package com.group5.model;

import com.group5.helper.Vector2D;
import java.util.List;
import java.util.Iterator;

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

    public void shootOneBullet(double bulletSpeed, double bulletWidth, double bulletHeight, double bulletDamage) {
        Vector2D bulletPosition = new Vector2D(this.getPosition().x, this.getLowerBoundary() - bulletHeight / 2.0d);
        Vector2D bulletDirection = new Vector2D(0.0d, -1.0d); // downwards
        Bullet newBullet = new Bullet(bulletPosition, bulletDirection, bulletSpeed, bulletWidth, bulletHeight, bulletDamage);
        newBullet.setFired(true);
        bulletList.add(newBullet);
    }

    public void updateBulletList(double gridWidth, double gridHeight)
    {
        Iterator<Bullet> iter = bulletList.iterator();
        while (iter.hasNext()) {
            Bullet bullet = iter.next();
            if (bullet.outOfBounds(gridWidth, gridHeight))
            {
                iter.remove();
            }
        }
    }
}