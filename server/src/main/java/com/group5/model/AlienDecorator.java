package com.group5.model;

public abstract class AlienDecorator extends AliveGridObject implements IAlien {
    protected IAlien decoratedAlien;

    public AlienDecorator(IAlien decoratedAlien) {
        this.decoratedAlien = decoratedAlien;
    }
}