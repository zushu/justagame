package com.group5.model;

import com.group5.helper.Vector2D;
import java.util.List;
import java.util.Iterator;


// TODO: DECIDE, SHOULD I CREATE A MainObject INTERFACE FOR SpaceShip AND Alien?
// THEY HAVE A LOT OF FUNCTIONS IN COMMON, DIFFERENT FROM BULLET
public class SpaceShip extends AliveGridObject {
    private double shootFrequency; // can change
    private List<Bullet> bulletList;
    private Bullet bulletType;

    public SpaceShip() {}
    public SpaceShip(Vector2D position, Vector2D direction, double speed, double width, double height, 
                    List<Bullet> bulletList, double shootFrequency, double health, Bullet bulletType)
    {
        super(position, direction, speed, width, height, health);
        this.bulletList = bulletList; // TODO: check, is it correct ???     this may make just references equal, so we may need deep copy
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

    // TODO: CALL IT IN A THREAD WITH SLEEPS USING shootFrequency
    public Vector2D getBulletsInitialPosition(){
        Vector2D bulletsInitialPosition = new Vector2D(this.getPosition().x, this.getLowerBoundary() - this.getBulletType().getHeight() / 2.0d);
        return bulletsInitialPosition;
    }

    public void shootOneBullet() {
        Vector2D upVector = new Vector2D(0.0d, 1.0d); // upwards
        Bullet newBullet = new Bullet(this.getBulletsInitialPosition(), upVector, this.getBulletType().getSpeed(), this.getBulletType().getWidth(), this.getBulletType().getHeight(), this.getBulletType().getDamage());
        bulletList.add(newBullet);
    }

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