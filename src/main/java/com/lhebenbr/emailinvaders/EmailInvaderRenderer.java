package com.lhebenbr.emailinvaders;

import com.lhebenbr.emailinvaders.model.*;
import javafx.scene.canvas.GraphicsContext;

import java.util.List;

import static com.lhebenbr.emailinvaders.Config.*;

public class EmailInvaderRenderer {

    private final GraphicsContext gc;

    public EmailInvaderRenderer(GraphicsContext gc) {
        this.gc = gc;
    }

    public void renderPlayer(Player player) {
        gc.drawImage(player.getImage(), player.getX(), player.getY());
    }

    public void renderBarriers(List<Barrier> barriers) {
        for (Barrier barrier : barriers) {
            gc.drawImage(barrier.getImage(), barrier.getX(), barrier.getY(), barrier.getWidth(), barrier.getHeight());
        }
    }

    public void renderEnemies(List<Enemy> enemies) {
        for (Enemy enemy : enemies) {
            gc.drawImage(enemy.getImage(), enemy.getX(), enemy.getY(), enemy.getWidth(), enemy.getHeight());
        }
    }

    public void renderBullets(Player player, List<Enemy> enemies) {
        for (PlayerBullet bullet : player.getBullets()) {
            gc.drawImage(bullet.getImage(), bullet.getX(), bullet.getY());
        }
        for (Enemy enemy : enemies) {
            for (EnemyBullet bullet : enemy.getEnemyBullets()) {
                gc.drawImage(bullet.getImage(), bullet.getX(), bullet.getY());
            }
        }
    }

    public void renderBonusMail(Enemy bonusEmail) {
        if (bonusEmail != null) {
            gc.drawImage(bonusEmail.getImage(), bonusEmail.getX(), bonusEmail.getY(), BONUS_MAIL_WIDTH, BONUS_MAIL_HEIGHT);
        }
    }

    public void renderBonusDrops(List<Bonus> bonusDrops) {
        for (Bonus bonus : bonusDrops) {
            gc.drawImage(bonus.getImage(), bonus.getX(), bonus.getY(), bonus.getWidth(), bonus.getHeight());
        }
    }

    public void renderHearts(Player player) {
        if (!player.getLife().isEmpty()) {
            int heartX = HEART_X - (50 * (player.getLife().size() - 3));
            for (int i = 0; i < player.getLife().size(); i++) {
                gc.drawImage(player.getLife().get(i), heartX, HEART_Y, HEART_WIDTH, HEART_HEIGHT);
                heartX -= 50;
            }
        }
    }


}
