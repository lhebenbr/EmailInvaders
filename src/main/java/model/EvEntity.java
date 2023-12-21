package model;

import javafx.scene.image.Image;

public abstract class EvEntity {

    protected double x;
    protected double y;
    protected Image image;
    protected int width;
    protected int height;


    public EvEntity(double x, double y, Image image, int width, int height) {
        this.x = x;
        this.y = y;
        this.image = image;
        this.width = width;
        this.height = height;
    }

    public EvEntity(double x, double y, int width, int height) {
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

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
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

    public boolean checkCollision(final EvEntity other) {
        if (other != null) {
            return x < other.x + other.width
                    && x + getWidth() > other.x
                    && y < other.getY() + other.height
                    && y + height > other.y;
        }
        return false;
    }
}
