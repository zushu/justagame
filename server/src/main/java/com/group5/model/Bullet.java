package com.group5.model;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

public class Bullet extends GridObject {
    private int damage;

    public Bullet() {
        //super(Vector2D(0, 0), Vector2D(0, 0), 0, 0, 0);
        //this.damage = 0;
    }
    public Bullet(Vector2D position, Vector2D direction, double speed, double width, double height, int damage) {
        super(position, direction, speed, width, height);
        this.damage = damage;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

}