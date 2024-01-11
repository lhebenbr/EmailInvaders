package model;

import javafx.animation.AnimationTimer;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Die Klasse {@code EvExplosion} stellt eine Explosion im Spiel dar.
 * Sie verwendet eine Animation, um die Explosion zu visualisieren.
 */
public class EvExplosion {
    private ImageView imageView;
    private Image[] frames;
    private int currentFrame = 0;
    private long lastUpdate = 0;
    private AnimationTimer timer;

    private int width;
    private int height;

    /**
     * Konstruktor für {@code EvExplosion}.
     *
     * @param frames Die Bildfolge für die Explosionsanimation.
     * @param x      Die X-Position der Explosion auf dem Bildschirm.
     * @param y      Die Y-Position der Explosion auf dem Bildschirm.
     * @param width  Die Breite der Explosionsanimation.
     * @param height Die Höhe der Explosionsanimation.
     */
    public EvExplosion(Image[] frames, double x, double y, int width, int height) {
        this.frames = frames;
        imageView = new ImageView(frames[0]);
        imageView.setX(x);
        imageView.setY(y);
        imageView.setFitWidth(width);
        imageView.setFitHeight(height);
        this.width = width;
        this.height = height;
        startAnimationTimer();
    }

    /**
     * Startet den Timer für die Animationssteuerung.
     */
    private void startAnimationTimer() {
        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (now - lastUpdate >= 5_000_000) { // Frame-Update alle 50ms
                    if (currentFrame < frames.length) {
                        imageView.setImage(frames[currentFrame++]);
                    } else {
                        stop();
                        imageView.setVisible(false);
                    }
                    lastUpdate = now;
                }
            }
        };
        timer.start();
    }

    public ImageView getImageView() {
        return imageView;
    }

    public boolean isAnimationFinished() {
        return currentFrame >= frames.length;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
