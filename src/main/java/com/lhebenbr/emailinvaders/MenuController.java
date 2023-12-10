package com.lhebenbr.emailinvaders;

import javafx.animation.FadeTransition;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

import static com.lhebenbr.emailinvaders.Config.HEIGHT;
import static com.lhebenbr.emailinvaders.Config.WIDTH;

public class MenuController {
    private Stage stage;

    @FXML
    private Button startButton;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void initialize() {
        startButton.setFocusTraversable(true);
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(1), startButton);
        fadeTransition.setFromValue(1.0); // Fully opaque
        fadeTransition.setToValue(0.0);   // Fully transparent
        fadeTransition.setCycleCount(Timeline.INDEFINITE);
        fadeTransition.setAutoReverse(true); // This makes it fade in again
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
            Scene gameScene = new Scene(gameRoot, WIDTH, HEIGHT);

            stage.setScene(gameScene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}