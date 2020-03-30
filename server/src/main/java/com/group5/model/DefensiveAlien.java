package com.group5.model;

public class DefensiveAlien extends AlienDecorator {
    public DefensiveAlien(IAlien decoratedAlien) {
        super(decoratedAlien);
    }

    @Override
    public void getHit(double bulletDamage) {

        if (this.health - bulletDamage/2 <= 0.0d) 
        {
            this.health = 0.0d;
            this.alive = false;
        }
        else 
        {
            this.health -= bulletDamage/2;
        }
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