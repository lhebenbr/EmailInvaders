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

import static com.lhebenbr.emailinvaders.Config.ENEMIES_PER_ROW;
import static com.lhebenbr.emailinvaders.Config.ENEMY_ROWS;

public class EmaillInvaderController {

    @FXML
    private Canvas gameCanvas;

    @FXML
    private Text scoreText;

    private boolean movingLeft = false;
    private boolean movingRight = false;
    private boolean isSpacePressed = false;
    private double playerShipX = 960;
    private double playerShipY = 910;

    private double enemyX= 80;
    private double enemyY= 80;
    private double bulletX= 20;
    private double bulletY= 20;
    private final double  playerShipSpeed = 4.5;
    private final Player player = new Player(100,40);;
    private double enemySpeed = 1; // Geschwindigkeit der Gegner
    private boolean enemyMovingRight = true; // Anfangsrichtung
    private Random random = new Random();
    private List<PlayerBullet> bullets = new ArrayList<>();
    private List<EnemyBullet> enemyBullets = new ArrayList<>();
    private List<Enemy> enemies = new ArrayList<>();
    private List<Barrier> barriers = new ArrayList<>();
    private double baseEnemySpeed = 1.0;

    private int playerLives = 3;
    private List<Image> heartImages = new ArrayList<>();
    private Enemy bonusEmail;
    private long bonusEmailTimer = 0;
    private final long bonusEmailInterval = 30_000_000_000L;
    private int currentScore = 0;



    public void initialize() {
        for (int i = 0; i < playerLives; i++) {
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
                        shoot();
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
                if (movingLeft && playerShipX > 0) {
                    playerShipX -= playerShipSpeed;
                }
                if (movingRight && playerShipX < gameCanvas.getWidth() - player.getWidth()) {
                    playerShipX += playerShipSpeed;
                }
                enemiesShoot();
                updateEnemyPositions();
                updateEnemyBullets();
                checkCollisions();
                checkEnemyBulletCollisions();
                if (enemies.isEmpty()) {
                    spawnEnemies();
                }
                redrawGameCanvas();
            }
        }.start();
    }




    private void spawnPlayerShip() {
        GraphicsContext gc = gameCanvas.getGraphicsContext2D();
        Image image = new Image("file:src/main/resources/com/lhebenbr/emailinvaders/assets/textures/ship.png");
        gc.drawImage(image, playerShipX, playerShipY);
    }

    private void spawnBarriers() {
        GraphicsContext gc = gameCanvas.getGraphicsContext2D();
        Image image = new Image("file:src/main/resources/com/lhebenbr/emailinvaders/assets/textures/barrier_1.png");
        int startWidth = 150;
        double barrierWidth = 200;
        double barrierHeight = 70;
        for (int i = 0; i < 4; i++) {
            barriers.add(new Barrier(startWidth, 810, barrierWidth, barrierHeight));
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

    private void spawnEnemies(GraphicsContext gc) {
        for (Enemy enemy : enemies) {
            gc.drawImage(enemy.getImage(), enemy.getX(), enemy.getY(), 80, 80);
        }
    }

    private void shoot() {
        long currentTime = System.currentTimeMillis();
        if (player.canShoot(currentTime)){
            bullets.add(new PlayerBullet(playerShipX + 45, playerShipY,8.0));
        }
    }

    private void checkEnemyBulletCollisions() {
        Iterator<EnemyBullet> bulletIterator = enemyBullets.iterator();
        while (bulletIterator.hasNext()) {
            EnemyBullet bullet = bulletIterator.next();

            // Kollision mit dem Spieler
            if (bullet.getX() < playerShipX + player.getWidth() &&
                    bullet.getX() + bulletX > playerShipX &&
                    bullet.getY() < playerShipY + player.getHeight() &&
                    bullet.getY() + bulletY > playerShipY) {
                // Treffer am Spieler
                bulletIterator.remove();
                handlePlayerHit(); // Implementieren Sie diese Methode entsprechend
                continue;
            }

            // Kollision mit Barrieren
            Iterator<Barrier> barrierIterator = barriers.iterator();
            while (barrierIterator.hasNext()) {
                Barrier barrier = barrierIterator.next();
                if (bullet.getX() < barrier.getX() + barrier.getWidth() &&
                        bullet.getX() + bulletX > barrier.getX() &&
                        bullet.getY() < barrier.getY() + barrier.getHeight() &&
                        bullet.getY() + bulletY > barrier.getY()) {
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
            Iterator<PlayerBullet> playerBulletIterator = bullets.iterator();
            while (playerBulletIterator.hasNext()) {
                PlayerBullet playerBullet = playerBulletIterator.next();
                if (bullet.getX() < playerBullet.getX() + bulletX &&
                        bullet.getX() + bulletX > playerBullet.getX() &&
                        bullet.getY() < playerBullet.getY() + bulletY &&
                        bullet.getY() + bulletY > playerBullet.getY()) {
                    // Treffer eines Spieler-Geschosses
                    bulletIterator.remove();
                    playerBulletIterator.remove();
                    break;
                }
            }
        }
    }

    private void handlePlayerHit() {
        playerLives--; // Ein Leben abziehen
        if (playerLives <= 0) {
            endGame(); // Spiel beenden, wenn keine Leben mehr vorhanden sind
        }
    }

    private void checkCollisions() {
        Iterator<PlayerBullet> bulletIterator = bullets.iterator();
        while (bulletIterator.hasNext()) {
            PlayerBullet bullet = bulletIterator.next();

            if (bonusEmail != null && bullet.collidesWith(bonusEmail)) {
                bulletIterator.remove();
                currentScore += bonusEmail.getPoints();
                bonusEmail = null;
                if(playerLives < 3) {
                    playerLives++;
                }
                break;
            }

            Iterator<Enemy> enemyIterator = enemies.iterator();
            while (enemyIterator.hasNext()) {
                Enemy enemy = enemyIterator.next();

                if (bullet.getX() < enemy.getX() + enemyX &&
                        bullet.getX() + bulletX > enemy.getX() &&
                        bullet.getY() < enemy.getY() + enemyY &&
                        bullet.getY() + bulletY > enemy.getY()) {
                    currentScore += enemy.getPoints();
                    // Kollision erkannt
                    bulletIterator.remove();
                    enemyIterator.remove();
                    // Weitere Aktionen bei Kollision (z.B. Punkte erhöhen)
                    break;
                }
            }
            Iterator<Barrier> barrierIterator = barriers.iterator();
            while (barrierIterator.hasNext()) {
                Barrier barrier = barrierIterator.next();

                if (bullet.getX() < barrier.getX() + barrier.getWidth() &&
                        bullet.getX() + bulletX > barrier.getX() &&
                        bullet.getY() < barrier.getY() + barrier.getHeight() &&
                        bullet.getY() + bulletY > barrier.getY()) {
                    // Kollision mit einer Barriere erkannt
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
        enemySpeed = baseEnemySpeed + (baseEnemySpeed * Math.round(1 - (double)enemies.size() / (ENEMIES_PER_ROW * ENEMY_ROWS)));

        for (Enemy enemy : enemies) {
            if (enemy.getX() > maxX) {
                maxX = enemy.getX();
            }
            if (enemy.getX() < minX) {
                minX = enemy.getX();
            }
        }

        // Prüfen, ob die Gegner umkehren müssen
        if ((enemyMovingRight && maxX + enemyX + enemySpeed > gameCanvas.getWidth()) ||
                (!enemyMovingRight && minX - enemySpeed < 0)) {
            enemyMovingRight = !enemyMovingRight;
            for (Enemy enemy : enemies) {
                enemy.setY(enemy.getY() + enemyY); // Gegner eine Reihe nach unten bewegen
                if (enemy.getY() + enemyY >= gameCanvas.getHeight()) {
                    endGame();
                    return;
                }
            }
        }

        // Gegner horizontal bewegen
        for (Enemy enemy : enemies) {
            if (enemyMovingRight) {
                enemy.setX(enemy.getX() + enemySpeed);
            } else {
                enemy.setX(enemy.getX() - enemySpeed);
            }
        }

        // Bonus-Email bewegen, falls vorhanden
        if (bonusEmail != null) {
            bonusEmail.setX(bonusEmail.getX() + 2);
            if (bonusEmail.getX() > gameCanvas.getWidth()) {
                bonusEmail = null;
            }
        }
    }


    private void enemiesShoot() {
        double shootProbability = calculateShootProbability();
        for (Enemy enemy : enemies) {
            if ((random.nextDouble()) < shootProbability) {
                enemyBullets.add(new EnemyBullet(enemy.getX() + enemyX / 2, enemy.getY(),5.0));
            }
        }
    }

    private void updateEnemyBullets() {
        Iterator<EnemyBullet> it = enemyBullets.iterator();
        while (it.hasNext()) {
            EnemyBullet bullet = it.next();
            bullet.update();
            if (bullet.getY() > gameCanvas.getHeight()) {
                it.remove(); // Entfernt das Geschoss, wenn es den unteren Bildschirmrand erreicht
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
        Iterator<PlayerBullet> it = bullets.iterator();
        while (it.hasNext()) {
            PlayerBullet bullet = it.next();
            bullet.update();
            if (bullet.getY() < 0) {
                it.remove();
            } else {
                gc.drawImage(new Image("file:src/main/resources/com/lhebenbr/emailinvaders/assets/textures/laser1.png"), bullet.getX(), bullet.getY());
            }
        }
        for (EnemyBullet enemyBullet : enemyBullets) {
            gc.drawImage(new Image("file:src/main/resources/com/lhebenbr/emailinvaders/assets/textures/bullet.png"), enemyBullet.getX(), enemyBullet.getY());
        }
        int heartX = (int) gameCanvas.getWidth() - 80;
        int heartY = 10;
        for (int i = 0; i < playerLives; i++) {
            gc.drawImage(heartImages.get(i), heartX, heartY, 40, 40);
            heartX -= 50;
        }
        // Bonus-Email zeichnen, falls vorhanden
        if (bonusEmail != null) {
            gc.drawImage(bonusEmail.getImage(), bonusEmail.getX(), bonusEmail.getY(),60,60);
        }
    }


    private void endGame() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("GameOverView.fxml"));
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

    private void spawnBonusEmail() {
        bonusEmail = EnemyFactory.createBonusEnemey( 0, 10, 60, 60);
    }

    private double calculateShootProbability() {
        // Basis-Schießwahrscheinlichkeit
        double baseProbability = 0.0024;

        // Erhöhte Wahrscheinlichkeit, wenn weniger Gegner vorhanden sind
        double multiplier = 1.0 + (1.0 - (double)enemies.size() / (ENEMIES_PER_ROW * ENEMY_ROWS));

        // Maximalwert
        double maxProbability = 0.2;

        return Math.min(baseProbability * multiplier, maxProbability);
    }



}