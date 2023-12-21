package model;

import javafx.animation.PauseTransition;
import javafx.scene.image.Image;
import javafx.util.Duration;
import manager.EvGameManager;
import manager.EvSoundManager;

import static config.EvConfig.BONUS_DROP_SPEED;

public class EvBonus extends EvEntity {

    private int typ;

    private int speed;

    public EvBonus(double x, double y, Image image, int width, int height, int typ) {
        super(x, y, image, width, height);
        this.typ = typ;
        this.speed = BONUS_DROP_SPEED;
    }

    public int getTyp() {
        return typ;
    }

    public void setTyp(int typ) {
        this.typ = typ;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }


    public void applyBonus(EvPlayer player) {
        EvSoundManager.getInstance().playSound("src/main/resources/assets/music/powerup.wav", false);
        switch (typ) {
            case 1:
                if (player.getLife() < 3) {
                    player.addLife();
                }
                break;
            case 2:
                EvGameManager.getInstance().addScore(1000);
                break;
            case 3:
                player.setNoLimit(true);
                PauseTransition delay = new PauseTransition(Duration.seconds(1));
                delay.setOnFinished(event -> player.setNoLimit(false));
                delay.play();
                break;
            case 4:
                player.loseLife();
            default:
                break;
        }
    }
}
