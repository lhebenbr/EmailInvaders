package model;

import javafx.animation.AnimationTimer;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class EvExplosion {
    private ImageView imageView;
    private Image[] frames;
    private int currentFrame = 0;
    private long lastUpdate = 0;
    private AnimationTimer timer;

    private int width;
    private int height;

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

    private void startAnimationTimer() {
        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (now - lastUpdate >= 5_000_000) { // update frame every 50ms
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
