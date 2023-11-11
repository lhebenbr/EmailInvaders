package com.lhebenbr.emailinvaders.model;

public class EnemyBullet {
    private double x;
    private double y;
    private final double speed = 5.0; // Geschwindigkeit des Geschosses

    public EnemyBullet(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void update() {
        y += speed; // Bewegt das Geschoss nach unten
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

    public double getSpeed() {
        return speed;
    }
}