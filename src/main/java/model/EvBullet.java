package model;

public abstract class EvBullet extends EvEntity {

    protected double speed;

    public EvBullet(double x, double y, int width, int height, double speed) {
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
