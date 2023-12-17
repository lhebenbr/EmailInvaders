package com.lhebenbr.emailinvaders.model;

public class EnemyBullet extends Bullet {
    public EnemyBullet(double x, double y, double speed) {
        super(x, y, speed);
    }

    @Override
    public void update() {
        y = y + speed;
    }
}