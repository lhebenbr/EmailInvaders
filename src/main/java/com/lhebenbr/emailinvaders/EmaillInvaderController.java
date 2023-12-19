package com.lhebenbr.emailinvaders;

import com.lhebenbr.emailinvaders.model.*;
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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import static com.lhebenbr.emailinvaders.Config.*;

public class EmaillInvaderController {

    @FXML
    private Canvas gameCanvas;

    @FXML
    private Text scoreText;

    private boolean movingLeft = false;
    private boolean movingRight = false;
    private boolean isSpacePressed = false;
    private boolean enemyMovingRight = true; // Anfangsrichtung
    private final List<Enemy> enemies = new ArrayList<>();
    private final List<Barrier> barriers = new ArrayList<>();
    private final List<Bonus> activeBonuses = new ArrayList<>();
    private final List<Explosion> activeExplosions = new ArrayList<>();
    private Enemy bonusEmail;
    private Player player;
    private final Random random = new Random();
    private long bonusEmailTimer = 0;
    private long lastBonusSpawnTime = 0;


    public void initialize() {
        GameManager.getInstance().setScore(0);
        SoundManager.getInstance().playSound("src/main/resources/com/lhebenbr/emailinvaders/assets/music/game_start.wav", false);
        player = new Player(PLAYER_START_X, PLAYER_START_Y, ImageCache.getImage("file:src/main/resources/com/lhebenbr/emailinvaders/assets/textures/ship.png"), PLAYER_WIDTH, PLAYER_HEIGHT, PLAYER_SPEED);
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


    private void spawnBarriers() {
        Image image = ImageCache.getImage("file:src/main/resources/com/lhebenbr/emailinvaders/assets/textures/barrier_1.png");
        int startWidth = 150;
        int barrierWidth = 200;
        int barrierHeight = 70;
        for (int i = 0; i < 4; i++) {
            barriers.add(new Barrier(startWidth, 810, image, barrierWidth, barrierHeight, 30));
            startWidth += 480;
        }
    }

    private void updateBarriers() {
        for (Barrier barrier : barriers) {
            if (!barrier.isDestroyed()) {
                Image barrierImage = selectBarrierImage(barrier);
                barrier.setImage(barrierImage);
            }
        }
    }

    private Image selectBarrierImage(Barrier barrier) {
        int i = barrier.getHealth() <= 10 ? 3 : barrier.getHealth() <= 20 ? 2 : 1;
        Image barrierImage = ImageCache.getImage("file:src/main/resources/com/lhebenbr/emailinvaders/assets/textures/barrier_" + i + ".png");
        return barrierImage;
    }

    private void spawnEnemies() {
        enemies.clear();
        double startWidth = 250;
        double startHeight = 100;
        for (int y = 0; y < ENEMY_ROWS; y++) {
            for (int x = 0; x < ENEMIES_PER_ROW; x++) {
                EnemyFactory.EnemyType type = EnemyFactory.EnemyType.values()[random.nextInt(EnemyFactory.EnemyType.values().length)];
                Enemy enemy = EnemyFactory.createEnemy(type, startWidth, startHeight, ENEMY_WIDTH, ENEMY_HEIGHT);
                enemies.add(enemy);
                startWidth += 140;
            }
            startHeight += 100;
            startWidth = 250;
        }
    }

    private void spawnBonusEmail() {
        bonusEmail = EnemyFactory.createBonusEnemey(0, 10, BONUS_MAIL_WIDTH, BONUS_MAIL_HEIGHT);
    }

    private void spawnBonusDrop() {
        int bonusType = random.nextInt(4) + 1; // Zufällige Zahl zwischen 1 und 4
        double bonusX = random.nextDouble() * (gameCanvas.getWidth() - Config.BONUS_DROP_WIDTH);
        double bonusY = 0;
        Bonus bonus = BonusFactory.createBonus(bonusX, bonusY, bonusType);
        activeBonuses.add(bonus);
    }

    private void checkEnemyBulletCollisions() {
        for (Enemy enemy : enemies) {
            Iterator<EnemyBullet> bulletIterator = enemy.getEnemyBullets().iterator();
            while (bulletIterator.hasNext()) {
                EnemyBullet bullet = bulletIterator.next();

                // Kollision mit dem Spieler
                if (CollisionManager.checkCollision(player, bullet)) {

                    bulletIterator.remove();
                    handlePlayerHit();
                    continue;
                }

                // Kollision mit Barrieren
                Iterator<Barrier> barrierIterator = barriers.iterator();
                while (barrierIterator.hasNext()) {
                    Barrier barrier = barrierIterator.next();
                    if (CollisionManager.checkCollision(barrier, bullet)) {
                        // Treffer an einer Barriere
                        bulletIterator.remove();
                        barrier.takeDamage();
                        if (barrier.isDestroyed()) {
                            barrierIterator.remove();
                        }
                        break;
                    }
                }

                // Kollision mit Spieler-Geschossen
                Iterator<PlayerBullet> playerBulletIterator = player.getBullets().iterator();
                while (playerBulletIterator.hasNext()) {
                    PlayerBullet playerBullet = playerBulletIterator.next();
                    if (CollisionManager.checkCollision(playerBullet, bullet)) {
                        // Treffer eines Spieler-Geschosses
                        bulletIterator.remove();
                        playerBulletIterator.remove();
                        break;
                    }
                }
            }
        }
    }

    private void handlePlayerHit() {
        player.loseLife();
    }

    private void checkCollisions() {
        Iterator<PlayerBullet> bulletIterator = player.getBullets().iterator();
        while (bulletIterator.hasNext()) {
            PlayerBullet bullet = bulletIterator.next();

            if (bonusEmail != null && CollisionManager.checkCollision(bonusEmail, bullet)) {
                activeExplosions.add(bonusEmail.destroy());
                GameManager.getInstance().addScore(bonusEmail.getPoints());
                bonusEmail = null;
                if (player.getLife().size() < 3) {
                    player.addLife();
                }
                break;
            }

            Iterator<Enemy> enemyIterator = enemies.iterator();
            while (enemyIterator.hasNext()) {
                Enemy enemy = enemyIterator.next();

                if (CollisionManager.checkCollision(enemy, bullet)) {
                    GameManager.getInstance().addScore(enemy.getPoints());
                    activeExplosions.add(enemy.destroy());
                    bulletIterator.remove();
                    enemyIterator.remove();
                    break;
                }
            }
            Iterator<Barrier> barrierIterator = barriers.iterator();
            while (barrierIterator.hasNext()) {
                Barrier barrier = barrierIterator.next();

                if (CollisionManager.checkCollision(barrier, bullet)) {
                    bulletIterator.remove();
                    barrier.takeDamage();
                    if (barrier.isDestroyed()) {
                        barrierIterator.remove();
                    }
                    break;
                }
            }
        }

    }

    private void updateEnemyPositions() {
        double maxX = 0;
        double minX = gameCanvas.getWidth();

        // Anpassung der Gegnergeschwindigkeit basierend auf der Anzahl der verbleibenden Gegner
        double baseEnemySpeed = ENEMY_SPEED;
        double speed = baseEnemySpeed + (baseEnemySpeed * Math.round(1 - (double) enemies.size() / (ENEMIES_PER_ROW * ENEMY_ROWS)));

        for (Enemy enemy : enemies) {
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
            for (Enemy enemy : enemies) {
                enemy.setY(enemy.getY() + ENEMY_HEIGHT); // Gegner eine Reihe nach unten bewegen
                if (enemy.getY() + ENEMY_HEIGHT >= gameCanvas.getHeight()) {
                    gameOver();
                    return;
                }
            }
        }

        // Gegner horizontal bewegen
        for (Enemy enemy : enemies) {
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


    private void updateEnemyBullets() {
        for (Enemy enemy : enemies) {
            Iterator<EnemyBullet> it = enemy.getEnemyBullets().iterator();
            while (it.hasNext()) {
                EnemyBullet bullet = it.next();
                bullet.update();
                if (bullet.getY() > gameCanvas.getHeight()) {
                    it.remove(); // Entfernt das Geschoss, wenn es den unteren Bildschirmrand erreicht
                }
            }
        }
    }

    private void updateBonuses() {
        Iterator<Bonus> bonusIterator = activeBonuses.iterator();
        while (bonusIterator.hasNext()) {
            Bonus bonus = bonusIterator.next();
            bonus.setY(bonus.getY() + bonus.getSpeed());

            if (bonus.getY() > gameCanvas.getHeight()) {
                bonusIterator.remove();
                continue; // Zum nächsten Bonus springen
            }

            if (bonus.getTyp() == 4) {
                removeEnemiesOnPath(bonus);
                if (CollisionManager.checkCollision(player, bonus)) {
                    applyBonusAndRemove(bonus, bonusIterator);
                }
                continue; // Zum nächsten Bonus springen
            }

            if (CollisionManager.checkCollision(player, bonus)) {
                applyBonusAndRemove(bonus, bonusIterator);
            }
        }
    }

    private void removeEnemiesOnPath(Bonus bonus) {
        Iterator<Enemy> enemyIterator = enemies.iterator();
        while (enemyIterator.hasNext()) {
            Enemy enemy = enemyIterator.next();
            if (CollisionManager.checkCollision(bonus, enemy)) {
                activeExplosions.add(enemy.destroy());
                enemyIterator.remove();
            }
        }
    }

    private void applyBonusAndRemove(Bonus bonus, Iterator<Bonus> bonusIterator) {
        bonus.applyBonus(player);
        bonusIterator.remove();
    }


    private void updateGame(long now) {
        updateBarriers();
        updateBonuses();
        if (enemies.isEmpty()) {
            spawnEnemies();
        }
        Iterator<PlayerBullet> it = player.getBullets().iterator();
        while (it.hasNext()) {
            PlayerBullet bullet = it.next();
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
        activeExplosions.removeIf(Explosion::isAnimationFinished);
        if (enemies.isEmpty()) {
            spawnEnemies();
        }
        if (barriers.isEmpty() && GameManager.getInstance().getScore() % 1000 == 0) {
            spawnBarriers();
        }
        if (player.getLife().isEmpty()) {
            if (isSpacePressed) {
                isSpacePressed = false;
            }
            enemies.clear();
            barriers.clear();
            activeBonuses.clear();
            activeExplosions.clear();
            gameOver();
        }
    }

    private void renderGame() {
        GraphicsContext gc = gameCanvas.getGraphicsContext2D();
        scoreText.setText("Score: " + GameManager.getInstance().getScore());
        gc.clearRect(0, 0, gameCanvas.getWidth(), gameCanvas.getHeight());
        EmailInvaderRenderer renderer = new EmailInvaderRenderer(gc);
        renderer.renderPlayer(player);
        renderer.renderBarriers(barriers);
        renderer.renderEnemies(enemies);
        renderer.renderBullets(player, enemies);
        renderer.renderHearts(player);
        renderer.renderBonusMail(bonusEmail);
        renderer.renderBonusDrops(activeBonuses);
        renderer.renderExplosions(activeExplosions);
    }


    private void gameOver() {
        try {
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("GameOverView.fxml"));
            Parent gameOverRoot = loader.load();
            GameManager.getInstance().updateHighscore();

            Scene gameOverScene = new Scene(gameOverRoot);

            Stage stage = (Stage) gameCanvas.getScene().getWindow();

            stage.setScene(gameOverScene);
            stage.show();
        } catch (Exception e) {
            //
        }
    }
}




