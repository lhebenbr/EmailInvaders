import cache.EvImageCache;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.ImageView;
import model.*;

import java.util.List;

import static config.EvConfig.*;

/**
 * Die Klasse {@code EmailInvaderRenderer} ist für das Rendering der verschiedenen Spielobjekte
 * im Spiel "Email Invaders" zuständig. Sie verwendet einen {@code GraphicsContext} für die Darstellung.
 */
public class EmailInvaderRenderer {

    private final GraphicsContext gc;

    /**
     * Konstruktor für {@code EmailInvaderRenderer}.
     *
     * @param gc Der {@code GraphicsContext}, der für das Zeichnen der Spielobjekte verwendet wird.
     */
    public EmailInvaderRenderer(GraphicsContext gc) {
        this.gc = gc;
    }

    /**
     * Zeichnet den Spieler auf dem Bildschirm.
     *
     * @param player Das {@code EvPlayer}-Objekt, das gerendert werden soll.
     */
    public void renderPlayer(EvPlayer player) {
        gc.drawImage(player.getImage(), player.getX(), player.getY());
    }

    /**
     * Zeichnet alle Barrieren auf dem Bildschirm.
     *
     * @param evBarriers Eine Liste von {@code EvBarrier}-Objekten, die gerendert werden sollen.
     */
    public void renderBarriers(List<EvBarrier> evBarriers) {
        for (EvBarrier evBarrier : evBarriers) {
            gc.drawImage(evBarrier.getImage(), evBarrier.getX(), evBarrier.getY(), evBarrier.getWidth(), evBarrier.getHeight());
        }
    }

    /**
     * Zeichnet alle Gegner auf dem Bildschirm.
     *
     * @param enemies Eine Liste von {@code EvEnemy}-Objekten, die gerendert werden sollen.
     */
    public void renderEnemies(List<EvEnemy> enemies) {
        for (EvEnemy enemy : enemies) {
            gc.drawImage(enemy.getImage(), enemy.getX(), enemy.getY(), enemy.getWidth(), enemy.getHeight());
        }
    }

    /**
     * Zeichnet alle Geschosse, die vom Spieler und den Gegnern abgefeuert wurden.
     *
     * @param player  Das {@code EvPlayer}-Objekt, dessen Geschosse gerendert werden sollen.
     * @param enemies Eine Liste von {@code EvEnemy}-Objekten, deren Geschosse gerendert werden sollen.
     */
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

    /**
     * Zeichnet die Bonus-E-Mail, wenn sie vorhanden ist.
     *
     * @param bonusEmail Das {@code EvEnemy}-Objekt, das die Bonus-E-Mail repräsentiert.
     */
    public void renderBonusMail(EvEnemy bonusEmail) {
        if (bonusEmail != null) {
            gc.drawImage(bonusEmail.getImage(), bonusEmail.getX(), bonusEmail.getY(), bonusEmail.getWidth(), bonusEmail.getHeight());
        }
    }

    /**
     * Zeichnet alle Bonus-Drops auf dem Bildschirm.
     *
     * @param bonusDrops Eine Liste von {@code EvBonus}-Objekten, die gerendert werden sollen.
     */
    public void renderBonusDrops(List<EvBonus> bonusDrops) {
        for (EvBonus bonus : bonusDrops) {
            gc.drawImage(bonus.getImage(), bonus.getX(), bonus.getY(), bonus.getWidth(), bonus.getHeight());
        }
    }

    /**
     * Zeichnet alle Explosionen auf dem Bildschirm.
     *
     * @param explosions Eine Liste von {@code EvExplosion}-Objekten, die gerendert werden sollen.
     */
    public void renderExplosions(List<EvExplosion> explosions) {
        for (EvExplosion explosion : explosions) {
            ImageView imageView = explosion.getImageView();
            if (imageView.isVisible()) {
                gc.drawImage(imageView.getImage(), imageView.getX(), imageView.getY(), explosion.getWidth(), explosion.getHeight());
            }
        }
    }

    /**
     * Zeichnet Herzen auf dem Bildschirm, die das verbleibende Leben des Spielers repräsentieren.
     *
     * @param player Das {@code EvPlayer}-Objekt, dessen Leben dargestellt werden sollen.
     */
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
