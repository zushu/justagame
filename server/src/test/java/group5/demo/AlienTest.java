package com.group5;

import com.group5.model.Alien;
import com.group5.model.IAlien;
import com.group5.model.DefensiveAlien;
import com.group5.model.ShootingAlien;
import com.group5.helper.Vector2D;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.boot.test.context.SpringBootTest;

// test functions in GridObject, AliveGridObject and Alien
// since GridObject and AliveGridObject are abstract, no objects of those classes can be instantiated.
@SpringBootTest
public class AlienTest {

    Vector2D position = new Vector2D(2.0d, 2.0d);
    Vector2D direction = new Vector2D(0.0d, 1.0d);
    IAlien gridObject; // normal Alien
    //IAlien shootingAlien;
    //IAlien defensiveAlien;
    //IAlien defensiveShootingAlien;

    @BeforeEach
    public void init() {
        Vector2D position = new Vector2D(2.0d, 2.0d);
        Vector2D direction = new Vector2D(0.0d, 1.0d);
        gridObject = new Alien(position, direction, 1.0d, 2.0d, 2.0d, 100.0d);
        //shootingAlien = new 
    }

    @Test
    public void testMove() {
        gridObject.move(new Vector2D(2.0d, 1.0d));
        Vector2D checkResult = new Vector2D(4.0d, 3.0d);
        assertEquals(gridObject.getPosition(), checkResult);
    }

    @Test
    public void testMoveLeft() {
        gridObject.moveLeft();
        Vector2D checkResult = new Vector2D(1.0d, 2.0d);
        assertEquals(gridObject.getPosition(), checkResult);
    }

    @Test
    public void testMoveRight() {
        gridObject.moveRight();
        Vector2D checkResult = new Vector2D(3.0d, 2.0d);
        assertEquals(gridObject.getPosition(), checkResult);
    }

    @Test
    public void testMoveUp() {
        gridObject.moveUp();
        Vector2D checkResult = new Vector2D(2.0d, 3.0d);
        assertEquals(gridObject.getPosition(), checkResult);
    }

    @Test
    public void testMoveDown() {
        gridObject.moveDown();
        Vector2D checkResult = new Vector2D(2.0d, 1.0d);
        assertEquals(gridObject.getPosition(), checkResult);
    }

    @Test
    public void testDoCollide() {
        Alien gridObject1 = new Alien(new Vector2D(2.0d, 2.0d), new Vector2D(0.0d, 1.0d), 1.0d, 2.0d, 2.0d, 100.0d);
        IAlien gridObject2 = new Alien(new Vector2D(3.0d, 3.0d), new Vector2D(0.0d, 1.0d), 1.0d, 2.0d, 2.0d, 100.0d);
        boolean result = gridObject.doCollide(gridObject1, gridObject2);
        boolean checkResult = true;
        assertEquals(result, checkResult);
    }

    @Test 
    public void testGetHit() {
        gridObject.getHit(50.0d);
        assertEquals(true, gridObject.getAlive());
        assertEquals(50.0d, gridObject.getHealth());
        gridObject.getHit(50.0d);
        assertEquals(false, gridObject.getAlive());
        assertEquals(0.0d, gridObject.getHealth());
    }
}

