package com.lhebenbr.emailinvaders.model;

import com.lhebenbr.emailinvaders.ImageCache;
import com.lhebenbr.emailinvaders.SoundManager;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.lhebenbr.emailinvaders.Config.*;

public class Enemy extends Entity {

    private int points;
    private double speed;
    private boolean isDestroyed = false;
    private final List<EnemyBullet> enemyBullets = new ArrayList<>();

    public Enemy(double x, double y, Image image, int width, int height, int points, int speed) {
        super(x, y, image, width, height);
        this.points = points;
        this.speed = speed;
    }

    public Enemy(double x, double y, int width, int height, int points, double speed) {
        super(x, y, width, height);
        this.points = points;
        this.speed = speed;
    }


    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public List<EnemyBullet> getEnemyBullets() {
        return enemyBullets;
    }

    /**
     * Führt das Schießen der Gegner aus und fügt Geschosse zur Liste hinzu.
     *
     * @param enemies Die Liste der Gegner, von denen geschossen werden soll.
     * @param random  Eine Instanz von {@code Random} für die Zufallsgenerierung.
     */
    public void enemyShoot(List<Enemy> enemies, Random random) {
        double shootProbability = calculateShootProbability(enemies);
        for (Enemy enemy : enemies) {
            if ((random.nextDouble()) < shootProbability) {
                enemy.getEnemyBullets().add(new EnemyBullet(enemy.getX() + ENEMY_WIDTH / 2, enemy.getY(), BULLET_WIDTH, BULLET_HEIGHT, BULLET_SPEED));
            }
        }
    }

    private double calculateShootProbability(List<Enemy> enemies) {
        // Basis-Schießwahrscheinlichkeit
        double baseProbability = 0.00015;

        // Erhöhte Wahrscheinlichkeit, wenn weniger Gegner vorhanden sind
        double multiplier = 1.0 + (1.0 - ((double) enemies.size() / (ENEMIES_PER_ROW * ENEMY_ROWS)));

        // Maximalwert
        double maxProbability = 0.00045;

        return Math.min(baseProbability * multiplier, maxProbability);
    }

    /**
     * Zerstört den Gegner und erzeugt eine neue Explosion.
     *
     * @return Eine neue {@code Explosion}-Instanz oder null, falls der Gegner bereits zerstört wurde.
     */
    public Explosion destroy() {
        if (!isDestroyed) {
            SoundManager.getInstance().playSound("src/main/resources/com/lhebenbr/emailinvaders/assets/music/explosion.wav", false);
            isDestroyed = true;
            Image[] explosionFrames = getExplosionFrames();
            return new Explosion(explosionFrames, x - 50, y - 60, EXPLOSION_WIDTH, EXPLOSION_HEIGHT);
        }
        return null;
    }

    private Image[] getExplosionFrames() {
        Image[] frames = new Image[9];
        for (int i = 0; i < 9; i++) {
            frames[i] = ImageCache.getImage("file:src/main/resources/com/lhebenbr/emailinvaders/assets/textures/explosion_" + (i + 1) + ".png");
        }
        return frames;
    }


}
