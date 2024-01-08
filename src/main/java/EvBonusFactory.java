import cache.EvImageCache;
import config.EvConfig;
import model.EvBonus;

public class EvBonusFactory {

    public static EvBonus createBonus(double x, double y, int typ) {
        switch (typ) {
            case 1:
                return new EvBonus(x, y, EvImageCache.getImage("/assets/textures/heart_bonus.png"), EvConfig.BONUS_DROP_WIDTH, EvConfig.BONUS_DROP_HEIGHT, typ);
            case 2:
                return new EvBonus(x, y, EvImageCache.getImage("/assets/textures/point_bonus.png"), EvConfig.BONUS_DROP_WIDTH, EvConfig.BONUS_DROP_HEIGHT, typ);
            case 3:
                return new EvBonus(x, y, EvImageCache.getImage("/assets/textures/powerup_bonus.png"), EvConfig.BONUS_DROP_WIDTH, EvConfig.BONUS_DROP_HEIGHT, typ);
            case 4:
                return new EvBonus(x, y, EvImageCache.getImage("/assets/textures/skull.png"), EvConfig.BONUS_DROP_WIDTH, EvConfig.BONUS_DROP_HEIGHT, typ);
            default:
                return null;
        }
    }
}
