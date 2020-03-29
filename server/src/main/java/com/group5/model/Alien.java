package com.group5.model;

import com.group5.helper.Vector2D;
//import java.util.List;

public class Alien extends MainGridObject {
    public Alien() {}
    public Alien(Vector2D position, Vector2D direction, double speed, double width, double height, double health) {
        super(position, direction, speed, width, height, health);
    }
}