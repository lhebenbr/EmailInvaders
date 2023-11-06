package com.lhebenbr.emailinvaders;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class EmaillInvaderController {

    @FXML
    private Canvas gameCanvas;

    public void initialize() {
        drawPlayerShip();
        drawEnemyInvaders();
    }


    private void drawPlayerShip() {
        GraphicsContext gc = gameCanvas.getGraphicsContext2D();
        gc.setFill(Color.GREEN);
        gc.fillRect(370, 550, 60, 20);
    }

    private void drawEnemyInvaders() {
        GraphicsContext gc = gameCanvas.getGraphicsContext2D();
        gc.setFill(Color.RED);
        // Draw enemy invaders at their respective positions
        // You can loop through an array of enemy positions and draw them here
    }
}