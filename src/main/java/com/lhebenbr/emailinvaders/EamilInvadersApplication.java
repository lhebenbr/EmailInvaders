package com.lhebenbr.emailinvaders;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import static com.lhebenbr.emailinvaders.Config.HEIGHT;
import static com.lhebenbr.emailinvaders.Config.WIDTH;

public class EamilInvadersApplication extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
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

