package com.example.dino.Sprites;

import com.example.dino.AnimationFrames;
import com.example.dino.Position;
import com.example.dino.Speed;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.Arrays;

public class Sprite {
    private Position pos;
    private long counter = 0;

    private final double screenWidth;
    private final double screenHeight;

    private Speed speed;

    private final AnimationFrames aframes;
    private int frameNumber;

    private double scale = 1;

    public Sprite(Position pos, Speed speed, double screenWidth, double screenHeight,  AnimationFrames aframes, int frameNumber, double scale) {
        this.pos = pos;
        this.speed = speed;
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.aframes = aframes;
        this.frameNumber = frameNumber;
        this.scale = scale;
    }
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
        counter++;
        pos.update(speed);
    }

    public void cycleFrames(){
        int nof = aframes.getNumberOfFrames();

        System.out.println("frameNof:"+ nof+ "frameno:" + ((frameNumber+1>nof)?frameNumber+1:1));
        frameNumber++;
        if (frameNumber >= nof) frameNumber = 1;
        System.out.println(Arrays.toString(aframes.getFrame(frameNumber)));
    }

    public void render(GraphicsContext gc) {
        double[] vals = aframes.getFrame(frameNumber);
        Image img = aframes.getImg();
        gc.drawImage(img, vals[0], vals[1], vals[2], vals[3], pos.getX(), pos.getY(), vals[2]*scale, vals[3]*scale);
    }

    public boolean isOutOfBounds(){
        double x = pos.getX();
        double y = pos.getY();
        return (x<0 || x>screenWidth || y<0 || y>screenHeight);
    }
    protected long getCounter() {
        return counter;
    }
}
