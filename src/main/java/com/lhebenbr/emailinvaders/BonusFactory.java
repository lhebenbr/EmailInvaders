package com.lhebenbr.emailinvaders;

import com.lhebenbr.emailinvaders.model.Bonus;

public class BonusFactory {

    public static Bonus createBonus(double x, double y, int typ) {
        switch (typ) {
            case 1:
                return new Bonus(x, y, ImageCache.getImage("file:src/main/resources/com/lhebenbr/emailinvaders/assets/textures/heart_bonus.png"), Config.BONUS_DROP_WIDTH, Config.BONUS_DROP_HEIGHT, typ);
            case 2:
                return new Bonus(x, y, ImageCache.getImage("file:src/main/resources/com/lhebenbr/emailinvaders/assets/textures/point_bonus.png"), Config.BONUS_DROP_WIDTH, Config.BONUS_DROP_HEIGHT, typ);
            case 3:
                return new Bonus(x, y, ImageCache.getImage("file:src/main/resources/com/lhebenbr/emailinvaders/assets/textures/powerup_bonus.png"), Config.BONUS_DROP_WIDTH, Config.BONUS_DROP_HEIGHT, typ);
            case 4:
                return new Bonus(x, y, ImageCache.getImage("file:src/main/resources/com/lhebenbr/emailinvaders/assets/textures/skull.png"), Config.BONUS_DROP_WIDTH, Config.BONUS_DROP_HEIGHT, typ);
            default:
                return null;
        }
    }
}
