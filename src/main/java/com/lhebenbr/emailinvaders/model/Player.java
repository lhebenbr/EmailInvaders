package com.lhebenbr.emailinvaders.model;

import com.lhebenbr.emailinvaders.ImageCache;
import com.lhebenbr.emailinvaders.SoundManager;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.List;

import static com.lhebenbr.emailinvaders.Config.*;

public class Player extends Entity {

    private long lastTimeShot = 0;
    private int speed;
    private boolean noLimit = false;
    private final List<Image> life = new ArrayList<>();
    private final List<PlayerBullet> bullets = new ArrayList<>();

    public Player(double x, double y, Image image, int width, int height, int speed) {
        super(x, y, image, width, height);
        this.speed = speed;
        initLife();
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public boolean isNoLimit() {
        return noLimit;
    }

    public void setNoLimit(boolean noLimit) {
        this.noLimit = noLimit;
    }

    public List<Image> getLife() {
        return life;
    }

    public void loseLife() {
        if (!life.isEmpty()) {
            SoundManager.getInstance().playSound("src/main/resources/com/lhebenbr/emailinvaders/assets/music/damage.wav", false);
            life.remove(life.size() - 1);
        }
    }

    public void addLife() {
        life.add(ImageCache.getImage("file:src/main/resources/com/lhebenbr/emailinvaders/assets/textures/heart.png"));
    }

    public List<PlayerBullet> getBullets() {
        return bullets;
    }

    public void shoot() {
        long currentTime = System.currentTimeMillis();
        if (canShoot(currentTime) || noLimit) {
            if (!noLimit) {
                SoundManager.getInstance().playSound("src/main/resources/com/lhebenbr/emailinvaders/assets/music/laser.wav", false);
            }
            bullets.add(new PlayerBullet(x + 45, y, BULLET_WIDTH, BULLET_HEIGHT, BULLET_SPEED));
        }
    }


    public boolean canShoot(long currentTime) {
        if (currentTime - lastTimeShot >= 500) {
            lastTimeShot = currentTime;
            return true;
        }
        return false;
    }

    private void initLife() {
        for (int i = 0; i < START_LIVES; i++) {
            life.add(ImageCache.getImage("file:src/main/resources/com/lhebenbr/emailinvaders/assets/textures/heart.png"));
        }
    }


}
