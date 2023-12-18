package com.lhebenbr.emailinvaders.model;

import com.lhebenbr.emailinvaders.ImageCache;

public class EnemyBullet extends Bullet {
    public EnemyBullet(double x, double y, int width, int height, double speed) {
        super(x, y, width, height, speed);
        image = ImageCache.getImage("file:src/main/resources/com/lhebenbr/emailinvaders/assets/textures/bullet.png");
    }

    @Override
    public void update() {
        y = y + speed;
    }
}