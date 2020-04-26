package com.group5.game;

import com.group5.helper.Vector2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

// alien interface for decorator design pattern
public interface IAlien {
    public Vector2D getPosition();
    public Vector2D getDirection();
    public double getSpeed();
    public double getWidth();
    public double getHeight();
    public Color getColor();
    public void updateBoundaries(Vector2D newPosition);
    public double getLeftBoundary();
    public double getRightBoundary();
    public double getLowerBoundary();
    public double getUpperBoundary();
    public void setPosition(Vector2D position);
    public void setDirection(Vector2D direction);
    public void setSpeed(double speed);
    public void setWidth(double width);
    public void setHeight(double height);
    public void setColor(Color color);
    public void move(Vector2D newStep);
    public void moveLeft();
    public void moveRight();
    public void moveDown();
    public void moveUp();
    public boolean doCollide(GridObject gridObj1, GridObject gridObj2);
    public boolean doCollide(IAlien gridObj1, GridObject gridObj2);
    public boolean doCollide(GridObject gridObj1, IAlien gridObj2);
    public double getHealth();
    public void setHealth(double health);
    public boolean getAlive();
    public void setAlive(boolean alive);
    public void getHit(double bulletDamage);
    //public void draw(GraphicsContext context, Image image);

}