package com.lhebenbr.emailinvaders.model;

import javafx.scene.image.Image;

public class Barrier extends  Entity{
    private int health;

    public Barrier(double x, double y, Image image, int width, int height, int health) {
        super(x, y, image, width, height);
        this.health = health;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public boolean isDestroyed() {
        return health <= 0;
    }

    public void takeDamage() {
        health--;
    }
}
