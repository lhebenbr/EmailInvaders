package manager;

import event.EmailInvadersExit;
import javafx.event.Event;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;


public class EvGameManager {
    private int score;
    private Scene currentScene;
    private static EvGameManager instance;
    public static Scene DUMMY_SCENE;

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
        try {
            Event.fireEvent(DUMMY_SCENE,exitEvent);
        } catch (NullPointerException e) {
           System.out.println("DUMMY_SCENE ist nötig für Integration");
        }
    }

}
