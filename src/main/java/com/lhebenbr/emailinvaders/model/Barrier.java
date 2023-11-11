package com.lhebenbr.emailinvaders.model;

public class Barrier {
    private double x;
    private double y;
    private final double width;
    private final double height;
    private int health = 30;

    public Barrier(double x, double y, double width, double height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public void takeDamage() {
        health--;
    }

    public boolean isDestroyed() {
        return health <= 0;
    }

    public int getHealth() {
        return health;
    }
}
