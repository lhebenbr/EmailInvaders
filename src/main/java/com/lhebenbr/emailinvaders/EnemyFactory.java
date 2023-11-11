package com.lhebenbr.emailinvaders;

import com.lhebenbr.emailinvaders.model.Enemy;

public class EnemyFactory {

    public static Enemy createEnemy(EnemyType type, double x, double y) {
        switch (type) {
            case TYPE1:
                return createType1Enemy(x, y);
            case TYPE2:
                return createType2Enemy(x, y);
            case TYPE3:
                return createType3Enemy(x, y);
            default:
                throw new IllegalArgumentException("Unknown enemy type: " + type);
        }
    }

    private static Enemy createType1Enemy(double x, double y) {
        Enemy enemy = new Enemy(x, y);
        enemy.setLife(3);
        enemy.setBulletSpeed(2);
        enemy.setPoints(100);
        return enemy;
    }

    private static Enemy createType2Enemy(double x, double y) {
        Enemy enemy = new Enemy(x, y);
        enemy.setLife(5);
        enemy.setBulletSpeed(3);
        enemy.setPoints(200);
        return enemy;
    }

    private static Enemy createType3Enemy(double x, double y) {
        Enemy enemy = new Enemy(x, y);
        enemy.setLife(8);
        enemy.setBulletSpeed(4);
        enemy.setPoints(300);
        return enemy;
    }
    public enum EnemyType {
        TYPE1, TYPE2, TYPE3
    }
}