package com.lhebenbr.emailinvaders.model;

public class PlayerBullet extends Bullet{


    public PlayerBullet(double x, double y, int width, int height, double speed) {
        super(x, y, width, height, speed);
    }

    @Override
    public void update() {
        y = y - speed;
    }

}
