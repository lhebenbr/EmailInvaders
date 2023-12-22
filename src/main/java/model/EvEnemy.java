package model;

import cache.EvImageCache;
import javafx.scene.image.Image;
import manager.EvSoundManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static config.EvConfig.*;

public class EvEnemy extends EvEntity {

    private int points;
    private double speed;
    private boolean isDestroyed = false;

    private final List<EvEnemyBullet> enemyBullets = new ArrayList<>();

    public EvEnemy(double x, double y, Image image, int width, int height, int points, int speed) {
        super(x, y, image, width, height);
        this.points = points;
        this.speed = speed;
    }

    public EvEnemy(double x, double y, int width, int height, int points, double speed) {
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

    public List<EvEnemyBullet> getEnemyBullets() {
        return enemyBullets;
    }

    /**
     * Führt das Schießen der Gegner aus und fügt Geschosse zur Liste hinzu.
     *
     * @param enemies Die Liste der Gegner, von denen geschossen werden soll.
     * @param random  Eine Instanz von {@code Random} für die Zufallsgenerierung.
     */
    public void enemyShoot(List<EvEnemy> enemies, Random random, int wave) {
        double shootProbability = calculateShootProbability(wave);
        for (EvEnemy enemy : enemies) {
            if ((random.nextDouble()) < shootProbability) {
                enemy.getEnemyBullets().add(new EvEnemyBullet(enemy.getX() + ENEMY_WIDTH / 2, enemy.getY(), BULLET_WIDTH, BULLET_HEIGHT, BULLET_SPEED + ((double) wave/10)));
            }
        }
    }

    private double calculateShootProbability( int wave) {
        // Basis-Schießwahrscheinlichkeit
        double baseProbability = 0.00005;


        // Erhöhen Sie die Basis-Schießwahrscheinlichkeit mit jeder Welle
        double waveAdjustedProbability = baseProbability * wave;


        // Maximalwert
        double maxProbability = 0.00035;

        return Math.min(waveAdjustedProbability, maxProbability);
    }

    /**
     * Zerstört den Gegner und erzeugt eine neue Explosion.
     *
     * @return Eine neue {@code Explosion}-Instanz oder null, falls der Gegner bereits zerstört wurde.
     */
    public EvExplosion destroy() {
        if (!isDestroyed) {
            EvSoundManager.getInstance().playSound("/assets/music/explosion.wav", false);
            isDestroyed = true;
            Image[] explosionFrames = getExplosionFrames();
            return new EvExplosion(explosionFrames, x - 50, y - 60, EXPLOSION_WIDTH, EXPLOSION_HEIGHT);
        }
        return null;
    }

    private Image[] getExplosionFrames() {
        Image[] frames = new Image[9];
        for (int i = 0; i < 9; i++) {
            frames[i] = EvImageCache.getImage("/assets/textures/explosion_" + (i + 1) + ".png");
        }
        return frames;
    }


}
