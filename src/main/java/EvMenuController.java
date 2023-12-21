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

import static config.EvConfig.HEIGHT;
import static config.EvConfig.WIDTH;


public class EvMenuController {

    @FXML
    private Button startButton;
    @FXML
    private Button exitButton;
    @FXML
    private Text highscoreText;

    /*
    "Willkommen bei Email Invaders, dem Spiel, das dich in die vorderste Linie der Cyberverteidigung bringt!  Bevor es los geht, lass mich dir ein paar wichtige Informationen geben.

Phishing-Mails sind betrügerische Nachrichten, die darauf abzielen, deine persönlichen Daten zu stehlen. Sie tarnen sich oft als dringende Anfragen von bekannten Institutionen oder als unwiderstehliche Angebote. Aber lass dich nicht täuschen! Ein Klick auf den falschen Link kann gefährliche Folgen haben.

In Email Invaders ist es deine Aufgabe, dein digitales Territorium gegen eine Armada von bösartigen Phishing-Mails zu verteidigen. Benutze die Pfeiltasten, um dein Schiff zu steuern, und die Leertaste, um die Eindringlinge zu bekämpfen. Sammle Power-ups und erhalte Bonuspunkte, indem du die Bonusmail triffst. Aber sei vorsichtig – wenn die Mails zu nah kommen oder du zu oft getroffen wirst, ist das Spiel vorbei.

Bereit für die Herausforderung? Dann lade deine Waffen und bereite dich darauf vor, dein Postfach zu beschützen. Das Spiel beginnt jetzt! Viel Erfolg!"
     */


    public void initialize() {
        highscoreText.setText("Highscore: " + String.valueOf(EvGameManager.getInstance().getHighScore()));
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
            Scene gameScene = new Scene(gameRoot, WIDTH, HEIGHT);
            Stage stage = (Stage) startButton.getScene().getWindow();

            stage.setScene(gameScene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onExitButtonClicked() {
        Stage stage = (Stage) exitButton.getScene().getWindow();
        EvGameManager.exitGame(stage);
    }
}