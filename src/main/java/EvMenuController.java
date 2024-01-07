import config.EvConfig;
import javafx.animation.FadeTransition;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;

import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import manager.EvGameManager;

import java.io.IOException;


public class EvMenuController {

    @FXML
    private Button startButton;
    @FXML
    private Button exitButton;



    public void initialize() {
        startButton.setFocusTraversable(true);
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(1), startButton);
        fadeTransition.setFromValue(1.0);
        fadeTransition.setToValue(0.0);
        fadeTransition.setCycleCount(Timeline.INDEFINITE);
        fadeTransition.setAutoReverse(true);
        fadeTransition.play();



        startButton.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER || event.getCode() == KeyCode.SPACE) {
                onStartButtonClicked();
            }
        });

    }

    @FXML
    private void onStartButtonClicked() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("EmailInvaderView.fxml"));
            Parent gameRoot = loader.load();
            Scene gameScene = new Scene(gameRoot);
            Stage stage = (Stage) startButton.getScene().getWindow();
            EvGameManager.getInstance().setCurrentScene(gameScene);

            stage.setScene(EvGameManager.getInstance().getCurrentScene());
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onExitButtonClicked() {
        EvGameManager.getInstance().fireEvent();
    }
}