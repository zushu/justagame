package com.group5.model;

import com.group5.helper.Vector2D;

public class Bullet extends GridObject {
    private double damage;

    public Bullet() {}
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

    /**
     * This method checks if bullet is still in grid.
     * ASSUMED: origin is at the lower left corner of the grid
     * @param gridWidth width of the current grid 
     * @param gridHeight  height of the current grid 
     * @return Boolean true if bullet is still in the grid, false otherwise.
     */
    public Boolean isOutOfBounds(double gridWidth, double gridHeight) {
        if (rightBoundary <= 0 || leftBoundary >= gridWidth || upperBoundary <= 0 || lowerBoundary >= gridHeight)
        {
            return true;
        }
        return false;
    }

}