package com.lhebenbr.emailinvaders.model;

import javafx.scene.image.Image;

public class EnemyBullet extends Bullet {
    public EnemyBullet(double x, double y,int width, int height, double speed) {
        super(x, y,width,height, speed);
        image = new Image("file:src/main/resources/com/lhebenbr/emailinvaders/assets/textures/bullet.png");
    }

    @Override
    public void update() {
        y = y + speed;
    }
}