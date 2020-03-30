package com.group5.model;

import com.group5.helper.Vector2D;

public class Alien extends AliveGridObject implements IAlien {
    public Alien() {}
    public Alien(Vector2D position, Vector2D direction, double speed, double width, double height, double health) {
        super(position, direction, speed, width, height, health);
    }

    @Override
    public boolean doCollide(IAlien gridObj1, GridObject gridObj2) {
        if ( !( gridObj1.getRightBoundary() <= gridObj2.getLeftBoundary() || gridObj1.getLeftBoundary() >= gridObj2.getRightBoundary() || 
                gridObj1.getUpperBoundary() <= gridObj2.getLowerBoundary() || gridObj1.getLowerBoundary() >= gridObj2.getUpperBoundary() ))
        {   
            return true;
        }
        return false;
    }

    @Override
    public boolean doCollide(GridObject gridObj1, IAlien gridObj2) {
        if ( !( gridObj1.getRightBoundary() <= gridObj2.getLeftBoundary() || gridObj1.getLeftBoundary() >= gridObj2.getRightBoundary() || 
                gridObj1.getUpperBoundary() <= gridObj2.getLowerBoundary() || gridObj1.getLowerBoundary() >= gridObj2.getUpperBoundary() ))
        {   
            return true;
        }
        return false;
    }
}