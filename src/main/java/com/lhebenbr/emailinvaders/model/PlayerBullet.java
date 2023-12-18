package com.lhebenbr.emailinvaders.model;

import com.lhebenbr.emailinvaders.ImageCache;

public class PlayerBullet extends Bullet {


    public PlayerBullet(double x, double y, int width, int height, double speed) {
        super(x, y, width, height, speed);
        image = ImageCache.getImage("file:src/main/resources/com/lhebenbr/emailinvaders/assets/textures/laser1.png");
    }

    @Override
    public void update() {
        y = y - speed;
    }

}
