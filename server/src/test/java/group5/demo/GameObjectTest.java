package com.group5;

import com.group5.model.Alien;
import com.group5.model.IAlien;
import com.group5.model.DefensiveAlien;
import com.group5.model.ShootingAlien;
import com.group5.model.Bullet;
import com.group5.model.SpaceShip;
import com.group5.model.MainGame;
import com.group5.helper.Vector2D;
import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.boot.test.context.SpringBootTest;

/** 
    * this class contains test functions for GridObject, AliveGridObject, Alien, ShootingAlien, DefensiveAlien, Bullet, SpaceShip and MainGame
    * since GridObject and AliveGridObject are abstract, no objects of those classes can be instantiated, Alien was used instead.
*/
@SpringBootTest
public class GameObjectTest {

    Vector2D position = new Vector2D(2.0d, 2.0d);
    Vector2D direction = new Vector2D(0.0d, 1.0d);
    Alien gridObject1; // extends GridObject
    IAlien gridObject; // normal Alien
    IAlien shootingAlien;
    IAlien defensiveAlien;
    IAlien defensiveShootingAlien;

    @BeforeEach
    public void init() {
        Bullet bulletType = new Bullet();
        gridObject = new Alien(position, direction, 1.0d, 2.0d, 2.0d, 100.0d);
        gridObject1 = new Alien(position, direction, 1.0d, 2.0d, 2.0d, 100.0d);
        shootingAlien = new ShootingAlien(new Alien(position, direction, 1.0d, 2.0d, 2.0d, 100.0d), 5, bulletType);
        defensiveAlien = new DefensiveAlien(new Alien(position, direction, 1.0d, 2.0d, 2.0d, 100.0d));
        defensiveShootingAlien = new DefensiveAlien(new ShootingAlien(new Alien(position, direction, 1.0d, 2.0d, 2.0d, 100.0d), 5, bulletType));
    }

    // move function tests

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
        IAlien gridObject2 = new Alien(new Vector2D(3.0d, 3.0d), new Vector2D(0.0d, 1.0d), 1.0d, 2.0d, 2.0d, 100.0d);
        // alien's left boundary coordinate check
        double left = gridObject2.getLeftBoundary();
        assertEquals(2.0d, left);
        // alien collides with another alien
        boolean result = gridObject.doCollide(gridObject1, gridObject2);
        boolean checkResult = true;
        assertEquals(result, checkResult);
        // alien's left boundary coordinate update check
        gridObject1.setPosition(new Vector2D(1.0d, 1.0d));
        left = gridObject1.getLeftBoundary();
        assertEquals(0.0d, left);
    }

    @Test 
    public void testGetHit() {
        gridObject.getHit(50.0d);
        // alien is alive after the first hit
        assertEquals(true, gridObject.getAlive());
        assertEquals(50.0d, gridObject.getHealth());
        gridObject.getHit(50.0d);
        // alien is alive after the second hit
        assertEquals(false, gridObject.getAlive());
        assertEquals(0.0d, gridObject.getHealth());
    }

    @Test 
    public void testDecoratedAliens() {
        shootingAlien.setPosition(new Vector2D(5.0d, 5.0d));
        boolean result = gridObject1.doCollide(gridObject1, shootingAlien);
        assertEquals(false, result);

    }

    @Test 
    public void testBullet() {
        Bullet bullet = new Bullet(position, direction, 1.0d, 2.0d, 2.0d, 20.0d);
        // bullet is within the grid
        boolean result = bullet.isOutOfBounds(10.0d, 10.0d);
        assertEquals(false, result);
        bullet.setPosition(new Vector2D(20.0d, 20.0d));
        // bullet is out of the grid
        result = bullet.isOutOfBounds(10.0d, 10.0d);
        assertEquals(true, result);
    }

    @Test
    public void testSpaceShip() {
        Bullet bulletType = new Bullet(position, direction, 1.0d, 2.0d, 2.0d, 20.0d);
        List<Bullet> bulletList = new ArrayList<Bullet>();
        bulletList.add(bulletType);
        SpaceShip spaceShip = new SpaceShip(position, direction, 1.0d, 2.0d, 2.0d,  bulletList, 4.0d, 200.0d, bulletType);

        // bullet's initial position is above the spaceship
        Vector2D result = spaceShip.getBulletsInitialPosition();
        Vector2D expectedResult = new Vector2D(2.0d, 0.0d);
        assertEquals(expectedResult, result);

        // one bullet is added to the list after shooting
        spaceShip.shootOneBullet();
        assertEquals(2, spaceShip.getBulletList().size());

        // none of bullets are out of bounds
        spaceShip.updateBulletList(10.0d, 10.0d);
        assertEquals(2, spaceShip.getBulletList().size());

    }

    @Test
    public void testMainGame() {
        MainGame mainGame = new MainGame();
        // there is one spaceship in the Grid
        assertEquals(1, mainGame.getGrid().getSpaceShipList().size());
        mainGame.setFirstLevelAliens();
        // there are 60 Aliens in the first level
        assertEquals(60, mainGame.getGrid().getAlienList().size());
        mainGame.setSecondLevelAliens();
        // there are 60 Aliens in the second level
        assertEquals(60, mainGame.getGrid().getAlienList().size());
        mainGame.setThirdLevelAliens();
        // there are 60 Aliens in the third level
        assertEquals(60, mainGame.getGrid().getAlienList().size());
        mainGame.setForthLevelAliens();
        // there are 60 Aliens in the fourth level
        assertEquals(60, mainGame.getGrid().getAlienList().size());        
    }


}

