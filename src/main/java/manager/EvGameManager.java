package manager;

import event.EmailInvadersExit;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;

public class EvGameManager {
    private int score;
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

    public static void exitGame(Stage stage) {
        EmailInvadersExit exitEvent = new EmailInvadersExit();
        stage.fireEvent(exitEvent);
        stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
    }

}
