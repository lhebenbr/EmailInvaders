package model;

import javafx.scene.image.Image;
import manager.EvSoundManager;

import java.util.ArrayList;
import java.util.List;

import static config.EvConfig.*;

public class EvPlayer extends EvEntity {

    private long lastTimeShot = 0;
    private int speed;
    private boolean noLimit = false;
    private Integer life;

    private final List<EvPlayerBullet> bullets = new ArrayList<>();

    public EvPlayer(double x, double y, Image image, int width, int height, int speed) {
        super(x, y, image, width, height);
        this.speed = speed;
        this.life = START_LIVES;
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

    public Integer getLife() {
        return life;
    }

    public void setLife(Integer life) {
        this.life = life;
    }

    public void loseLife() {
        if (life > 0) {
            EvSoundManager.getInstance().playSound("/assets/music/damage.wav", false);
            life -= 1;
        }
    }

    public void addLife() {
        life += 1;
    }

    public List<EvPlayerBullet> getBullets() {
        return bullets;
    }

    public void shoot() {
        long currentTime = System.currentTimeMillis();
        if (canShoot(currentTime) || noLimit) {
            if (!noLimit) {
                EvSoundManager.getInstance().playSound("/assets/music/laser.wav", false);
            }
            bullets.add(new EvPlayerBullet(x + 45, y, BULLET_WIDTH, BULLET_HEIGHT, BULLET_SPEED));
        }
    }


    public boolean canShoot(long currentTime) {
        if (currentTime - lastTimeShot >= 500) {
            lastTimeShot = currentTime;
            return true;
        }
        return false;
    }


}
