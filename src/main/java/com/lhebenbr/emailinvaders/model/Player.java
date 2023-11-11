package com.lhebenbr.emailinvaders.model;

public class Player {

    public Player(int width, int height) {
        this.width = width;
        this.height = height;
    }

    private long lastTimeShot = 0;
    private int width;

    private int height;

    public boolean canShoot(long currentTime) {
        if (currentTime - lastTimeShot >= 1_000_000_000L/4) {
            lastTimeShot = currentTime;
            return true;
        }
        return false;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
