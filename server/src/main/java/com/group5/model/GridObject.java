package com.group5.model;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

public abstract class GridObject {
    private Vector2D position;  // x, y
    private Vector2D direction; // x, y
    private double speed;
    private double width;
    private double height;

    public GridObject() {}
    public GridObject(Vector2D position, Vector2D direction, double speed, double width, double height) {
        this.position = position;
        this.direction = direction;
        this.speed = speed;
        this.width = width;
        this.height = height; 
    }

    public Vector2D getPosition() {
        return position;
    }

    public Vector2D getDirection() {
        return direction;
    }

    public double getSpeed() {
        return speed;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public void setPosition(Vector2D position) {
        this.position = position;
    }

    public void setDirection(Vector2D direction) {
        this.direction = direction;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    protected void move(Vector2D newPosition) // update position
    {
        setPosition(newPosition);
    }

}