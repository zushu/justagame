package com.group5.model;

import com.group5.helper.Vector2D;
//import java.lang.Math;

public abstract class GridObject {
    private Vector2D position;  // x, y
    private Vector2D direction; // x, y
    private double speed;
    private double width;
    private double height;
    // angle of orientation from x axis counterclockwise
    private double angle;

    // TODO: KEEP DIRECTION AS A UNIT VECTOR
    // CHANGE DIRECTION IN ROTATE FUNCTION

    public GridObject() {}
    public GridObject(Vector2D position, Vector2D direction, double speed, double width, double height) {
        this.position = position;
        this.direction = direction;
        this.speed = speed;
        this.width = width;
        this.height = height; 
        // if angle is pi/2 or -pi/2 (atan2 undefined)
        if (direction.x - position.x == 0) {
            // looking upwards
            if (direction.y >= position.y) {
                this.angle = Math.PI / 2;
            }
            // looking downwards
            if (direction.y < position.y) {
                this.angle = - Math.PI / 2;
            }
        }
        else {
            //atan2(y,x)
            this.angle = Math.atan2(direction.y - position.y, direction.x - position.x); 
        }
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

    public double getAngle() {
        return angle;
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

    public void setAngle(double angle) {
        this.angle = angle;
    }
    // TODO: CHECK GRID BOUNDARIES
    protected void move(Vector2D newStep) // update position
    {
        Vector2D newPosition = position;
        newPosition.add(newStep);
        setPosition(newPosition);
    }

    /*protected void rotate(double rotationAngle) // angle in radians
    {
        double newAngle = angle + rotationAngle;
        direction.x = 
        if (newAngle >= 2* Math.PI) {
            setAngle(newAngle - 2*Math.PI);
        }
        if (newAngle < 0 )
        
    }*/

}