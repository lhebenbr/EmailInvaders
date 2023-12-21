package model;

import cache.EvImageCache;

public class EvEnemyBullet extends EvBullet {
    public EvEnemyBullet(double x, double y, int width, int height, double speed) {
        super(x, y, width, height, speed);
        image = EvImageCache.getImage("file:src/main/resources/assets/textures/bullet.png");
    }

    @Override
    public void update() {
        y = y + speed;
    }
}