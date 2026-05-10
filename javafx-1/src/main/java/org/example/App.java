package org.example;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * JavaFX App
 */
public class App extends Application {

    private final double SCREEN_WIDTH = 800;
    private final double SCREEN_HEIGHT = 600;

    @Override
    public void start(Stage stage) {
        ArrayList<Puntino> puntini = new ArrayList<>();


        Button btn1 = new Button("Spawn new dot");
        Button btn2 = new Button("Spawn 500");
        Button btn3 = new Button("Java");
        Button btn4 = new Button("Java");

        Stream.of(btn4, btn3).forEach(b -> b.setDisable(true));


        Canvas canvas = new Canvas(SCREEN_WIDTH, SCREEN_HEIGHT);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.WHITE);

        BorderPane root = new BorderPane();

        HBox top = new HBox(btn1, btn2);
        HBox cen = new HBox(canvas);
        HBox bot = new HBox(btn3, btn4);

        top.setPadding(new Insets(10));


        root.setTop(top);
        root.setCenter(cen);
        root.setBottom(bot);


        btn1.setOnAction(e -> {
//            cen.getChildren().add(new Label("Cliccked on JavaFX"));
            creaNuovoPuntino(SCREEN_WIDTH,SCREEN_HEIGHT,gc,puntini);
        });
        btn2.setOnAction(e -> {
            for (int i = 0; i < 500; i++) {
                creaNuovoPuntino(SCREEN_WIDTH,SCREEN_HEIGHT,gc,puntini);
            }
        });

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                gc.setFill(Color.web("#FFFFFF"));
                gc.fillRect(0,0,SCREEN_WIDTH,SCREEN_HEIGHT);

                puntini.forEach(p -> {
                    p.render(gc);
                });
            }
        };
        timer.start();

        var scene = new Scene(root, SCREEN_WIDTH, SCREEN_HEIGHT);
        stage.setScene(scene);
        stage.show();


    }
    private static void creaNuovoPuntino(double SCREEN_WIDTH, double SCREEN_HEIGHT, GraphicsContext gc, List<Puntino> puntini) {
        double x = Math.random() * SCREEN_WIDTH;
        double y = Math.random() * SCREEN_HEIGHT;
        double s = Math.random() * 0.5;
        double a = Math.random() * Math.PI * 2; // In radianti
        Color c = Color.rgb((int) a, (int) s, (int) a);
        Puntino p = new Puntino(x,y,s,a,c);
        p.render(gc);
        puntini.add(p);
        System.out.println("New "+p);
    }
    public static void main(String[] args) {
        launch();
    }

}