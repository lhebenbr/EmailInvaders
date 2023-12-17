package com.lhebenbr.emailinvaders.model;

public abstract class Bullet {

    protected double x;
    protected double y;
    protected double speed;

    public Bullet(double x, double y, double speed) {
        this.x = x;
        this.y = y;
        this.speed = speed;
    }

    public void update() {
        //
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

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public boolean collidesWith(Enemy enemy) {
        double bulletWidth = x;
        double bulletHeight = y;

        double enemyWidth = enemy.getWidth();
        double enemyHeight = enemy.getHeight();

        return x < enemy.getX() + enemyWidth &&
                x + bulletWidth > enemy.getX() &&
                y < enemy.getY() + enemyHeight &&
                y + bulletHeight > enemy.getY();
    }
}
