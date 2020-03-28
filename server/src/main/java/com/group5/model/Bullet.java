package com.group5.model;
//import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;
import com.group5.helper.Vector2D;

public class Bullet extends GridObject {
    private double damage;

    public Bullet() {
        //super(Vector2D(0, 0), Vector2D(0, 0), 0, 0, 0);
        //this.damage = 0;
    }
    public Bullet(Vector2D position, Vector2D direction, double speed, double width, double height, double damage) {
        super(position, direction, speed, width, height);
        this.damage = damage;
    }

    public double getDamage() {
        return damage;
    }

    public void setDamage(double damage) {
        this.damage = damage;
    }

}