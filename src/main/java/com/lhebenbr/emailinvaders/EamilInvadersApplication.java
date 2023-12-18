package com.lhebenbr.emailinvaders;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.File;

import static com.lhebenbr.emailinvaders.Config.HEIGHT;
import static com.lhebenbr.emailinvaders.Config.WIDTH;

public class EamilInvadersApplication extends Application {

    public void startGame(Stage primaryStage) throws Exception {
        start(primaryStage);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        String musicFile = "src/main/resources/com/lhebenbr/emailinvaders/assets/music/music.wav";
        Media sound = new Media(new File(musicFile).toURI().toString());
        AudioClip mediaPlayer = new AudioClip(sound.getSource());
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayer.setVolume(0.5);
        mediaPlayer.play();


        FXMLLoader loader = new FXMLLoader(getClass().getResource("MenuView.fxml"));
        Font.loadFont(getClass().getResourceAsStream("assets/font/8bit_wonder.TTF"), 36);
        Parent root = loader.load();


        Scene scene = new Scene(root, WIDTH, HEIGHT);
        primaryStage.setTitle("Menu");
        primaryStage.setScene(scene);
        primaryStage.show();
        }

        public static void main(String[] args) {
            launch(args);
        }
    }

