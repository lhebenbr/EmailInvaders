package com.lhebenbr.emailinvaders.model;

import javafx.scene.image.Image;

public class Enemy {

    public Enemy(double x, double y) {
        this.x = x;
        this.y = y;
    }
    private int points;
    private double x;
    private double y;
    private Image image;

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
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

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }
}
