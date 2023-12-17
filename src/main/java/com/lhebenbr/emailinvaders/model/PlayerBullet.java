package com.lhebenbr.emailinvaders.model;

public class PlayerBullet extends Bullet{

    public PlayerBullet(double x, double y, double speed) {
        super(x, y, speed);
    }

    @Override
    public void update() {
        y = y - speed;
    }

}
