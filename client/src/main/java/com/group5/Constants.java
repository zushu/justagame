package com.group5;

import javafx.scene.paint.Color;

public class Constants {
    public static final String SERVER_SCORE_API_URL = "http://localhost:8080/api/score";
    public static final String SERVER_USER_API_URL = "http://localhost:8080/api/user";

    public static final String ALL_TIMES_DATE_STRING = "1970-01-01T00:00:00";
    public static final String SCOREBOARD_DATE_FORMAT_STRING = "yyyy-MM-dd'T'HH:mm:ss";

    public static final double CUSTOM_TIME_STEP_ALIEN_BULLET = 0.01;
    public static final double LEVEL1_TIMESTEP_INCREMENT = 0.05;
    public static final double LEVEL2_TIMESTEP_INCREMENT = 0.09;
    public static final double LEVEL3_TIMESTEP_INCREMENT = 0.14;
    public static final double LEVEL4_TIMESTEP_INCREMENT = 0.24;


    public static final double TOTAL_TIME = 1;
    public static final int BULLET_WIDTH = 5;
    public static final int BULLET_HEIGHT = 15;
    public static final double ALIEN_BULLET_DAMAGE = 20;
    public static final double SPACESHIP_BULLET_DAMAGE = 100;
    public static final double SPACESHIP_HEALTH = 1000.0d;
    public static final double NORMAL_ALIEN_HEALTH = 100.0d;
    public static final double DEFENSIVE_ALIEN_HEALTH = 400.0d;
    public static final double SHOOTING_ALIEN_HEALTH = 200.0d;
    public static final int GRID_HEIGHT = 800;
    public static final int GRID_WIDTH = 600;

    public static final int BULLET_SPEED = 5;

    public static final double ROW_PADDING = 60.0d;
    public static final Integer ROW_COUNT = 4;
    public static final Integer COLUMN_COUNT = 6;

    public static final Color SPACESHIP_COLOR = Color.DARKBLUE;
    public static final Color SPACESHIP_BULLET_COLOR = Color.BLACK;
    public static final Color ALIEN_BULLET_COLOR = Color.VIOLET;
    public static final Color NORMAL_ALIEN_COLOR = Color.GREEN;
    public static final Color DEFENSIVE_ALIEN_COLOR = Color.YELLOW;
    public static final Color SHOOTING_ALIEN_COLOR = Color.RED;
    public static final Color HARD_ALIEN_COLOR = Color.DARKRED;






}
