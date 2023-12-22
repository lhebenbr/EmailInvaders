import cache.EvImageCache;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.ImageView;
import model.*;

import java.util.List;

import static config.EvConfig.*;

public class EmailInvaderRenderer {

    private final GraphicsContext gc;

    public EmailInvaderRenderer(GraphicsContext gc) {
        this.gc = gc;
    }

    public void renderPlayer(EvPlayer player) {
        gc.drawImage(player.getImage(), player.getX(), player.getY());
    }

    public void renderBarriers(List<EvBarrier> evBarriers) {
        for (EvBarrier evBarrier : evBarriers) {
            gc.drawImage(evBarrier.getImage(), evBarrier.getX(), evBarrier.getY(), evBarrier.getWidth(), evBarrier.getHeight());
        }
    }

    public void renderEnemies(List<EvEnemy> enemies) {
        for (EvEnemy enemy : enemies) {
            gc.drawImage(enemy.getImage(), enemy.getX(), enemy.getY(), enemy.getWidth(), enemy.getHeight());
        }
    }

    public void renderBullets(EvPlayer player, List<EvEnemy> enemies) {
        for (EvPlayerBullet bullet : player.getBullets()) {
            gc.drawImage(bullet.getImage(), bullet.getX(), bullet.getY());
        }
        for (EvEnemy enemy : enemies) {
            for (EvEnemyBullet bullet : enemy.getEnemyBullets()) {
                gc.drawImage(bullet.getImage(), bullet.getX(), bullet.getY());
            }
        }
    }

    public void renderBonusMail(EvEnemy bonusEmail) {
        if (bonusEmail != null) {
            gc.drawImage(bonusEmail.getImage(), bonusEmail.getX(), bonusEmail.getY(), bonusEmail.getWidth(), bonusEmail.getHeight());
        }
    }

    public void renderBonusDrops(List<EvBonus> bonusDrops) {
        for (EvBonus bonus : bonusDrops) {
            gc.drawImage(bonus.getImage(), bonus.getX(), bonus.getY(), bonus.getWidth(), bonus.getHeight());
        }
    }

    public void renderExplosions(List<EvExplosion> explosions) {
        for (EvExplosion explosion : explosions) {
            ImageView imageView = explosion.getImageView();
            if (imageView.isVisible()) {
                gc.drawImage(imageView.getImage(), imageView.getX(), imageView.getY(), explosion.getWidth(), explosion.getHeight());
            }
        }
    }

    public void renderHearts(EvPlayer player) {
        if (player.getLife() != null) {
            int heartX = HEART_X;
            for (int i = 0; i < player.getLife(); i++) {
                gc.drawImage(EvImageCache.getImage("/assets/textures/heart.png"), heartX, HEART_Y, HEART_WIDTH, HEART_HEIGHT);
                heartX -= 50;
            }
        }
    }


}
