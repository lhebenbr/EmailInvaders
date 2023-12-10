package com.lhebenbr.emailinvaders;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import static com.lhebenbr.emailinvaders.Config.HEIGHT;
import static com.lhebenbr.emailinvaders.Config.WIDTH;

public class EamilInvadersApplication extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MenuView.fxml"));
        Parent root = loader.load();

        // Zugriff auf den Controller
        MenuController controller = loader.getController();
        controller.setStage(primaryStage);

        Scene scene = new Scene(root, WIDTH, HEIGHT);
        primaryStage.setTitle("Menu");
        primaryStage.setScene(scene);
        primaryStage.show();
        }

        public static void main(String[] args) {
            launch(args);
        }
    }

