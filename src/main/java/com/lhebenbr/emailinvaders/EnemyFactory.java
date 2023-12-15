package com.lhebenbr.emailinvaders;

import com.lhebenbr.emailinvaders.model.Enemy;
import javafx.scene.image.Image;

public class EnemyFactory {

    public static Enemy createEnemy(EnemyType type, double x, double y, int width, int height) {
        return switch (type) {
            case TYPE1 -> createType1Enemy(x, y, width, height);
            case TYPE2 -> createType2Enemy(x, y, width, height);
            case TYPE3 -> createType3Enemy(x, y, width, height);
            default -> throw new IllegalArgumentException("Unknown enemy type: " + type);
        };
    }

    private static Enemy createType1Enemy(double x, double y, int width, int height) {
        Enemy enemy = new Enemy(x, y, width, height);
        enemy.setPoints(100);
        enemy.setImage(new Image("file:src/main/resources/com/lhebenbr/emailinvaders/assets/textures/mail_1.png"));
        return enemy;
    }

    private static Enemy createType2Enemy(double x, double y, int width, int height) {
        Enemy enemy = new Enemy(x, y, width, height);
        enemy.setPoints(200);
        enemy.setImage(new Image("file:src/main/resources/com/lhebenbr/emailinvaders/assets/textures/mail_2.png"));
        return enemy;
    }

    private static Enemy createType3Enemy(double x, double y, int width, int height) {
        Enemy enemy = new Enemy(x, y, width, height);
        enemy.setPoints(300);
        enemy.setImage(new Image("file:src/main/resources/com/lhebenbr/emailinvaders/assets/textures/mail_3.png"));
        return enemy;
    }

    public static Enemy createBonusEnemey(double x, double y, int width, int height) {
        Enemy enemy = new Enemy(x, y, width, height);
        enemy.setPoints(1000);
        enemy.setImage(new Image("file:src/main/resources/com/lhebenbr/emailinvaders/assets/textures/mail_bonus.png"));
        return enemy;
    }
    public enum EnemyType {
        TYPE1, TYPE2, TYPE3
    }
}