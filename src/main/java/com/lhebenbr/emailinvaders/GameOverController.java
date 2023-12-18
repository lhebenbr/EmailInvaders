package com.lhebenbr.emailinvaders;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

public class GameOverController {

    @FXML
    private Button restartButton;

    @FXML
    private Button exitButton;

    @FXML
    private TextFlow textFlow;

    @FXML
    private Text highscoreText;

    @FXML
    private void initialize() {
        highscoreText.setText("Highscore: " + String.valueOf(GameManager.getInstance().getHighScore()));

        Text bodyContent = new Text("Lieber E-Mail Benutzer,\n\n" +
                "Wir migrieren alle E-Mail-Konten auf neue Outlook Web App 2023...:");
        bodyContent.setFill(javafx.scene.paint.Color.YELLOW);

        textFlow.getChildren().addAll(
                bodyContent
        );
    }

    @FXML
    private void onRestartButtonClicked() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("MenuView.fxml"));
            Parent menuView = loader.load();
            Stage stage = (Stage) restartButton.getScene().getWindow();
            stage.setScene(new Scene(menuView));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onExitButtonClicked() {
        Stage stage = (Stage) exitButton.getScene().getWindow();
        GameManager.exitGame(stage);
    }
}
