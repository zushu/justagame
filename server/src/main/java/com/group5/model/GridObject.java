package com.group5.model;

import com.group5.helper.Vector2D;
import java.util.List;
//import java.lang.Math;

public abstract class GridObject {
    protected Vector2D position;  // x, y
    protected Vector2D direction; // x, y
    protected double speed;
    protected double width;
    protected double height;
    protected double leftBoundary;
    protected double rightBoundary;
    protected double lowerBoundary;
    protected double upperBoundary;

    public GridObject() {}
    public GridObject(Vector2D position, Vector2D direction, double speed, double width, double height) {
        this.position = position;
        this.direction = direction;
        this.speed = speed;
        this.width = width;
        this.height = height; 
        updateBoundaries(position);
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

    public double getLeftBoundary() {
        return leftBoundary;
    }

    public double getRightBoundary() {
        return rightBoundary;
    }

    public double getLowerBoundary() {
        return lowerBoundary;
    }

    public double getUpperBoundary() {
        return upperBoundary;
    }

    public void setPosition(Vector2D position) {
        this.position = position;
        updateBoundaries(position);
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

    public void updateBoundaries(Vector2D newPosition) {
        this.leftBoundary = newPosition.x - width/2.0d;
        this.rightBoundary = newPosition.x + width/2.0d;
        this.lowerBoundary = newPosition.y - height/2.0d;
        this.upperBoundary = newPosition.y + height/2.0d;
    }

    // TODO: CHECK GRID BOUNDARIES
    protected void move(Vector2D newStep) // update position
    {
        // TODO: unnecessary steps here???
        Vector2D newPosition = new Vector2D(position);
        newPosition.add(newStep);
        setPosition(newPosition);
    }

    // move in one direction by the amount of speed units
    // TODO: is it correct???
    protected void moveLeft()
    {
        move(new Vector2D((-1)*speed, 0.0d));
    }

    protected void moveRight()
    {
        move(new Vector2D(speed, 0.0d));
    }

    protected void moveDown()
    {
        move(new Vector2D(0.0d, (-1)*speed));
    }

    protected void moveUp()
    {
        move(new Vector2D(0.0d, speed));
    }

}