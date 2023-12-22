import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import manager.EvGameManager;

import static config.EvConfig.HEIGHT;
import static config.EvConfig.WIDTH;

public class EvGameOverController {

    @FXML
    private Button restartButton;

    @FXML
    private Button exitButton;

    @FXML
    private TextFlow textFlow;

    @FXML
    private Text ScoreText;

    @FXML
    private void initialize() {
        ScoreText.setText("Score: " + String.valueOf(EvGameManager.getInstance().getScore()));

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
            stage.setScene(new Scene(menuView,WIDTH,HEIGHT));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onExitButtonClicked() {
        Stage stage = (Stage) exitButton.getScene().getWindow();
        EvGameManager.exitGame(stage);
    }
}
