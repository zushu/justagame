package com.group5.game;

public abstract class AlienDecorator extends AliveGridObject implements IAlien {
    protected IAlien decoratedAlien;

    public AlienDecorator(IAlien decoratedAlien) {
        this.decoratedAlien = decoratedAlien;
    }
}