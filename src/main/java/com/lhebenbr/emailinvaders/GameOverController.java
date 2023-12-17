package com.lhebenbr.emailinvaders;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;

import java.awt.*;
public class GameOverController {

    @FXML
    private Button restartButton;

    @FXML
    private TextFlow textFlow;

    @FXML
    private Text highscoreText;

    @FXML
    private void initialize() {
        highscoreText.setText("Highscore: " + String.valueOf(GameManager.getInstance().getHighScore()));

        Text bodyContent = new Text("Lieber E-Mail Benutzer,\n\n" +
                "Wir migrieren alle E-Mail-Konten auf neue Outlook Web App 2023.\n Daher müssen sich alle aktiven Kontoinhaber verifizieren und anmelden, \n " +
                "damit Upgrades und Migrationen jetzt automatisch wirksam werden. Dies geschieht,\n" +
                " um die Sicherheit und Effizienz aufgrund der neusten empfangenen Spam-Nachrichten zu verbessern.\n\n" +
                "Um Dienstunterbrechungen zu vermeiden, klicken Sie bitte auf den folgenden Link,\n um ihre Beiträge zu aktualisieren\n ") ;
        bodyContent.setFill(javafx.scene.paint.Color.YELLOW);
        Hyperlink link = new Hyperlink("Outlook Web App 2023");
        Text bodyContent2 = new Text(" und melden Sie sich an, um mehrere Spam-Mails zu migrieren und zu blockieren.\n\n" +
                "Wenn Sie ihr Konto nicht innerhalb von 24 Stunden übertragen, wird ihr Konto vorübergehend gesperrt,\n" +
                " sodass Sie keine E-Mails mehr empfangen oder senden können.\n\n" +
                "IKT-Helpdesk\n" +
                "Informationstechnologie");
        bodyContent2.setFill(javafx.scene.paint.Color.YELLOW);
        textFlow.getChildren().addAll(
                bodyContent,
                link,
                bodyContent2
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
}
