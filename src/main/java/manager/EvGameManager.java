package manager;

import event.EmailInvadersExit;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;


public class EvGameManager {
    private int score;
    private Scene currentScene;
    private static EvGameManager instance;

    public static EvGameManager getInstance() {
        if (instance == null) {
            instance = new EvGameManager();
        }
        return instance;
    }

    public EvGameManager() {
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void addScore(int score) {
        this.score += score;
    }

    public Scene getCurrentScene() {
        return currentScene;
    }

    public void setCurrentScene(Scene currentScene) {
        this.currentScene = currentScene;
    }

    public void fireEvent() {
        EmailInvadersExit exitEvent = new EmailInvadersExit();
        EvSoundManager.getInstance().stopAllSounds();
        WindowEvent.fireEvent(this.getCurrentScene(),exitEvent);
    }

}
