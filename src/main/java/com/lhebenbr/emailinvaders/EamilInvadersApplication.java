package com.lhebenbr.emailinvaders;
import com.lhebenbr.emailinvaders.controller.EmaillInvaderController;
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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("EmailInvaderView.fxml"));
        Parent root = loader.load();

        // Zugriff auf den Controller
        EmaillInvaderController controller = loader.getController();

        Scene scene = new Scene(root, WIDTH, HEIGHT);
        primaryStage.setTitle("Email Invaders");
        primaryStage.setScene(scene);
        primaryStage.show();
        }

        public static void main(String[] args) {
            launch(args);
        }
    }
