package com.group5.model;

import com.group5.helper.Vector2D;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

public class ShootingAlien extends AlienDecorator {
    private List<Bullet> bulletList;
    private double shootFrequency;
    private Bullet bulletType;

    //bulletlist başta boş olacağı için constructor'a parametre olarak vermedim
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

    public Vector2D getBulletsInitialPosition(){
        Vector2D bulletsInitialPosition = new Vector2D(this.getPosition().x, this.getLowerBoundary() - this.getBulletType().getHeight() / 2.0d);
        return bulletsInitialPosition;
    }

    public void shootOneBullet() {
        Vector2D downVector = new Vector2D(0.0d, -1.0d); // downwards
        Bullet newBullet = new Bullet(this.getBulletsInitialPosition(), downVector, this.getBulletType().getSpeed(), this.getBulletType().getWidth(), this.getBulletType().getHeight(), this.getBulletType().getDamage());
        bulletList.add(newBullet);
    }

    // from Iterator design pattern slide
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

    @Override
    public boolean doCollide(IAlien gridObj1, GridObject gridObj2) {
        if ( !( gridObj1.getRightBoundary() <= gridObj2.getLeftBoundary() || gridObj1.getLeftBoundary() >= gridObj2.getRightBoundary() || 
                gridObj1.getUpperBoundary() <= gridObj2.getLowerBoundary() || gridObj1.getLowerBoundary() >= gridObj2.getUpperBoundary() ))
        {   
            return true;
        }
        return false;
    }

    @Override
    public boolean doCollide(GridObject gridObj1, IAlien gridObj2) {
        if ( !( gridObj1.getRightBoundary() <= gridObj2.getLeftBoundary() || gridObj1.getLeftBoundary() >= gridObj2.getRightBoundary() || 
                gridObj1.getUpperBoundary() <= gridObj2.getLowerBoundary() || gridObj1.getLowerBoundary() >= gridObj2.getUpperBoundary() ))
        {   
            return true;
        }
        return false;
    }
}