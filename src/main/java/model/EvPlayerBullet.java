package model;

import cache.EvImageCache;

public class EvPlayerBullet extends EvBullet {


    public EvPlayerBullet(double x, double y, int width, int height, double speed) {
        super(x, y, width, height, speed);
        image = EvImageCache.getImage("/assets/textures/laser1.png");
    }

    @Override
    public void update() {
        y = y - speed;
    }

}
