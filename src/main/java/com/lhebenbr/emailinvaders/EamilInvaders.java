package com.lhebenbr.emailinvaders;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import static com.lhebenbr.emailinvaders.Config.HEIGHT;
import static com.lhebenbr.emailinvaders.Config.WIDTH;

public class EamilInvaders extends Application {

    public void startGame(Stage primaryStage) throws Exception {
        start(primaryStage);
    }

    public Scene getScene() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("MenuView.fxml"));
            Parent gameRoot = loader.load();
            return new Scene(gameRoot, WIDTH, HEIGHT);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public void start(Stage primaryStage) throws Exception {
        SoundManager.getInstance().playSound("src/main/resources/com/lhebenbr/emailinvaders/assets/music/music.wav", true);
        Font.loadFont(getClass().getResourceAsStream("assets/font/8bit_wonder.TTF"), 36);
        Scene scene = getScene();
        primaryStage.setTitle("Menu");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

