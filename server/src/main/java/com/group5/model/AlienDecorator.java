package com.group5.model;

public abstract class AlienDecorator extends AliveGridObject implements IAlien {
    protected Alien decoratedAlien;

    public AlienDecorator(Alien decoratedAlien) {
        this.decoratedAlien = decoratedAlien;
    }
}