import cache.EvImageCache;
import config.EvConfig;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import manager.EvGameManager;
import manager.EvSoundManager;
import model.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import static config.EvConfig.*;

public class EmaillInvaderController {

    @FXML
    private Canvas gameCanvas;

    @FXML
    private Text scoreText;

    private boolean movingLeft = false;
    private boolean movingRight = false;
    private boolean isSpacePressed = false;
    private boolean enemyMovingRight = true; // Anfangsrichtung
    private final List<EvEnemy> enemies = new ArrayList<>();
    private final List<EvBarrier> evBarriers = new ArrayList<>();
    private final List<EvBonus> activeBonuses = new ArrayList<>();
    private final List<EvExplosion> activeExplosions = new ArrayList<>();
    private EvEnemy bonusEmail;
    private EvPlayer player;
    private final Random random = new Random();
    private long bonusEmailTimer = 0;
    private long lastBonusSpawnTime = 0;


    /**
     * Initialisiert das Spiel, indem die Spiellogik und die Anfangszustände wie Spieler,
     * Barrieren und Feinde gesetzt werden.
     */
    public void initialize() {
        EvGameManager.getInstance().setScore(0);
        EvSoundManager.getInstance().playSound("/assets/music/game_start.wav", false);
        player = new EvPlayer(PLAYER_START_X, PLAYER_START_Y, EvImageCache.getImage("file:src/main/resources/assets/textures/ship.png"), PLAYER_WIDTH, PLAYER_HEIGHT, PLAYER_SPEED);
        spawnBarriers();
        spawnEnemies();

        gameCanvas.setFocusTraversable(true);
        gameCanvas.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case LEFT:
                    movingLeft = true;
                    break;
                case RIGHT:
                    movingRight = true;
                    break;
                case SPACE:
                    isSpacePressed = true;
                    break;
                default:
                    break;
            }
        });

        gameCanvas.setOnKeyReleased(event -> {
            switch (event.getCode()) {
                case LEFT:
                    movingLeft = false;
                    break;
                case RIGHT:
                    movingRight = false;
                    break;
                case SPACE:
                    isSpacePressed = false;
                    break;
                default:
                    break;
            }
        });


        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                updateGame(now);
                renderGame();
            }
        };
        timer.start();
    }

    /**
     * Erzeugt Barrieren im Spiel.
     */
    private void spawnBarriers() {
        Image image = EvImageCache.getImage("file:src/main/resources/assets/textures/barrier_1.png");
        int startWidth = 150;
        int barrierWidth = 200;
        int barrierHeight = 70;
        for (int i = 0; i < 4; i++) {
            evBarriers.add(new EvBarrier(startWidth, 810, image, barrierWidth, barrierHeight, 30));
            startWidth += 480;
        }
    }

    /**
     * Aktualisiert den Zustand der Barrieren im Spiel.
     */
    private void updateBarriers() {
        for (EvBarrier evBarrier : evBarriers) {
            if (!evBarrier.isDestroyed()) {
                Image barrierImage = selectBarrierImage(evBarrier);
                evBarrier.setImage(barrierImage);
            }
        }
    }

    /**
     * Wählt das passende Bild für die Barriere basierend auf ihrer Gesundheit aus.
     *
     * @param evBarrier Die Barriere, deren Bild aktualisiert werden soll.
     * @return Das Bild, das der aktuellen Gesundheit der Barriere entspricht.
     */
    private Image selectBarrierImage(EvBarrier evBarrier) {
        int i = evBarrier.getHealth() <= 10 ? 3 : evBarrier.getHealth() <= 20 ? 2 : 1;
        Image barrierImage = EvImageCache.getImage("file:src/main/resources/assets/textures/barrier_" + i + ".png");
        return barrierImage;
    }


    /**
     * Erzeugt die Feinde im Spiel.
     */
    private void spawnEnemies() {
        enemies.clear();
        double startWidth = 250;
        double startHeight = 100;
        for (int y = 0; y < ENEMY_ROWS; y++) {
            for (int x = 0; x < ENEMIES_PER_ROW; x++) {
                EnemyFactory.EnemyType type = EnemyFactory.EnemyType.values()[random.nextInt(EnemyFactory.EnemyType.values().length)];
                EvEnemy enemy = EnemyFactory.createEnemy(type, startWidth, startHeight, ENEMY_WIDTH, ENEMY_HEIGHT);
                enemies.add(enemy);
                startWidth += 140;
            }
            startHeight += 100;
            startWidth = 250;
        }
    }

    /**
     * Erzeugt eine Bonus-Email im Spiel.
     */
    private void spawnBonusEmail() {
        bonusEmail = EnemyFactory.createBonusEnemey(0, 10, BONUS_MAIL_WIDTH, BONUS_MAIL_HEIGHT);
    }

    /**
     * Erzeugt ein Bonus-Drop-Objekt an einer zufälligen Position.
     */
    private void spawnBonusDrop() {
        int bonusType = random.nextInt(4) + 1; // Zufällige Zahl zwischen 1 und 4
        double bonusX = random.nextDouble() * (gameCanvas.getWidth() - EvConfig.BONUS_DROP_WIDTH);
        double bonusY = 0;
        EvBonus bonus = EvBonusFactory.createBonus(bonusX, bonusY, bonusType);
        activeBonuses.add(bonus);
    }


    /**
     * Überprüft die Kollisionen zwischen den Geschossen der Feinde und anderen Objekten
     * wie dem Spieler und den Barrieren.
     */
    private void checkEnemyBulletCollisions() {
        for (EvEnemy enemy : enemies) {
            Iterator<EvEnemyBullet> bulletIterator = enemy.getEnemyBullets().iterator();
            while (bulletIterator.hasNext()) {
                EvEnemyBullet bullet = bulletIterator.next();

                // Kollision mit dem Spieler
                if (player.checkCollision(bullet)) {

                    bulletIterator.remove();
                    handlePlayerHit();
                    continue;
                }

                // Kollision mit Barrieren
                Iterator<EvBarrier> barrierIterator = evBarriers.iterator();
                while (barrierIterator.hasNext()) {
                    EvBarrier evBarrier = barrierIterator.next();
                    if (evBarrier.checkCollision(bullet)) {
                        // Treffer an einer Barriere
                        bulletIterator.remove();
                        evBarrier.takeDamage();
                        if (evBarrier.isDestroyed()) {
                            barrierIterator.remove();
                        }
                        break;
                    }
                }

                // Kollision mit Spieler-Geschossen
                Iterator<EvPlayerBullet> playerBulletIterator = player.getBullets().iterator();
                while (playerBulletIterator.hasNext()) {
                    EvPlayerBullet playerBullet = playerBulletIterator.next();
                    if (playerBullet.checkCollision(bullet)) {
                        // Treffer eines Spieler-Geschosses
                        bulletIterator.remove();
                        playerBulletIterator.remove();
                        break;
                    }
                }
            }
        }
    }

    /**
     * Verarbeitet die Situation, wenn der Spieler getroffen wird.
     */
    private void handlePlayerHit() {
        player.loseLife();
    }

    /**
     * Überprüft alle Kollisionen zwischen Geschossen des Spielers und Feinden oder Barrieren.
     */
    private void checkCollisions() {
        Iterator<EvPlayerBullet> bulletIterator = player.getBullets().iterator();
        while (bulletIterator.hasNext()) {
            EvPlayerBullet bullet = bulletIterator.next();

            if (bonusEmail != null && bonusEmail.checkCollision(bullet)) {
                activeExplosions.add(bonusEmail.destroy());
                EvGameManager.getInstance().addScore(bonusEmail.getPoints());
                bonusEmail = null;
                if (player.getLife() < 3) {
                    player.addLife();
                }
                break;
            }

            Iterator<EvEnemy> enemyIterator = enemies.iterator();
            while (enemyIterator.hasNext()) {
                EvEnemy enemy = enemyIterator.next();

                if (enemy.checkCollision(bullet)) {
                    EvGameManager.getInstance().addScore(enemy.getPoints());
                    activeExplosions.add(enemy.destroy());
                    bulletIterator.remove();
                    enemyIterator.remove();
                    break;
                }
            }
            Iterator<EvBarrier> barrierIterator = evBarriers.iterator();
            while (barrierIterator.hasNext()) {
                EvBarrier evBarrier = barrierIterator.next();

                if (evBarrier.checkCollision(bullet)) {
                    bulletIterator.remove();
                    evBarrier.takeDamage();
                    if (evBarrier.isDestroyed()) {
                        barrierIterator.remove();
                    }
                    break;
                }
            }
        }

    }

    /**
     * Aktualisiert die Positionen der Feinde.
     */
    private void updateEnemyPositions() {
        double maxX = 0;
        double minX = gameCanvas.getWidth();

        // Anpassung der Gegnergeschwindigkeit basierend auf der Anzahl der verbleibenden Gegner
        double baseEnemySpeed = ENEMY_SPEED;
        double speed = baseEnemySpeed + (baseEnemySpeed * Math.round(1 - (double) enemies.size() / (ENEMIES_PER_ROW * ENEMY_ROWS)));

        for (EvEnemy enemy : enemies) {
            enemy.setSpeed(speed);
            if (enemy.getX() > maxX) {
                maxX = enemy.getX();
            }
            if (enemy.getX() < minX) {
                minX = enemy.getX();
            }
        }

        // Prüfen, ob die Gegner umkehren müssen
        if ((enemyMovingRight && maxX + ENEMY_WIDTH + speed > gameCanvas.getWidth()) ||
                (!enemyMovingRight && minX - speed < 0)) {
            enemyMovingRight = !enemyMovingRight;
            for (EvEnemy enemy : enemies) {
                enemy.setY(enemy.getY() + ENEMY_HEIGHT); // Gegner eine Reihe nach unten bewegen
                if (enemy.getY() + ENEMY_HEIGHT >= gameCanvas.getHeight()) {
                    gameOver();
                    return;
                }
            }
        }

        // Gegner horizontal bewegen
        for (EvEnemy enemy : enemies) {
            if (enemyMovingRight) {
                enemy.setX(enemy.getX() + speed);
            } else {
                enemy.setX(enemy.getX() - speed);
            }
        }

        // Bonus-Email bewegen, falls vorhanden
        if (bonusEmail != null) {
            bonusEmail.setX(bonusEmail.getX() + BONUS_MAIL_SPEED);
            if (bonusEmail.getX() > gameCanvas.getWidth()) {
                bonusEmail = null;
            }
        }
    }

    /**
     * Aktualisiert die Geschosse der Feinde.
     */
    private void updateEnemyBullets() {
        for (EvEnemy enemy : enemies) {
            Iterator<EvEnemyBullet> it = enemy.getEnemyBullets().iterator();
            while (it.hasNext()) {
                EvEnemyBullet bullet = it.next();
                bullet.update();
                if (bullet.getY() > gameCanvas.getHeight()) {
                    it.remove(); // Entfernt das Geschoss, wenn es den unteren Bildschirmrand erreicht
                }
            }
        }
    }

    /**
     * Aktualisiert die aktiven Boni im Spiel.
     */
    private void updateBonuses() {
        Iterator<EvBonus> bonusIterator = activeBonuses.iterator();
        while (bonusIterator.hasNext()) {
            EvBonus bonus = bonusIterator.next();
            bonus.setY(bonus.getY() + bonus.getSpeed());

            if (bonus.getY() > gameCanvas.getHeight()) {
                bonusIterator.remove();
                continue; // Zum nächsten Bonus springen
            }

            if (bonus.getTyp() == 4) {
                removeEnemiesOnPath(bonus);
                if (player.checkCollision(bonus)) {
                    applyBonusAndRemove(bonus, bonusIterator);
                }
                continue; // Zum nächsten Bonus springen
            }

            if (player.checkCollision(bonus)) {
                applyBonusAndRemove(bonus, bonusIterator);
            }
        }
    }


    private void removeEnemiesOnPath(EvBonus bonus) {
        Iterator<EvEnemy> enemyIterator = enemies.iterator();
        while (enemyIterator.hasNext()) {
            EvEnemy enemy = enemyIterator.next();
            if (bonus.checkCollision(enemy)) {
                activeExplosions.add(enemy.destroy());
                enemyIterator.remove();
            }
        }
    }


    private void applyBonusAndRemove(EvBonus bonus, Iterator<EvBonus> bonusIterator) {
        bonus.applyBonus(player);
        bonusIterator.remove();
    }

    /**
     * Aktualisiert das Spielgeschehen basierend auf der Zeit und dem Zustand des Spiels.
     *
     * @param now Die aktuelle Zeit in Nanosekunden.
     */
    private void updateGame(long now) {
        updateBarriers();
        updateBonuses();
        if (enemies.isEmpty()) {
            spawnEnemies();
        }
        Iterator<EvPlayerBullet> it = player.getBullets().iterator();
        while (it.hasNext()) {
            EvPlayerBullet bullet = it.next();
            bullet.update();
            if (bullet.getY() < 0) {
                it.remove();
            }
        }
        if (bonusEmail == null && now - bonusEmailTimer > BONUS_MAIL_SPAWN_TIME) {
            spawnBonusEmail();
            bonusEmailTimer = now;
        }
        if (now - lastBonusSpawnTime > BONUS_DROP_SPAWN_TIME) {
            spawnBonusDrop();
            lastBonusSpawnTime = now;
        }
        if (movingLeft && player.getX() > 0) {
            player.setX(player.getX() - player.getSpeed());
        }
        if (movingRight && player.getX() < gameCanvas.getWidth() - player.getWidth()) {
            player.setX(player.getX() + player.getSpeed());
        }
        if (isSpacePressed) {
            player.shoot();
        }
        enemies.forEach(enemy -> enemy.enemyShoot(enemies, random));
        updateEnemyPositions();
        updateEnemyBullets();
        updateBarriers();
        checkCollisions();
        checkEnemyBulletCollisions();
        activeExplosions.removeIf(EvExplosion::isAnimationFinished);
        if (enemies.isEmpty()) {
            spawnEnemies();
        }
        if (evBarriers.isEmpty() && EvGameManager.getInstance().getScore() % 1000 == 0) {
            spawnBarriers();
        }
        if (player.getLife() == 0) {
            if (isSpacePressed) {
                isSpacePressed = false;
            }
            enemies.clear();
            evBarriers.clear();
            activeBonuses.clear();
            activeExplosions.clear();
            gameOver();
        }
    }

    /**
     * Zeichnet das Spielgeschehen auf das Canvas.
     */
    private void renderGame() {
        GraphicsContext gc = gameCanvas.getGraphicsContext2D();
        scoreText.setText("Score: " + EvGameManager.getInstance().getScore());
        gc.clearRect(0, 0, gameCanvas.getWidth(), gameCanvas.getHeight());
        EmailInvaderRenderer renderer = new EmailInvaderRenderer(gc);
        renderer.renderPlayer(player);
        renderer.renderBarriers(evBarriers);
        renderer.renderEnemies(enemies);
        renderer.renderBullets(player, enemies);
        renderer.renderHearts(player);
        renderer.renderBonusMail(bonusEmail);
        renderer.renderBonusDrops(activeBonuses);
        renderer.renderExplosions(activeExplosions);
    }

    /**
     * Beendet das Spiel und zeigt den Game-Over-Bildschirm.
     */
    private void gameOver() {
        try {
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("GameOverView.fxml"));
            Parent gameOverRoot = loader.load();
            EvGameManager.getInstance().updateHighscore();

            Scene gameOverScene = new Scene(gameOverRoot);

            Stage stage = (Stage) gameCanvas.getScene().getWindow();

            stage.setScene(gameOverScene);
            stage.show();
        } catch (Exception e) {
            //
        }
    }
}




