package com.lhebenbr.emailinvaders.model;

public abstract class Bullet extends Entity {

    protected double speed;

    public Bullet(double x, double y, int width, int height, double speed) {
        super(x, y, width, height);
        this.speed = speed;
    }

    public void update() {
        //
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }
}
