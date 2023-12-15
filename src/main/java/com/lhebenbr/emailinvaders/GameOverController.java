package com.lhebenbr.emailinvaders;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import java.awt.Desktop;
import java.net.URI;

public class GameOverController {

    @FXML
    private Button restartButton;


    @FXML
    private void onRestartButtonClicked() {
        try {
            // MenuView laden und anzeigen
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
    private void openHyperlink() {
        try {
            Desktop.getDesktop().browse(new URI("https://www.youtube.com/watch?v=dQw4w9WgXcQ"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
