package com.group5.model;

public class DefensiveAlien extends AlienDecorator {
    public DefensiveAlien(Alien decoratedAlien) {
        super(decoratedAlien);
    }

    @Override
    protected void getHit(double bulletDamage) {

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
}