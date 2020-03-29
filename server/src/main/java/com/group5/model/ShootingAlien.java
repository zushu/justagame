package com.group5.model;

import com.group5.helper.Vector2D;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

public class ShootingAlien extends AlienDecorator {
    private List<Bullet> bulletList;
    private double shootFrequency;
    private Bullet bulletType;

    public ShootingAlien(IAlien decoratedAlien, double shootFrequency, Bullet bulletType) {
        super(decoratedAlien);
        this.bulletList = new ArrayList<Bullet>();
        this.shootFrequency = shootFrequency;
        this.bulletType = bulletType;
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

    public Bullet getBulletType(){
        return this.bulletType;
    }

    /** 
    * This method calculates initial position of a new bullet.
    * @return Vector2D The initial position of a newly fired bullet. 
    */
    public Vector2D getBulletsInitialPosition(){
        Vector2D bulletsInitialPosition = new Vector2D(this.getPosition().x, this.getLowerBoundary() - this.getBulletType().getHeight() / 2.0d);
        return bulletsInitialPosition;
    }

    /** 
    * This method fires a bullet from alien(simply adds a new bullet to its bulletList).
    */
    public void shootOneBullet() {
        Vector2D downVector = new Vector2D(0.0d, -1.0d); // downwards
        Bullet newBullet = new Bullet(this.getBulletsInitialPosition(), downVector, this.getBulletType().getSpeed(), this.getBulletType().getWidth(), this.getBulletType().getHeight(), this.getBulletType().getDamage());
        bulletList.add(newBullet);
    }

    /** 
    * This method checks if bullets moved out of the grid and updates bullet list.
    * @param gridWidth width of the current grid 
    * @param gridHeight  height of the current grid 
    */
    public void updateBulletList(double gridWidth, double gridHeight)
    {
        Iterator<Bullet> iter = bulletList.iterator();
        while (iter.hasNext()) {
            Bullet bullet = iter.next();
            if (bullet.isOutOfBounds(gridWidth, gridHeight))
            {
                iter.remove();
            }
        }
    }
}