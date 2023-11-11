package com.lhebenbr.emailinvaders.model;

public class Enemy {

    private int life;
    private int movementSpeed;
    private int bulletSpeed;
    private int points;

    private int x;
    private int y;

    public Enemy(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
