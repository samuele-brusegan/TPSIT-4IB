package com.example.dino;


import com.example.dino.sprites.Sprite;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Objects;

import com.example.dino.sprites.*;
/**
 * Classe principale dell'applicazione "Gino Game".
 * <p>
 * Implementa un gioco simile a Chrome Dino Runner dove il giocatore controlla
 * un personaggio (Gino) che deve evitare ostacoli (rocce) che compaiono
 * periodicamente. Il gioco utilizza JavaFX per il rendering e gestisce
 * collisioni tramite un pattern Observer.
 * <p>
 * Funzionalità principali:
 *   - Generazione procedurali di rocce con frame casuali
 *   - Sistema di fisica con gravità e salto
 *   - Rilevamento collisioni hitbox-based
 *   - Animazione frame-based degli sprite
 *   - Input da tastiera (SPAZIO per saltare)
 */
public class App extends Application {
    private final double SCREEN_WIDTH = 800;
    private final double SCREEN_HEIGHT = 600;
    private final double GAME_BASELINE = SCREEN_HEIGHT - 90; // Linea del terreno (piedi di Gino e rocce)
    private final double MAX_ROCK_SPEED = 5000;
    private long acumu_rock = 0;
    private final long trash_rock = 3000;
    private final double rock_speed = 512;
    private long acumu_gino = 0;
    private final long trash_gino = 150;
    private long acumu_scor = 0;
    private final long trash_scor = 150;
    private long lastFrame  = 0;
    private double distanceRan = 0;

    @Override
    public void start(Stage stage) {
        Canvas canvas = new Canvas(SCREEN_WIDTH, SCREEN_HEIGHT);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        StackPane root = new StackPane(canvas);
        Score score = new Score(0);
        Label scoreLabel = new Label("Score: "+score.get());

        Image resRock = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/assets/rocks.png")));
        FrameManager rockManager = new FrameManager(resRock, 24,24);
        Image resGino = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/assets/animation.png")));
        FrameManager ginoManager = new FrameManager(resGino, 48,48);

        // Build main character
        double ginoHeight = ginoManager.getFrameH() * 4; // Scala 4
        double ginoY = GAME_BASELINE - ginoHeight; // Posizione Y della testa (groundY)
        Gino gino = new Gino(
            new Position(10, ginoY),
            new Speed(0,0),
            SCREEN_WIDTH,
            SCREEN_HEIGHT,
            ginoManager,
            0,
            4,
            ginoY
        );
        gino.setCustomHitbox(16*gino.getScale(),24*gino.getScale(), 16*gino.getScale(), 18*gino.getScale());

        // Creating Sprite Lists
        ArrayList<Sprite> sprites = new ArrayList<>();
        ArrayList<Sprite> rocks = new ArrayList<>();
        sprites.add(gino);

        CollisionDetector cd = new CollisionDetector();

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                long deltaTime = (now - lastFrame) / 1_000_000;

                lastFrame = now;

                // Gino corre
                acumu_gino += deltaTime;
                if (acumu_gino >= trash_gino) {
                    gino.cycleFrames();
                    acumu_gino = 0;
                }

                // Spawn Rocks
                may_spawn_rocks(rocks,sprites,deltaTime);

                // Calcolo il punteggio
                may_update_score(deltaTime);

                // Cancella schermo
                clean_screen();

                // Posiziona gli sprite
                for (Sprite s : sprites) {
                    s.update(deltaTime);
                    s.render(gc);
                }
                gino.render(gc); // Gino sopra tutti

                // Check collisions
                for (Sprite s : rocks) {
                    cd.check(gino.getHitbox(), s.getHitbox());
                }

                // Remove out of bounds rocks
                sprites.removeIf(Sprite::isOutOfBounds);
                rocks.removeIf(Sprite::isOutOfBounds);
            }

            private void may_spawn_rocks(ArrayList<Sprite> rocks, ArrayList<Sprite> sprites, long deltaTime) {
                acumu_rock += deltaTime;
                long trash_rock_1 = trash_rock * ( score.get()+1 )/100;
                double deltaRock = Math.random()*10;

                if (acumu_rock >= trash_rock_1+deltaRock) {

                    int frameType = (int) (Math.random() * rockManager.getNumberOfFrames());
//                    int scale = (int) (Math.random()*2.5)+2;
                    int scale = 2;
                    double speed = -rock_speed*((double) (score.get()+1) /100);

                    Sprite newRock = new Sprite(
                            new Position(SCREEN_WIDTH, GAME_BASELINE - (scale * rockManager.getFrameH())-12),
                            new Speed( (Math.abs(speed)<MAX_ROCK_SPEED)?speed:MAX_ROCK_SPEED,0),
                            SCREEN_WIDTH,
                            SCREEN_HEIGHT,
                            rockManager,
                            frameType,
                            scale
                    );
                    rocks.add(newRock);
                    sprites.add(newRock); // Aggiungi anche a sprites per il rendering
                    acumu_rock = 0;
                }
            }

            private void clean_screen() {
                gc.setFill(Color.rgb(20, 20, 75));
                gc.fillRect(0, 0, 800, 600); // Cancella tutto
                gc.setFill(Color.web("#311602"));
                gc.fillRect(0, GAME_BASELINE-24, SCREEN_WIDTH, SCREEN_HEIGHT - GAME_BASELINE+24);
            }

            private void may_update_score(long deltaTime) {
                acumu_scor += deltaTime;
                if (acumu_scor >= trash_scor) {
                    distanceRan += (double) acumu_scor / 1_000_000 * rock_speed * ( (double) (score.get()+1) /2);

                    score.set((int) (distanceRan*0.025));
                    scoreLabel.setText("Score: "+score.get());
                    acumu_scor = 0;
                }
            }
        };
        timer.start();

        cd.addListener(() -> {
            System.out.println("Collision Detected!");
            timer.stop();
        });

        scoreLabel.setTextFill(Color.WHITE);
        root.getChildren().add(scoreLabel);
        StackPane.setAlignment(scoreLabel, Pos.TOP_RIGHT);

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
