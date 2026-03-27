package com.example.dino;

import com.example.dino.Sprites.Sprite;
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

import com.example.dino.Sprites.*;

public class App extends Application {
    private final double SCREEN_WIDTH = 800;
    private final double SCREEN_HEIGHT = 600;
    private final double GAME_BASELINE = SCREEN_HEIGHT-250;
    private long acumu_rock = 0;
    private long trash_rock = 1700;
    private long acumu_gino = 0;
    private long trash_gino = 150;
    private long lastFrame  = 0;

    @Override
    public void start(Stage stage) {
        Canvas canvas = new Canvas(SCREEN_WIDTH, SCREEN_HEIGHT);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        long rootStartTime = System.nanoTime();

        StackPane root = new StackPane(canvas);

        Image resRock = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/assets/rocks.png")));
        AnimationFrames rockManager = new AnimationFrames(resRock, 24,24);
        Image resGino = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/assets/animation.png")));
        AnimationFrames ginoManager = new AnimationFrames(resGino, 48,48);


        ArrayList<Sprite> rocks = new ArrayList<>();
        rocks.add(
            new Sprite(
                new Position(SCREEN_WIDTH,GAME_BASELINE), 
                new Speed(-5,0), 
                SCREEN_WIDTH,  
                SCREEN_HEIGHT, 
                rockManager, 
                1
            )
        );
        RunningCharacter gino = new RunningCharacter(
                new Position(10, GAME_BASELINE), 
                new Speed(0,0), 
                SCREEN_WIDTH, 
                SCREEN_HEIGHT,
                ginoManager,
                0,
                4
        );
        long lastFrameTimeStamp = 0;

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                gc.setFill(Color.rgb(20, 20, 35));
                gc.fillRect(0, 0, 800, 600); // Cancella tutto

                for (Sprite s : rocks) {
                    s.update();
                    s.render(gc);
                }
                gino.update();
                gino.render(gc);



                // Remove out of bounds rocks
                rocks.removeIf(Sprite::isOutOfBounds);
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
