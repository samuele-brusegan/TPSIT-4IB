package com.example.dino;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Sprite {
    private Position pos;

    private final double screenWidth;
    private final double screenHeight;

    private Speed speed;

    private final AnimationFrames aframes;
    private int frameNumber;

    public Sprite(Position pos, Speed speed, double screenWidth, double screenHeight,  AnimationFrames aframes, int frameNumber) {
        this.pos = pos;
        this.speed = speed;
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.aframes = aframes;
        this.frameNumber = frameNumber;
    }

    public Speed getSpeed() {
        return speed;
    }

    public void setSpeed(Speed speed) {
        this.speed = speed;
    }

    public Position getPos() {
        return pos;
    }

    public void setPos(Position pos) {
        this.pos = pos;
    }

    public void update() {
        pos.update(speed);
    }

    public void render(GraphicsContext gc) {
        double[] vals = aframes.getFrame(frameNumber);
        Image img = aframes.getImg();
        gc.drawImage(img, vals[0], vals[1], vals[2], vals[3], pos.getX(), pos.getY(), img.getWidth(), img.getHeight());
    }
}
