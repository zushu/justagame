package com.group5.model;

import com.group5.helper.Vector2D;
import java.util.List;

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

    /** 
    * This method updates gridObject's boundaries.
    * @param newPosition the new position of the gridObject
    */
    public void updateBoundaries(Vector2D newPosition) {
        this.leftBoundary = newPosition.x - width/2.0d;
        this.rightBoundary = newPosition.x + width/2.0d;
        this.lowerBoundary = newPosition.y - height/2.0d;
        this.upperBoundary = newPosition.y + height/2.0d;
    }

    /** 
    * This method adds newStep to the gridObject's current position.
    * @param newStep step to be added to the gridObject's position.
    */
    public void move(Vector2D newStep) // update position    // TODO: CHECK GRID BOUNDARIES
    {   
        Vector2D newPosition = new Vector2D(position);          // TODO: unnecessary steps here???
        newPosition.add(newStep);
        setPosition(newPosition);
    }

    /** 
    * This method moves gridObject in left direction by the amount of speed units  
    */
    public void moveLeft()                                   // TODO: is it correct???
    {
        move(new Vector2D((-1)*speed, 0.0d));
    }

    /** 
    * This method moves gridObject in right direction by the amount of speed units  
    */
    public void moveRight()
    {
        move(new Vector2D(speed, 0.0d));
    }

    /** 
    * This method moves gridObject in down direction by the amount of speed units  
    */
    public void moveDown()
    {
        move(new Vector2D(0.0d, (-1)*speed));
    }

    /** 
    * This method moves gridObject in up direction by the amount of speed units  
    */
    public void moveUp()
    {
        move(new Vector2D(0.0d, speed));
    }

    /** 
    * This method checks if two gridObjects are colliding.  
    * @param gridObj1 object_1 to check collusion
    * @param gridObj2 object_2 to check collusion
    * @return Boolean true if two objects are colliding, false otherwise.
    */
    public boolean doCollide(GridObject gridObj1, GridObject gridObj2) {
        if ( !( gridObj1.getRightBoundary() <= gridObj2.getLeftBoundary() || gridObj1.getLeftBoundary() >= gridObj2.getRightBoundary() || 
                gridObj1.getUpperBoundary() <= gridObj2.getLowerBoundary() || gridObj1.getLowerBoundary() >= gridObj2.getUpperBoundary() ))
        {   
            return true;
        }
        return false;
    }

}