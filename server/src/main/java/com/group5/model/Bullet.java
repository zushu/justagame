package com.group5.model;
//import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;
import com.group5.helper.Vector2D;

public class Bullet extends GridObject {
    private double damage;
    private boolean fired = false;

    public Bullet() {}
    public Bullet(Vector2D position, Vector2D direction, double speed, double width, double height, double damage) {
        super(position, direction, speed, width, height);
        this.damage = damage;
    }

    public double getDamage() {
        return damage;
    }

    // TODO: should i name it isFired to be more intuitive?
    public boolean getFired() {
        return fired;
    }

    public void setDamage(double damage) {
        this.damage = damage;
    }

    public void setFired(boolean fired) {
        this.fired = fired;
    }

    // ASSUMED: origin is at the lower left corner of the grid
    public boolean outOfBounds(double gridWidth, double gridHeight) {
        if (rightBoundary <= 0 || leftBoundary >= gridWidth || upperBoundary <= 0 || lowerBoundary >= gridHeight)
        {
            return true;
        }
        return false;
    }

}