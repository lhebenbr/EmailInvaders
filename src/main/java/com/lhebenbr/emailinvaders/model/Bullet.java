package com.lhebenbr.emailinvaders.model;

public class Bullet {
    private double x;
    private double y;
    private final double speed = 8.0;

    public Bullet(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void update() {
        y -= speed;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
}
