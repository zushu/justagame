package com.group5.model;

import com.group5.helper.Vector2D;
import java.util.List;
import java.util.Iterator;


// TODO: DECIDE, SHOULD I CREATE A MainObject INTERFACE FOR SpaceShip AND Alien?
// THEY HAVE A LOT OF FUNCTIONS IN COMMON, DIFFERENT FROM BULLET
public class SpaceShip extends MainGridObject {
    //private double health;
    private double shootFrequency; // can change
    private List<Bullet> bulletList;
    public SpaceShip() {}
    public SpaceShip(Vector2D position, Vector2D direction, double speed, double width, double height, List<Bullet> bulletList, double shootFrequency, double health)
    {
        super(position, direction, speed, width, height, health);
        this.bulletList = bulletList; // TODO: check, is it correct ???
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

    // TODO: CALL IT IN A THREAD WITH SLEEPS USING shootFrequency
    public void shootOneBullet(double bulletSpeed, double bulletWidth, double bulletHeight, double bulletDamage) {
        Vector2D bulletPosition = new Vector2D(this.getPosition().x, this.getUpperBoundary() + bulletHeight / 2.0d);
        Vector2D bulletDirection = new Vector2D(0.0d, 1.0d); // upwards
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