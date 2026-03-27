package com.example.dino;

/**
 * Classe principale dell'applicazione "Gino Game".
 *
 * Implementa un gioco simile a Chrome Dino Runner dove il giocatore controlla
 * un personaggio (Gino) che deve evitare ostacoli (rocce) che compaiono
 * periodicamente. Il gioco utilizza JavaFX per il rendering e gestisce
 * collisioni tramite un pattern Observer.
 *
 * Funzionalità principali:
 *   - Generazione procedurali di rocce con frame casuali
 *   - Sistema di fisica con gravità e salto
 *   - Rilevamento collisioni hitbox-based
 *   - Animazione frame-based degli sprite
 *   - Input da tastiera (SPAZIO per saltare)
 *
 * @author Claude Code
 * @version 1.0
 */
import com.example.dino.Sprites.Sprite;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Objects;

import com.example.dino.Sprites.*;

public class App extends Application {
    private final double SCREEN_WIDTH = 800;
    private final double SCREEN_HEIGHT = 600;
    private final double GAME_BASELINE = SCREEN_HEIGHT-250;
    private long acumu_rock = 0;
    private long trash_rock = 3000;
    private double rock_speed = 512;
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
                new Position(SCREEN_WIDTH,GAME_BASELINE+125),
                new Speed(-rock_speed,0), // 60 pixel/secondo
                SCREEN_WIDTH,
                SCREEN_HEIGHT,
                rockManager,
                1,
                2
            )
        );
        Gino gino = new Gino(
            new Position(10, GAME_BASELINE),
            new Speed(0,0),
            SCREEN_WIDTH,
            SCREEN_HEIGHT,
            ginoManager,
            0,
            4,
            GAME_BASELINE
        );
        gino.setCustomHitbox(24*gino.getScale(),24*gino.getScale(), 12*gino.getScale(), 18*gino.getScale());

        ArrayList<Sprite> sprites = new ArrayList<>();
        for(Sprite s : rocks) sprites.add(s);
        sprites.add(gino);

        long lastFrameTimeStamp = 0;
        CollisionDetector cd = new CollisionDetector();

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                long deltaTime = (now - lastFrame) / 1_000_000;
                lastFrame = now;

                acumu_gino += deltaTime;
                if (acumu_gino >= trash_gino) {
                    gino.cycleFrames();
                    acumu_gino = 0;
                }
                acumu_rock += deltaTime;
                double deltaRock = Math.random()*10;
                if (acumu_rock >= trash_rock+deltaRock) {
                    int frameType = (int) (Math.random() * rockManager.getNumberOfFrames());
                    int scale = (int) (Math.random()*3)+2;

                    System.out.println(frameType);
                    Sprite newRock = new Sprite(
                        new Position(SCREEN_WIDTH,GAME_BASELINE+125),
                        new Speed(-rock_speed,0), // 60 pixel/secondo
                        SCREEN_WIDTH,
                        SCREEN_HEIGHT,
                        rockManager,
                        frameType,
                        scale
                    );
                    rocks.add(newRock);
                    sprites.add(newRock); // Aggiungi anche a sprites per il rendering
                    System.out.println("Rock Added: " + newRock);
                    acumu_rock = 0;
                }

                gc.setFill(Color.rgb(20, 20, 75));
                gc.fillRect(0, 0, 800, 600); // Cancella tutto
                gc.setFill(Color.web("#311602"));
                gc.fillRect(0, GAME_BASELINE+160, SCREEN_WIDTH, SCREEN_HEIGHT-(GAME_BASELINE+160));

                for (Sprite s : sprites) {
                    s.update(deltaTime);
                    s.render(gc);
                }

                for (Sprite s : rocks) {
                    cd.check(gino.getHitbox(), s.getHitbox());
                }

                // Remove out of bounds rocks
                rocks.removeIf(Sprite::isOutOfBounds);
            }
        };
        timer.start();

        cd.addListener(() -> {
            System.out.println("Collision Detected!");
            timer.stop();
        });

        stage.setScene(new Scene(root, 800, 600));
        stage.setTitle("Gino Game");
        stage.getScene().setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.SPACE) {
                gino.jump();
            }
        });
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
