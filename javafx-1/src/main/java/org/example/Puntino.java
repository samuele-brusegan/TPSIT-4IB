package org.example;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.ArrayList;
import java.util.List;

public class Puntino {
    double x;
    double y;
    double speed;
    double direction;
    Color color;
    List<double[]> positions;

    public Puntino(double x, double y, double speed, double direction, Color color) {
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.direction = direction;
        this.color = color;
        positions = new ArrayList<>();
    }

    public void render(GraphicsContext gc) {
        double x_b = x;
        double y_b = y;

        x = x_b + Math.cos(direction) * speed;
        y = y_b + Math.sin(direction) * speed;

        while (!gc.getCanvas().getLayoutBounds().contains(this.x, this.y)) {
            x = x_b + Math.cos(direction) * speed;
            y = y_b + Math.sin(direction) * speed;
            direction += Math.PI/4;
        }
        positions.add(new double[]{x,y});
        if (positions.size() > 500) positions.remove(0);

        double j = 0;
        double mult = 0.02;
        for (int i = positions.size()-1; i >= 0 && i >= positions.size()-(10/(mult*2)); i--) {

            double ix = positions.get(i)[0];
            double iy = positions.get(i)[1];

            gc.setFill(color.deriveColor(0,0,0,1.0-(j/2)));
            gc.fillOval(ix, iy, 10-j, 10-j);
            j+=mult;
        }


    }

    @Override
    public String toString() {
        return "Puntino{" +
                "x=" + x +
                ", y=" + y +
                ", speed=" + speed +
                ", direction=" + direction +
                ", color=" + color +
                '}';
    }
}
