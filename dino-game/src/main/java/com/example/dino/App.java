package com.example.dino;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Objects;

public class App extends Application {
    private final double SCREEN_WIDTH = 800;
    private final double SCREEN_HEIGHT = 600;

    @Override
    public void start(Stage stage) {
        Canvas canvas = new Canvas(SCREEN_WIDTH, SCREEN_HEIGHT);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        long rootStartTime = System.nanoTime();

        StackPane root = new StackPane(canvas);

        Image resRock = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/assets/rocks.png")));
        AnimationFrames rockManager = new AnimationFrames(resRock, 24,24);
        Image anm = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/assets/animation.png")));

        ArrayList<Sprite> rocks = new ArrayList<>();
        rocks.add(new Sprite(new Position(SCREEN_WIDTH,0), new Speed(-5,0), SCREEN_WIDTH,  SCREEN_HEIGHT, rockManager, 1));

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                gc.setFill(Color.rgb(20, 20, 35));
                gc.fillRect(0, 0, 800, 600); // Cancella tutto

                for (Sprite s : rocks) {
                    s.update();
                    s.render(gc);
                }

            }
        };
        timer.start();

        stage.setScene(new Scene(root, 800, 600));
        stage.setTitle("Gino Game");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
