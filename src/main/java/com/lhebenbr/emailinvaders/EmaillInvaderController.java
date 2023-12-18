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
    private final List<Image> heartImages = new ArrayList<>();
    private Enemy bonusEmail;
    private Player player;
    private final Random random = new Random();
    private long bonusEmailTimer = 0;
    private final long bonusEmailInterval = 30_000_000_000L;
    private int currentScore = 0;



    public void initialize() {
        player= new Player(PLAYER_START_X, PLAYER_START_Y, new Image("file:src/main/resources/com/lhebenbr/emailinvaders/assets/textures/ship.png"), PLAYER_WIDTH, PLAYER_HEIGHT,PLAYER_SPEED, START_LIVES);

        for (int i = 0; i < player.getLife(); i++) {
            heartImages.add(new Image("file:src/main/resources/com/lhebenbr/emailinvaders/assets/textures/heart.png"));
        }

        spawnPlayerShip();
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
                    if (!isSpacePressed) {
                        player.shoot();
                    }
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

        new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (bonusEmail == null && now - bonusEmailTimer > bonusEmailInterval) {
                    spawnBonusEmail();
                    bonusEmailTimer = now;
                }
                if (movingLeft && player.getX() > 0) {
                    player.setX(player.getX() - player.getSpeed());
                }
                if (movingRight && player.getX() < gameCanvas.getWidth() - player.getWidth()) {
                    player.setX(player.getX() + player.getSpeed());
                }
                enemies.forEach(enemy -> enemy.enemyShoot(enemies, random));
                updateEnemyPositions();
                updateEnemyBullets();
                checkCollisions();
                checkEnemyBulletCollisions();
                if (enemies.isEmpty()) {
                    spawnEnemies();
                }
                if(barriers.isEmpty() && currentScore % 5000 == 0) {
                    spawnBarriers();
                }
                redrawGameCanvas();
            }
        }.start();
    }




    private void spawnPlayerShip() {
        GraphicsContext gc = gameCanvas.getGraphicsContext2D();
        Image image = new Image("file:src/main/resources/com/lhebenbr/emailinvaders/assets/textures/ship.png");
        gc.drawImage(image, player.getX(), player.getY());
    }

    private void spawnBarriers() {
        GraphicsContext gc = gameCanvas.getGraphicsContext2D();
        Image image = new Image("file:src/main/resources/com/lhebenbr/emailinvaders/assets/textures/barrier_1.png");
        int startWidth = 150;
        int barrierWidth = 200;
        int barrierHeight = 70;
        for (int i = 0; i < 4; i++) {
            barriers.add(new Barrier(startWidth, 810, image, barrierWidth, barrierHeight,30));
            gc.drawImage(image, startWidth, 810, barrierWidth, barrierHeight);
            startWidth += 480;
        }
    }

    private void spawnBarriers(GraphicsContext gc) {
        for (Barrier barrier : barriers) {
            if (!barrier.isDestroyed()) {
                int i = barrier.getHealth() <= 10 ? 3 : barrier.getHealth() <= 20 ? 2 : 1;
                Image barrierImage = new Image("file:src/main/resources/com/lhebenbr/emailinvaders/assets/textures/barrier_"+i+".png");
                gc.drawImage(barrierImage, barrier.getX(), barrier.getY(), barrier.getWidth(), barrier.getHeight());
            }
        }
    }

    private void spawnEnemies() {
        enemies.clear();
        double startWidth = 250;
        double startHeight = 50;
        for (int y = 0; y < ENEMY_ROWS; y++) {
            for (int x = 0; x < ENEMIES_PER_ROW; x++) {
                EnemyFactory.EnemyType type = EnemyFactory.EnemyType.values()[random.nextInt(EnemyFactory.EnemyType.values().length)];
                Enemy enemy = EnemyFactory.createEnemy(type, startWidth, startHeight, 80, 80);
                enemies.add(enemy);
                startWidth += 140;
            }
            startHeight += 100;
            startWidth = 250;
        }
    }

    private void spawnBonusEmail() {
        bonusEmail = EnemyFactory.createBonusEnemey( 0, 10, 60, 60);
    }

    private void checkEnemyBulletCollisions() {
        for(Enemy enemy : enemies) {
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
        if (player.getLife() <= 0) {
            gameOver();
        }
    }

    private void checkCollisions() {
        Iterator<PlayerBullet> bulletIterator = player.getBullets().iterator();
        while (bulletIterator.hasNext()) {
            PlayerBullet bullet = bulletIterator.next();

            if (bonusEmail != null && CollisionManager.checkCollision(bonusEmail,bullet)) {
                bulletIterator.remove();
                currentScore += bonusEmail.getPoints();
                bonusEmail = null;
                if(player.getLife() < 3) {
                    player.addLife();
                }
                break;
            }

            Iterator<Enemy> enemyIterator = enemies.iterator();
            while (enemyIterator.hasNext()) {
                Enemy enemy = enemyIterator.next();

                if (CollisionManager.checkCollision(enemy, bullet)) {
                    currentScore += enemy.getPoints();
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
        double baseEnemySpeed = 1.0;
        double speed = baseEnemySpeed + (baseEnemySpeed * Math.round(1 - (double)enemies.size() / (ENEMIES_PER_ROW * ENEMY_ROWS)));

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

    private void redrawGameCanvas() {
        GraphicsContext gc = gameCanvas.getGraphicsContext2D();
        scoreText.setText("Score: " + currentScore);
        gc.clearRect(0, 0, gameCanvas.getWidth(), gameCanvas.getHeight()); // Löscht den aktuellen Inhalt
        spawnPlayerShip(); // Zeichnet das Schiff an der neuen Position
        spawnBarriers(gc); // Zeichnet die Barrieren neu (falls sich etwas geändert hat)
        spawnEnemies(gc); // Zeichnet die Gegner neu
        Iterator<PlayerBullet> it = player.getBullets().iterator();
        while (it.hasNext()) {
            PlayerBullet bullet = it.next();
            bullet.update();
            if (bullet.getY() < 0) {
                it.remove();
            } else {
                gc.drawImage(new Image("file:src/main/resources/com/lhebenbr/emailinvaders/assets/textures/laser1.png"), bullet.getX(), bullet.getY());
            }
        }
        for(Enemy enemy : enemies) {
            for (EnemyBullet enemyBullet : enemy.getEnemyBullets()) {
                gc.drawImage(new Image("file:src/main/resources/com/lhebenbr/emailinvaders/assets/textures/bullet.png"), enemyBullet.getX(), enemyBullet.getY());
            }
        }
           int heartX = HEART_X;
        for (int i = 0; i < player.getLife(); i++) {
            gc.drawImage(heartImages.get(i), heartX, HEART_Y, HEART_WIDTH, HEART_HEIGHT);
            heartX -= 50;
        }
        // Bonus-Email zeichnen, falls vorhanden
        if (bonusEmail != null) {
            gc.drawImage(bonusEmail.getImage(), bonusEmail.getX(), bonusEmail.getY(),BONUS_MAIL_WIDTH,BONUS_MAIL_HEIGHT);
        }
    }


    private void gameOver() {
        try {
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("GameOverView.fxml"));
            Parent gameOverRoot = loader.load();
            GameManager.getInstance().updateScore(currentScore);

            Scene gameOverScene = new Scene(gameOverRoot);

            Stage stage = (Stage) gameCanvas.getScene().getWindow();

            stage.setScene(gameOverScene);
            stage.show();
        } catch (Exception e) {
           //
        }
    }




    private void spawnEnemies(GraphicsContext gc) {
        for (Enemy enemy : enemies) {
            gc.drawImage(enemy.getImage(), enemy.getX(), enemy.getY(), ENEMY_WIDTH, ENEMY_HEIGHT);
        }
    }



}