import event.EmailInvadersExit;
import javafx.application.Application;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import manager.EvGameManager;
import manager.EvSoundManager;


public class EmailInvaders extends Application {

    public static Event EMAIL_INVADERS_EXIT = new EmailInvadersExit();



    public void startGame(Stage primaryStage) throws Exception {
        start(primaryStage);
    }

    public Scene getScene() {
        try {
            EvSoundManager.getInstance().playSound("/assets/music/music.wav", true);
            Font.loadFont(getClass().getResourceAsStream("assets/font/8bit_wonder.TTF"), 36);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("MenuView.fxml"));
            Parent gameRoot = loader.load();
            return new Scene(gameRoot);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public void start(Stage primaryStage) throws Exception {
        Scene scene = getScene();
        EvGameManager.getInstance().setCurrentScene(scene);
        primaryStage.setTitle("Menu");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

