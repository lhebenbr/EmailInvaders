package manager;

import event.EmailInvadersExit;
import javafx.event.Event;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;



public class EvGameManager {
    private int score;
    private Scene currentScene;
    private static EvGameManager instance;
    public static Scene DUMMY_SCENE = new Scene(new Pane(), 300, 300);


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
            Event.fireEvent(DUMMY_SCENE,exitEvent);
    }

}
